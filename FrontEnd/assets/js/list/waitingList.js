const scroll = document.querySelector(".scroll");
	const refContent = document.querySelector("#waitingList .ref");
	const appContent = document.querySelector("#waitingList .app");
	
	let isFetching = false;
	let hasNext = true;

	const listTitle = document.querySelector(".list-title");
	const writer = document.querySelector(".list-writer");
	const listType = document.querySelector(".list-type");
	const listProgress = document.querySelector(".list-progress");
	const createDate = document.querySelector(".list-date");

	const data = {
		name: writer,
		title: listTitle,
		type: listType,
		state: listProgress,
		createDate: createDate
	};

	let refPage = 0;
	let appPage = 0;

	const chk = document.querySelector(".checkbox");
	let chkB = false;
	
	
	const listService = {
		referenceList : function (data){
			fetch(`http://localhost:8080/board/refList?pageNumber=${refPage}&DocState=REFERENCE`, {
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
					
					const type = chgType(data.content[i].type);
					const state = chgState(data.content[i].state);

					refContent.innerHTML += '<div class="list-box flex space-between align-center pdx30">'
								+ '<div class="list-title-box">'
								+ '<div class="mb8">'
								+ '<span class="list-writer">'+`${data.content[i].name}`+'</span>'
								+ '<span class="list-type ml8">'+`${type}`+'</span>'
								+ '</div>'
								+ '<span class="list-progress">'+`${state}`+'</span>'
								+ '</div>'
								+ '<span class="list-date text-center">'+`${data.content[i].createDate}`+'</span>'
								+ '</div>';								
				}

				if(data == null || data == "null"){
					console.log("null 나옴");
				}
				refPage++;
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
					
					const type = chgType(data.content[i].type);
					const state = chgState(data.content[i].state);

					appContent.innerHTML += '<div class="list-box flex space-between align-center pdx30">'
								+ '<div class="list-title-box">'
								+ '<div class="mb8">'
								+ '<span class="list-writer">'+`${data.content[i].name}`+'</span>'
								+ '<span class="list-type ml8">'+`${type}`+'</span>'
								+ '</div>'
								+ '<span class="list-progress">'+`${state}`+'</span>'
								+ '</div>'
								+ '<span class="list-date text-center">'+`${data.content[i].createDate}`+'</span>'
								+ '</div>';								
				}

				if(data == null || data == "null"){
					console.log("null 나옴");
				}
				appPage++;
			})
		},
	}

	listService.approveList(data);
	listService.referenceList(data);

	chk.addEventListener("click", function(){
		if(!chkB){
			chkB = true;
			appContent.classList.add("hide");
			refContent.classList.remove("hide");

		} else {
			chkB = false;
			refContent.classList.add("hide");
			appContent.classList.remove("hide");
		}
	})

	scroll.addEventListener("scroll", ()=>{
		if(isFetching || !hasNext) {
			return;
		}
				
		if((scroll.scrollTop + scroll.clientHeight + 50) >= scroll.scrollHeight) {
			if (chk.checked){
				listService.refList(data);
			} else {
				listService.approveList(data);
			}
			console.log("scroll");
		}
	})


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