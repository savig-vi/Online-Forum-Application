// Hàm load comments (thêm tham số isPublic)
function loadComments(postId, isPublic) {
    $.ajax({
        url: `https://localhost:8443/api/comment/getCommentsByPostId/${postId}`,
        method: 'GET',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function(comments) {
            const selector = isPublic ? '#publicPostDetail .comments-section' : '#hiddenPostDetail .comments-section';
            renderComments(comments, selector);
        }
    });
}

// Hàm render comments (nhận tham số là danh sách comment và selector của container)
function renderComments(comments, selector) {
    const $container = $(selector).empty();
    
    comments.forEach(comment => {
        // Nếu comment.isActive là true, hiển thị nút Ẩn, ngược lại hiển thị nút Hiện
        const actionButtons = comment.active ? 
            `<button class="btn btn-warning btn-sm comment-action-btn btn-hide-comment">Ẩn</button>` : 
            `<button class="btn btn-success btn-sm comment-action-btn btn-show-comment">Hiện</button>`;
        
        const commentElement = `
            <div class="comment-item" data-comment-id="${comment.commentId}">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <strong>${comment.commenter.fullName}</strong>
                        <span class="text-muted small">${new Date(comment.commentDate).toLocaleString()}</span>
                        <p class="mb-0 mt-1">${comment.content}</p>
                    </div>
                    <div>
                        ${actionButtons}
                        <button class="btn btn-danger btn-sm comment-action-btn btn-delete-comment">Xóa</button>
                    </div>
                </div>
            </div>
        `;
        $container.append(commentElement);
    });
}

// COMMENT EVENT
    // 1. Xử lý sự kiện Ẩn comment
    $(document).on('click', '.btn-hide-comment', function() {
        const $commentItem = $(this).closest('.comment-item');
        const commentId = $commentItem.data('comment-id');
        
        // Lấy postId từ phần tử cha chứa bài viết
        const $postItem = $commentItem.closest('.post-item');
        const postId = $postItem.data('post-id');

        // Kiểm tra bài viết có thuộc danh sách public hay không
        const isPublic = $postItem.closest('#publicPosts').length > 0; 

        if (confirm('Bạn có chắc chắn muốn ẩn comment này không?')) {
            $.ajax({
                url: `https://localhost:8443/api/admin/comment/updateActive?commentId=${commentId}&active=false`,
                type: 'PATCH',
                xhrFields: {
                    withCredentials: true
                },
                success: function(response) {
                    alert("Comment đã được ẩn thành công.");
                    // Gọi lại loadComments với postId và isPublic để cập nhật danh sách comment
                    loadComments(postId, isPublic);
                },
                error: function() {
                    alert("Có lỗi khi ẩn comment!");
                }
            });
        }
    });

    // 2. Xử lý sự kiện Hiện comment
    $(document).on('click', '.btn-show-comment', function() {
        const commentId = $(this).closest('.comment-item').data('comment-id');
        if (confirm('Bạn có chắc chắn muốn hiện comment này không?')) {
            $.ajax({
                url: `https://localhost:8443/api/admin/comment/updateIsActive/${commentId}?isActive=true`,
                type: 'PATCH',
                xhrFields: {
                    withCredentials: true
                },
                success: function(response) {
                    alert("Comment đã được hiển thị thành công.");
                    // Reload lại comment nếu cần
                    // Ví dụ: loadComments(currentPostId, selector);
                },
                error: function() {
                    alert("Có lỗi khi hiển thị comment!");
                }
            });
        }
    });

    // 3. Xử lý sự kiện Xóa comment
    $(document).on('click', '.btn-delete-comment', function() {
        const $commentItem = $(this).closest('.comment-item');
        const commentId = $commentItem.data('comment-id');
        if (confirm('Bạn có chắc chắn muốn xóa comment này không?')) {
            $.ajax({
                url: `https://localhost:8443/api/admin/comment/delete?commentId=${commentId}`,
                type: 'DELETE',
                xhrFields: {
                    withCredentials: true
                },
                success: function(response) {
                    alert("Comment đã được xóa thành công.");
                    // Loại bỏ comment khỏi giao diện
                    $commentItem.remove();
                },
                error: function() {
                    alert("Có lỗi khi xóa comment!");
                }
            });
        }
    });