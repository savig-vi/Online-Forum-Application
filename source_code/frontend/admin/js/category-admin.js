$(document).ready(function () {
    loadCategories();

    // TÌM KIẾM THỂ LOẠI
    $('#categorySearch').on('input', function() {
        const searchTerm = $(this).val().toLowerCase();
        let count = 1;
        $('#categoryList .list-group-item').each(function() {
            const categoryName = $(this).find('h5').text().toLowerCase();
            console.log('Lấy ra để so sanh: ' +  categoryName);
            $(this).toggle(categoryName.includes(searchTerm));
        });
    });

    // XỬ LÝ KHI CLICK VÀO CATEGORY TRÊN NAVBAR
    $('.management-link[data-target="category"]').click(function(e) {
        e.preventDefault();
        $('#categoryContainer').addClass('active');
    });

    // TẢI DANH SÁCH THỂ LOẠI
    function loadCategories() {
        $.ajax({
            url: 'https://localhost:8443/api/category/getAllCategories',
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function(categories) {
                renderCategories(categories);
            }
        });
    }

    // HIỂN THỊ DANH SÁCH THỂ LOẠI
    function renderCategories(categories) {
        $('#categoryList').empty();
        categories.forEach(cat => {
            const categoryItem = `
                <div class="list-group-item d-flex justify-content-between align-items-center" data-category-id="${cat.categoryId}">
                    <div>
                        <h5 class="mb-1">${cat.categoryName}</h5>
                        <p class="mb-1">${cat.description}</p>
                    </div>
                    <div>
                        <button class="btn btn-warning btn-sm btn-edit-category">Sửa</button>
                        <button class="btn btn-danger btn-sm btn-delete-category">Xóa</button>
                    </div>
                </div>
            `;
            $('#categoryList').append(categoryItem);
        });
    }


    // XỬ LÝ KHI CLICK SỬA
    $(document).on('click', '.btn-edit-category', function() {
        const categoryId = $(this).closest('.list-group-item').data('category-id');
        $.ajax({
            url: `https://localhost:8443/api/admin/category/getCategoryById?categoryId=${categoryId}`,
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function(category) {
                $('#editCategoryName').val(category.categoryName);
                $('#editCategoryDescription').val(category.description);
                $('#editCategoryModal').data('category-id', categoryId).modal('show');
            }
        });
    });

    // LƯU THAY ĐỔI
    $('#saveCategoryChanges').click(function() {
        const categoryId = $('#editCategoryModal').data('category-id');
        const updatedCategory = {
            categoryId: categoryId,
            categoryName: $('#editCategoryName').val(),
            description: $('#editCategoryDescription').val(),
            createdBy: null
        };
        $.ajax({
            url: `https://localhost:8443/api/admin/category/updateCategory`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(updatedCategory),
            xhrFields: {
                withCredentials: true
            },
            success: function() {
                alert('Cập nhật thể loại thành công');
                $('#editCategoryModal').modal('hide');
                loadCategories();
            }
        });
    });

    // XỬ LÝ KHI CLICK XÓA
    $(document).on('click', '.btn-delete-category', function() {
        const categoryId = $(this).closest('.list-group-item').data('category-id');
        if (confirm('Bạn có chắc chắn muốn xóa thể loại này không?')) {
            $.ajax({
                url: `https://localhost:8443/api/admin/category/deleteCategory?categoryId=${categoryId}`,
                type: 'DELETE',
                xhrFields: {
                    withCredentials: true
                },
                success: function() {
                    alert('Xóa thành công');
                    loadCategories();
                }
            });
        }
    });
});