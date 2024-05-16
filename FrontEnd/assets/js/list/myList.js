const scroll = document.querySelector('.scroll');
const content = document.querySelector('#myList .content');
let isFetching = false;
let hasNext = true;

let pageNumber = 0;

const listService = {
    myList: function () {
        fetch(`http://localhost:8080/board/myList?pageNumber=${pageNumber}`, {
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
                    let listTitle = document.createElement('span');
                    let listType = document.createElement('span');
                    let listState = document.createElement('span');
                    let listDate = document.createElement('span');

                    // class 넣기
                    listBox.classList.add('list-box', 'flex', 'space-between', 'align-center', 'pdx30');
                    listTitleBox.classList.add('list-title-box');
                    marginBox.classList.add('mb8');
                    listTitle.classList.add('list-title');
                    listType.classList.add('list-type', 'ml8');
                    listState.classList.add('list-progress');
                    listDate.classList.add('list-date', 'text-center');

                    // html내 부모 자식 요소 생성
                    content.appendChild(listBox);
                    listBox.appendChild(listTitleBox);
                    listBox.appendChild(listDate);
                    listTitleBox.appendChild(marginBox);
                    listTitleBox.appendChild(listState);
                    marginBox.appendChild(listTitle);
                    marginBox.appendChild(listType);

                    // 헤딩 type, state 한글로 바꿔주는 작업
                    const type = chgType(data.content[i].type);
                    const state = chgState(data.content[i].state);

                    // json 데이터 받아서 넣어줌
                    listTitle.innerHTML = data.content[i].title;
                    listType.innerHTML = type;
                    listDate.innerHTML = data.content[i].createDate;
                    listState.innerHTML = state;

                    let documentId = data.content[i].documentId;

                    // 경석 =--------------------------------------------------------------------
                    listBox.addEventListener('click', () => {
                        window.location.href = `http://localhost:3200/read?documentId=${documentId}&type=${data.content[i].type}`;
                    });
                    // ----------------------------------------------------------------------------
                }
                pageNumber++;

                if (data == null || data == 'null') {
                    console.log('null 나옴');
                }
            });
    },
};

listService.myList();

// 무한스크롤
scroll.addEventListener('scroll', () => {
    if (isFetching || !hasNext) {
        return;
    }

    if (scroll.scrollTop + scroll.clientHeight + 50 >= scroll.scrollHeight) {
        listService.myList();
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
    if (state === 'PROCESS_1') {
        return '1차 결재중';
    } else if (state === 'PROCESS_2') {
        return '2차 결재중';
    } else if (state === 'PROCESS_3') {
        return '3차 결재중';
    } else if (state === 'DENY') {
        return '반려';
    } else if (state === 'COMPLETE') {
        return '승인 완료';
    } else if (state === 'REFERENCE') {
        return '참조';
    }
}
