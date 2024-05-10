import { getApproveList, getReferenceList, initAsnc } from './applyCommon.js';
import { applyService } from '../apis/applyAPI.js';

const element = {
    startDate: document.querySelector('#startDate'),
    endDate: document.querySelector('#endDate'),
    destination: document.querySelector('#destination'),
    reason: document.querySelector('#reason'),
    submit: document.querySelector('#submit'),
};

element.submit.addEventListener('click', () => {
    const approves = getApproveList();
    const references = getReferenceList();

    const template = {
        type: 'BUSSINESSTRIP',
        refList: references,
        approverList: approves,
        startDate: element.startDate.value,
        endDate: element.endDate.value,
        location: element.destination.value,
        reason: element.reason.value,
    };

    console.log(template);

    applyService.createTemplate(template, () => {
        alert('결재 신청이 완료되었습니다.');
        location.href = 'http://localhost:3200/approve/main';
    });
});

initAsnc();
