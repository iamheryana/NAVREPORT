package solusi.hapis.backend.mail.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import solusi.hapis.backend.mail.service.SendMailService;

public class SendMailServiceImpl implements SendMailService {
	private JavaMailSender javaMailSender;

	@Override
	public void sendMailWithAttachment(String from, String[] to, String[] cc,
			String subject, String mssg, String filename, String format, byte[] attachment) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setCc(cc);
			helper.setSubject(subject);
			helper.setText(mssg);
			if (attachment != null) {
				String vFileName = filename;
				if("PDF".equalsIgnoreCase(format)){
					vFileName = filename+".pdf";
				} else if("XLS".equalsIgnoreCase(format)){
					vFileName = filename+".xls";
				} else {
					vFileName = filename+"."+format.toLowerCase();
				}
				helper.addAttachment(vFileName, new ByteArrayResource(attachment));
			}
			javaMailSender.send(message);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MailSendException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

}
