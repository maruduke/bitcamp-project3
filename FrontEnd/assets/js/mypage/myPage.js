
// document.addEventListener('DOMContentLoaded', () => {

//     //임시 데이터
//     const fakeUserData = {
//         userId: 2,
//         email: "sejw4973@naver.com",
//         position: "대리",
//         dept: "인사과",
//         name: "서주원",
//         tel: "01011112222"
//     };

//     // 전화번호 형식 변환 함수
//     function formatPhoneNumber(phoneNumber) {
//         const regex = /(\d{3})(\d{4})(\d{4})/;
//         const formatted = phoneNumber.replace(regex, '$1-$2-$3');
//         return formatted;
//     }

//     document.getElementById('userName').textContent = fakeUserData.name;
//     document.getElementById('userPosition').textContent = fakeUserData.position;
//     document.getElementById('userDept').textContent += fakeUserData.dept;
//     document.getElementById('userId').textContent += fakeUserData.userId;
//     document.getElementById('userEmail').textContent += fakeUserData.email;
//     document.getElementById('userPhone').textContent += formatPhoneNumber(fakeUserData.tel);
// });


document.addEventListener('DOMContentLoaded', () => {

    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/login/mypage',{
        method: 'GET',
        headers:{
            'Content-Type': 'application/json',
            'authorization': `Bearer ${jwt}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {

        function formatPhoneNumber(phoneNumber) {
            const regex = /(\d{3})(\d{4})(\d{4})/;
            const formatted = phoneNumber.replace(regex, '$1-$2-$3');
            return formatted;
        }

        document.getElementById('userName').textContent = data.name;
        document.getElementById('userPosition').textContent = data.position;
        document.getElementById('userDept').textContent += data.dept;
        document.getElementById('userId').textContent += data.userId;
        document.getElementById('userEmail').textContent += data.email;
        document.getElementById('userPhone').textContent += formatPhoneNumber(data.tel);
    })
    .catch(error => {
        console.error('Fetch Error:', error);
    });
});


const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/login/mypage',{
        method: 'GET',
        headers:{
            'Content-Type': 'application/json',
            'authorization': `Bearer ${jwt}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {

        function formatPhoneNumber(phoneNumber) {
            const regex = /(\d{3})(\d{4})(\d{4})/;
            const formatted = phoneNumber.replace(regex, '$1-$2-$3');
            return formatted;
        }

        document.getElementById('userName').textContent = data.name;
        document.getElementById('userPosition').textContent = data.position;
        document.getElementById('userDept').textContent += data.dept;
        document.getElementById('userId').textContent += data.userId;
        document.getElementById('userEmail').textContent += data.email;
        document.getElementById('userPhone').textContent += formatPhoneNumber(data.tel);
    })
    .catch(error => {
        console.error('Fetch Error:', error);
    });

    