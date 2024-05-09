function includeHeader() {
	const xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://localhost:3200/common/header', true);
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.querySelector('header').innerHTML = xhr.responseText;

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

			// 로그아웃 버튼 클릭 이벤트 처리
			// document.getElementById('logoutButton').addEventListener('click', () => {
			// 	// 로그아웃 처리를 위한 코드 작성
			// 	// 예: 서버에 로그아웃 요청을 보내고 성공 시 로그인 페이지로 리다이렉트

			// 	const jwt = sessionStorage.getItem('jwt');
			// 	fetch('http://localhost:8080/login/logout', {
			// 		method: 'POST',
			// 		headers: {
			// 			'Content-Type': 'application/json',
			// 			'authorization': `Bearer ${jwt}`
			// 		}
			// 	})
			// 		.then(response => {
			// 			if (!response.ok) {
			// 				throw new Error(`HTTP error! Status: ${response.status}`);
			// 			}
			// 			return response.json();
			// 		})


			// 	window.location.href = 'http://localhost:3200/login';
			// });

			document.getElementById('logoutButton').addEventListener('click', () => {
				const jwt = sessionStorage.getItem('jwt');
				fetch('http://localhost:8080/login/logout', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'authorization': `Bearer ${jwt}`
					}
				})
				.then(response => {
					if (!response.ok) {
						throw new Error(`HTTP error! Status: ${response.status}`);
					}
					sessionStorage.removeItem('jwt'); // 세션 스토리지에서 토큰 제거
					alert('로그아웃 되었습니다!');
					window.location.href = 'http://localhost:3200/login'; // 로그인 페이지로 리다이렉트
				})
				.catch(error => {
					console.error('Logout Error:', error);
					// 로그아웃 처리 중에 오류가 발생했을 때 실행할 코드 작성
				});
			});
			
		}
	};
	xhr.send();

	return 'ok';

}

function includeFooter() {
	const xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://localhost:3200/common/footer', true);
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.querySelector('footer').innerHTML = xhr.responseText;

			// footer 코드 작성


		}
	};
	xhr.send();

	return 'ok';
}





includeHeader();
includeFooter();