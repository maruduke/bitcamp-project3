
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