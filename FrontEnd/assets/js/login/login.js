import { loginService, loginDto } from '../apis/loginAPI.js';

// 로그인이 되어있다면 로그인 페이지로 이동할 수 없도록 처리
const jwt = sessionStorage.getItem('jwt');
if (!jwt) {
} else {
    fetch('http://localhost:8080/login/get', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'authorization': `Bearer ${jwt}`
        }
    })
        .then(response => {
            if(response.ok){
            alert('이미 로그인 상태입니다.');
            window.location.href = 'http://localhost:3200/';
            }
        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
}

const element = {
    email: document.querySelector('#email'),
    password: document.querySelector('#password'),
    login_button: document.querySelector('.btn'),
};

const login = () => {
    const email = element.email.value;
    const password = element.password.value;

    loginService.login(new loginDto(email, password), () => {
        window.location.href = 'http://localhost:3200/';
    });
};

// 로그인 버튼 클릭 이벤트
element.login_button.addEventListener('click', login);

// Enter 키 누를 때 로그인 실행
document.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        login();
    }
});