export const init_header = () => {
    // header 코드
    // MEMBER 버튼 클릭 이벤트 처리
    document.getElementById('memberButton').addEventListener('click', () => {
        window.location.href = 'http://localhost:3200/member';
    });

    // 결재 버튼 클릭 이벤트 처리
    document.getElementById('approveButton').addEventListener('click', () => {
        window.location.href = 'http://localhost:3200/approve/main';
    });

    // MY PAGE 버튼 클릭 이벤트 처리
    document.getElementById('myPageButton').addEventListener('click', () => {
        window.location.href = 'http://localhost:3200/mypage/mypage';
    });

    // 로그아웃 버튼 클릭 시 세션 스토리지 토큰 제거
    document.getElementById('logoutButton').addEventListener('click', () => {
        const jwt = sessionStorage.getItem('jwt');
        fetch('http://localhost:8080/login/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                sessionStorage.removeItem('jwt'); // 세션 스토리지에서 토큰 제거
                alert('로그아웃 되었습니다!');
                window.location.href = 'http://localhost:3200/login'; // 로그인 페이지로 리다이렉트
            })
            .catch((error) => {
                console.error('Logout Error:', error);
            });
    });

}