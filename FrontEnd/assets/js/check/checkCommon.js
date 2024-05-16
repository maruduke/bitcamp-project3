import { checkService } from '../apis/checkAPI.js';

const element = {
    approve: document.querySelector('#approve'),
    deny: document.querySelector('#deny'),
};

const params = new URL(location.href).searchParams;

const documentId = params.get('documentId');

element.approve.addEventListener('click', () => {
    const req = {
        documentId: documentId,
        approvalState: true,
    };

    checkService.sign(req, () => {
        alert('결재가 승인되었습니다.');
        window.location.href = 'http://localhost:3200/approve/main';
    });
});

element.deny.addEventListener('click', () => {
    console.log(documentId);

    const req = {
        documentId: documentId,
        approvalState: false,
    };

    console.log(req);
    checkService.sign(req, () => {
        alert('결재가 거부 되었습니다.');
        window.location.href = 'http://localhost:3200/approve/main';
    });
});
