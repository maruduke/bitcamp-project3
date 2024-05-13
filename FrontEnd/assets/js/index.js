import { initModal } from './message/messageModal.js';
import { init_pagemove } from './message/message_pagemove.js';
import { initHandler } from './message/messageHander.js';
import { init_receiveList } from './message/messageReceiveList.js';
import { init_sendList } from './message/messageSendList.js';

const jwt = sessionStorage.getItem('jwt');
if (!jwt) {
    // 로그인이 되어 있지 않은 경우 로그인 페이지로 이동
    alert('로그인이 필요합니다.');
    window.location.href = 'http://localhost:3200/login';
} else {
    fetch('http://localhost:8080/login/header', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'authorization': `Bearer ${jwt}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('username').textContent = data.name;
        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
}


document.getElementById('joinbtn').addEventListener('click', () => {
    fetch('http://localhost:8080/login/joinget', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'authorization': `Bearer ${jwt}`
        }
    })
        .then(response => {
            if (!response.ok) {
                alert('권한이 없습니다.')
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            alert('권한자입니다.');
            window.location.href = 'http://localhost:3200/registration';
        })

        .catch(error => {
            console.error('Fetch Error:', error);
        });
});

// 로그아웃 버튼 클릭 시 세션 스토리지 토큰 제거
document.getElementById('logoutButton').addEventListener('click', () => {
    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/login/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            authorization: `Bearer ${jwt}`,
        },
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            sessionStorage.removeItem('jwt');
            alert('로그아웃 되었습니다!');
            window.location.href = 'http://localhost:3200/login';
        })
        .catch((error) => {
            console.error('Logout Error:', error);
        });
});

function includeMessage() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:3200/message/message_inbox', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.querySelector('#message').innerHTML = xhr.responseText;

            // message 코드 작성
            init_pagemove();
            initHandler();
            init_receiveList();
            init_sendList();
            initModal();
        }
    };
    xhr.send();

    return 'ok';
}

includeMessage();