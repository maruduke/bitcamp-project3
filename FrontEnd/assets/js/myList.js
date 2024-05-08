const loginService = {
    login: async function (loginDto) {
        return fetch(`http://localhost:8080/login/post`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginDto),
        })
            .then((res) => {
                if (!res.ok) throw exception('error');
				return res;
			})
            .then((res) => res.json())
            .then((res) => {
                // session storage에 jwt 저장
                sessionStorage.setItem('jwt', res['atk']);
            });
    },
};

class loginDto {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }
}

const scroll = document.querySelector(".scroll");
const content = document.querySelector(".content");

let currentPage = 1;
let isFetching = false;
let hasNext = true;

async function login() {
    loginService.login(new loginDto('nevers1117@gmail.com', '1234'));

	
	let listTitle = document.querySelector(".list-title");
	let listType = document.querySelector(".list-type");
	let listProgress = document.querySelector(".list-progress");
	let createDate = document.querySelector(".list-date");

	let pageNumber = 0;

	let data = 
		{
			title: listTitle,
			type: listType,
			state: listProgress,
			createDate: createDate
		};

		function fetchData(){
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
		const content = document.querySelector(".content");
		isFetching = false;

		if(data.content.length === 0){
			hasNext = false;
			return;
		}

		for(let i = 0; i < data.content.length; i++){
			
			const type = chgType(data.content[i].type);
			const state = chgState(data.content[i].state);

			content.innerHTML += '<div class="list-box flex space-between align-center pdx30">'
						+ '<div class="list-title-box">'
						+ '<div class="mb8">'
						+ '<span class="list-title">'+`${data.content[i].title}`+'</span>'
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
		pageNumber++;
	})
}

		fetchData();

		scroll.addEventListener("scroll", ()=>{
	
			if(isFetching || !hasNext) {
				return;
			}
		
			if((scroll.scrollTop + scroll.clientHeight + 100) >= scroll.scrollHeight) {
				fetchData();
				
				console.log("scroll");
			}
		})

}

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
	if(state === "PROCESS_1") {
		return "1차 결재중";
	} else if(state === "PROCESS_2") {
		return "2차 결재중";
	} else if(state === "PROCESS_3") {
		return "3차 결재중";
	} else if(state === "DENY") {
		return "반려";
	} else if(state === "COMPLETE") {
		return "승인";
	} 
}

login();
