const jwt = sessionStorage.getItem('jwt');

export const applyService = {
    getUserList: async function (callback) {
        const users = await fetch('http://localhost:8080/sign/getUserList', {
            headers: {
                authorization: `Bearer ${jwt}`,
            },
        });

        return users.json();
    },

    createTemplate: async function (template, callback) {
        fetch('http://localhost:8080/sign/create', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json',
                authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(template),
        })
            .then((res) => {
                if (res.status != 200) throw alert('템플릿 저장 에러 발생');
            })
            .then((res) => callback());
    },
};
