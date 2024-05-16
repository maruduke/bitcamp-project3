export const init_pagemove = () => {
    const topbtn = document.querySelectorAll('.message_top_btn');
    const bottombtn = document.querySelectorAll('.message_bottom_btn');

    // 페이지들을 선택합니다.
    const pages = document.querySelectorAll('.page');

    // 페이지 변경 함수를 정의합니다.
    function changePage(pageToShow) {
        // 모든 페이지를 비활성화합니다.
        pages.forEach(function (page) {
            page.style.display = 'none'; // 모든 페이지를 숨깁니다.
        });
        // 해당 페이지를 보여줍니다.
        pageToShow.style.display = 'block';
    }

    // 버튼 클릭 이벤트 핸들러 함수를 정의합니다.
    function handleButtonClick(event) {
        const targetBtn = event.target;
        // 클릭된 버튼에 따라 페이지를 변경합니다.
        if (targetBtn.classList.contains('inbox_btn')) {
            changePage(document.querySelector('#note_inbox'));
        } else if (targetBtn.classList.contains('outbox_btn')) {
            changePage(document.querySelector('#note_outbox'));
        } else if (targetBtn.classList.contains('send_btn')) {
            changePage(document.querySelector('#note_send'));
        } else if (targetBtn.classList.contains('reply_btn')) {
            changePage(document.querySelector('#note_send'));
        }
    }

    // 각 버튼에 클릭 이벤트 리스너를 추가합니다.
    topbtn.forEach((button) => {
        button.addEventListener('click', handleButtonClick);
    });

    bottombtn.forEach((button) => {
        button.addEventListener('click', handleButtonClick);
    });

    // 초기에 수신함 페이지를 활성화합니다.
    changePage(document.querySelector('#note_inbox'));

    // messageItem 클릭 이벤트 핸들러 함수를 정의합니다.
    function handleMessageItemClick(event) {
        // 페이지를 변경합니다.
        changePage(document.querySelector('#note_inbox_check'));
    }

    // 각 messageItem에 클릭 이벤트 리스너를 추가합니다.
    const messageItems = document.querySelectorAll('.message_inbox_select');
    messageItems.forEach((messageItem) => {
        messageItem.addEventListener('click', handleMessageItemClick);
    });
};
