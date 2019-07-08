package com.example.registrationform.config;

import org.springframework.context.annotation.Bean;

import com.example.registrationform.model.ActiveUserStore;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

	@Bean
	public ActiveUserStore activeUserStore() {
		return ActiveUserStore.getInstance();
	}
}
