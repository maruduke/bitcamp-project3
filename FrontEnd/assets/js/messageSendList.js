function sendMessageList(sendMessage) {

    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/message/sentList', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`,
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {

            sendMessage.innerHTML = '';

            // 메시지를 최신 sendTime 순서대로 정렬하는 함수
            data.sort((a, b) => {
                
                // sendTime을 ISO 8601 형식의 문자열로 비교하여 정렬
                return new Date(b.sendTime) - new Date(a.sendTime);
            });

            // 정렬된 메시지 리스트를 순회하면서 HTML 요소로 추가
            data.forEach(message => {
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
                    // 클릭 이벤트 처리
                    // 클릭 시 메시지 내용 표시 등의 동작 수행 추가
                });

                sendMessage.appendChild(messageItem); // sendMessage에 messageItem 추가
            });

        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
}
document.addEventListener('DOMContentLoaded', () => {
    const sendMessageListContainer = document.querySelector('.message_sendBody');
    sendMessageList(sendMessageListContainer);
})