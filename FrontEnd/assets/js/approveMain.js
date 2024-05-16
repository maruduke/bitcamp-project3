const appScroll = document.querySelector("#app");
const myListScroll = document.querySelector("#myList");
const myListContent = document.querySelector(".content.myList");
const approveContent = document.querySelector(".content.approve");
let isFetching = false;
let hasNext = true;

let pageNumber = 0;
let appPage = 0;

	const listService = {
		myList : function (){
			fetch(`http://localhost:8080/board/myList?pageNumber=${pageNumber}`, {
				method: 'GET',
				headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + sessionStorage.getItem('jwt')},
				// body: JSON.stringify(data),

			}).then(response=>{
				isFetching = true;
				if(!response.ok){
					throw new Error("에러떴음");
				}

				return response.json();
			}).then(data => {
				isFetching = false;

				if(data.content.length === 0){
					hasNext = false;
					return;
				}

				for(let i = 0; i < data.content.length; i++){
					// div 생성
					let listBox = document.createElement("div");
					let listTitleBox = document.createElement("div");
					let listTitle = document.createElement("span");
					let listType = document.createElement("span");
					let listDate = document.createElement("span");

					// class 넣기
					listBox.classList.add("list-box-40","flex","space-between","align-center","pdx20");
					listTitleBox.classList.add("list-title-box","inline-flex","space-between","align-center", "w100p");
					listTitle.classList.add("list-title", "w50p","text-ellipsis","mb0");
					listType.classList.add("list-type");
					listDate.classList.add("list-date");

					// html내 부모 자식 요소 생성
					myListContent.appendChild(listBox);
					listBox.appendChild(listTitleBox);
					listTitleBox.appendChild(listTitle);
					listTitleBox.appendChild(listType);
					listTitleBox.appendChild(listDate);

					// 헤딩 type, state 한글로 바꿔주는 작업
					const type = chgType(data.content[i].type);

					// json 데이터 받아서 넣어줌
					listTitle.innerHTML = data.content[i].title;
					listType.innerHTML = type;
					listDate.innerHTML = data.content[i].createDate;

					let documentId = data.content[i].documentId;
                    // 경석 =--------------------------------------------------------------------
                    listBox.addEventListener('click', () => {
                        window.location.href = `http://localhost:3200/read?documentId=${documentId}&type=${data.content[i].type}`;
                    });
                    // ----------------------------------------------------------------------------	
				}
				pageNumber++;

				if(data == null || data == "null"){
					console.log("null 나옴");
				}
			})
		},
		approveList : function (data){
			fetch(`http://localhost:8080/board/approveList?pageNumber=${appPage}&DocState=PROCESS_1`, {
				method: 'GET',
				headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + sessionStorage.getItem('jwt')},
				// body: JSON.stringify(data),

			}).then(response=>{
				isFetching = true;
				if(!response.ok){
					throw new Error("에러떴음");
				}

				return response.json();
			}).then(data => {
				isFetching = false;

				if(data.content.length === 0){
					hasNext = false;
					return;
				}

				for(let i = 0; i < data.content.length; i++){
					
					// div 생성
					let listBox = document.createElement("div");
					let listTitleBox = document.createElement("div");
					let listWriter = document.createElement("span");
					let listType = document.createElement("span");
					let listState = document.createElement("span");

					// class 넣기
					listBox.classList.add("list-box-40","flex","space-between","align-center","pdx20");
					listTitleBox.classList.add("list-title-box");
					listWriter.classList.add("list-writer");
					listType.classList.add("list-type","ml8");
					listState.classList.add("list-progress");

					// html내 부모 자식 요소 생성
					approveContent.appendChild(listBox);
					listBox.appendChild(listTitleBox);
					listBox.appendChild(listState);
					listTitleBox.appendChild(listWriter);
					listTitleBox.appendChild(listType);

					// 헤딩 type, state 한글로 바꿔주는 작업
					const type = chgType(data.content[i].type);
					const state = chgState(data.content[i].state);

					// json 데이터 받아서 넣어줌
					listWriter.innerHTML = data.content[i].name;
					listType.innerHTML = type;
					listState.innerHTML = state;

					let documentId = data.content[i].documentId;
                    // 경석 =--------------------------------------------------------------------
                    listBox.addEventListener('click', () => {
                        window.location.href = `http://localhost:3200/read?documentId=${documentId}&type=${data.content[i].type}`;
                    });
                    // ----------------------------------------------------------------------------					
				}

				if(data == null || data == "null"){
					console.log("null 나옴");
				}
				appPage++;
			})
		},
	}

	listService.myList();
	listService.approveList();

	appScroll.addEventListener('scroll', () => {
		if (isFetching || !hasNext) {
			return;
		}
	
		if (appScroll.scrollTop + appScroll.clientHeight + 50 >= appScroll.scrollHeight) {
			listService.approveList();
		}
	});
	
	myListScroll.addEventListener('scroll', () => {
		if (isFetching || !hasNext) {
			return;
		}
	
		if (myListScroll.scrollTop + myListScroll.clientHeight + 50 >= myListScroll.scrollHeight) {
			listService.myList();
		}
	});


function chgType(type) {
	if(type === "VACATION") {
		return "휴가 신청";
	} else if(type === "BUSSINESSTRIP") {
		return "출장 신청";
	} else if(type === "REPORT") {
		return "보고서";
	} else if(type === "ACCOUNTINGEXPENSE") {
		return "경비 신청";
	}	
}

function chgState(state) {
	if(state === "PROCESS_1" || 
		state === "PROCESS_2" ||
		state === "PROCESS_3") {
		return "승인 필요";
	} else if(state === "REFERENCE") {
		return "참조";
	}
}