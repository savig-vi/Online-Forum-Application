$(document).ready(function () {
    $("#navbar-container").load("navbar.html");
    let currentUserId = null;
    const urlParams = new URLSearchParams(window.location.search);
    const postId = urlParams.get('postId');

    // Kiểm tra session khi tải trang
    $.ajax({
        url: 'https://localhost:8443/api/get-session',
        type: 'GET',
        dataType: 'json',
        xhrFields: { withCredentials: true },
        success: function (data) {
            if (data.loggedIn) {
                currentUserId = data.user.userId;
                $('#login-link, #register-link').hide();
                $('#user-info').text('Xin chào, ' + data.user.fullName).show();
                $('#logout-button').show();
            } else {
                $('#user-info, #logout-button').hide();
                $('#login-link, #register-link').show();
            }
            loadComments();
        }
    });

    // Load bài viết và kiểm tra quyền sở hữu
    if (postId) {
        $.ajax({
            url: `https://localhost:8443/api/post/getPostById/${postId}`,
            type: 'GET',
            dataType: 'json',
            xhrFields: { withCredentials: true },
            success: function (post) {
                $('#post-title').text(post.title);
                $('#post-content').text(post.content);
                $('#post-author').text(post.author.fullName);
                $('#post-date').text(new Date(post.postDate).toLocaleString());
                
                // Kiểm tra quyền sở hữu để hiển thị nút xóa
                if (currentUserId && post.author.userId === currentUserId) {
                    $('#post-actions').show();
                }

            },
            error: function () {
                alert("Lỗi tải bài viết!");
            }
        });
    }

    // Thêm sự kiện xử lý cho nút 3 chấm
    $(document).on('click', '.edit-post', function() {
        const currentTitle = $('#post-title').text().trim();
        const currentContent = $('#post-content').text().trim();
        $('#edit-post-content').val(currentContent);
        $('#edit-post-title').val(currentTitle);
        $('#editPostModal').modal('show');
    });

    // Xử lý sửa bài viết
    $('#edit-post-form').on('submit', function(e) {
        e.preventDefault();
        const newContent = $('#edit-post-content').val().trim();
        const newTitle = $('#edit-post-title').val().trim();
        
        if (!newContent || !newTitle) {
            alert("Nội dung không được để trống!");
            return;
        }

        $.ajax({
            url: `https://localhost:8443/api/user/post/update/${postId}`,
            type: 'PUT',
            contentType: 'application/json',
            xhrFields: { withCredentials: true },
            data: JSON.stringify({ 
                title: newTitle,
                content: newContent
            }),
            success: function() {
                $('#post-title').text(newTitle);
                $('#post-content').text(newContent);
                $('#editPostModal').modal('hide');
            },
            error: function() {
                alert("Lỗi khi cập nhật bài viết!");
                console.log(newTitle);
                console.log(newContent);
            }
        });
    });

    // SỰ KIỆN XÓA BÀI VIẾT
    $(document).on('click', '.delete-post', function() {
        if (!confirm('Bạn có chắc chắn muốn xóa bài viết này?')) return;
        
        $.ajax({
            url: `https://localhost:8443/api/user/post/delete/${postId}`,
            type: 'DELETE',
            xhrFields: { withCredentials: true },
            success: function(response) {
                // Giả sử API trả về categoryId của bài viết đã xóa
                categoryId = response.data.categoryId;
                // window.location.href = `home.html?category=${categoryId}`;
                window.location.href = `posts.html?categoryId=${categoryId}`;
            },
            error: function() {
                alert("Lỗi khi xóa bài viết!");
            }
        });
    });

    // SỰ KIỆN GỬI BÌNH LUẬN
    $('#comment-form').on('submit', function (e) {
        e.preventDefault();
        const commentContent = $('#comment-content').val().trim();

        if (!commentContent) {
            alert("Vui lòng nhập nội dung bình luận!");
            return;
        }

        // Kiểm tra đăng nhập khi gửi
        $.ajax({
            url: 'https://localhost:8443/api/get-session',
            type: 'GET',
            dataType: 'json',
            xhrFields: { withCredentials: true },
            success: function (sessionData) {
                if (!sessionData.loggedIn) {
                    alert("Bạn cần đăng nhập để gửi bình luận!");
                    window.location.href = 'login.html';
                    return;
                }

                $.ajax({
                    url: 'https://localhost:8443/api/comment/createComment',
                    type: 'POST',
                    contentType: 'application/json',
                    xhrFields: { withCredentials: true },
                    data: JSON.stringify({
                        postId: postId,
                        content: commentContent
                    }),
                    success: function () {
                        $('#comment-content').val('');
                        loadComments();
                    },
                    error: function () {
                        alert("Lỗi khi gửi bình luận!");
                    }
                });
            }
        });
    });

    // TẢI BÌNH LUẬN
    function loadComments() {
        $.ajax({
            url: `https://localhost:8443/api/comment/getCommentsByPostId/${postId}`,
            type: 'GET',
            dataType: 'json',
            success: function (comments) {
                const commentsList = $('#comments-list');
                commentsList.empty();

                comments.forEach(comment => {
                    const isOwner = currentUserId && (comment.commenter.userId === currentUserId);
                    
                    const commentHTML = `
                        <div class="card mb-3" data-comment-id="${comment.commentId}">
                            <div class="card-body">
                                <p class="card-text" style="white-space: pre-line;">${comment.content}</p>
                                ${isOwner ? `
                                <div class="comment-actions mt-2">
                                    <button class="btn btn-danger btn-sm delete-comment">Xóa</button>
                                    <button class="btn btn-secondary btn-sm edit-comment ms-2">Sửa</button>
                                </div>
                                ` : ''}
                                <p class="text-muted">${comment.commenter.fullName} - ${new Date(comment.commentDate).toLocaleString()}</p>
                            </div>
                        </div>`;
                    commentsList.append(commentHTML);
                });
            },
            error: function () {
                alert("Lỗi tải bình luận!");
            }
        });
    }

    // SỰ KIỆN XÓA BÌNH LUẬN
    $(document).on('click', '.delete-comment', function () {
        if (!confirm('Bạn chắc chắn muốn xóa?')) return;
        
        const commentId = $(this).closest('.card').data('comment-id');
        $.ajax({
            url: `https://localhost:8443/api/comment/deleteComment/${commentId}`,
            type: 'DELETE',
            xhrFields: { withCredentials: true },
            success: function () {
                loadComments();
            },
            error: function () {
                alert("Lỗi khi xóa bình luận!");
            }
        });
    });

    // SỰ KIỆN SỬA BÌNH LUẬN
    $(document).on('click', '.edit-comment', function () {
        const card = $(this).closest('.card');
        const commentId = card.data('comment-id');
        const contentPara = card.find('.card-text');
        const actionDiv = card.find('.comment-actions');
        const currentContent = contentPara.text().trim();

        // ẨN NÚT VÀ NỘI DUNG GỐC
        actionDiv.hide();
        contentPara.hide();

        // TẠO FORM SỬA
        const editForm = `
            <div class="edit-form">
                <textarea class="form-control mb-2">${currentContent}</textarea>
                <div class="text-end">
                    <button class="btn btn-primary btn-sm save-edit">Lưu</button>
                    <button class="btn btn-secondary btn-sm cancel-edit ms-2">Hủy</button>
                </div>
            </div>
        `;
        
        contentPara.after(editForm);

        // HỦY SỬA
        card.on('click', '.cancel-edit', function () {
            card.find('.edit-form').remove();
            contentPara.show();
            actionDiv.show();
        });

        // LƯU SỬA
        card.on('click', '.save-edit', function () {
            const newContent = card.find('textarea').val().trim();
            
            if (!newContent) {
                if (!card.find('.error-message').length) {
                    card.find('.edit-form').append(
                        '<div class="error-message text-danger mt-2">Nội dung không được để trống!</div>'
                    );
                }
                return;
            }

            $.ajax({
                url: `https://localhost:8443/api/comment/updateComment/${commentId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ content: newContent }),
                xhrFields: { withCredentials: true },
                success: function () {
                    loadComments();
                },
                error: function () {
                    alert("Lỗi khi cập nhật bình luận!");
                }
            });
        });
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