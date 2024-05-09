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

export async function init() {
    const userList = await applyService.getUserList();
    createSelectors('approvers', userList);
    createSelectors('references', userList);
}

init();
