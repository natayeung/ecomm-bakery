package com.natay.ecomm.bakery;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author natayeung
 */
@SpringBootApplication
@EnableEncryptableProperties
public class BakeryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakeryApplication.class, args);
	}

	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
