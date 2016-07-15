package com.epsilon.vtr.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.epsilon.vtr.model.ProductOrder;
import com.epsilon.vtr.model.TrailRoom;
import com.epsilon.vtr.vo.TrailRoomVO;

import freemarker.template.Configuration;

@SuppressWarnings("deprecation")
@Service("mailService")
public class MailServiceImpl implements MailService{

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    VelocityEngine velocityEngine;

    @Autowired
    Configuration freemarkerConfiguration;


    @Override
    public void sendEmail(TrailRoomVO trailRoomVO) throws MessagingException {

        MimeMessagePreparator preparator = getMessagePreparator(trailRoomVO);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Your Trail on Dress with Templates");
            helper.setFrom("irvingfashionstore@gmail.com");
            helper.setTo("vbora@epsilon.com");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("trailRoomVO", trailRoomVO);

            String text = geFreeMarkerTemplateContent(model);//Use Freemarker or Velocity
            System.out.println("Template content : "+text);
            text = "<img alt=\"image\" height=\"150\" width=\"150\" src=\"data:image/png;base64,"+trailRoomVO.getBase64EncodedForProfileTrailPhoto()+"\" />";
            // use the true flag to indicate you need a multipart message
            helper.setText(text, true);
            message.setContent(text, "text/html; charset=utf-8");
            mailSender.send(message);
            System.out.println("Message has been sent.............................");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private MimeMessagePreparator getMessagePreparator(final TrailRoomVO trailRoomVO){

        System.out.println("Preparing Message");
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("Your Trail on Dress with Templates");
                helper.setFrom("irvingfashionstore@gmail.com");
                helper.setTo(trailRoomVO.getEmailAddress());

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("trailRoomVO", trailRoomVO);

                String text = geFreeMarkerTemplateContent(model);//Use Freemarker or Velocity
                System.out.println("Template content : "+text);

                // use the true flag to indicate you need a multipart message
                helper.setText(text, true);

                //Additionally, let's add a resource as an attachment as well.
                //helper.addAttachment("cutie.png", new ClassPathResource("linux-icon.png"));

            }
        };
        return preparator;
    }


    public String geVelocityTemplateContent(Map<String, Object> model){
        StringBuffer content = new StringBuffer();
        try{
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/vmtemplates/velocity_mailTemplate.vm", model));
            return content.toString();
        }catch(Exception e){
            System.out.println("Exception occured while processing velocity template:"+e.getMessage());
        }
          return "";
    }


    public String geFreeMarkerTemplateContent(Map<String, Object> model){
        StringBuffer content = new StringBuffer();
        try{
         content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                 freemarkerConfiguration.getTemplate("fm_mailTemplate.txt"),model));
         return content.toString();
        }catch(Exception e){
            System.out.println("Exception occured while processing fmtemplate:"+e.getMessage());
        }
          return "";
    }
}
