$(document).ready(function () {
    let currentUserId;
    // Lấy postId từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const postId = urlParams.get('postId'); // Giả sử bạn truyền postId qua query parameter
    // Lấy thông tin bài viết từ API
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
                },
                error: function () {
                    alert("Lỗi tải bài viết!");
                }
            });
        }


    // Kiểm tra session
    $.ajax({
        url: 'https://localhost:8443/api/get-session',
        type: 'GET',
        dataType: 'json',
        xhrFields: { withCredentials: true }, // Đảm bảo gửi cookie session
        success: function (data) {
            if (data.loggedIn) {
                currentUserId = data.user.userId;
                $('#login-link, #register-link').hide();
                $('#user-info').text('Xin chào, ' + data.user.fullName).show();
                $('#logout-button').show();

                // Xử lý gửi bình luận
                $('#comment-form').on('submit', function (e) {
                    e.preventDefault();
                    const commentContent = $('#comment-content').val().trim();

                    if (commentContent) {
                        $.ajax({
                            url: 'https://localhost:8443/api/comment/createComment',
                            type: 'POST',
                            contentType: 'application/json',
                            xhrFields: { withCredentials: true }, // Gửi cookie
                            data: JSON.stringify({
                                postId: postId,
                                content: commentContent
                            }),
                            success: function () {
                                $('#comment-content').val(''); // Reset textarea
                                loadComments(); // Gọi hàm tải lại bình luận
                            },
                            error: function () {
                                alert("Lỗi khi gửi bình luận!");
                            }
                        });
                    }
                });

                // Tải bình luận ban đầu
                loadComments();
            } else {
                // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
                // alert("Bạn cần đăng nhập để bình luận!");
                // window.location.href = 'login.html';
            }
        }
    });

    function loadComments() {
        $.ajax({
            url: `https://localhost:8443/api/comment/getCommentsByPostId/${postId}`,
            type: 'GET',
            dataType: 'json',
            xhrFields: { withCredentials: true },
            success: function (comments) {
                const commentsList = $('#comments-list');
                commentsList.empty(); // Xóa danh sách cũ
                comments.forEach(comment => {
                    const isOwner = comment.commenter.userId === currentUserId;
                    const commentHTML = `
                        <div class="card mb-3" data-comment-id="${comment.commentId}">
                            <div class="card-body">
                                <p class="card-text">${comment.content}</p>
                                ${isOwner ? `
                                <div class="comment-actions mt-2"> <!-- Thêm wrapper cho các nút -->
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

    // Xử lý xóa comment
    $(document).on('click', '.delete-comment', function () {
        const commentId = $(this).closest('.card').data('comment-id');
        if (confirm('Bạn chắc chắn muốn xóa?')) {
            $.ajax({
                url: `https://localhost:8443/api/comment/deleteComment/${commentId}`,
                type: 'DELETE',
                xhrFields: { withCredentials: true },
                success: function () {
                    loadComments();
                }
            });
        }
    });

    // Xử lý sửa comment
    $(document).on('click', '.edit-comment', function () {
        const card = $(this).closest('.card');
        const commentId = card.data('comment-id');
        const contentPara = card.find('.card-text');
        const actionDiv = card.find('.comment-actions'); // Thêm div chứa các nút
        const currentContent = contentPara.text().trim();

        // Ẩn nút sửa/xóa ngay khi bắt đầu chỉnh sửa
        actionDiv.hide(); // ****************** FIX 1: Ẩn nút ngay lập tức ******************

        // Tạo form sửa
        const editForm = `
            <div class="edit-form">
                <textarea class="form-control mb-2">${currentContent}</textarea>
                <div class="text-end">
                    <button class="btn btn-primary btn-sm save-edit">Lưu</button>
                    <button class="btn btn-secondary btn-sm cancel-edit ms-2">Hủy</button>
                </div>
            </div>
        `;
        
        // Chèn form vào sau content
        contentPara.after(editForm); // ********** FIX 2: Dùng after() thay vì replaceWith() **********
        contentPara.hide(); // Ẩn nội dung gốc

        // Hủy sửa
        card.on('click', '.cancel-edit', function () {
            $('.edit-form').remove();
            contentPara.show();
            actionDiv.show();
            card.find('.error-message').remove(); // Thêm dòng này để xóa thông báo lỗi
        });

        // Lưu thay đổi
        card.on('click', '.save-edit', function () {
            const newContent = card.find('textarea').val().trim();
            // Kiểm tra nội dung trống
            if (!newContent) {
                // Hiển thị thông báo lỗi
                if (!card.find('.error-message').length) {
                    card.find('.edit-form').append(
                        '<div class="error-message text-danger mt-2">Nội dung không được để trống!</div>'
                    );
                }
                return; // Dừng không gửi request
            }

            $.ajax({
                url: `https://localhost:8443/api/comment/updateComment/${commentId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ content: newContent }),
                xhrFields: { withCredentials: true },
                success: function () {
                    loadComments(); // Tải lại danh sách
                }
            });
        });
    });

});