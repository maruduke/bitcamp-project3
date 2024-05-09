// document.addEventListener('DOMContentLoaded', () => {
//     const jwt = sessionStorage.getItem('jwt');


//     const inputName = document.getElementById('name').value;
//     const inputEmail = document.getElementById('email').value;
//     const inputPwd = document.getElementById('password').value;
//     const inputTel = document.getElementById('tel').value;
//     const inputDept = document.getElementById('dept').value;
//     const inputPosition = document.getElementById('position').value;

//     document.querySelector('.btn').addEventListener('click', () => {

//     fetch('http://localhost:8080/login/join', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//             'authorization': `Bearer ${jwt}`
//         },
//         body: JSON.stringify({
//             name: inputName,
//             email: inputEmail,
//             password: inputPwd,
//             tel: inputTel,
//             dept: inputDept,
//             position: inputPosition
//         })
//     })
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error(`HTTP error! Status: ${response.status}`);
//             }
//             window.location.href = 'http://localhost:3200';
//         })

//         .catch(error => {
//             console.error('Fetch Error:', error);
//         });
//     });
// });

document.addEventListener('DOMContentLoaded', () => {
    const jwt = sessionStorage.getItem('jwt');

    document.querySelector('.btn').addEventListener('click', () => {
        const inputName = document.getElementById('name').value;
        const inputEmail = document.getElementById('email').value;
        const inputPwd = document.getElementById('password').value;
        const inputTel = document.getElementById('tel').value;
        const inputDept = document.getElementById('dept').value;
        const inputPosition = document.getElementById('position').value;

        fetch('http://localhost:8080/login/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify({
                name: inputName,
                email: inputEmail,
                password: inputPwd,
                tel: inputTel,
                dept: inputDept,
                position: inputPosition
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            alert('사원 등록을 완료했습니다!');
            window.location.href = 'http://localhost:3200';
        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
    });
});
