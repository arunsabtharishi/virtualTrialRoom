package com.epsilon.vtr.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

    @Autowired
    TrialRoomService trialRoomService;

    public void sendEmail1(TrailRoomVO trailRoomVO) throws MessagingException {

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
            MimeBodyPart textPart = new MimeBodyPart();

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

    @Override
    public void sendEmail(TrailRoomVO trailRoomVO) throws MessagingException {

        MimeMessagePreparator preparator = getMessagePreparator(trailRoomVO);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Your Trail on Dress with Templates");
            helper.setFrom("irvingfashionstore@gmail.com");
            helper.setTo(trailRoomVO.getEmailAddress());

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("trailRoomVO", trailRoomVO);

            String text = geFreeMarkerTemplateContent(model);//Use Freemarker or Velocity
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H1>Dear "+ trailRoomVO.getFirstName() +","+trailRoomVO.getLastName()+", <br> Check Out our new Products which might interest you. </H1><img height=\"150\" width=\"150\" src=\"cid:image\"> <br>";
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            String profilePhotoFileName = "testImage";
            String profilePhotoFileExtension = "png";
            FileOutputStream fos;
            try {
                File profilePhotoTempFile = File.createTempFile(profilePhotoFileName,"."+profilePhotoFileExtension);
                fos = new FileOutputStream(profilePhotoTempFile);
                fos.write(trailRoomVO.getProfileTrailPhoto());
                fos.close();
                messageBodyPart = new MimeBodyPart();
                DataSource fds = new FileDataSource(profilePhotoTempFile);

                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", "<image>");

                // add image to the multipart
                multipart.addBodyPart(messageBodyPart);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // put everything together
            message.setContent(multipart);

            mailSender.send(message);

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
