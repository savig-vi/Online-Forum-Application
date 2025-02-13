$(document).ready(() => {
    $('#register-form').submit((event) => {
        event.preventDefault();
        let userName = $("#userName").val();
        let passwordHash = $("#passwordHash").val();
        let confirmPasswordHash = $("#confirmPasswordHash").val();
        let email = $("#email").val();
        let fullName = $("#fullName").val();
        let phoneNumber = $("#phoneNumber").val();
        let address = $("#address").val();

        if (passwordHash !== confirmPasswordHash) {
            alert("Mật khẩu và xác nhận mật khẩu không khớp!");
            return;
        }

        let formData = {
            userName: userName,
            passwordHash: passwordHash, // Gửi password đã hash nếu cần
            email: email,
            fullName: fullName,
            phoneNumber: phoneNumber,
            address: address
        };
        $.ajax({
            url: 'https://localhost:8443/api/user/register',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(formData), // Chuyển đối tượng JS thành JSON
            success: function (response) {
                alert('Đăng ký thành công. Vui lòng đăng nhập lại!');
                window.location.href = "login.html";
            },
            error: function (xhr, status, error) {
                alert("Lỗi đăng ký: " + xhr.responseText);
            }
        })
    }
    )
})