$(document).ready(function () {
    $('#loginForm').submit(function(e) {
        e.preventDefault(); // Ngừng hành động mặc định của form (không reload trang)
        let email = $('#email').val();
        let passwordHash = $('#passwordHash').val();

        $.ajax({
            url: 'https://localhost:8443/api/admin/login',
            type: 'POST',
            xhrFields: {
                withCredentials: true
            },
            data: {
                email: email,
                passwordHash: passwordHash
            },
            success: function(response) {
                if (response) {
                    alert("Đăng nhập tài khoản admin thành công!");
                    window.location.href = 'home-admin.html';
                } else alert('Sai thông tin đăng nhập admin');
            },
            error: function() {
                alert("Sai tài khoản hoặc mật khẩu!");
            }
        });
    });
    
});