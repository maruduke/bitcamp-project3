document.addEventListener('DOMContentLoaded', () => {
    const jwt = sessionStorage.getItem('jwt');
    fetch('http://localhost:8080/member',{
        method: 'GET',
        headers:{
            'Authorization': `Bearer ${jwt}`,
            'Content-Type' : 'application/json'
        }

    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const orgChartData = {
                name: "CEO",
                position: `
                <div class="employee">
                    <div class="name">${data[0].name} ${data[0].position}</div>
                    <div class="info" style="display:none;">
                        <div class="email">${data[0].email}</div>
                        <div class="tel">${data[0].tel}</div>
                    </div>
                </div>
                `,
                children: [
                    { name: "인사팀", position: generateDepartmentPosition(data, "인사") },
                    { name: "운영팀", position: generateDepartmentPosition(data, "운영") },
                    { name: "디자인팀", position: generateDepartmentPosition(data, "디자인") },
                    { name: "개발팀", position: generateDepartmentPosition(data, "개발") },
                    { name: "영업팀", position: generateDepartmentPosition(data, "영업") }
                ]
            };

            // OrgChart 생성
            $(".box").orgchart({
                data: orgChartData,
                nodeContent: "position",
                pan: true,
                zoom: true,
            });

            // 이름 클릭 시 개인 정보 보여주기
            $('.box').on('click', '.name', function() {
                // 클릭한 이름의 부모 요소인 .employee 내의 .info 요소 토글
                $(this).parent('.employee').find('.info').toggle();
            });
        })
        .catch(error => {
            console.error('Fetch Error:', error);
        });
});
        function generateDepartmentPosition(data, department) {
            const departmentEmployees = data.filter(member => member.dept === department);
            let departmentPosition = "";
            departmentEmployees.forEach(employee => {
                departmentPosition += `
                <div class="employee">
                    <div class="name">${employee.name} ${employee.position}</div>
                    <div class="info" style="display:none;">
                        <div class="email">${employee.email}</div>
                        <div class="tel">${employee.tel}</div>
                </div>
            </div>
                `;
            });
            return departmentPosition;
        }