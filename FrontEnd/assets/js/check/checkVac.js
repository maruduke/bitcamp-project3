import { checkService } from '../apis/checkAPI.js';

checkService.getTemplate((res) => {
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

    console.log(res);

    document.getElementById('checkTitle').textContent = res['typeData']['title'];
    document.getElementById('userNamePosition').textContent = res['writer'];
    document.getElementById('checkContext').textContent = res['typeData']['reason'];
    document.getElementById('startDate').textContent = res['typeData']['startDate'];
    document.getElementById('endDate').textContent = res['typeData']['endDate'];

    document.getElementById('signMember').textContent = approvers.slice(0, -2);
    document.getElementById('refMember').textContent = refs.slice(0, -2);
    document.getElementById('createdDate').textContent = res['createDate'];
});
