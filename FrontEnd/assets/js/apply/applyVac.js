// myPageModify.js
document.addEventListener('DOMContentLoaded', () => {
    const fakeUserData = [
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "야스오", position: "추방자"},
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융20", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "이경석", position: "사장" },
        { name: "안슬기", position: "팀장" },
        { name: "최병민", position: "부장" },
        { name: "김융", position: "사원" },
        { name: "둘리", position: "트롤"},
        { name: "물티슈",position: "청결"}
    ];

    const createSelect = () => {
        const select = document.createElement('select');
        select.className = 'select';
        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.selected = true;
        defaultOption.disabled = true;
        defaultOption.textContent = '선택';
        select.appendChild(defaultOption);
        fakeUserData.forEach(user => {
            const option = document.createElement('option');
            option.value = `${user.name} ${user.position}`;
            option.textContent = `${user.name} ${user.position}`;
            select.appendChild(option);
        });
        return select;
    };

    const createSelectors = (containerId) => {
        const container = document.getElementById(containerId);
        for (let i = 0; i < 3; i++) {
            container.appendChild(createSelect());
        }
    };

    createSelectors('approvers');
    createSelectors('references');
});
