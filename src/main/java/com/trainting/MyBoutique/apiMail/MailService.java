package com.trainting.MyBoutique.apiMail;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.trainting.MyBoutique.dto.*;
import com.trainting.MyBoutique.services.*;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService  {
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration config;

    public void sendSimpleMessage(String to, String subject, String text) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("mohamedaziz.skiken@esprit.tn.com", "AzizMarketPlace")));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("mohamedaziz.skiken@esprit.tn.com", "AzizMarketPlace"));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("facture.pdf", file);
        emailSender.send(message);
    }

    public MailResponse sendOrderEmailWithTemplate(MailPojo mailPojo,Long orderId) {

        MailResponse response = new MailResponse();
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
            // add attachment
            //helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t,this.getMap(orderId));
            helper.setTo(mailPojo.getTo());
            helper.setText(html, true);
            helper.setSubject(mailPojo.getSubject());
            helper.setFrom(new InternetAddress("mohamedaziz.skiken@esprit.tn.com",mailPojo.getFrom()));
            emailSender.send(message);
            response.setMessage("mail send to : " + mailPojo.getTo());
            response.setStatus(Boolean.TRUE);
        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }

    public MailResponse sendConfirmationEmailWithTemplate(MailPojo mailPojo,Long orderId) throws IOException, MessagingException {

        MailResponse response = new MailResponse();
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("confirmation-email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t,this.getMap(orderId));
            helper.setTo(mailPojo.getTo());
            helper.setText(html, true);
            helper.setSubject(mailPojo.getSubject());
            helper.setFrom(new InternetAddress("mohamedaziz.skiken@esprit.tn.com",mailPojo.getFrom()));
            emailSender.send(message);
            response.setMessage("mail send to : " + mailPojo.getTo());
            response.setStatus(Boolean.TRUE);
        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+e.getMessage()+this.getMap(orderId));
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }




    private CustomerDto getCustomerInformation(Long idOrder){
        CartDto cartDto=this.cartService.findByOrderId(idOrder);
        return cartDto.getCustomerDto(); }

    private OrderDto getOrderDetails(Long idOrder){
        return this.orderService.findOrderById(idOrder);
    }

    private List<OrderItemDto> getOrderItems(Long idOrder){
        return this.orderItemService.findByIdOrder(idOrder);

    }
    private  Map<String, Object> getMap(Long orderId) throws IOException, MessagingException {

       Map<String, Object> model = new HashMap<>();
       CustomerDto customerDto=this.getCustomerInformation(orderId);
       OrderDto orderDto=this.getOrderDetails(orderId);
       List<OrderItemDto> list=this.getOrderItems(orderId);
       Map<ProductDto,Long> productQuantity = new HashMap<>();

       for (OrderItemDto orderItemDto: list) {
           productQuantity.put(this.productService.findById(orderItemDto.getProductId()),orderItemDto.getQuantity());
       }

       System.out.println(productQuantity);
       model.put("mapProductQuantity",productQuantity);
       model.put("customer",customerDto);
       model.put("order",orderDto);
       model.put("totalToPay",orderDto.getTotalPrice().doubleValue()+7);

    return  model;
    }







    }
