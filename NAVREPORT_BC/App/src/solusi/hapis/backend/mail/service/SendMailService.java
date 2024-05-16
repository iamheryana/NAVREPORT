package solusi.hapis.backend.mail.service;

public interface SendMailService {
	public void sendMailWithAttachment(String from, String to[], String[] cc, String subject, String mssg, String filename, String format, byte[] attachment);
	
}
