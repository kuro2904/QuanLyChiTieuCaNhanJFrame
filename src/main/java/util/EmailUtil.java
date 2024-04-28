package util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import config.MailConfig;

public class EmailUtil {

    public void sendMail(String to, String code) throws IOException {

        Properties pros;
		try {
			pros = new MailConfig().loadMailProperties();
			
			Authenticator auth = new Authenticator() {
	            @Override
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	                // TODO Auto-generated method stub
	                return new javax.mail.PasswordAuthentication(pros.getProperty("mail.from"), pros.getProperty("mail.password"));
	            }
	        };

	        // Create Working Session

	        Session ses = Session.getInstance(pros, auth);

	        // Send Email

	        // Create a new message
	        MimeMessage msg = new MimeMessage(ses);
	        try {
	            msg.addHeader("Content-Type", "text/HTML; charset=UTF-8");

	            // Mail sender
	            msg.setFrom(pros.getProperty("mail.from"));

	            // Recipients
	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

	            // Subject
	            msg.setSubject("Authorize Account");

	            // Set Date to send email

	            msg.setSentDate(new Date());

	            // Set reply

	            msg.setReplyTo(null); // Do not reply

	            // Construct the HTML content
	            String htmlContent = "<html><body><p>Your security code is: <strong>" + code + "</strong></p></body></html>";

	            // Create a multipart message
	            MimeMultipart multipart = new MimeMultipart();
	            MimeBodyPart htmlPart = new MimeBodyPart();
	            htmlPart.setContent(htmlContent, "text/html");

	            // Add the HTML part to the multipart message
	            multipart.addBodyPart(htmlPart);

	            // Set the multipart as the message content
	            msg.setContent(multipart);

	            // sending Email

	            Transport.send(msg);

	        } catch (MessagingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
}
