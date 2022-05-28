## Chủ đề: Hệ thống hỏi đáp online

Nhóm môn học Lập trình web 04 - nhóm BTL 24

#### 1. Mô tả dự án

##### 1.1 Các chức năng

- Đăng kí/Đăng nhập.

- Thêm câu hỏi, trả lời câu hỏi.

- Bình luận vào câu hỏi, câu trả lời.

- Đánh giá câu hỏi, câu trả lời.

- Với người dùng là quản trị:
  
  - Quản lý người dùng: 
    
    - Xem danh sách người dùng, số lượng câu hỏi, câu trả lời, bình luận của mỗi người dùng.
    
    - Đánh dấu/bỏ đánh dấu người dùng là quản trị.
  
  - Quản lý câu hỏi/câu trả lời: 
    
    - Xem danh sách các câu hỏi, câu trả lời, điểm đánh giá, số bình luận.
    
    - Xoá câu hỏi/câu trả lời.
  
  - Quản lý lĩnh vực:
    
    - Xem danh sách lĩnh vực, số câu hỏi thuộc mỗi lĩnh vực.
    
    - Sửa tên lĩnh vực.
    
    - Xoá lĩnh vực chưa có câu hỏi nào.

##### 1.2 Thiết kế hệ thống

- Thiết kế CSDL

<img title="CSDL" src="imgs\csdl.png" alt="" width="443" data-align="center">

- Cấu trúc thư mục src của dự án
  
  ```
  +---main
  |   +---java
  |   |   \---qaweb
  |   |       |   QawebApplication.java
  |   |       |   
  |   |       +---controller
  |   |       |       AnswerController.java
  |   |       |       AskController.java
  |   |       |       CommentController.java
  |   |       |       IndexController.java
  |   |       |       LoginController.java
  |   |       |       ManagerController.java
  |   |       |       QuestionController.java
  |   |       |       RegisterController.java
  |   |       |       SearchPostController.java
  |   |       |       VoteController.java
  |   |       |       
  |   |       +---model
  |   |       |       Category.java
  |   |       |       Comment.java
  |   |       |       Post.java
  |   |       |       User.java
  |   |       |       Vote.java
  |   |       |       
  |   |       \---repository
  |   |               CategoryRepository.java
  |   |               CommentRepository.java
  |   |               PostRepository.java
  |   |               UserRepository.java
  |   |               VoteRepository.java
  |   |               
  |   \---resources
  |       |   application.properties
  |       |   
  |       +---static
  |       |   |   script.js
  |       |   |   styles.css
  |       |   |   
  |       |   \---simplemde
  |       |           simplemde.min.css
  |       |           simplemde.min.js
  |       |           
  |       \---templates
  |               answer.html
  |               ask.html
  |               category-manager.html
  |               comment.html
  |               confirm-delete-post.html
  |               edit-answer.html
  |               edit-comment.html
  |               edit-question.html
  |               error.html
  |               index.html
  |               login.html
  |               manager.html
  |               post-manager.html
  |               post.html
  |               question-detail.html
  |               register.html
  |               statistics.html
  |               user-manager.html
  |               user.html
  |               
  \---test
      \---java
          \---qaweb
                  QawebApplicationTests.java
  ```

- Framework, thư viện, công cụ sử dụng
  
  - Spring
  
  - Bootstrap
  
  - SimpleMDE Markdown Editor

##### 1.3 Các màn hình demo

<img title="Trang chủ - danh sách câu hỏi" src="images_demo\trangchu.png" alt="" width="443">

<img title="Đăng kí" src="images_demo\dangki.png" alt="" width="443">

<img title="Đăng nhập" src="images_demo\dangnhap.png" alt="" width="443">

<img title="Đặt câu hỏi" src="images_demo\datcauhoi.png" alt="" width="443">

<img title="Chi tiết câu hỏi" src="images_demo\chitietcauhoi.png" alt="" width="443">

<img title="Trả lời câu hỏi" src="images_demo\traloicauhoi.png" alt="" width="443">

<img title="Bình luận" src="images_demo\comment.png" alt="" width="443">

<img title="Quản lý người dùng" src="images_demo\qlnguoidung.png" alt="" width="443">

<img title="Quản lý lĩnh vực" src="images_demo\qllinhvuc.png" alt="" width="443">

<img title="Quản lý câu hỏi/câu trả lời" src="images_demo\qlcauhoicautraloi.png" alt="" width="443">

<img title="Thống kê" src="images_demo\thongke.png" alt="" width="443">

<img title="Tìm Kiếm Bài Viết" src="images_demo\timkiembaiviet" alt="" width="443">

<img title="Lọc bài viết theo lĩnh vực" src="images_demo\locbaiviettheolinhvuc.png" alt="" width="443">

#### 2. Đóng góp của các thành viên

- Đỗ Ngọc Cường
  
  - Xoá câu hỏi, câu trả lời, bình luận.
  - Quản lí người dùng, thống kê.
  - Tìm kiếm bài viết
  - Lọc bài viết theo lĩnh vực

- Nguyễn Thanh Hùng
  
  - Đăng kí/Đăng nhập, đổi mật khẩu.
  - Thêm câu hỏi, câu trả lời, bình luận.
  - Thêm, sửa, xoá đánh giá.
  - Quản lí câu hỏi/câu trả lời.

- Nguyễn Như Mạnh
  
  - Sửa câu hỏi, câu trả lời, bình luận.
  - Quản lý  lĩnh vực.
