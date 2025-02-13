// KIỂM TRA SESSION XEM ADMIN ĐÃ ĐĂNG NHẬP HAY CHƯA
$.ajax({
    url: 'https://localhost:8443/api/admin/isLoggedIn', // API kiểm tra session
    type: 'GET',
    xhrFields: {
        withCredentials: true // Quan trọng! Để gửi cookie session
    },
    success: function(response) {
        if (!response) { // Nếu không phải admin
            alert("Bạn phải đăng nhập với quyền admin!");
            window.location.href = "login-admin.html"; // Chuyển về trang đăng nhập
        }
    },
    error: function() { // Nếu API trả về lỗi (chưa đăng nhập)
        alert("Vui lòng đăng nhập trước!");
        window.location.href = "login-admin.html"; // Chuyển về trang đăng nhập
    }
});

$(document).ready(function () {
    // Xử lý đăng xuất
    $('#logoutBtn').click(function() {
        // Thêm logic đăng xuất ở đây
        alert('Đăng xuất thành công!');
        window.location.href = '/login';
    });
})