package stageapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

	Session newSession = null;
	MimeMessage mimeMessage = null;

	public static void main(String args[]) throws AddressException, MessagingException, IOException {
		
	}

	void sendEmail(String user, String password) throws MessagingException {
		String fromUser = user;
		String fromUserPassword = password; // 

		String emailHost = "smtp.gmail.com";
		Transport transport = newSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserPassword);
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
		System.out.println("Email successfully sent!!!");
	}

	MimeMessage draftEmailsans_att(String Subject, String Body, List<String> emails)
			throws AddressException, MessagingException, IOException {
		

		List<String> emailReceipients1 = new ArrayList<>();

		emailReceipients1.addAll(emails);

		String emailSubject = Subject;
		String emailBody = Body;
		mimeMessage = new MimeMessage(newSession);

		for (String element : emailReceipients1) {
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(element));
		}

		mimeMessage.setSubject(emailSubject);

		MimeMultipart multiPart = new MimeMultipart();

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(emailBody);

		multiPart.addBodyPart(messageBodyPart);
		mimeMessage.setContent(multiPart);

		return mimeMessage;
	}

	MimeMessage draftEmailsans_att1(String Subject, String Body, String email)
			throws AddressException, MessagingException, IOException {
		

		List<String> emailReceipients1 = new ArrayList<>();

		emailReceipients1.add(email);

		
		String emailSubject = Subject;
		String emailBody = Body;
		mimeMessage = new MimeMessage(newSession);

		for (String element : emailReceipients1) {
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(element));
		}

		mimeMessage.setSubject(emailSubject);

		MimeMultipart multiPart = new MimeMultipart();

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(emailBody);

		multiPart.addBodyPart(messageBodyPart);
		mimeMessage.setContent(multiPart);

		return mimeMessage;
	}

	MimeMessage draftEmail(String f1, String f2, String Subject, String Body, List<String> emails)
			throws AddressException, MessagingException, IOException {
		

		List<String> emailReceipients1 = new ArrayList<>();

		emailReceipients1.addAll(emails);

		
		String b = "";
		String emailSubject = Subject;
		String emailBody = Body;
		mimeMessage = new MimeMessage(newSession);

		for (String element : emailReceipients1) {
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(element));
		}

		mimeMessage.setSubject(emailSubject);

		MimeMultipart multiPart = new MimeMultipart();

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(emailBody);

		File f = new File(f1);

		if (f.exists()) {
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			attachmentBodyPart.attachFile(f);
			multiPart.addBodyPart(attachmentBodyPart);
		}

		File k = new File(f2);
		if (k.exists()) {
			MimeBodyPart attachmentBodyPart2 = new MimeBodyPart();
			attachmentBodyPart2.attachFile(k);
			multiPart.addBodyPart(attachmentBodyPart2);
		}

		multiPart.addBodyPart(messageBodyPart);
		mimeMessage.setContent(multiPart);

		return mimeMessage;
	}

	MimeMessage draftEmail1(File[] f1, String Subject, String Body, List<String> emails)
			throws AddressException, MessagingException, IOException {

		List<String> emailReceipients1 = new ArrayList<>();

		emailReceipients1.addAll(emails);

		String b = "";// C:\\Users\\UTENTE\\Desktop\\send.txt
		String emailSubject = Subject;
		String emailBody = Body;
		mimeMessage = new MimeMessage(newSession);

		for (String element : emailReceipients1) {
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(element));
		}

		mimeMessage.setSubject(emailSubject);

		MimeMultipart multiPart = new MimeMultipart();

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(emailBody);

		if (f1 != null) {
			for (File f : f1) {

				MimeBodyPart attachmentBodyPart = new MimeBodyPart();
				File file = new File(f.getAbsolutePath());
				attachmentBodyPart.attachFile(file);
				multiPart.addBodyPart(attachmentBodyPart);

			}
		}

		multiPart.addBodyPart(messageBodyPart);
		mimeMessage.setContent(multiPart);

		return mimeMessage;
	}

	MimeMessage draftEmail2(String f1, String Subject, String Body, String emails)
			throws AddressException, MessagingException, IOException {

		List<String> emailReceipients1 = new ArrayList<>();

		emailReceipients1.add(emails);

		
		String b = "";// C:\\Users\\UTENTE\\Desktop\\send.txt
		String emailSubject = Subject;
		String emailBody = Body;
		mimeMessage = new MimeMessage(newSession);

		for (String element : emailReceipients1) {
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(element));
		}

		mimeMessage.setSubject(emailSubject);

		MimeMultipart multiPart = new MimeMultipart();

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(emailBody);

		if (f1 != null) {
			String[] pathes = f1.split(";");
			for (String f : pathes) {

				MimeBodyPart attachmentBodyPart = new MimeBodyPart();
				File file = new File(f);
				attachmentBodyPart.attachFile(file);
				multiPart.addBodyPart(attachmentBodyPart);

			}
		}

		multiPart.addBodyPart(messageBodyPart);
		mimeMessage.setContent(multiPart);

		return mimeMessage;
	}

	void setupServerProperties() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		newSession = Session.getDefaultInstance(properties, null);
	}

}