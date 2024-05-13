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
            .then((res) => defaultExceptionHandler(res))
            .then((res) => callback(res));
    },

    // 템플릿 임시저장
    tempStoreTemplate: async function (template, callback) {
        fetch('http://localhost:8080/sign/temporaryStorage', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json',
                authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(template),
        })
            .then((res) => defaultExceptionHandler(res))
            .then((res) => callback(res));
    },

    getTemporary: async function (callback) {
        fetch('http://localhost:8080/sign/getTemporary', {
            method: 'GET',
            headers: {
                authorization: `Bearer ${jwt}`,
            },
        })
            .then((res) => {
                if (res.status != 200) {
                    throw new exception('template 생성 오류 발생');
                }
                return res;
            })
            .then((res) => res.json())
            .then((res) => callback(res))
            .catch(() => {
                console.log('임시 저장된 파일에 오류 발생함 뭔지는 모름, 없거나 여러개 들어가 있을 수도?');
            });
    },
};
