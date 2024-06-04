package com.example.Sphere;

import com.example.Sphere.entity.User;
import com.example.Sphere.repository.RefreshTokenRepository;
import com.example.Sphere.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(SphereApplication.class, args);
	}

}
