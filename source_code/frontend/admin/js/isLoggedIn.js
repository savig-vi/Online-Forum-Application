$.ajax({
    url: 'https://localhost:8443/api/admin/isLoggedIn',
    type: 'GET',
    dataType: 'json',
    xhrFields: {
        withCredentials: true
    },
    success: function (response) {
        if (response) {
            $("#adminContent").show();
        } else {
            alert('Yêu cầu đăng nhập với tài khoản admin!')
            window.location.href = 'login-admin.html';
        }
    },
    error: function () {
        window.location.href = 'login-admin.html'; // Nếu có lỗi, cũng quay về trang login
    }
});