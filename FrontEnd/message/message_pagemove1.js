document.addEventListener("DOMContentLoaded", function () {
    // 페이지 버튼들을 선택합니다.
    const inboxBtn = document.querySelector(".inbox_btn");
    const outboxBtn = document.querySelector(".outbox_btn");
    const sendBtn = document.querySelector(".send_btn");
    const messageBtn = document.querySelector(".message");

    // 페이지들을 선택합니다.
    const noteInboxPage = document.querySelector("#note_inbox");
    const noteOutboxPage = document.querySelector("#note_outbox");
    const noteSendPage = document.querySelector("#note_send");

    // message 버튼 클릭 시
            messageBtn.addEventListener("click", function () {
                // note_inbox 페이지만 보여주고 다른 페이지는 숨깁니다.
                document.querySelectorAll('.page').forEach(function(page) {
                    page.classList.remove('active');
                });
                document.getElementById('note_inbox').classList.add('active');
            });
    // "수신함" 버튼 클릭 시
    inboxBtn.addEventListener("click", function () {
        // 해당 페이지를 보여주고 다른 페이지는 숨깁니다.
        noteOutboxPage.classList.remove("active");
        noteSendPage.classList.remove("active");
        noteInboxPage.classList.remove("active");
        noteInboxPage.classList.add("active");
        console.log('수신함 제대로 됨');
    });

    // "발신함" 버튼 클릭 시
    outboxBtn.addEventListener("click", function () {
        // 해당 페이지를 보여주고 다른 페이지는 숨깁니다.
        noteInboxPage.classList.remove("active");
        noteSendPage.classList.remove("active");
        noteOutboxPage.classList.remove("active");
        noteOutboxPage.classList.add("active");
                console.log('발신함 제대로 됨');
    });

    // "보내기" 버튼 클릭 시
    sendBtn.addEventListener("click", function () {
        // 해당 페이지를 보여주고 다른 페이지는 숨깁니다.
        noteInboxPage.classList.remove("active");
        noteOutboxPage.classList.remove("active");
        noteSendPage.classList.add("active");
                console.log('보내기 제대로 됨');
    });
});