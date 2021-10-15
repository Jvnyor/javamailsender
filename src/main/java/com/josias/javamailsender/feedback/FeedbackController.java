package com.josias.javamailsender.feedback;

import javax.validation.ValidationException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	private EmailCfg emailCfg;

	public FeedbackController(EmailCfg emailCfg) {
		this.emailCfg = emailCfg;
	}
	
	@PostMapping
	public void sendFeedback(@RequestBody Feedback feedback, BindingResult result) {
		if(result.hasErrors()) {
			throw new ValidationException("Feedback is not valid");
		}
		//Create a email sender
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.emailCfg.getHost());
		mailSender.setPort(this.emailCfg.getPort());
		mailSender.setUsername(this.emailCfg.getUsername());
		mailSender.setPassword(this.emailCfg.getPassword());
		
		//Create a email instance
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(feedback.getEmail());
		mailMessage.setTo("josias@feedback.com");
		mailMessage.setSubject("New feedback from "+feedback.getName());
		mailMessage.setText(feedback.getFeedback());
		
		//Send email
		mailSender.send(mailMessage);
//		log.info("Name: "+feedback.getName()+" | Email: "+feedback.getEmail()+" | Feedback: "+feedback.getFeedback());
	}
}
