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
            function formatUserId(userId) {
                return String(userId).padStart(4, '0');
            }

            document.getElementById('userName').textContent = data.name;
            document.getElementById('userPosition').textContent = data.position;
            document.getElementById('userDept').textContent += data.dept;
            document.getElementById('userId').textContent += formatUserId(data.userId);
            document.getElementById('userEmail').textContent += data.email;

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

            // 수정 버튼 클릭 시 서버에 변경된 정보 전송(현재 전화번호만 변경 가능)
            document.getElementById('saveChanges').addEventListener('click', () => {
                const updatedPhone = document.getElementById('userPhone').value;

                // 전화번호 입력은 숫자만 가능하도록
                const numberRegex = /^\d+$/;
                if (!numberRegex.test(updatedPhone)) {
                    alert('전화번호는 숫자만 입력할 수 있습니다.');
                    return; // 숫자가 아닌 경우 함수 종료
                }

                // 서버에 변경된 정보 전송
                fetch('http://localhost:8080/login/mypage/modify', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        authorization: `Bearer ${jwt}`,
                    },
                    body: JSON.stringify({ tel: updatedPhone }),
                })
                    .then((response) => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                        alert('수정이 완료되었습니다!');
                        window.location.href = 'http://localhost:3200/mypage';
                    })
                    .catch((error) => {
                        console.error('Save Changes Error:', error);
                    });
            });
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
});
