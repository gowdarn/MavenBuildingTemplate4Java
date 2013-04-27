package com.youdo.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author shsun
 * 
 */
public abstract class AbstractMailSender {
	/**
	 * 
	 * @param info
	 * @return
	 */
	public boolean sendMail(MailSenderInfo info) {
		boolean success = false;
		CustomizedAuthenticator authenticator = null;
		Properties pro = info.getProperties();
		if (info.isValidate()) {
			authenticator = new CustomizedAuthenticator(info.getUserName(), info.getPassword());
		}
		Session session = Session.getDefaultInstance(pro, authenticator);
		try {
			Message message = new MimeMessage(session);
			Address from = new InternetAddress(info.getFromAddress());
			message.setFrom(from);
			Address[] to = new Address[info.getToAddresses().length];
			for (int i = 0; i < info.getToAddresses().length; i++) {
				to[i] = new InternetAddress(info.getToAddresses()[i]);
			}
			message.setRecipients(Message.RecipientType.TO, to);
			message.setSubject(info.getSubject());
			message.setSentDate(new Date());
			//
			this.doSend(info, message);
			//
			success = true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return success;
	}

	protected abstract void doSend(MailSenderInfo info, Message message);
}
