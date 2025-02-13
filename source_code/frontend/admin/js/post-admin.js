$(document).ready(function () {
    loadCategories();
    // XỬ LÝ KHI CLICK CÁC NÚT TRÊN NAVBAR MENU
    $('.management-link').click(function(e) {
        e.preventDefault();
        
        // Xóa active class cũ
        $('.management-link').removeClass('active');
        $('.management-section').removeClass('active');
        
        // Thêm active class mới
        const target = $(this).data('target');
        $(this).addClass('active');
        $(`#${target}Container`).addClass('active');
    });

    // TẢI DANH SÁCH THỂ LOẠI VÀO DROP DOWN
    function loadCategories() {
        $.ajax({
            url: 'https://localhost:8443/api/category/getAllCategories',
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function(categories) {
                $('#categoryFilter').empty().append('<option value="">-- Chọn thể loại --</option>');
                categories.forEach(cat => {
                    $('#categoryFilter').append(`<option value="${cat.categoryId}">${cat.categoryName}</option>`);
                });
            }
        });
    }
    
    // TẢI DANH SÁCH BÀI VIẾT DỰA VÀO THỂ LOẠI ĐÃ CHỌN
    function loadPostsByCategory(categoryId) {
        $.ajax({
            url: `https://localhost:8443/api/post/getPostsByCategoryId/${categoryId}`,
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function(posts) {
                renderPosts(posts);
            }
        });
    }
    
    // HÀM TẢI LẠI DANH SÁCH CÁC BÀI VIẾT
    function renderPosts(posts) {
        $('#publicPosts').empty();
        $('#hiddenPosts').empty();
    
        posts.forEach(post => {
            const postElement = `
                <div class="post-item" data-post-id="${post.postId}">
                    <h6>${post.title}</h6>
                    <p class="text-muted small">${post.content.substring(0, 50)}...</p>
                    <div class="d-flex justify-content-end">
                        ${post.active ? 
                            '<button class="btn btn-warning btn-post-action btn-hide">Ẩn</button>' : 
                            '<button class="btn btn-success btn-post-action btn-show">Hiện</button>'}
                        <button class="btn btn-danger btn-post-action btn-delete">Xóa</button>
                    </div>
                </div>
            `;
    
            post.active ? 
                $('#publicPosts').append(postElement) : 
                $('#hiddenPosts').append(postElement);
        });
    }
    
    $(document).on('change', '#categoryFilter', function() {
        const selectedCategory = $(this).val();
        // Ẩn chi tiết bài viết
        $('#publicPostDetail').hide();
        $('#hiddenPostDetail').hide();
        // Hiển thị lại danh sách bài viết
        $('#publicPosts').show();
        $('#hiddenPosts').show();
        // Tải lại danh sách bài viết theo category
        loadPostsByCategory(selectedCategory);
    });
    
    // XỬ LÝ KHI NHẤN NÚT ẨN
    $(document).on('click', '.btn-hide', function(event) {
        event.stopPropagation(); // Ngăn sự kiện click lan lên .post-item
        const postId = $(this).closest('.post-item').data('post-id');
        updatePostStatus(postId, false);
    });
    
    // XỬ LÝ KHI NHẤN NÚT HIỆN
    $(document).on('click', '.btn-show', function(event) {
        event.stopPropagation();
        const postId = $(this).closest('.post-item').data('post-id');
        updatePostStatus(postId, true);
    });
    
    // XỬ LÝ KHI NHẤN NÚT XÓA
    $(document).on('click', '.btn-delete', function(event) {
        event.stopPropagation();
        const postId = $(this).closest('.post-item').data('post-id');
        if (confirm('Xóa bài viết này?')) {
            deletePost(postId);
        }
    });
    
    
    // HÀM THAY ĐỔI TRẠNG THÁI ẨN/HIỆN BÀI VIẾT
    function updatePostStatus(postId, isActive) {
        $.ajax({
            url: `https://localhost:8443/api/admin/post/updateIsActive/${postId}?isActive=${isActive}`,
            type: 'PATCH',
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: () => {
                loadPostsByCategory($('#categoryFilter').val());
            }
        });
    }
    
    // HÀM XÓA BÀI VIẾT
    function deletePost(postId) {
        if (!confirm('Bạn có chắc chắn muốn xóa bài viết này?')) return;
            
            $.ajax({
                url: `https://localhost:8443/api/admin/post/delete?postId=${postId}`,
                type: 'DELETE',
                xhrFields: { withCredentials: true },
                success: function(response) {
                    loadPostsByCategory($('#categoryFilter').val());
                },
                error: function() {
                    alert("Lỗi khi xóa bài viết!");
                }
            });
    }

    // TỪ PHẦN NÀY XỬ LÝ CLICK VÀO BÀI VIẾT TRONG DANH SÁCH ẨN HIỆN
    function loadPostDetail(postId) {
        $.ajax({
            url: `https://localhost:8443/api/post/getPostsByCategoryId/${postId}`,
            type: 'GET',
            xhrFields: {
                withCredentials: true // Quan trọng! Để gửi cookie session
            },
            success: function(post) {
                renderPostDetail(post);
                loadComments(postId);
            }
        });
    }

    function loadComments(postId) {
        $.ajax({
            url: `https://localhost:8443/api/comment/getCommentsByPostId/${postId}`,
            type: 'GET',
            xhrFields: {
                withCredentials: true // Quan trọng! Để gửi cookie session
            },
            success: function(comments) {
                renderComments(comments);
            }
        });
    }

    function renderComments(comments) {
        $('#commentsSection').empty();
        
        comments.forEach(comment => {
            const commentElement = `
                <div class="comment-item" data-comment-id="${comment.commentId}">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <strong>${comment.commenter.fullName}</strong>
                            <span class="text-muted small">${new Date(comment.commentDate).toLocaleString()}</span>
                            <p class="mb-0 mt-1">${comment.content}</p>
                        </div>
                        <div>
                            ${comment.isActive ? 
                                '<button class="btn btn-warning btn-sm comment-action-btn btn-hide-comment">Ẩn</button>' : 
                                '<button class="btn btn-success btn-sm comment-action-btn btn-show-comment">Hiện</button>'}
                            <button class="btn btn-danger btn-sm comment-action-btn btn-delete-comment">Xóa</button>
                        </div>
                    </div>
                </div>
            `;
            $('#commentsSection').append(commentElement);
        });
    }

    // Hàm toggle giữa danh sách và chi tiết
    function togglePostDetail(isPublic) {
        const detailContainerId = isPublic ? '#publicPostDetail' : '#hiddenPostDetail';
        const postsListId = isPublic ? '#publicPosts' : '#hiddenPosts';
    
        // Ẩn container chi tiết và xóa nội dung bên trong (nếu cần)
        $(detailContainerId).hide().empty();
        // Hiện lại container danh sách
        $(postsListId).show();
    
        // Lấy category đang được chọn (giả sử select #categoryFilter đang được chọn)
        const categoryId = $('#categoryFilter').val();
        if (categoryId) {
            // Gọi lại hàm loadPostsByCategory để tải lại danh sách bài viết mới nhất
            loadPostsByCategory(categoryId);
        }
    }
    
    // Sau khi định nghĩa togglePostDetail, gán nó cho window để có thể gọi từ HTML
    window.togglePostDetail = togglePostDetail;
    

    // XỬ LÝ SỰ KIỆN KHI CLICK VÀO BÀI VIẾT
    function renderPostDetail(post, isPublic) {
        const detailContainerId = isPublic ? '#publicPostDetail' : '#hiddenPostDetail';
        const postsListId = isPublic ? '#publicPosts' : '#hiddenPosts';
    
        // Ẩn danh sách và hiển thị chi tiết
        $(postsListId).hide();
        $(detailContainerId).html(`
            <div class="post-detail-content">
                <div class="post-detail-back" onclick="togglePostDetail(${isPublic})">← Quay lại</div>
                <h3>${post.title}</h3>
                <p class="text-muted small">
                    Tác giả: ${post.author.fullName}<br>
                    Ngày đăng: ${new Date(post.postDate).toLocaleDateString()}
                </p>
                <p>${post.content}</p>
                <h5>Bình luận</h5>
                <div class="comments-section"></div>
            </div>
        `).show();
    
        loadComments(post.postId, isPublic);
    }


    // Xử lý click bài viết (phân biệt public/hidden)
    $(document).on('click', '.post-item', function() {
        const postId = $(this).data('post-id');
        const isPublic = $(this).closest('#publicPosts').length > 0; // Kiểm tra post thuộc cột nào
        
        $.ajax({
            url: `https://localhost:8443/api/post/getPostById/${postId}`,
            type: 'GET',
            dataType: 'json',
            xhrFields: {
            withCredentials: true
            },
            success: function(post) {
                renderPostDetail(post, isPublic);
            }
        });
    });

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

    // Hàm lấy postId và isPublic từ comment-item
    function getPostInfoFromComment($commentItem) {
        const $postDetail = $commentItem.closest('#publicPostDetail, #hiddenPostDetail');
        const postId = $postDetail.data('post-id'); // Lấy postId từ container chứa comment
        const isPublic = $postDetail.attr('id') === 'publicPostDetail';
        return { postId, isPublic };
    }
    // COMMENT EVENT
    // Ẩn comment
    $(document).on('click', '.btn-hide-comment', function(event) {
        event.stopPropagation(); // Ngăn không cho sự kiện lan lên các phần tử cha (nếu cần)
        
        const $button = $(this); // Lưu tham chiếu đến nút đã nhấn
        const $commentItem = $button.closest('.comment-item');
        const { postId, isPublic } = getPostInfoFromComment($commentItem);
        const commentId = $commentItem.data('comment-id');
    
        if (confirm('Bạn có chắc chắn muốn ẩn comment này không?')) {
            $.ajax({
                url: `https://localhost:8443/api/admin/comment/updateActive?commentId=${commentId}&active=false`,
                type: 'PATCH',
                xhrFields: { withCredentials: true },
                success: function() {
                    $button
                        .removeClass('btn-warning btn-hide-comment')
                        .addClass('btn-success btn-show-comment')
                        .text('Hiện');
                    $commentItem.data('active', false);
                },
                error: function() {
                    alert("Có lỗi khi ẩn comment!");
                }
            });
        }
    });
    

    // Hiển thị comment
    $(document).on('click', '.btn-show-comment', function(event) {
        event.stopPropagation();
        
        const $button = $(this);
        const $commentItem = $button.closest('.comment-item');
        const { postId, isPublic } = getPostInfoFromComment($commentItem);
        const commentId = $commentItem.data('comment-id');
    
        if (confirm('Bạn có chắc chắn muốn hiển thị comment này không?')) {
            $.ajax({
                url: `https://localhost:8443/api/admin/comment/updateActive?commentId=${commentId}&active=true`,
                type: 'PATCH',
                xhrFields: { withCredentials: true },
                success: function() {
                    $button
                        .removeClass('btn-success btn-show-comment')
                        .addClass('btn-warning btn-hide-comment')
                        .text('Ẩn');
                    $commentItem.data('active', true);
                },
                error: function() {
                    alert("Có lỗi khi hiển thị comment!");
                }
            });
        }
    });
    

    // Xóa comment
    $(document).on('click', '.btn-delete-comment', function(event) {
        event.stopPropagation(); // Ngăn không cho sự kiện lan lên các phần tử cha nếu cần
        const $commentItem = $(this).closest('.comment-item');
        const commentId = $commentItem.data('comment-id');
        const { postId, isPublic } = getPostInfoFromComment($commentItem);

        if (!commentId) {
            alert("Lỗi: Không tìm thấy ID bình luận!");
            return;
        }
    
        if (confirm('Bạn có chắc chắn muốn xóa comment này không?')) {
            $.ajax({
                url: `https://localhost:8443/api/admin/comment/delete?commentId=${commentId}`,
                type: 'DELETE',
                xhrFields: { withCredentials: true },
                cache: false, 
                success: function() {
                    alert("Comment đã được xóa.");
                    $commentItem.remove();
                },
                error: function() {
                    alert("Có lỗi khi xóa comment!");
                }
            });
        }
    });
})