package APIS;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {
	public Correo(){}
	 public void SentMail(String to,String subject,String msn){
		 
		 final String username = "ruralhousenotifications@bytebreakers.esy.es";
			final String password = "amapola2011";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "mx1.hostinger.es");
			props.put("mail.smtp.port", "2525");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("ruralhousenotifications@bytebreakers.esy.es"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
				message.setSubject(subject);
				message.setText(msn);
				
				message.setContent(msn, "text/html; charset=utf-8");
				
				Transport.send(message);

				System.out.println("Mail sent..");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		 
	 }

}
