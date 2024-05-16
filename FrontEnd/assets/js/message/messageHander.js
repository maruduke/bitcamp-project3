// 유저 정보 가져오기
export const initHandler = () => {
    fetchUsers(userSelect);

    // 전송 버튼 클릭 시 메시지 전송
    const sendBtn = document.getElementById('sendBtn');
    sendBtn.addEventListener('click', () => {
        sendMessage();
    })
};
function fetchUsers() {
    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/message/send', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${jwt}`,
        },
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            // 유저 정보를 셀렉트 박스에 추가
            const userSelect = document.getElementById('userSelect');
            data.forEach((user) => {
                const option = document.createElement('option');
                option.value = user.email;
                option.textContent = user.name;
                userSelect.appendChild(option);
            });
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
}
// 메시지 전송
function sendMessage() {
    const userSelect = document.getElementById('userSelect');
    const receiverEmail = userSelect.value;
    const messageContentInput = document.querySelector('.send_content_area');
    const messageContent = messageContentInput.value;


    if (receiverEmail === '') {
        alert('보낼 사람을 선택해주세요.');
        return;
    }

    // 메시지 내용이 공백인 경우
    if (messageContent === '') {
        alert('메시지 내용을 입력해주세요.');
        return; // 메시지 전송 중지
    }

    const message = {
        message: messageContent,
        receiverEmail: receiverEmail,
        receiverName: userSelect.options[userSelect.selectedIndex].textContent
    };

    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/message/send', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${jwt}`, // JWT를 사용하여 인증
        },
        body: JSON.stringify(message),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            console.log('Message sent successfully!');
            alert('메세지가 성공적으로 전송되었습니다.');
            location.reload();
            messageContentInput.value = '';
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
            alert('메세지 전송 중 오루가 발생했습니다.');
        });
}

