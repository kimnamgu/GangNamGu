/*package sosong.common.mail;

import javax.mail.internet.MimeMessage; 
import org.springframework.stereotype.Controller; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.mail.javamail.JavaMailSender; 
import org.springframework.mail.javamail.MimeMessageHelper; 
import org.springframework.web.bind.annotation.RequestMapping;


@Controller 
public class MailController {
	@Autowired private JavaMailSender mailSender; 
	private String from = "mskim7402@gmail.com"; 
	private String subject	= "皋老力格 (积帆啊瓷)"; 
	@RequestMapping(value = "/mail.do")
	
	
	public String sendMail() { 
		try { 
			MimeMessage message = mailSender.createMimeMessage(); 
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8"); 
			messageHelper.setTo("bluegreen@gangnam.go.kr"); 
			messageHelper.setText("tttt"); 
			messageHelper.setFrom(from); 
			messageHelper.setSubject(subject);	
			mailSender.send(message); 
		} 
		catch(Exception e){ 
			System.out.println(e); 
		} 
		return "/main/mail"; 
		}

}
*/
package sosong.common.mail;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class MailController {
	Logger log = Logger.getLogger(this.getClass());

@Autowired
private JavaMailSenderImpl mailSender;

	@RequestMapping(value="/mail.do")
	public String sendMailIn() { 
		try { 
			SimpleMailMessage message = new SimpleMailMessage();
			 
			  log.debug("[webmail] ============== start!!!!  =============\t");
			
			  message.setFrom("mskim7402@gmail.com");
			  message.setTo("bluegreen@gangnam.go.kr");
			  message.setSubject("kkkk");
			  message.setText("bbb");		
			  mailSender.send(message);
			  
			  log.debug("[webmail] ============== end!!!!  =============\t");
		 }catch(Exception e){
			  e.printStackTrace();
		 }
		
		return "/main/mail";
	}	
}