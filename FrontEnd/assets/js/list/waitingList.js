const refScroll = document.querySelector('#ref');
const appScroll = document.querySelector('#app');
const refContent = document.querySelector('#waitingList .ref');
const appContent = document.querySelector('#waitingList .app');

let isFetching = false;
let hasNext = true;

let refPage = 0;
let appPage = 0;

const chk = document.querySelector('.checkbox');
let chkB = false;

const listService = {
    referenceList: function (data) {
        fetch(`http://localhost:8080/board/refList?pageNumber=${refPage}&DocState=REFERENCE`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', Authorization: 'Bearer ' + sessionStorage.getItem('jwt') },
        })
            .then((response) => {
                isFetching = true;
                if (!response.ok) {
                    throw new Error('에러떴음');
                }

                return response.json();
            })
            .then((data) => {
                isFetching = false;

                if (data.content.length === 0) {
                    hasNext = false;
                    return;
                }

                for (let i = 0; i < data.content.length; i++) {
                    // div 생성
                    let listBox = document.createElement('div');
                    let listTitleBox = document.createElement('div');
                    let marginBox = document.createElement('div');
                    let listWriter = document.createElement('span');
                    let listType = document.createElement('span');
                    let listState = document.createElement('span');
                    let listDate = document.createElement('span');

                    // class 넣기
                    listBox.classList.add('list-box', 'flex', 'space-between', 'align-center', 'pdx30');
                    listTitleBox.classList.add('list-title-box');
                    marginBox.classList.add('mb8');
                    listWriter.classList.add('list-writer');
                    listType.classList.add('list-type', 'ml8');
                    listState.classList.add('list-progress');
                    listDate.classList.add('list-date', 'text-center');

                    // html내 부모 자식 요소 생성
                    refContent.appendChild(listBox);
                    listBox.appendChild(listTitleBox);
                    listBox.appendChild(listDate);
                    listTitleBox.appendChild(marginBox);
                    listTitleBox.appendChild(listState);
                    marginBox.appendChild(listWriter);
                    marginBox.appendChild(listType);

                    // 헤딩 type, state 한글로 바꿔주는 작업
                    const type = chgType(data.content[i].type);
                    const state = chgState(data.content[i].state);

                    // json 데이터 받아서 넣어줌
                    listWriter.innerHTML = data.content[i].name;
                    listType.innerHTML = type;
                    listDate.innerHTML = data.content[i].createDate;
                    listState.innerHTML = state;

                    // 경석
                    let documentId = data.content[i].documentId;
                    listBox.addEventListener('click', () => {
                        window.location.href = `http://localhost:3200/read?documentId=${documentId}&type=${data.content[i].type}`;
                    });
                }

                if (data == null || data == 'null') {
                    console.log('null 나옴');
                }
                refPage++;
            });
    },
    approveList: function (data) {
        fetch(`http://localhost:8080/board/approveList?pageNumber=${appPage}&DocState=PROCESS_1`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', Authorization: 'Bearer ' + sessionStorage.getItem('jwt') },
            // body: JSON.stringify(data),
        })
            .then((response) => {
                isFetching = true;
                if (!response.ok) {
                    throw new Error('에러떴음');
                }

                return response.json();
            })
            .then((data) => {
                isFetching = false;

                if (data.content.length === 0) {
                    hasNext = false;
                    return;
                }

                for (let i = 0; i < data.content.length; i++) {
                    // div 생성
                    let listBox = document.createElement('div');
                    let listTitleBox = document.createElement('div');
                    let marginBox = document.createElement('div');
                    let listWriter = document.createElement('span');
                    let listType = document.createElement('span');
                    let listState = document.createElement('span');
                    let listDate = document.createElement('span');

                    // class 넣기
                    listBox.classList.add('list-box', 'flex', 'space-between', 'align-center', 'pdx30');
                    listTitleBox.classList.add('list-title-box');
                    marginBox.classList.add('mb8');
                    listWriter.classList.add('list-writer');
                    listType.classList.add('list-type', 'ml8');
                    listState.classList.add('list-progress');
                    listDate.classList.add('list-date', 'text-center');

                    // html내 부모 자식 요소 생성
                    appContent.appendChild(listBox);
                    listBox.appendChild(listTitleBox);
                    listBox.appendChild(listDate);
                    listTitleBox.appendChild(marginBox);
                    listTitleBox.appendChild(listState);
                    marginBox.appendChild(listWriter);
                    marginBox.appendChild(listType);

                    // 헤딩 type, state 한글로 바꿔주는 작업
                    const type = chgType(data.content[i].type);
                    const state = chgState(data.content[i].state);

                    // json 데이터 받아서 넣어줌
                    listWriter.innerHTML = data.content[i].name;
                    listType.innerHTML = type;
                    listDate.innerHTML = data.content[i].createDate;
                    listState.innerHTML = state;

                    // 경석
                    let documentId = data.content[i].documentId;
                    listBox.addEventListener('click', () => {
                        window.location.href = `http://localhost:3200/read?documentId=${documentId}&type=${data.content[i].type}`;
                    });
                }

                if (data == null || data == 'null') {
                    console.log('null 나옴');
                }
                appPage++;
            });
    },
};

listService.approveList();

chk.addEventListener('click', function () {
    listService.referenceList();
    if (!chkB) {
        chkB = true;
        appScroll.classList.add('hide');
        refScroll.classList.remove('hide');
    } else {
        chkB = false;
        refScroll.classList.add('hide');
        appScroll.classList.remove('hide');
    }
});

appScroll.addEventListener('scroll', () => {
    if (isFetching || !hasNext) {
        return;
    }

    if (appScroll.scrollTop + appScroll.clientHeight + 50 >= appScroll.scrollHeight) {
        listService.approveList();
        console.log('scroll');
    }
});

refScroll.addEventListener('scroll', () => {
    if (isFetching || !hasNext) {
        return;
    }

    if (refScroll.scrollTop + refScroll.clientHeight + 50 >= refScroll.scrollHeight) {
        listService.referenceList();
        console.log('scroll');
    }
});


function chgType(type) {
    if (type === 'VACATION') {
        return '휴가 신청';
    } else if (type === 'BUSSINESSTRIP') {
        return '출장 신청';
    } else if (type === 'REPORT') {
        return '보고서';
    } else if (type === 'ACCOUNTINGEXPENSE') {
        return '경비 신청';
    }
}

function chgState(state) {
    if (state === 'PROCESS_1' || state === 'PROCESS_2' || state === 'PROCESS_3') {
        return '승인 필요';
    } else if (state === 'REFERENCE') {
        return '참조';
    } else if (state === 'DENY'){
        return '반려';
    } else if (state === 'COMPLETE'){
        return '승인';
    }
}
