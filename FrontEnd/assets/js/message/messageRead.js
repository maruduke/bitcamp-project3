document.addEventListener('DOMContentLoaded', () => {
    const messageUnReadList = document.querySelector('.notebox');
    messageUnCheckList(messageUnReadList);
});

function messageUnCheckList(receiveUnCheck) {
    const jwt = sessionStorage.getItem('jwt');
    let isFetching = false;
    let hasNext = true;
    let pageNumber = 0;

    function fetchMessages() {
        fetch(`http://localhost:8080/message/noReadReceivedList?pageNumber=${pageNumber}`, {
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
                data.content.forEach((message) => {
                    
                        const messageItem = document.createElement('div');
                        messageItem.classList.add('index_notelist');

                        // 여기에 messageItem1dp user정보가 담기고 담긴 messageItem1이
                        // messageItem에 담겨서index_notelist 이게 반복생성되게하는 방법
                        const messageBox = document.createElement('div');
                        messageBox.classList.add('index_notebox');

                        const noteUserdName = document.createElement('span');
                        noteUserdName.classList.add('note_username');
                        noteUserdName.textContent = message.senderName;

                        const noteUserPosition = document.createElement('span');
                        noteUserPosition.classList.add('note_emp');
                        noteUserPosition.textContent = message.senderPosition;

                        const noteUserContent = document.createElement('p');
                        noteUserContent.classList.add('note_content');
                        noteUserContent.textContent = message.message;

                        messageBox.appendChild(noteUserdName);
                        messageBox.appendChild(document.createTextNode(' '));
                        messageBox.appendChild(noteUserPosition);
                        messageBox.appendChild(noteUserContent);

                        messageItem.appendChild(messageBox);

                        receiveUnCheck.appendChild(messageItem);
                   
                })

                pageNumber++;
            })
            .catch((error) => {
                console.error('Fetch Error:', error);
            })
            .finally(() => {
                isFetching = false;
            });
    }
    fetchMessages();

    // 무한스크롤
    receiveUnCheck.addEventListener("scroll", () => {
        if (isFetching || !hasNext) {
            return;
        }

        // 스크롤이 맨 하단에 도달하면 새로운 페이지 데이터 로드
        if ((receiveUnCheck.scrollTop + receiveUnCheck.clientHeight + 50) >= receiveUnCheck.scrollHeight) {
            isFetching = true;
            fetchMessages(); // 새로운 페이지의 메시지를 추가로 로드
        }
    });

}