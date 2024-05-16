package com.example.backend.service;

import com.example.backend.dto.sign.ApproveDto;
import com.example.backend.dto.sign.UserDto;
import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.Ref;
import com.example.backend.entity.maria.TaskProgress;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class SignService {

    private final DocumentRepository documentRepository;
    private final TemplateRepository templateRepository;
    private final TaskProgressRepository taskProgressRepository;
    private final RefRepository refRepository;
    private final UserRepository userRepository;

    // MongoDB template ID값 반환

    /**
     * 작성한 휴가, 출장, 보고서, 경비 문서를 MongoDB(문서 저장), Document(문서 메타 정보) \n
     * task_progress(진행 상황 저장소)에 저장
     * 
     * @param templateDto   휴가, 출장, 보고서, 경비 Template 입력
     * @return String   MongoDB에 저장된 Template ObjectId값 반환
     */
    public String saveTemplate(TemplateDto templateDto) {

        LocalDate createDate = LocalDate.now();


        List<User> refList = userRepository.findAllByEmailOrderByField(templateDto.getRefList()).orElseThrow(() -> new IllegalArgumentException("없는 참조가 있음"));
        List<User> approveList = userRepository.findAllByEmailOrderByField(templateDto.getApproverList()).orElseThrow(() -> new IllegalArgumentException("없는 참조가 있음"));

        List<Long> sortedRefList = templateDto.getRefList().stream().map( (email) -> {
            log.info(email);
            for(User user : refList) {
                log.info(user);
                if(user.getEmail().equals(email))
                    return user.getUserId();
            }
                    return null;
                }
        ).toList();

        List<Long> sortedApproveList = templateDto.getApproverList().stream().map( (email) -> {
                    for(User user : approveList) {
                        if(user.getEmail().equals(email))
                            return user.getUserId();
                    }
                    return null;
                }
        ).toList();


        log.info(approveList);

        // mongodb 템플릿 저장
        Template<? extends TypeData> template = templateDto.toTemplateEntity(sortedApproveList, sortedRefList);
        templateRepository.save(template);

        log.info(template);

        // Document Table 템플릿 메타 정보 저장
        Document document = templateDto.toDocumentEntity(template.getId(), createDate);
        documentRepository.save(document);

        // TaskProgress Table 결재 진행 상황 저장
        TaskProgress taskProgressApprover = TaskProgress.builder()
                .documentId(template.getId())
                .state(DocState.PROCESS_1)
                .ref_user_id(template.getApproverList().get(0))
                .build();

        // 작성자 확인용 결재 진행 상황 저장
        TaskProgress taskProgressWriter = TaskProgress.builder()
                .documentId(template.getId())
                .state(DocState.PROCESS_1)
                .ref_user_id(templateDto.getWriter())
                .build();

        taskProgressRepository.save(taskProgressApprover);
        taskProgressRepository.save(taskProgressWriter);

        return template.getId();
    }

    /**
     * 결재 문서 승인 기능
     * @param user 결재자 정보
     * @param approveDto 결재 정보: 문서 id, 결재 여부(True, False)
     */
    @Transactional
    public void approve(User user, ApproveDto approveDto) {
        
        // 문서 가져오기
        Template template = templateRepository.findById(approveDto.getDocumentId()).orElseThrow(() -> new IllegalArgumentException("template is not exist"));

        List<Long> approveList = template.getApproverList();
        List<Boolean> approveCheckList = template.getApproverCheckList();

        int index = approveCheckList.size();

        // 결재 체크 리스트를 확인하여 결재 권한이 있는지 확인
        if(user.getUserId() != approveList.get(index))
            throw new IllegalArgumentException("현재 결재 승인 권한이 존재하지 않습니다.");

        // 결재 거부일 경우
        if(approveDto.getApprovalState() == false)
            documentDeny(template, approveCheckList);

        // 결재 승인일 경우
        if(approveDto.getApprovalState() == true)
            documentApprove(template, approveCheckList);

    }


    /**
     * 결재 거부
     * @param template
     * @param approveCheckList
     */
    @Transactional
    public void documentDeny(Template template, List<Boolean> approveCheckList) {
        approveCheckList.add(false);
        template.updateCheckList(approveCheckList);

        // approveCheckList template 수정
        templateRepository.save(template);

        // document 메타 정보 상태 deny로 수정
        Document document = documentRepository.findById(template.getId()).orElseThrow(() -> new IllegalArgumentException("문서 정보가 없습니다."));
        document.updateState(DocState.DENY);

        // task_progress에서 삭제
        taskProgressRepository.deleteAllByDocumentId(template.getId());

        // Ref table에서 작성자 참조 상태 deny 설정 - 다른 참조자 결재자는 설정 안함.
        Ref ref = Ref.builder()
                .documentId(template.getId())
                .state(DocState.DENY)
                .refUserId(template.getWriter())
                .build();

        refRepository.save(ref);

    }

    /**
     * 결재 승인
     * @param template
     * @param approveCheckList
     */
    @Transactional
    public void documentApprove(Template template, List<Boolean> approveCheckList) {
        approveCheckList.add(true);
        template.updateCheckList(approveCheckList);

        // approveCheckList template 수정
        templateRepository.save(template);

        // 결재 완료
        if (template.getApproverList().size() == approveCheckList.size()) {
            // document 메타 정보 상태 Complete로 수정
            Document document = documentRepository.findById(template.getId()).orElseThrow(() -> new IllegalArgumentException("문서 정보가 없습니다."));
            document.updateState(DocState.COMPLETE);

            // task_progress 테이블에서 삭제
            taskProgressRepository.deleteAllByDocumentId(template.getId());

            // 참조자에게 참조 지정
            List<Long> refList = template.getRefList();
            refList.stream().forEach((ref_id) -> {
                Ref ref = Ref.builder()
                        .documentId(template.getId())
                        .state(DocState.REFERENCE)
                        .refUserId(ref_id)
                        .build();
                refRepository.save(ref);
            });

            // 작성자에게 참조 지정
            Ref ref = Ref.builder()
                    .documentId(template.getId())
                    .state(DocState.COMPLETE)
                    .refUserId(template.getWriter())
                    .build();

            refRepository.save(ref);
        }
        // 결재 진행 중
        else {
            
            // document 메타 정보 상태 수정
            Document document = documentRepository.findById(template.getId()).orElseThrow(() -> new IllegalArgumentException("문서 정보가 없습니다."));
            DocState docState = changeState(template.getApproverCheckList().size());
            
            document.updateState(docState);

            // task_progress 테이블에서 삭제
            taskProgressRepository.deleteAllByDocumentId(template.getId());

            // task_progress 결재자, 작성자 내용 저장
            TaskProgress taskProgressWriter = TaskProgress.builder()
                    .documentId(template.getId())
                    .state(docState)
                    .ref_user_id(template.getWriter())
                    .build();

            TaskProgress taskProgressApproval = TaskProgress.builder()
                    .documentId(template.getId())
                    .state(docState)
                    .ref_user_id((Long) template.getApproverList().get(template.getApproverCheckList().size()))
                    .build();

            taskProgressRepository.save(taskProgressWriter);
            taskProgressRepository.save(taskProgressApproval);

        }
    }

    /**
     * 결재 상태 반환
     * @param check 현재 결재자 수
     * @return DocState 문서 현재 상태
     */
    public DocState changeState(int check) {
        if(check == 1)
            return DocState.PROCESS_2;
        if(check == 2)
            return DocState.PROCESS_3;
        
        throw new IllegalArgumentException("결재자 오류");
    }


    /**
     * 템플릿 임시 저장
     * @param user
     * @param templateDto
     */
    public void temporaryStorage(User user,TemplateDto templateDto) {

        List<Long> refList = userRepository.findAllByEmailOrderByField(templateDto.getRefList()).orElseThrow(() -> new IllegalArgumentException("없는 참조가 있음"))
                .stream().map(u -> u.getUserId()).toList();
        List<Long> approveList = userRepository.findAllByEmailOrderByField(templateDto.getApproverList()).orElseThrow(() -> new IllegalArgumentException("없는 참조가 있음"))
                .stream().map(u -> u.getUserId()).toList();


        // mongodb 템플릿 저장
        Template<? extends TypeData> template = templateDto.toTemplateEntity(approveList, refList);
        templateRepository.save(template);


        // Document Table 템플릿 메타 정보는 저장
        Document document = templateDto.toDocumentEntity(template.getId(), LocalDate.now());
        log.info(document);
        document.updateState(DocState.TEMPORARY);

        documentRepository.save(document);

        // TaskProgress에 작성 상태 저장
        TaskProgress taskProgress = TaskProgress.builder()
                .documentId(template.getId())
                .state(DocState.TEMPORARY)
                .ref_user_id(user.getUserId())
                .build();

        taskProgressRepository.save(taskProgress);
    }

    /**
     * 임시 저장 템플릿 가져오기 + 삭제
     * @param user
     * @return 저장된 임시저장 템플릿 반환
     */
    @Transactional
    public Template getTemporaryStorage(User user) {

        // 임시파일 저장 확인
        TaskProgress taskProgress = taskProgressRepository.findByRefUserIdAndState(user.getUserId(), DocState.TEMPORARY)
                .orElseThrow(() -> new IllegalArgumentException("임시 저장 파일이 없습니다."));
        Document document = documentRepository.findById(taskProgress.getDocumentId()).orElseThrow(() -> new IllegalArgumentException("임시 저장 파일이 없습니다."));

        // template 타입 지정
        Template< ? extends TypeData> template = templateRepository.findById(taskProgress.getDocumentId()).orElse(null);


        // 임시파일 삭제
        templateRepository.delete(template);
        documentRepository.deleteById(template.getId());
        taskProgressRepository.deleteByDocumentId(taskProgress.getDocumentId());


        return template;

    }


    @Transactional(readOnly = true)
    public List<UserDto> getUserList() {

        List<UserDto> users = userRepository.findAll().stream().map(user -> new UserDto(user)).toList();

        return users;
    }

}