package sosong.common.alrim;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.mail.javamail.JavaMailSender; 
import org.springframework.mail.javamail.JavaMailSenderImpl; 

@Configuration 
public class MailConfig { 
	@Bean 
	public static JavaMailSender mailSender()
	{ 
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); 
		mailSender.setHost("host"); 
		mailSender.setUsername("username"); 
		mailSender.setPassword("password"); 
		
		return mailSender; 
	} 
}
