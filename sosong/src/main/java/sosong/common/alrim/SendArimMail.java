package sosong.common.alrim;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper; 
import org.springframework.web.bind.annotation.RequestMapping;


public class SendArimMail {
	
	@Autowired 
	private static JavaMailSender mailSender; 
	
	private static String from = "mskim7402@gmail.com"; 
	private static String subject	= "皋老力格 (积帆啊瓷)"; 
	
	
	public static void sendMail() {
		
		try { 
			System.out.println("[mail] ============== start!!!!  =============\t");
			MimeMessage message = mailSender.createMimeMessage(); 
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8"); 
			messageHelper.setTo("bluegreen@gangnam.go.kr"); 
			messageHelper.setText("tttt"); 
			messageHelper.setFrom(from); 
			messageHelper.setSubject(subject);	
			System.out.println("[mail] ============== end!!!!  =============\t");
			mailSender.send(message); 
		} 
		catch(Exception e){ 
			System.out.println(e); 
		} 
		 
	}

}
