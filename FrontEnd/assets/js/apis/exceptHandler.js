export const exceptionHandler = (res) => {
    if (res.status == 403) {
        // window.location.href = '../../../login/login.html';
        window.location.href = 'http://localhost:3200/login';
    }

    if (!res.ok) throw exception('error');

    return res;
};
