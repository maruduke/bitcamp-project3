import { defaultExceptionHandler } from './exceptHandler.js';

const jwt = sessionStorage.getItem('jwt');

export const applyService = {
    // User 리스트 가져오기
    getUserList: async function (callback) {
        const users = await fetch('http://localhost:8080/sign/getUserList', {
            headers: {
                authorization: `Bearer ${jwt}`,
            },
        }).then((res) => defaultExceptionHandler(res));

        return users.json();
    },

    // 템플릿 생성하기
    createTemplate: async function (template, callback) {
        fetch('http://localhost:8080/sign/create', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json',
                authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(template),
        })
            .then((res) => {
                (res) => defaultExceptionHandler(res);
            })
            .then((res) => callback());
    },
};
