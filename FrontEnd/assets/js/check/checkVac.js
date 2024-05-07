document.addEventListener('DOMContentLoaded', () => {

    //임시 데이터
    const fakeUserData = {
        userId: 2,
        email: "sgw0816@naver.com",
        position: "대리",
        dept: "인사과",
        name: "서주원",
        title: "5월8일 휴가 신청합니다",
        sign1: "이경석",
        sign2: "안슬기",
        sign3: "김융",
        startTime: "2014-05-17 15:20",
        end: "5월10일",
        time: "5월6일",
        context: "몸이 아파요"

    };

    // 보낸 사람 이름+직급 저장
    const userNamePosition = `${fakeUserData.name} ${fakeUserData.position}`;

    // 결재자 저장
    const signMember = `${fakeUserData.sign1}, ${fakeUserData.sign2}, ${fakeUserData.sign3} `;

    document.getElementById('checkTitle').textContent = fakeUserData.title;
    document.getElementById('userNamePosition').textContent = userNamePosition;
    document.getElementById('signMember').textContent = signMember;
    document.getElementById('checkContext').textContent = fakeUserData.context;
    document.getElementById('startTime').textContent = fakeUserData.startTime;
    
    
});