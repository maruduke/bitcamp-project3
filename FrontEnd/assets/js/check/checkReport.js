import { checkService } from '../apis/checkAPI.js';

checkService.getTemplate((res) => {
    console.log(res);

    let refs = '';
    let approvers = '';

    const refsList = res['refList'];
    const approversList = res['approverList'];

    refsList.forEach((ref) => {
        refs += `${ref} / `;
    });
    approversList.forEach((approver) => {
        approvers += `${approver} / `;
    });

    document.querySelector('#title').innerHTML = res['typeData']['title'];
    document.querySelector('#writer').innerHTML = res['writer'];
    document.querySelector('#approversList').innerHTML = approvers.slice(0, -2);
    document.querySelector('#refList').innerHTML = refs.slice(0, -2);
    document.querySelector('#detail').innerHTML = res['typeData']['detail'];
});
