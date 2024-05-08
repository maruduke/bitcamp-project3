import { loginExceptionHandler } from './exceptHandler.js';

export const loginService = {
    login: async function (loginDto, callback) {
        return fetch(`http://localhost:8080/login/post`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginDto),
        })
            .then((res) => {
                return loginExceptionHandler(res);
            })
            .then((res) => res.json())
            .then((res) => {
                // session storage에 jwt 저장
                sessionStorage.setItem('jwt', res['atk']);
            })
            .then(() => callback());
    },
};

export class loginDto {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }
}
