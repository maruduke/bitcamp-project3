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
 * User 정보 DB에 가져와서 Select box생성
 */
export async function initAsnc() {
    const userList = await applyService.getUserList();
    createSelectors('approvers', userList);
    createSelectors('references', userList);
}

/**
 *
 * @returns 결재자 명단
 */
export const getApproveList = () => {
    let approves = [];

    document.querySelectorAll('.approvers').forEach((app) => {
        if (app.value != '') approves.push(app.value);
    });

    return approves;
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

    return refs;
};

/**
 * 돌아가기 버튼 eventListener 추가
 */
document.querySelector('#return').addEventListener('click', () => {
    location.href = 'http://localhost:3200/approve/main';
});
