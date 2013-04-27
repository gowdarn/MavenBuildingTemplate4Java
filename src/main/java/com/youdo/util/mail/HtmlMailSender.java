package com.youdo.util.mail;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * @author shsun
 * 
 */
public class HtmlMailSender extends AbstractMailSender {

	@Override
	protected void doSend(MailSenderInfo info, Message message) {
		Multipart mPart = new MimeMultipart();
		BodyPart bPart = new MimeBodyPart();
		try {
			bPart.setContent(info.getContent(), "text/html; charset=utf-8");
			mPart.addBodyPart(bPart);
			message.setContent(mPart);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
