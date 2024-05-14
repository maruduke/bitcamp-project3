import { applyService } from '../apis/applyAPI.js';

const createSelect = (userList, containerId) => {
    const select = document.createElement('select');
    select.className = 'select';
    select.classList.add(containerId);
    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.selected = true;
    defaultOption.disabled = true;
    defaultOption.textContent = '선택';
    select.appendChild(defaultOption);
    userList.forEach((user) => {
        const option = document.createElement('option');
        option.value = `${user.email}`;
        option.textContent = `${user.name} ${user.position}`;
        select.appendChild(option);
    });
    return select;
};

const createSelectors = (containerId, userList) => {
    const container = document.getElementById(containerId);
    for (let i = 0; i < 3; i++) {
        container.appendChild(createSelect(userList, containerId));
    }
};

/**
 *
 * @returns 결재자 명단
 */
export const getApproveList = () => {
    let approves = [];

    document.querySelectorAll('.approvers').forEach((app) => {
        if (app.value != '') approves.push(app.value);
    });

    return JSON.stringify(approves);
};

/**
 *
 * @returns 참조자 명단
 */
export const getReferenceList = () => {
    let refs = [];

    document.querySelectorAll('.references').forEach((ref) => {
        if (ref.value != '') refs.push(ref.value);
    });

    return JSON.stringify(refs);
};

/**
 * 임시 저장 다루기
 */

export async function temporary(callback) {
    console.log('temporary 실행 됨');

    if (localStorage.getItem('template') == null) {
        console.log('임시파일 저장되어 있지 않음');
        applyService.getTemporary((res) => {
            if (res == null) return;

            console.log(res);
            if (!confirm('임시저장된 파일이 있습니다. 불러오시겠습니가?')) {
                alert('왜 저장함?');
            } else {
                alert('저장하거 불러옴');
                localStorage.setItem('template', JSON.stringify(res));

                if (res['type'] == 'REPORT') location.href = 'http://localhost:3200/approve/apply/apply_report';
                else if (res['type'] == 'VACATION') location.href = 'http://localhost:3200/approve/apply/apply_vac';
                else if (res['type'] == 'BUSSINESSTRIP')
                    location.href = 'http://localhost:3200/approve/apply/apply_business';
                else if (res['type'] == 'ACCOUNTINGEXPENSE')
                    location.href = 'http://localhost:3200/approve/apply/apply_expense';
            }
        });
    } else {
        callback();
    }
}

/**
 * User 정보 DB에 가져와서 Select box생성
 */
export async function initAsnc() {
    const userList = await applyService.getUserList();
    createSelectors('approvers', userList);
    createSelectors('references', userList);

    /**
     * 돌아가기 버튼 eventListener 추가
     */
    document.querySelector('#return').addEventListener('click', () => {
        location.href = 'http://localhost:3200/approve/main';
    });
}
