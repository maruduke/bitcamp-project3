import { getApproveList, getReferenceList, initAsnc, temporary } from './applyCommon.js';
import { applyService } from '../apis/applyAPI.js';

const element = {
    title: document.querySelector('#title'),
    startDate: document.querySelector('#startDate'),
    endDate: document.querySelector('#endDate'),
    destination: document.querySelector('#destination'),
    reason: document.querySelector('#reason'),
    submit: document.querySelector('#submit'),
    tempStore: document.querySelector('#tempStore'),
};

/**
 * 전송 템플릿 데이터 직렬화
 */
const createTemplateData = () => {
    const approves = getApproveList();
    const references = getReferenceList();

    const template = {
        title: element.title.value,
        type: 'BUSSINESSTRIP',
        refList: references,
        approverList: approves,
        startDate: element.startDate.value,
        endDate: element.endDate.value,
        location: element.destination.value,
        reason: element.reason.value,
    };

    return template;
};

element.submit.addEventListener('click', () => {
    const template = createTemplateData();

    applyService.createTemplate(template, () => {
        alert('결재 신청이 완료되었습니다.');
        location.href = 'http://localhost:3200/approve/main';
    });
});

element.tempStore.addEventListener('click', () => {
    const template = createTemplateData();

    applyService.tempStoreTemplate(template, () => {
        alert('임시 저장 되었습니다.');
        location.href = 'http://localhost:3200/approve/main';
    });
});

temporary(() => {
    const tmpData = JSON.parse(localStorage.getItem('template'));

    element.title.value = tmpData['typeData']['title'];
    element.startDate.value = tmpData['typeData']['startDate'];
    element.endDate.value = tmpData['typeData']['endDate'];
    element.destination.value = tmpData['typeData']['location'];
    element.reason.value = tmpData['typeData']['reason'];

    localStorage.removeItem('template');
});
initAsnc();
