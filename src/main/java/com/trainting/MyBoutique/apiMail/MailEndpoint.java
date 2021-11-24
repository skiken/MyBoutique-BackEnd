package com.trainting.MyBoutique.apiMail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api"+"/mailApi")
public class MailEndpoint {

        @Autowired
        MailService mailService;

        @PostMapping()
        public String sendSimpleEmail(String to, String subject, String text) throws UnsupportedEncodingException {
            this.mailService.sendSimpleMessage(to,subject,text);
            return "Email sent successfully";
        }



    @PostMapping("/sendingEmail/{orderId}")
    public MailResponse sendOrderEmail(@RequestBody MailPojo mailPojo,@PathVariable Long orderId) {

        return mailService.sendOrderEmailWithTemplate(mailPojo,orderId);

    }

    @PostMapping("/sendingConfirmationEmail/{orderId}")
    public MailResponse sendConfirmationEmail(@RequestBody MailPojo mailPojo,@PathVariable Long orderId) throws IOException, MessagingException {

        return mailService.sendConfirmationEmailWithTemplate(mailPojo,orderId);

    }

}
