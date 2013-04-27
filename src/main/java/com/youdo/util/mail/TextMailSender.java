package com.youdo.util.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

/**
 * 
 * @author shsun
 * 
 */
public class TextMailSender extends AbstractMailSender {

	@Override
	protected void doSend(MailSenderInfo info, Message message) {
		String mailContent = info.getContent();
		try {
			message.setText(mailContent);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
