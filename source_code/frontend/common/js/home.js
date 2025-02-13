$(document).ready(function () {
  var categoryContainer = $('#category-list');
  var postsByCategory = $('#posts-by-category');
  var postDetailContainer = $('#post-detail');
  var selectedCategoryId = null;
  let currentUser = null; 


  // Lấy thông tin người dùng từ API khi đăng nhập
  $.ajax({
    url: 'https://localhost:8443/api/get-session',
    type: 'GET',
    dataType: 'json',
    xhrFields: {
      withCredentials: true
    },
    success: function(response) {
      if (response.loggedIn) {
        const user = response.user;
        currentUser = user;
        // Nếu người dùng đã đăng nhập, ẩn các nút đăng nhập và đăng ký, hiển thị thông tin cá nhân
        $('#login-link').hide();
        $('#register-link').hide();
        $('#user-info').text('Xin chào, ' + user.fullName).show(); // Hiển thị tên người dùng
        $('#logout-button').show();
        $('#update-info-button').show();

        // Điền thông tin người dùng vào form khi mở modal
        $('#updateInfoModal').on('show.bs.modal', function (event) {
          $('#user_name').val(user.userName);
          $('#full_name').val(user.fullName);
          $('#phone_number').val(user.phoneNumber);
          $('#email').val(user.email);
          $('#address').val(user.address);
        });
      } else {
        // Nếu chưa đăng nhập, hiển thị các nút đăng nhập và đăng ký
        $('#login-link').show();
        $('#register-link').show();
        $('#user-info').hide();
        $('#logout-button').hide();
        $('#update-info-button').hide();
      }
    },
    error: function() {
      // Nếu không thể kiểm tra session, giả sử người dùng chưa đăng nhập
      $('#login-link').show();
      $('#register-link').show();
      $('#user-info').hide();
      $('#logout-button').hide();
      $('#update-info-button').hide();
    }
  });

  // Xử lý cập nhật thông tin người dùng
  $('#update-info-form').submit(function(event) {
    event.preventDefault();
    // Lấy thông tin từ form
    const updatedUser = {
      userId: currentUser.userId,
      userName: $('#user_name').val(),
      fullName: $('#full_name').val(),
      phoneNumber: $('#phone_number').val(),
      email: $('#email').val(),
      address: $('#address').val(),
      newPasswordHash: $('#new_password').val()
    };

    // Kiểm tra mật khẩu hiện tại
    const currentPasword = currentUser.passwordHash;
    if ($('#current_password').val() !== currentPasword) {
      alert('Mật khẩu hiện tại chưa chính xác');
      return;
    }

    // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới
    const confirmPassword = $('#confirm_password').val();
    if ($('#new_password').val() !== $('#confirm_password').val()) {
      alert('Mật khẩu mới và xác nhận mật khẩu mới không khớp!');
      return;
    }

    // Gửi thông tin cập nhật đến server qua API
    $.ajax({
      url: 'https://localhost:8443/api/user/updateInformation', // Thay bằng URL API của bạn để cập nhật thông tin người dùng
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify(updatedUser),
      success: function(response) {
        console.log('Thông tin người dùng đã được cập nhật:', response);
        // Cập nhật thông tin hiển thị trên trang
        // currentUser.userName = updatedUser.userName;
        currentUser.fullName = updatedUser.fullName;
        currentUser.phoneNumber = updatedUser.phoneNumber;
        currentUser.email = updatedUser.email;
        currentUser.address = updatedUser.address;
        $('#user-info').text('Xin chào, ' + currentUser.fullName);
        $('#updateInfoModal').modal('hide');
      },
      error: function(error) {
        console.error('Lỗi khi cập nhật thông tin người dùng:', error);
      }
    });
  });

  // API LẤY DANH SÁCH THỂ LOẠI
  $.ajax({
    url: 'https://localhost:8443/api/category/getAllCategories', // Đường dẫn API của bạn
    type: 'GET',
    dataType: 'json',
    xhrFields: {
      withCredentials: true
    },
    success: function (categories) {
      categoryContainer.empty(); // Xóa dữ liệu cũ nếu có
      categories.forEach(category => {
        let categoryHTML = `
          <div class="col-md-4 mb-4">
            <a href="posts.html?categoryId=${category.categoryId}" 
              class="topic-card d-block text-decoration-none" 
              data-category-id="${category.categoryId}">
              <div class="card h-100">
                <div class="card-body">
                  <h5 class="card-title">${category.categoryName}</h5>
                  <p class="card-text">${category.description}</p>
                </div>
              </div>
            </a>
          </div>
        `;
        categoryContainer.append(categoryHTML);
      });
    },
    error: function () {
      console.error("Lỗi khi tải danh sách thể loại.");
    }
  });

  // API TẢI CÁC BÀI VIẾT THEO THỂ LOẠI
  function loadPostsByCategory(categoryId) {
    selectedCategoryId = categoryId;
    postsByCategory.empty().show();
    postDetailContainer.hide();

    $.ajax({
      url: `https://localhost:8443/api/common/post/getPostsByCategoryId/${categoryId}`,
      type: 'GET',
      dataType: 'json',
      xhrFields: { withCredentials: true },
      success: function (posts) {
        if (posts.length === 0) {
          postsByCategory.append('<p>Không có bài viết nào trong thể loại này.</p>');
        } else {
          posts.forEach(post => {
            let postHTML = `
              <div class="topic-post" data-post-id="${post.postId}">
                <div class="topic-title">${post.title}</div>
                <div class="topic-description">${post.content}</div>
              </div>
            `;
            postsByCategory.append(postHTML);
          });

          $('.topic-post').off('click').on('click', function () {
            let postId = $(this).data('post-id');
            loadPostDetail(postId);
          });
        }
      },
      error: function () {
        console.error("Lỗi khi tải bài viết theo thể loại.");
      }
    });
  }

  // Đăng xuất
  $("#logout-button").click(function () {
    $.ajax({
      url: 'https://localhost:8443/api/common/logout',
      type: 'POST',
      contentType: 'application/json',
      xhrFields: {
        withCredentials: true
      },
      success: function (response) {
        window.location.href = "login.html"; // Chuyển hướng về trang đăng nhập
        alert(response.message); // Hiển thị thông báo "Đăng xuất thành công!"
      },
      error: function (xhr, status, error) {
        alert('Lỗi đăng xuất: ' + xhr.responseText);
      }
    });
  });

});