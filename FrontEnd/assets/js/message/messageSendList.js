//----------------------------------- 내가 보낸 메세지 리스트-----------------------------

function sendMessageList(sendMessage) {
    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/message/sentList', {
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
            sendMessage.innerHTML = '';

            // 메시지를 최신 sendTime 순서대로 정렬하는 함수
            data.sort((a, b) => {
                // sendTime을 ISO 8601 형식의 문자열로 비교하여 정렬
                return new Date(b.sendTime) - new Date(a.sendTime);
            });

            // 정렬된 메시지 리스트를 순회하면서 HTML 요소로 추가
            data.forEach((message) => {
                const messageItem = document.createElement('div');
                messageItem.classList.add('message_sendList');

                const receiverName = document.createElement('span');
                receiverName.classList.add('myReceiver_Name');
                receiverName.textContent = message.receiverName;

                const receiverPosition = document.createElement('span');
                receiverPosition.classList.add('myReceiver_position');
                receiverPosition.textContent = message.receiverPosition;

                const messageContent = document.createElement('p');
                messageContent.classList.add('message_sendContent');
                messageContent.textContent = message.message;

                messageItem.appendChild(receiverName);
                messageItem.appendChild(receiverPosition);
                messageItem.appendChild(messageContent);

                messageItem.addEventListener('click', () => {
                    // 활성화된 페이지 숨기기
                    document.querySelector('#note_outbox').style.display = 'none';

                    // 활성화할 페이지 표시
                    const noteOutboxCheck = document.querySelector('#note_outbox_check');
                    noteOutboxCheck.style.display = 'block';

                    // 메세지 상세 정보 가져오기
                    fetchSendMessageDetails(message.messageId);
                });

                sendMessage.appendChild(messageItem); // sendMessage에 messageItem 추가
            });
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
}

//---------------------- 보낸 메세지 정보 가져오기 ---------------------------------------------------
function fetchSendMessageDetails(messageId) {
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/sentList/${messageId}`, {
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
            displaySendMessageDetails(data);

            // 삭제 버튼 이벤트 핸들러
            const sendDeleteButton = document.querySelector('#send_deletebtn');

            sendDeleteButton.addEventListener('click', () => {
                document.querySelector('#note_outbox_check').style.display = 'none';

                // 활성화할 페이지 표시
                const sendDeleteList = document.querySelector('#note_outbox');
                sendDeleteList.style.display = 'block';

                // 삭제 버튼 클릭시 메세지아이디 전송
                fetchSendMessageDelete(messageId);

                console.log('dddddddddddddd');
            });
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
}

function displaySendMessageDetails(message) {
    const senderDetailName = document.querySelector('.myReceiverDetailName');
    const senderDetailPosition = document.querySelector('.myReceiverDetailPosition');
    const senderDetailTime = document.querySelector('.myReceiverDetailTime');
    const senderDetailContent = document.querySelector('.myReceiverDetailContent');
    const formattedDate = moment(message.sendTime).format('YYYY-MM-DD HH:mm:ss');

    senderDetailName.textContent = message.receiverName;
    senderDetailPosition.textContent = message.receiverPosition;
    senderDetailTime.textContent = formattedDate;
    senderDetailContent.textContent = message.message;
}

//---------------------------------- 삭제 클릭시 리스트 삭제-------------------
function fetchSendMessageDelete(messageId) {
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/sentList/${messageId}/removed`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${jwt}`,
        },
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }

            location.reload();
            alert('메세지가 성공적으로 삭제되었습니다.')
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
}

export const init_sendList = () => {
    const sendMessageListContainer = document.querySelector('.message_sendBody');
    sendMessageList(sendMessageListContainer);
};
