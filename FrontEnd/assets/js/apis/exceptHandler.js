export const exceptionHandler = (res) => {
    if (res.status == 403) console.log('login direct');

    if (!res.ok) throw exception('error');

    return res;
};
