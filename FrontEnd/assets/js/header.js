
    // MEMBER 버튼 클릭 이벤트 처리
    document.getElementById('memberButton').addEventListener('click', () => {
        window.location.href = 'http://localhost:3200/member';
    });

    // 결재 버튼 클릭 이벤트 처리
    document.getElementById('approveButton').addEventListener('click', () => {
        window.location.href = '/approve/main';
    });

    // MY PAGE 버튼 클릭 이벤트 처리
    document.getElementById('myPageButton').addEventListener('click', () => {
        window.location.href = '/mypage/mypage';
    });

    // 로그아웃 버튼 클릭 이벤트 처리
    document.getElementById('logoutButton').addEventListener('click', () => {
        // 로그아웃 처리를 위한 코드 작성
        // 예: 서버에 로그아웃 요청을 보내고 성공 시 로그인 페이지로 리다이렉트
        window.location.href = '/login';
    });
