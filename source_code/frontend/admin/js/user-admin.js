// user-admin.js
$(document).ready(function() {
    fetchPostCategoryStats();
    // Hiển thị phần quản lý User khi nhấn vào link trên navbar
    $('.management-link').click(function() {
        var target = $(this).data('target');
        $('.management-section').hide();
        $('#' + target + 'Container').show();
    });

    // Hiển thị biểu đồ khi nhấn vào "Admin Panel"
    $('.navbar-brand').click(function() {
        $('.management-section').hide();
        $('#dashboardContainer').show();
        fetchPostCategoryStats();
    });


    function fetchPostCategoryStats() {
        $.ajax({
            url: 'https://localhost:8443/api/admin/post/getPostCategoryStats', // Đường dẫn API của bạn để lấy thống kê bài viết theo thể loại
            method: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function(response) {
                console.log(response);
                renderPostCategoryChart(response);
                renderPostCategoryPieChart(response);
            },
            error: function(error) {
                alert('Có lỗi xảy ra khi lấy dữ liệu thống kê.');
            }
        });
    }

    // Tạo biểu đồ cột cho số lượng bài viết theo thể loại
    function renderPostCategoryChart(data) {
        var ctx = document.getElementById('postCategoryChart').getContext('2d');
        var postCategoryChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.labels, // Tên các thể loại
                datasets: [{
                    label: 'Số lượng bài viết',
                    data: data.values, // Số lượng bài viết tương ứng với từng thể loại
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: false,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    // Tạo biểu đồ tròn cho tỷ lệ bài viết theo thể loại
    function renderPostCategoryPieChart(data) {
        var ctx = document.getElementById('postCategoryPieChart').getContext('2d');
        var postCategoryPieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: data.labels, // Tên các thể loại
                datasets: [{
                    label: 'Tỷ lệ bài viết',
                    data: data.values, // Số lượng bài viết tương ứng với từng thể loại
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    datalabels: {
                        formatter: (value, ctx) => {
                            let sum = ctx.chart.data.datasets[0].data.reduce((a, b) => a + b, 0);
                            let percentage = (value * 100 / sum).toFixed(2) + "%";
                            return percentage;
                        },
                        color: 'red',
                        font: {
                            weight: 'bold'
                        }
                    }
                }
            },
            plugins: [ChartDataLabels]
        });
    }

    fetchPostCategoryStats();

    // Hàm để lấy danh sách người dùng từ API
    function fetchUserList() {
        $.ajax({
            url: 'https://localhost:8443/api/admin/user/getAllUsers', // Đường dẫn API của bạn để lấy danh sách người dùng
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function(response) {
                renderUserList(response);
            },
            error: function(error) {
                alert('Có lỗi xảy ra khi lấy danh sách người dùng.');
            }
        });
    }

    // Chuyển đổi timestamp sang định dạng ngày tháng
    function formatDate(timestamp) {
        var date = new Date(timestamp);
        var day = String(date.getDate()).padStart(2, '0');
        var month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
        var year = date.getFullYear();
        return `${day}/${month}/${year}`;
    }

    // Hiển thị danh sách người dùng
    function renderUserList(users) {
        var userList = $('#userList');
        userList.empty();
        users.forEach(function(user, index) {
            var formattedDate = formatDate(user.registrationDate);
            var userRow = `
                <tr>
                    <th scope="row">${index + 1}</th>
                    <td>${user.userName}</td>
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.address}</td>
                    <td>${formattedDate}</td>
                    <td>
                        <label class="switch">
                            <input type="checkbox" ${user.active ? 'checked' : ''} data-id="${user.userId}">
                            <span class="slider"></span>
                        </label>
                    </td>
                </tr>
            `;
            userList.append(userRow);
        });
    }

    // Cập nhật trạng thái hoạt động của người dùng
    $('#userList').on('change', 'input[type="checkbox"]', function() {
        var _userId = $(this).data('id');
        var _active = $(this).is(':checked');
        var _status = _active ? 'hoạt động' : 'khóa';

        // Gọi API cập nhật trạng thái người dùng
        $.ajax({
            url: ' https://localhost:8443/api/admin/user/updateActive', // Đường dẫn API của bạn để cập nhật trạng thái người dùng
            type: 'PATCH',
            contentType: 'application/json',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify({
                userId: _userId,
                active: _active
            }),
            success: function(response) {
                alert('Trạng thái người dùng đã được cập nhật thành công: ' + _status);
            },
            error: function(error) {
                console.log('Có lỗi xảy ra khi cập nhật trạng thái người dùng.');
                console.log(_userId, _active);
            }
        });
    });

    // Gọi hàm lấy danh sách người dùng khi trang được tải
    fetchUserList();
});