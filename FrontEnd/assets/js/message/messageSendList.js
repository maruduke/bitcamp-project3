//----------------------------------- 내가 보낸 메세지 리스트-----------------------------
let currentMessageId = null;

export const init_sendList = () => {
    const sendMessageListContainer = document.querySelector('.message_sendBody');
    sendMessageList(sendMessageListContainer);
};

function sendMessageList(sendMessage) {
    const jwt = sessionStorage.getItem('jwt');
    let isFetching = false;
    let hasNext = true;
    let pageNumber = 0;

    function myFetchMessages() {
        fetch(`http://localhost:8080/message/sentList?pageNumber=${pageNumber}`, {
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
                if (!data || data.content.length === 0) {
                    hasNext = false;
                    return;
                }
    
                // 정렬된 메시지 리스트를 순회하면서 HTML 요소로 추가
                data.content.forEach((message) => {
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
    
                        document.querySelector('#note_outbox').style.display = 'none';
                        const noteOutboxCheck = document.querySelector('#note_outbox_check');
                        noteOutboxCheck.style.display = 'block';
    
                        currentMessageId = message.messageId;
    
                        // 메세지 상세 정보 가져오기
                        fetchSendMessageDetails(currentMessageId);
                    });
    
                    sendMessage.appendChild(messageItem);
                });
    
                pageNumber++;
            })
            .catch((error) => {
                console.error('Fetch Error:', error);
            })
            .finally(() => {
                isFetching = false;
            });
    }

    myFetchMessages();

    sendMessage.addEventListener("scroll", () => {
        if (isFetching || !hasNext) {
            return;
        }

        // 스크롤이 맨 하단에 도달하면 새로운 페이지 데이터 로드
        if ((sendMessage.scrollTop + sendMessage.clientHeight + 50) >= sendMessage.scrollHeight) {
            isFetching = true;
            myFetchMessages(); // 새로운 페이지의 메시지를 추가로 로드
        }
    })

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

            sendDeleteButton.addEventListener('click', handleSendDeleteButton);
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
}



function handleSendDeleteButton(){
    if (currentMessageId) {
        // 확인 버튼 클릭 시 해당 messageId에 대한 처리
        fetchSendMessageDelete(currentMessageId);
    }

    const noteOutboxCheck = document.querySelector('#note_outbox_check');
    noteOutboxCheck.style.display = 'none';

    // 활성화할 페이지 표시
    const noteOutbox = document.querySelector('#note_outbox');
    noteOutbox.style.display = 'block';

}



function displaySendMessageDetails(message) {
    const senderDetailName = document.querySelector('.myReceiverDetailName');
    const senderDetailPosition = document.querySelector('.myReceiverDetailPosition');
    const senderDetailTime = document.querySelector('.myReceiverDetailTime');
    const senderDetailContent = document.querySelector('.myReceiverDetailContent');

    senderDetailName.textContent = message.receiverName;
    senderDetailPosition.textContent = message.receiverPosition;
    senderDetailTime.textContent = message.sendTime;
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


