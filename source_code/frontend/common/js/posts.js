$(document).ready(function () {
  $("#navbar-container").load("navbar.html");
  // Lấy categoryId từ URL
  const urlParams = new URLSearchParams(window.location.search);
  const categoryId = urlParams.get('categoryId');

  // Kiểm tra session người dùng (copy từ home.js)
  $.ajax({
    url: 'https://localhost:8443/api/get-session',
    type: 'GET',
    xhrFields: { withCredentials: true },
    success: function (response) {
      if (response.loggedIn) {
        $('#login-link').hide();
        $('#register-link').hide();
        $('#user-info').text('Xin chào, ' + response.user.fullName);
        $('#user-info').show();
        $('#logout-button').show();
      } else {
        $('#login-link').show();
        $('#register-link').show();
        $('#user-info').hide();
        $('#logout-button').hide();
      }
    }
  });
  // Load tên chủ đề
  if (categoryId) {
    $.ajax({
      url: `https://localhost:8443/api/category/getCategoryNameById/${categoryId}`,
      type: 'GET',
      dataType: 'text',
      xhrFields: { withCredentials: true },
      success: function (category) {
        $('#category-name').text(category);
      }, error: function () {
        alert("Lỗi tải tên thể loại");
      }
    });

    // Load bài viết
    $.ajax({
      url: `https://localhost:8443/api/post/getPostsByCategoryId/${categoryId}`,
      type: 'GET',
      dataType: 'json',
      xhrFields: { withCredentials: true },
      success: function (posts) {
        const postsList = $('#posts-list');
        postsList.empty();

        posts.forEach(post => {
          postsList.append(`
            <div class="col-md-6 mb-4">
              <div class="card h-100" data-id="${post.postId}">
                <div class="card-body">
                  <h5 class="card-title">${post.title}</h5>
                  <p class="card-text">${post.content.substring(0, 25)}...</p>
                  <p class="text-muted">Tác giả: ${post.author.fullName}</p>
                  <p class="text-muted">Ngày đăng: ${new Date(post.postDate).toLocaleString()}</p>
                </div>
              </div>
            </div>
          `);
        });
      }
    });

  } else {
    alert('Không tìm thấy chủ đề!');
    window.location.href = "home.html";
  }

  $(document).on("click", ".card", function () {
    const postId = $(this).data("id"); // Lấy ID bài viết
    window.location.href = `post-detail.html?postId=${postId}`;
  });

  $('#create-post-btn').click(function() {
    $.ajax({
      url: 'https://localhost:8443/api/get-session',
      type: 'GET',
      xhrFields: { withCredentials: true },
      success: function(response) {
        if (!response.loggedIn) {
          if (confirm('Bạn cần đăng nhập để tạo bài viết. Chuyển đến trang đăng nhập?')) {
            window.location.href = 'login.html';
          }
          return;
        }
        $('#createPostModal').modal('show');
      }
    });
  });

  // Xử lý submit bài viết
  $('#submit-post-btn').click(function() {
    const title = $('#post-title').val().trim();
    const content = $('#post-content').val().trim();
    
    if (!title || !content) {
      alert('Vui lòng điền đầy đủ tiêu đề và nội dung');
      return;
    }

    const postData = {
      title: title,
      content: content,
      categoryId: categoryId
    };

    $.ajax({
      url: 'https://localhost:8443/api/user/post/create',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(postData),
      xhrFields: { withCredentials: true },
      success: function(response) {
        window.location.href = `post-detail.html?postId=${response.postId}`;
      },
      error: function(xhr) {
        alert(`Lỗi tạo bài viết: ${xhr.responseJSON?.message || 'Lỗi không xác định'}`);
      }
    });
  });

  // Reset form khi đóng modal
  $('#createPostModal').on('hidden.bs.modal', function() {
    $('#create-post-form')[0].reset();
  });

  // ĐĂNG XUẤT
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