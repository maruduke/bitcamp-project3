//----------------------------------------------- 내가 받은 메세지 리스트--------------------------------------------
let currentMessageId = null;


export const init_receiveList = () => {
    const messageListContainer = document.querySelector('.message_body');
    receiveMessageList(messageListContainer);
    fetchMessageCount();
};

function receiveMessageList(receiveMessage) {
    const jwt = sessionStorage.getItem('jwt');
    let isFetching = false;
    let hasNext = true;
    let pageNumber = 0;

    function fetchMessages() {

        fetch(`http://localhost:8080/message/receivedList?pageNumber=${pageNumber}`, {
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
                        document.querySelector('#note_inbox').style.display = 'none';
                        const noteInboxCheck = document.querySelector('#note_inbox_check');
                        noteInboxCheck.style.display = 'block';

                        // 현재 클릭된 messageId 설정
                        currentMessageId = message.messageId;

                        // 메시지 상세 정보 가져오기
                        fetchMessageDetails(currentMessageId);
                    });

                    // 기존의 메시지 목록에 추가
                    receiveMessage.appendChild(messageItem);
                });

                pageNumber++; // 다음 페이지 번호 증가
            })
            .catch((error) => {
                console.error('Fetch Error:', error);
            })
            .finally(() => {
                isFetching = false;
            });
    }

    // 초기 메시지 로딩
    fetchMessages();

    // 무한스크롤
    receiveMessage.addEventListener("scroll", () => {
        if (isFetching || !hasNext) {
            return;
        }

        // 스크롤이 맨 하단에 도달하면 새로운 페이지 데이터 로드
        if ((receiveMessage.scrollTop + receiveMessage.clientHeight + 50) >= receiveMessage.scrollHeight) {
            isFetching = true;
            fetchMessages(); // 새로운 페이지의 메시지를 추가로 로드
        }
    });
}

//--------------------------------메세지 정보 가져오기--------------------------------------------------

function fetchMessageDetails(messageId) {
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/receivedList/${messageId}`, {
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
            displayMessageDetails(data); // Display message details

            // 확인 버튼 클릭 이벤트 핸들러
            const checkButton = document.querySelector('.check_btn');
            const deleteButton = document.querySelector('.delete_btn');


            checkButton.addEventListener('click', handleCheckButtonClick);

            deleteButton.addEventListener('click', handleDeleteButtonClick);
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
}

function handleCheckButtonClick() {
    if (currentMessageId) {
        // 확인 버튼 클릭 시 해당 messageId에 대한 처리
        fetchMessageCheck(currentMessageId);
    }

    // 활성화된 페이지를 수신함으로 변경
    const noteInboxCheck = document.querySelector('#note_inbox_check');
    noteInboxCheck.style.display = 'none';

    const noteInbox = document.querySelector('#note_inbox');
    noteInbox.style.display = 'block';
}

function handleDeleteButtonClick() {
    if (currentMessageId) {
        // 확인 버튼 클릭 시 해당 messageId에 대한 처리
        fetchMessageDelete(currentMessageId);
    }

    const noteInboxCheck = document.querySelector('#note_inbox_check');
    noteInboxCheck.style.display = 'none';

    // 활성화할 페이지 표시
    const receiveDeleteList = document.querySelector('#note_inbox');
    receiveDeleteList.style.display = 'block';

}


function displayMessageDetails(message) {
    const receiverDetailName = document.querySelector('.receiverDetailName');
    const receiverDetailPosition = document.querySelector('.receiverDetailPosition');
    const receiverDetailTime = document.querySelector('.receiverDetailTime');
    const receiverDetailContent = document.querySelector('.receiverDetailContent');

    receiverDetailName.textContent = message.senderName;
    receiverDetailPosition.textContent = message.senderPosition;
    receiverDetailTime.textContent = message.sendTime;
    receiverDetailContent.textContent = message.message;
}

//------------------------------------ 확인 클릭시 읽음표시---------------------------

function fetchMessageCheck(messageId) {
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/receivedList/${messageId}/check`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${jwt}`,
        },
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            } location.reload();
            alert('메세지를 확인 했습니다.')

        })
        .catch((error) => {
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

//-------------------------------------- 안읽은 메세지 카운트------------------------------
function fetchMessageCount(){
    const jwt = sessionStorage.getItem('jwt');
    fetch(`http://localhost:8080/message/noReadReceivedCount`, {
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
            // 모든 메시지를 처리한 후 unreadMessageCount 확인
            if (data > 0) {
                document.querySelector('.message').classList.add('new'); // 읽지 않은 메시지가 있을 때 new 클래스 추가
            } else {
                document.querySelector('.message').classList.remove('new'); // 모든 메시지가 읽혔을 때 new 클래스 제거
            }
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
}