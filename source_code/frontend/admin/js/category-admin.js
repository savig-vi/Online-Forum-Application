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
        // e.preventDefault();
        // $('#categoryContainer').addClass('active');
        // Cái này mới thêm
        e.preventDefault();
        $('.management-section').removeClass('active');
        const target = $(this).data('target');
        $('#' + target + 'Container').addClass('active');
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
            },
            error: function(xhr, status, error) {
                alert('Có lỗi xảy ra khi tải danh sách thể loại: ' + error);
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

    // XỬ LÝ KHI CLICK "TẠO THỂ LOẠI MỚI"
    $('#createCategoryBtn').click(function() {
        $('#createCategoryModal').modal('show');
    });

    // LƯU THỂ LOẠI MỚI
    $('#saveNewCategory').click(function() {
        const newCategoryName = $('#newCategoryName').val();
        const newCategoryDescription = $('#newCategoryDescription').val();
        if (isCategoryNameExists(newCategoryName)) {
            alert('Thể loại này đã có rồi bạn ơi.');
            return;
        }
        const newCategory = {
            categoryName: normalizeString(newCategoryName),
            description: newCategoryDescription.trim()
        };
        $.ajax({
            url: `https://localhost:8443/api/admin/category/createCategory`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(newCategory),
            xhrFields: {
                withCredentials: true
            },
            success: function() {
                alert('Tạo thể loại mới thành công');
                $('#createCategoryModal').modal('hide');
                loadCategories();
            }
        });
    });

    // HÀM CHUẨN HÓA TÊN THỂ LOAỊ
    function normalizeString(str) {
        return str.trim().replace(/\s+/g, ' ').toLowerCase();
    }

    // HÀM KIỂM TRA TÊN THỂ LOẠI ĐÃ TỒN TẠI CHƯA, DỰA VÀO HTML RENDER
    function isCategoryNameExists(categoryName) {
    const normalizedCategoryName = normalizeString(categoryName);
    let exists = false;
    $('#categoryList .list-group-item').each(function() {
        const existingCategoryName = normalizeString($(this).find('h5').text());
        if (existingCategoryName === normalizedCategoryName) {
            exists = true;
            return false; // Dừng vòng lặp
        }
    });
    return exists;
}
});