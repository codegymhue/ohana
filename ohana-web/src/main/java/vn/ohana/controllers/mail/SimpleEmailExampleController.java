package vn.ohana.controllers.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.ohana.controllers.post.PostAPI;
import vn.ohana.post.PostService;

//import static vn.ohana.config.MailConfig.FRIEND_EMAIL;

//@Controller
//public class SimpleEmailExampleController {
//
//    @Autowired
//    public JavaMailSender emailSender;
//
//    @ResponseBody
//    @RequestMapping("/sendSimpleEmail")
//    public String sendSimpleEmail() {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo("anhtrduong@gmail.com");
//        message.setSubject("Email thông báo kiểm duyệt bài đăng");
//        message.setText("Bài viết của bạn đã bị khóa!");
//
//        this.emailSender.send(message);
//
//        return "redirect:/";
//    }
//}
