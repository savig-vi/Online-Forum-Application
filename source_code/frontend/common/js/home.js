$(document).ready(function () {
  var categoryContainer = $('#category-list');
  var postsByCategory = $('#posts-by-category');
  var postDetailContainer = $('#post-detail');
  var selectedCategoryId = null;

  // API KIỂM TRA SESSION NGƯỜI DÙNG
  $.ajax({
    url: 'https://localhost:8443/api/common/get-session',
    type: 'GET',
    xhrFields: {
      withCredentials: true
    },
    success: function (response) {
      if (response.loggedIn) {
        // Nếu người dùng đã đăng nhập, ẩn các nút đăng nhập và đăng ký, hiển thị thông tin cá nhân
        $('#login-link').hide();
        $('#register-link').hide();
        $('#user-info').text('Xin chào, ' + response.user.fullName); // Hiển thị tên người dùng
        $('#user-info').show();
        $('#logout-button').show();
      } else {
        // Nếu chưa đăng nhập, hiển thị các nút đăng nhập và đăng ký
        $('#login-link').show();
        $('#register-link').show();
        $('#user-info').hide();
        $('#logout-button').hide();
      }
    },
    error: function () {
      // Nếu không thể kiểm tra session, giả sử người dùng chưa đăng nhập
      $('#login-link').show();
      $('#register-link').show();
      $('#user-info').hide();
      $('#logout-button').hide();
    }
  });

  // API LẤY DANH SÁCH THỂ LOẠI
  $.ajax({
    url: 'https://localhost:8443/api/common/category/getAll', // Đường dẫn API của bạn
    type: 'GET',
    dataType: 'json',
    success: function (categories) {
      categoryContainer.empty(); // Xóa dữ liệu cũ nếu có
      categories.forEach(category => {
        let categoryHTML = `
          <div class="col-md-4">
            <div class="topic-card" data-category-id="${category.categoryId}">
              <div class="topic-title">${category.categoryName}</div>
              <div class="topic-description">${category.description}</div>
            </div>
          </div>
        `;
        categoryContainer.append(categoryHTML);
      });

      $('.topic-card').click(function () {
        let categoryId = $(this).data('category-id');
        loadPostsByCategory(categoryId);
      });
    },
    error: function () {
      console.error("Lỗi khi tải danh sách thể loại.");
    }
  });

  function loadPostsByCategory(categoryId) {
    selectedCategoryId = categoryId;
    postsByCategory.empty().show();
    postDetailContainer.hide();

    $.ajax({
      url: `https://localhost:8443/api/common/post/getPostsByCategoryId/${categoryId}`,
      type: 'GET',
      dataType: 'json',
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

  function loadPostDetail(postId) {
    postsByCategory.hide();
    postDetailContainer.empty().show();
  
    $.ajax({
      url: `https://localhost:8443/api/common/post/getById/${postId}`,
      type: 'GET',
      dataType: 'json',
      success: function (post) {
        let postDetailHTML = `
          <div class="post-detail">
            <button id="back-to-category" class="btn btn-secondary">← Quay lại</button>
            <h3>${post.title}</h3>
            <p><strong>Người đăng:</strong> ${post.authorName}</p>
            <p><strong>Thời gian đăng:</strong> ${new Date(post.postDate).toLocaleString()}</p>
            <p><strong>Nội dung:</strong> ${post.content}</p>
            <h4>Bình luận:</h4>
            <div id="comments">
              ${post.comments.length > 0 ? post.comments.map(comment => `
                <div class="comment">
                  <p><strong>${comment.author}</strong>: ${comment.content}</p>
                  ${comment.isCurrentUserComment ? `<span class="delete-comment" data-comment-id="${comment.commentId}">Xóa</span>` : ''}
                </div>
              `).join('') : '<p>Chưa có bình luận.</p>'}
            </div>
            <div class="comment-section">
              <textarea id="comment-input" class="form-control" placeholder="Nhập bình luận của bạn..."></textarea>
              <div class="mt-2">
                <button id="attach-media" class="btn btn-secondary btn-sm">Đính kèm ảnh/video</button>
                <input type="file" id="media-input" style="display: none;" accept="image/*, video/*">
              </div>
              <button id="submit-comment" class="btn btn-primary mt-2">Gửi bình luận</button>
            </div>
          </div>
        `;
        postDetailContainer.append(postDetailHTML);
  
        // Sự kiện nút đính kèm
        $('#attach-media').click(function () {
          $('#media-input').click(); // Kích hoạt input file
        });
        
        $('#media-input').change(function () {
          let file = this.files[0];
          if (file) {
            alert(`Bạn đã chọn file: ${file.name}`);
            // Bạn có thể xử lý file ở đây (ví dụ: hiển thị preview hoặc upload lên server)
          }
        });

        // Xử lý sự kiện xóa bình luận
        $('.delete-comment').click(function () {
          let commentId = $(this).data('comment-id');
          deleteComment(commentId, postId);
        });
  
        // Xử lý sự kiện gửi bình luận
        $('#submit-comment').click(function () {
          let commentContent = $('#comment-input').val();
          if (commentContent.trim() !== "") {
            submitComment(postId, commentContent);
          } else {
            alert("Vui lòng nhập nội dung bình luận.");
          }
        });
  
        // Xử lý sự kiện quay lại
        $('#back-to-category').click(function () {
          postDetailContainer.hide();
          loadPostsByCategory(selectedCategoryId);
        });
      },
      error: function () {
        console.error("Lỗi khi tải chi tiết bài viết.");
      }
    });
  }

  function submitComment(postId, content) {
    $.ajax({
      url: 'https://localhost:8443/api/common/comment/add',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        postId: postId,
        content: content
      }),
      xhrFields: {
        withCredentials: true
      },
      success: function (response) {
        if (response.success) {
          loadPostDetail(postId); // Tải lại chi tiết bài viết để hiển thị bình luận mới
        } else {
          alert("Gửi bình luận thất bại: " + response.message);
        }
      },
      error: function (xhr, status, error) {
        alert("Lỗi khi gửi bình luận: " + xhr.responseText);
      }
    });
  }

  // Đăng xuất
  $("#logout-button").click(function () {
    $.ajax({
      url: 'https://localhost:8443/api/common/logout',
      type: 'POST',
      xhrFields: {
        withCredentials: true
      },
      success: function (response) {
        alert(response.message); // Hiển thị thông báo "Đăng xuất thành công!"
        window.location.href = "login.html"; // Chuyển hướng về trang đăng nhập
      },
      error: function (xhr, status, error) {
        alert('Lỗi đăng xuất: ' + xhr.responseText);
      }
    });
  });
});