package com.jke.jkemq.service;

import com.jke.jkemq.util.LogUtil;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Configuration
@Service
public class JkeMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${jke.notification.sendgrid.key}")
    private String sendgridKey;

    public String sendMail(String from, String to, String cc, String subject, String msg) {

        LogUtil.log("JkeMailSender : sendMail started" );

        LogUtil.logDebug("JkeMailSender : sendMail -->" + from + " : " + to + " : "+ subject);

        Email fromEmail = new Email(from);
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", msg);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendgridKey);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            LogUtil.logDebug("JkeMailSender : sendMail : Response Status Code -->" + from + " : " + to + " : "+ response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.log("JkeMailSender : sendMail error" );
            return "Mail Error";
        }

        LogUtil.log("JkeMailSender : sendMail completed" );
        return "Mail Sent";
    }


    public String sendMail123(String from, String to, String cc, String subject, String msg) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        try {
            if (from != null) {
                mimeMessageHelper.setFrom(from);
            }
            if (cc != null) {
                mimeMessageHelper.setCc(cc);
            }

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Mail Error";
        }

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "Mail Error";
        }
        return "Mail Sent";
    }
}
