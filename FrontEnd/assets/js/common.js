import { initModal } from './message/messageModal.js';
import { init_pagemove } from './message/message_pagemove.js';
import { initHandler } from './message/messageHander.js';
import { init_receiveList } from './message/messageReceiveList.js';
import { init_sendList } from './message/messageSendList.js';
import { init_header } from './header.js';

function includeHeader() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:3200/common/header', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.querySelector('header').innerHTML = xhr.responseText;

            init_header();
        }
    };
    xhr.send();

    return 'ok';
}

function includeFooter() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:3200/common/footer', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.querySelector('footer').innerHTML = xhr.responseText;
            // js 코드 작성
        }
    };
    xhr.send();

    return 'ok';
}

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

// 로그인 안되어 있을 경우 홈페이지 접근 방지
const jwt = sessionStorage.getItem('jwt');
                fetch('http://localhost:8080/login/header',{
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization': `Bearer ${jwt}`
                    }
                })
                    .then(response=>{
                        if(!response.ok){
                            alert('로그인이 필요합니다.')
                            window.location.href = 'http://localhost:3200/login';
                        }
                        return response.json();                       
                    })
                    .then(data=> {
                        console.log(data.name);
                        document.getElementById('username').textContent = data.name;
                    })
                    .catch(error =>{
                        console.error('Fetch Error:', error)
                    });
                


includeHeader();
includeFooter();
includeMessage();
