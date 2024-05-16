document.addEventListener('DOMContentLoaded', () => {

    const memberDeptList = document.querySelector('.deptbox');
    memberMyDeptList(memberDeptList);
}) 

function memberMyDeptList(myDeptList){
    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/deptMember', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${jwt}`,
        },
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
           
            data.forEach((member) => {

                function formatPhoneNumber(phoneNumber) {
                    const regex = /(\d{3})(\d{4})(\d{4})/;
                    const formatted = phoneNumber.replace(regex, '$1-$2-$3');
                    return formatted;
                }

                const memberItem = document.createElement('div');
                memberItem.classList.add('dept_list');

                const memberBox = document.createElement('div');
                memberBox.classList.add('dept_userbox');

                const deptUserName = document.createElement('span');
                deptUserName.classList.add('dept_username');
                deptUserName.textContent = member.name;

                const deptEmp = document.createElement('span');
                deptEmp.classList.add('dept_emp');
                deptEmp.textContent = member.position;

                const deptPhone = document.createElement('span');
                deptPhone.classList.add('dept_phone');
                deptPhone.textContent = formatPhoneNumber(member.tel);

                const deptEmail = document.createElement('p');
                deptEmail.classList.add('dept_email');
                deptEmail.textContent = member.email;

                memberBox.appendChild(deptUserName);
                memberBox.appendChild(document.createTextNode(' ')); // 띄어쓰기 추가
                memberBox.appendChild(deptEmp);
                memberBox.appendChild(document.createTextNode(' '));
                memberBox.appendChild(deptPhone);
                memberBox.appendChild(deptEmail);

                memberItem.appendChild(memberBox);

                myDeptList.appendChild(memberItem);

                
            });
        })
        .catch((error) => {
            console.error('Fetch Error:', error);
        });
    }