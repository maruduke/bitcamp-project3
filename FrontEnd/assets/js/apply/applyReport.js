import { getApproveList, getReferenceList, initAsnc, temporary } from './applyCommon.js';
import { applyService } from '../apis/applyAPI.js';

const element = {
    title: document.querySelector('#title'),
    detail: document.querySelector('#detail'),
    submit: document.querySelector('#submit'),
    tempStore: document.querySelector('#tempStore'),
};

const createTemplateData = () => {
    const approves = getApproveList();
    const references = getReferenceList();

    const template = {
        type: 'REPORT',
        refList: references,
        approverList: approves,
        title: title.value,
        detail: detail.value,
    };

    console.log(JSON.stringify(template));
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
    element.detail.value = tmpData['typeData']['detail'];

    localStorage.removeItem('template');
});

initAsnc();
