export const loginExceptionHandler = (res) => {
    if (res.status == 401) {
        // window.location.href = '../../../login/login.html';
        alert('로그인이 필요합니다.');
        window.location.href = 'http://localhost:3200/login';
    }

    if (!res.ok) alert('아이디 또는 비밀번호가 틀렸습니다. 다시 입력해주세요');

    return res;
};

export const defaultExceptionHandler = (res) => {
    if (res.status == 401) {
        window.location.href = 'http://localhost:3200/login';
    } else if (res.status != 200) {
        throw new exception('template 생성 오류 발생');
    }
    return res;
};
