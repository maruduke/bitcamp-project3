import { exceptionHandler } from './exceptHandler.js';

const url = 'http://localhost:8080/sign';

const signService = {
    createTemplate: async function (jwt, templateDto) {
        return fetch(`${url}/create`, {
            method: 'POST',
            headers: {
                authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(templateDto),
        });
    },

    approve: async function (jwt, approveDto) {
        return fetch(`${url}/approve`, {
            method: 'POST',
            headers: {
                authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(approveDto),
        });
    },

    getTemporary: async function (jwt) {
        return fetch(`${url}/getTemporary`, {
            method: 'POST',
            headers: {
                authorization: `Bearer ${jwt}`,
            },
        });
    },

    temporaryStorage: async function (jwt, templateDto) {
        return fetch(`${url}/temporaryStorage`, {
            method: 'POST',
            headers: {
                authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(templateDto),
        }).then((res) => exceptionHandler(res));
    },
};

signService.temporaryStorage('', 'test');
