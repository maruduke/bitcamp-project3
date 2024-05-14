document.addEventListener('DOMContentLoaded', () => {
    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/login/mypage', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            authorization: `Bearer ${jwt}`,
        },
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            // 전화번호 출력 형식 변경(000-0000-0000)
            function formatPhoneNumber(phoneNumber) {
                const regex = /(\d{3})(\d{4})(\d{4})/;
                const formatted = phoneNumber.replace(regex, '$1-$2-$3');
                return formatted;
            }
            // 사번 출력 형식 변경(000X)
            function formatUserId(userId) {
                return String(userId).padStart(4, '0');
            }

            document.getElementById('userName').textContent = data.name;
            document.getElementById('userPosition').textContent = data.position;
            document.getElementById('userDept').textContent += data.dept;
            document.getElementById('userId').textContent += formatUserId(data.userId);
            document.getElementById('userEmail').textContent += data.email;
            document.getElementById('userPhone').textContent += formatPhoneNumber(data.tel);
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });

    document.querySelector('#image').src =
        'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/test/declan-sun-2aZlAV53kww-unsplash.jpg';
});
