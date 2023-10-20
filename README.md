![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/19fec480-47d3-4685-aa61-391de0a180e0)# ltat_lab2
## Bài 7:

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/2447700a-d407-49d1-b9a2-9b7746d2b5ad)

- Mục tiêu của bài này là xóa được user ```carlos``` .
- Trước hết mình phải login bằng user và pass được cho trước.
- Sau một hồi scan thì mình tìm được đường dẫn ```/admin``` .

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/dd419f75-29bf-43d1-b51a-9f758e2dc25d)

- Có vẻ mình không thể vào được với người dùng bình thường.
- Check sơ qua cookie thì mình thấy một cookie có giá trị là ```session``` .
- Mình sẽ thử bật burpsuite lên để test thử.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/d502bf30-8304-40cb-86d9-9f0b932b65a2)

- Khi gửi req đến ```/admin``` server sẽ kiểm tra biến session của chúng ta, khi mình decode thử thì ra được một đối tượng là user.
- Điểm đáng chú ý ở đây là giá trị admin của session này đang là 0 ( do biến bool quyết định )
- Giờ mình chỉ cần chuyển nó về thành 1 và gửi req thì có thể bypass phần này.

  ![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/932acce0-7e60-4306-9eda-9602885769af)

- Giờ thì khi xóa user ```carlos``` chúng ta cũng sẽ làm tương tự.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/6018c782-e03a-4a65-a71e-21dd2e4ef85c)

```session=Tzo0OiJVc2VyIjoyOntzOjg6InVzZXJuYW1lIjtzOjY6IndpZW5lciI7czo1OiJhZG1pbiI7YjoxO30%3d```

## Bài 8:

- Bài này lúc đầu vào cũng giống bài trước nên mình sẽ vào thẳng phần session.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/1eb9bc4f-1399-4c68-816c-81066b427d44)

```O:4:"User":2:{s:8:"username";s:6:"wiener";s:12:"access_token";s:32:"f4fb8seskjly3qw37onpt6ujkprn1co7";}```

- Ở đây chúng ta có vài trường mới trong class User này đó là ```username``` và ```access_token```.
- Theo mình hiểu thì ```access_token``` là một trường dùng để kiểm tra xem class có bị sửa đổi gì hay không và nếu nó sai thì sẽ không cho ta truy cập.
- Còn về phần username thì ở đây bắt buộc phải là ```administrator``` .
- Mình đọc hint thì có vẻ bài này được viết trên PHP, và mình nghỉ ngay đến một lỗi trong quá trình so sánh kiểu dữ liệu của PHP đó là php type juggling.
- Từ những dữ kiện trên, mình đã đã tạo được một class mới với giá trị như sau.

```O:4:"User":2:{s:8:"username";s:13:"administrator";s:12:"access_token";i:0;}```
- Với Class này, mình đã đổi tên của user và đồng thời ép kiểu dữ liệu của access_token là int với giá trị là 0. Điều này làm cho biến access_token sẽ bị lỗi và trả về giá trị đúng.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/e353703a-36f7-4295-9906-9aace41bb32e)

- Vậy là mình đã bypass qua phần admin, bây giờ chỉ cần xóa User là solve được bài này.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/e1018c97-39d1-4d8e-bfd8-5ff91227087a)

```session=Tzo0OiJVc2VyIjoyOntzOjg6InVzZXJuYW1lIjtzOjEzOiJhZG1pbmlzdHJhdG9yIjtzOjEyOiJhY2Nlc3NfdG9rZW4iO2k6MDt9```

## Bài 9:
