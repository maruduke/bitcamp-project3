import { getApproveList, getReferenceList, initAsnc } from './applyCommon.js';
import { applyService } from '../apis/applyAPI.js';

const element = {
    title: document.querySelector('#title'),
    category: document.querySelector('#category'),
    cost: document.querySelector('#cost'),
    expenseDate: document.querySelector('#expenseDate'),
    reason: document.querySelector('#reason'),
    submit: document.querySelector('#submit'),
    tempStore: document.querySelector('#tempStore'),
};

const createTemplateData = () => {
    const approves = getApproveList();
    const references = getReferenceList();

    const template = {
        type: 'ACCOUNTINGEXPENSE',
        refList: references,
        approverList: approves,
        title: element.title.value,
        category: element.category.value,
        cost: element.cost.value,
        expenseDate: element.expenseDate.value,
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

initAsnc();
