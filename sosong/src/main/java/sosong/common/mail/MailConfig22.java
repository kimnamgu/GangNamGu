package sosong.common.mail;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.mail.javamail.JavaMailSender; 
import org.springframework.mail.javamail.JavaMailSenderImpl; 

@Configuration public class MailConfig22 { 
	
	@Bean 
	public static JavaMailSender mailSender(){ 
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); 
		mailSender.setHost("smtp.gmail.com"); 
		mailSender.setPort(25);
		mailSender.setUsername("mskim7402@gmail.com"); 
		mailSender.setPassword("kim$1423"); 
		return mailSender; 
	} 
}
