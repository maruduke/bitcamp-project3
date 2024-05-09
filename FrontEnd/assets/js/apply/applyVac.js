import './applyCommon.js';
import { applyService } from '../apis/applyAPI.js';

const element = {
    submit: document.querySelector('#submit'),
    title: document.querySelector('#title'),
    startDate: document.querySelector('#startDate'),
    endDate: document.querySelector('#endDate'),
    reason: document.querySelector('#reason'),
    return: document.querySelector('#return'),
};

element.submit.addEventListener('click', () => {
    let approves = [];
    let refs = [];

    document.querySelectorAll('.approvers').forEach((app) => {
        approves.push(app.value);
    });

    document.querySelectorAll('.references').forEach((ref) => {
        refs.push(ref.value);
    });

    const template = {
        type: 'VACATION',
        title: element.title.value,
        startDate: element.startDate.value,
        endDate: element.endDate.value,
        reason: element.reason.value,
        refList: refs,
        approverList: approves,
    };

    applyService.createTemplate(template, () => {
        location.href = 'http://localhost:3200/approve/main';
    });
});

element.return.addEventListener('click', () => {
    location.href = 'http://localhost:3200/approve/main';
});
