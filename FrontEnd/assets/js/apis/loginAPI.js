const url = 'http://localhost:8080/login';

const loginService = {
    login: async function (loginDto) {
        return fetch(`${url}/post`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginDto),
        })
            .then((res) => {
                if (!res.ok) throw exception('error');
            })
            .then((res) => res.json())
            .then((res) => {
                // session storage에 jwt 저장
                sessionStorage.setItem('jwt', res['atk']);
            });
    },
};

class loginDto {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }
}

async function login() {
    loginService.login(new loginDto('sgw0816@naver.com', '1234'));
}

login();
