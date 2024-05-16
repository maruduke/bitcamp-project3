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

            let img_src;

            if (data.position == 'CEO') img_src = 'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/CEO.png';
            if (data.position == '대리')
                img_src = 'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/%EB%8C%80%EB%A6%AC.png';
            if (data.position == '팀장')
                img_src = 'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/%ED%8C%80%EC%9E%A5.png';
            if (data.position == '과장')
                img_src = 'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/%EA%B3%BC%EC%9E%A5.png';
            if (data.position == '주임')
                img_src = 'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/%EC%A3%BC%EC%9E%84.png';
            if (data.position == '사원')
                img_src = 'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/%EC%82%AC%EC%9B%90.png';
            if (data.position == '인턴')
                img_src = 'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/%EC%9D%B8%ED%84%B4.png';

            document.querySelector('#image').src = img_src;
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });

    document.querySelector('#image').src =
        'https://kr.object.ncloudstorage.com/lgs-20240411-bucket/test/declan-sun-2aZlAV53kww-unsplash.jpg';
});
