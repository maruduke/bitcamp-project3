export const loginExceptionHandler = (res) => {
    if (res.status == 401) {
        // window.location.href = '../../../login/login.html';
        alert('아이디 비밀번호 둘 중 하나는 틀린 듯?');
        window.location.href = 'http://localhost:3200/login';
    }

    if (!res.ok) alert('로그인 에러 발생 뭔지는 몰라!');

    return res;
};
