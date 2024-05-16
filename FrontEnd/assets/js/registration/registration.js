document.addEventListener('DOMContentLoaded', () => {
    const jwt = sessionStorage.getItem('jwt');

    fetch('http://localhost:8080/login/joinget', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'authorization': `Bearer ${jwt}`
            }
        })
            .then(response => {
                if (response.status==401) {
                    alert('로그인이 필요합니다.')
                    window.location.href = 'http://localhost:3200/login';
                }else if(response.status==403){
                alert('권한이 없습니다.');
                window.location.href = 'http://localhost:3200/';
                }
            })

            .catch(error => {
                console.error('Fetch Error:', error);
            });

    document.querySelector('.btn').addEventListener('click', () => {
        const inputName = document.getElementById('name').value;
        const inputEmail = document.getElementById('email').value;
        const inputPwd = document.getElementById('password').value;
        const inputTel = document.getElementById('tel').value;
        const inputDept = document.getElementById('dept').value;
        const inputPosition = document.getElementById('position').value;

        // 경고 메시지를 담을 배열 초기화
        const errorMessages = [];

        // 전화번호가 숫자로만 구성되었는지 확인
        if (!/^\d+$/.test(inputTel)) {
            errorMessages.push('전화번호는 숫자로만 입력해주세요.');
        }

        // 부서가 유효한지 확인
        const validDepartments = ['인사', '개발', '영업', '운영', '디자인'];
        if (!validDepartments.includes(inputDept)) {
            errorMessages.push('올바른 부서를 입력해주세요.');
        }

        // 직위가 유효한지 확인
        const validPositions = ['CEO', '팀장', '과장', '대리', '주임', '사원', '인턴'];
        if (!validPositions.includes(inputPosition)) {
            errorMessages.push('올바른 직위를 입력해주세요.');
        }

        // 모든 조건을 검사한 후에 경고 메시지를 표시
        if (errorMessages.length > 0) {
            alert(errorMessages.join('\n')); // 배열에 있는 메시지를 줄바꿈으로 연결하여 표시
            return;
        }

        // 유효한 값이면 서버로 전송
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
