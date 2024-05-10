import { loginService, loginDto } from '../apis/loginAPI.js';

const element = {
    email: document.querySelector('#email'),
    password: document.querySelector('#password'),
    login_button: document.querySelector('.btn'),
};

element.login_button.addEventListener('click', () => {
    const email = element.email.value;
    const password = element.password.value;

    loginService.login(new loginDto(email, password), () => {
        window.location.href = 'http://localhost:3200/';
    });
});
