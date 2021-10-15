package com.josias.javamailsender.feedback;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Feedback {
	@NotNull
	private String name;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Min(10)
	private String feedback;
}
