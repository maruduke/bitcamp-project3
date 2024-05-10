document.addEventListener('DOMContentLoaded', () => {
    const jwt = sessionStorage.getItem('jwt');

    document.getElementById('joinbtn').addEventListener('click', () => {



        fetch('http://localhost:8080/login/joinget', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'authorization': `Bearer ${jwt}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    alert('권한이 없습니다.')
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                alert('권한자입니다.');
                window.location.href = 'http://localhost:3200/registration';
            })

            .catch(error => {
                console.error('Fetch Error:', error);
            });
    });
});