
document.addEventListener('DOMContentLoaded', () => {

    //임시 데이터
    const fakeUserData = {
        userId: 2,
        email: "sgw0816@naver.com",
        position: "대리",
        dept: "인사과",
        name: "서주원",
        tel: "01011112222"
    };


    document.getElementById('userName').textContent = fakeUserData.name;
    document.getElementById('userPosition').textContent = fakeUserData.position;
    document.getElementById('userDept').textContent += fakeUserData.dept;
    document.getElementById('userId').textContent += fakeUserData.userId;
});


// fetch('http://localhost:8080/userData')
//     .then(response => {
//         if (!response.ok) {
//             throw new Error(`HTTP error! Status: ${response.status}`);
//         }
//         return response.json();
//     })
//     .then(data => {     
//         document.getElementById('userDept').textContent = data.dept;
//         document.getElementById('userId').textContent = data.userId;
//         document.getElementById('userEmail').textContent = data.email;
//         document.getElementById('userPhone').textContent = data.tel;
//     })
//     .catch(error => {
//         console.error('Fetch Error:', error);
//     });


/*전송(이메일,휴대번호)
document.addEventListener('DOMContentLoaded', () => {
    // "수정하기" 버튼에 클릭 이벤트 리스너 추가
    const modifyButton = document.querySelector('.blue-btn');
    modifyButton.addEventListener('click', () => {
        // 입력된 전화번호와 이메일 가져오기
        const phoneNumber = document.getElementById('phoneNumber').value;
        const email = document.getElementById('email').value;

        // JSON 객체 생성
        const data = {
            phoneNumber: phoneNumber,
            email: email
        };

        // JSON 객체를 서버로 전송
        fetch('URL_여기에_서버_URL을_입력하세요', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('서버로부터의 응답:', data);
        })
        .catch(error => {
            console.error('오류 발생:', error);
        });
    });
});
*/