import { getApproveList, getReferenceList, initAsnc } from './applyCommon.js';
import { applyService } from '../apis/applyAPI.js';

const element = {
    title: document.querySelector('#title'),
    detail: document.querySelector('#detail'),
    submit: document.querySelector('#submit'),
};

element.submit.addEventListener('click', () => {
    const approves = getApproveList();
    const references = getReferenceList();

    const template = {
        type: 'REPORT',
        refList: references,
        approverList: approves,
        title: title.value,
        detail: detail.value,
    };
});

initAsnc();
