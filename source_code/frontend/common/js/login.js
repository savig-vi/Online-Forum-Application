$.ajax({
    url: 'https://localhost:8443/api/get-session',
    type: 'GET',
    xhrFields: {
        withCredentials: true
    },
    success: function (response) {
        if (response.loggedIn) {
            window.location.href = "home.html";
        }
    },
    error: function (xhr, status, error) {
        console.log("Chưa đăng nhập, tiếp tục hiển thị form đăng nhập.");
    }
});

$(document).ready(function () {
    $('#login-form').submit(function (event) {
        event.preventDefault(); // Ngừng hành động mặc định của form

        let var_email = $('#email').val();
        let var_passwordHash = $('#passwordHash').val();

        if (email && passwordHash) {
            $.ajax({
                url: 'https://localhost:8443/api/login', // Địa chỉ API của server đăng nhập
                type: 'POST',
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    email: var_email,
                    passwordHash: var_passwordHash
                },
                success: function (response) {
                    window.location.href = "home.html"; // Redirect tới trang chủ sau khi đăng nhập
                },
                error: function (xhr, status, error) {
                    if (xhr.status === 401)
                        alert('Sai thông tin đăng nhập!');
                    else alert('Lỗi đăng nhập: ' + xhr.responseText);
                }
            });
        } else {
            alert('Vui lòng nhập đầy đủ thông tin!');
        }
    });
});