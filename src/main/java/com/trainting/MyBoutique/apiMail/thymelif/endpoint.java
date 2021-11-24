package com.trainting.MyBoutique.apiMail.thymelif;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api"+"/test")
@Slf4j
public class endpoint {

    @Autowired
    EmailSenderService emailService;


    @PostMapping("/test")
    public void test() throws IOException, MessagingException {
        log.info("START... Sending email");

        Mail mail = new Mail();
        mail.setFrom("aziz");//replace with your desired email
        mail.setMailTo("azizskiken@yahoo.com");//replace with your desired email
        mail.setSubject("Email with Spring boot and thymeleaf template!");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Developer!");
        model.put("location", "United States");
        model.put("sign", "Java Developer");
        mail.setProps(model);

        emailService.sendEmail(mail);
        log.info("END... Email sent success");
    }
}
