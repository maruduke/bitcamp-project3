//----------------------------------------------- 내가 받은 메세지 리스트--------------------------------------------

document.addEventListener('DOMContentLoaded', () => {
    const messageListContainer = document.querySelector('.message_body');
    receiveMessageList(messageListContainer);
})


function receiveMessageList(receiveMessage) {

    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/message/receivedList', {
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

            receiveMessage.innerHTML = '';

            // 메시지를 최신 sendTime 순서대로 정렬하는 함수
            data.sort((a, b) => {

                // sendTime을 ISO 8601 형식의 문자열로 비교하여 정렬
                return new Date(b.sendTime) - new Date(a.sendTime);
            });

            // 정렬된 메시지 리스트를 순회하면서 HTML 요소로 추가
            data.forEach(message => {
                const messageItem = document.createElement('div');
                messageItem.classList.add('message_list');

                const receiverName = document.createElement('span');
                receiverName.classList.add('receiver_Name');
                receiverName.textContent = message.senderName;

                const receiverPosition = document.createElement('span');
                receiverPosition.classList.add('receiver_Position');
                receiverPosition.textContent = message.senderPosition;

                const messageContent = document.createElement('p');
                messageContent.classList.add('message_content');
                messageContent.textContent = message.message;

                messageItem.appendChild(receiverName);
                messageItem.appendChild(receiverPosition);
                messageItem.appendChild(messageContent);

                // 읽음 여부에 따라 스타일 설정
                if (message.readCheck) {
                    messageItem.style.color = 'lightgray';
                } else {
                    messageItem.style.color = 'black';
                }
                messageItem.addEventListener('click', () => {

                    // 비활성화된 페이지 숨기기
                    document.querySelector('#note_inbox').style.display = 'none';

                    // 활성화할 페이지 표시
                    const noteInboxCheck = document.querySelector('#note_inbox_check');
                    noteInboxCheck.style.display = 'block';

                    // 메시지 상세 정보 가져오기
                    fetchMessageDetails(message.messageId);

                });

                receiveMessage.appendChild(messageItem); // receiveMessage에 messageItem 추가
            });

        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
}

//--------------------------------메세지 정보 가져오기--------------------------------------------------

function fetchMessageDetails(messageId) {
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/receivedList/${messageId}`, {
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
            displayMessageDetails(data); // Display message details

            // 확인 버튼 클릭 이벤트 핸들러
            const checkButton = document.querySelector('.check_btn');
            const deleteButton = document.querySelector('.delete_btn');

            // 확인 버튼 클릭시 메세지아이디 전송
            checkButton.addEventListener('click', () => {
                document.querySelector('#note_inbox_check').style.display = 'none';
                
                // 활성화할 페이지 표시
                const receiveUpdateList = document.querySelector('#note_inbox');
                receiveUpdateList.style.display = 'block';
                fetchMessageCheck(messageId);
            })

            // 삭제 버튼 클릭시 메세지아이디 전송
            deleteButton.addEventListener('click', () => {
                document.querySelector('#note_inbox_check').style.display = 'none';

                // 활성화할 페이지 표시
                const receiveDeleteList = document.querySelector('#note_inbox');
                receiveDeleteList.style.display = 'block';

                fetchMessageDelete(messageId);
                console.log('dddddddddddddd');
            })
      
        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
}

function displayMessageDetails(message) {
    const receiverDetailName = document.querySelector('.receiverDetailName');
    const receiverDetailPosition = document.querySelector('.receiverDetailPosition');
    const receiverDetailTime = document.querySelector('.receiverDetailTime');
    const receiverDetailContent = document.querySelector('.receiverDetailContent');
    const formattedDate = moment(message.sendTime).format('YYYY-MM-DD HH:mm:ss');

    receiverDetailName.textContent = message.senderName;
    receiverDetailPosition.textContent = message.senderPosition;
    receiverDetailTime.textContent = formattedDate;
    receiverDetailContent.textContent = message.message;

}

//------------------------------------ 확인 클릭시 읽음표시---------------------------

function fetchMessageCheck(messageId) {
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/receivedList/${messageId}/check`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`,
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            location.reload();
            alert('메세지를 확인 했습니다.')
        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
}

//--------------------------------------삭제 클릭시 리스트 삭제--------------------------

function fetchMessageDelete(messageId) {
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/receivedList/${messageId}/removed`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`,
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            location.reload();
            alert('메세지가 성공적으로 삭제되었습니다.')
        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
}