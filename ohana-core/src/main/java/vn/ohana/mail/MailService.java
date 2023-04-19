package vn.ohana.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.ohana.config.MailConfig;

import java.util.concurrent.CompletableFuture;

@Service
public class MailService {

    @Autowired
    public JavaMailSender emailSender;

    @Async
    public CompletableFuture<Boolean> sendPostApprovedMail (String email) throws InterruptedException{
        SimpleMailMessage message = new SimpleMailMessage();


        message.setFrom(MailConfig.MY_EMAIL);
        message.setTo(email);
        message.setSubject("THÔNG BÁO KIỂM DUYỆT BÀI ĐĂNG");
        message.setText("Ohana xin thông báo: Bài viết của bạn đã được đăng trên hệ thống website Ohana! Xin cảm ơn Quý Khách hàng luôn tin tưởng ủng hộ!\n\nTrân trọng!\nOhana team!");

        try {
            this.emailSender.send(message);
            return CompletableFuture.completedFuture(Boolean.TRUE);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Boolean.TRUE);
        }
    }
    @Async
    public CompletableFuture<Boolean>  sendPostRefusedMail (String email) throws InterruptedException{
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(MailConfig.MY_EMAIL);
        message.setTo(email);
        message.setSubject("THÔNG BÁO KIỂM DUYỆT BÀI ĐĂNG");
        message.setText("Ohana xin thông báo: Bài viết của bạn đã bị thu hồi do vi phạm một số điều khoản và không được đăng trên website Ohana! Rất mong Quý Khách hàng điều chỉnh nọi dung bài đăng để được xét duyệt đăng tải trong bài viết tiếp theo! \n\nTrân trọng!\nOhana team!");

        try {
            this.emailSender.send(message);
            return CompletableFuture.completedFuture(Boolean.TRUE);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Boolean.TRUE);
        }
    }
}
