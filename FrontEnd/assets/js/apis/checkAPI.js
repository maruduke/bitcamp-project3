import { defaultExceptionHandler } from './exceptHandler.js';

const jwt = sessionStorage.getItem('jwt');

export const checkService = {
    getTemplate: (callback) => {
        const urlParams = new URL(location.href).searchParams;

        const documentId = urlParams.get('documentId');
        fetch(`http://localhost:8080/board/read?documentId=${documentId}`, {
            headers: {
                authorization: `Bearer ${jwt}`,
            },
        })
            .then((res) => defaultExceptionHandler(res))
            .then((res) => res.json())
            .then((res) => callback(res));
    },

    sign: (status, callback) => {
        fetch('http://localhost:8080/sign/approve', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(status),
        })
            .then((res) => defaultExceptionHandler(res))
            .then((res) => res.JSON)
            .then((res) => callback(res));
    },
};
