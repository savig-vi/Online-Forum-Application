CREATE DATABASE OnlineForumApplication;
USE OnlineForumApplication;

CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255), -- NULL nếu đăng nhập qua Google/Facebook
    Email VARCHAR(100) UNIQUE,
    FullName VARCHAR(50),
    PhoneNumber VARCHAR(15),
    Address TEXT,
    RegistrationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    LastLogin DATETIME,
    Role ENUM('Admin', 'Member') DEFAULT 'Member',
    IsActive BOOLEAN DEFAULT TRUE, -- Quản lý trạng thái khóa/xóa tài khoản
    GoogleID VARCHAR(255), -- ID từ Google
    FacebookID VARCHAR(255) -- ID từ Facebook
);

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    CategoryName VARCHAR(100) NOT NULL,
    Description TEXT,
    CreatedBy INT, -- Quản trị viên tạo thể loại
    CreatedDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CreatedBy) REFERENCES Users(UserID)
);

CREATE TABLE Posts (
    PostID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255) NOT NULL,
    Content TEXT NOT NULL,
    CategoryID INT, -- Thể loại bài viết
    AuthorID INT, -- Người đăng bài
    PostDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    Visibility ENUM('Public', 'Friends', 'Followers') DEFAULT 'Public', -- Chế độ hiển thị
    IsActive BOOLEAN DEFAULT TRUE, -- Quản lý trạng thái khóa/xóa bài viết
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (AuthorID) REFERENCES Users(UserID)
);

CREATE TABLE Comments (
    CommentID INT PRIMARY KEY AUTO_INCREMENT,
    PostID INT, -- Bài viết được bình luận
    AuthorID INT, -- Người bình luận
    Content TEXT NOT NULL,
    CommentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    IsActive BOOLEAN DEFAULT TRUE, -- Quản lý trạng thái khóa/xóa bình luận
    FOREIGN KEY (PostID) REFERENCES Posts(PostID),
    FOREIGN KEY (AuthorID) REFERENCES Users(UserID)
);

CREATE TABLE Messages (
    MessageID INT PRIMARY KEY AUTO_INCREMENT,
    SenderID INT, -- Người gửi
    ReceiverID INT, -- Người nhận
    Content TEXT NOT NULL, -- Nội dung tin nhắn (chỉ chữ)
    SentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    IsRead BOOLEAN DEFAULT FALSE, -- Đánh dấu đã đọc hay chưa
    FOREIGN KEY (SenderID) REFERENCES Users(UserID),
    FOREIGN KEY (ReceiverID) REFERENCES Users(UserID)
);

CREATE TABLE Media (
    MediaID INT PRIMARY KEY AUTO_INCREMENT,
    PostID INT, -- Bài viết chứa media (nếu có)
    CommentID INT, -- Bình luận chứa media (nếu có)
    FileURL VARCHAR(255) NOT NULL, -- Đường dẫn đến file
    FileType ENUM('Image', 'Video') NOT NULL,
    UploadedBy INT, -- Người tải lên
    UploadDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (PostID) REFERENCES Posts(PostID),
    FOREIGN KEY (CommentID) REFERENCES Comments(CommentID),
    FOREIGN KEY (UploadedBy) REFERENCES Users(UserID)
);

CREATE TABLE Follows (
    FollowID INT PRIMARY KEY AUTO_INCREMENT,
    FollowerID INT, -- Người theo dõi
    FollowedID INT, -- Người được theo dõi
    FollowDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (FollowerID) REFERENCES Users(UserID),
    FOREIGN KEY (FollowedID) REFERENCES Users(UserID)
);