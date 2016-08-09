package com.example;

import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Sample programs using javamail to connect to imap and smtp.
 */
public class AppTest{

    @Test
	public void testimap() throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");

		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("mesos-akhilesh.hpeswlab.net", 143, "verity@mesos-akhilesh.hpeswlab.net", "skyline");
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			Message msg = inbox.getMessage(inbox.getMessageCount());
			Address[] in = msg.getFrom();
			for (Address address : in) {
				System.out.println("FROM:" + address.toString());
			}
			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);
			System.out.println("SENT DATE:" + msg.getSentDate());
			System.out.println("SUBJECT:" + msg.getSubject());
			System.out.println("CONTENT:" + bp.getContent());
		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}

	@Test
	public void testsmtp() throws Exception {
		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "16.103.36.103");
		props.put("mail.smtps.port", "10443");
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtps.connectiontimeout", "10000");
		//props.put("mail.smtps.timeout", "10000");
		// props.put("mail.smtps.quitwait", "false");

		Session mailSession = Session.getInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		message.setSubject("Testing SMTP-SSL");
		message.setContent("This is a test", "text/plain");

		message.addRecipient(Message.RecipientType.TO, new InternetAddress("aspen-dev@hpe.com"));

		transport.connect("16.103.36.103", "", "");

		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}
}
