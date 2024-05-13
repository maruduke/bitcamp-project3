export const initModal = () => {
    const modal = document.querySelector('.message');
    const modalopen = document.querySelector('.message_modal');

    // 모달 열기 함수
    function openModal() {
        modalopen.style.display = 'block';
    }

    // 모달 닫기 함수
    function closeModal() {
        modalopen.style.display = 'none';
    }

    // 모달 열기 버튼에 클릭 이벤트 추가
    modal.onclick = function () {
        // 모달이 이미 열려있는 경우에는 모달을 닫습니다.
        if (modalopen.style.display === 'block') {
            closeModal();
        } else {
            openModal();
        }
    };

    // 모달 영역 클릭 시 이벤트 전파(stopPropagation) 방지
    modalopen.onclick = function (event) {
        event.stopPropagation();
    };

    // document에 클릭 이벤트 리스너 추가
    document.addEventListener('click', function (event) {
        // 클릭된 요소가 모달 이외의 영역이면 모달 닫기
        if (!modalopen.contains(event.target) && !modal.contains(event.target)) {
            closeModal();
        }
    });

    closeModal();
};
