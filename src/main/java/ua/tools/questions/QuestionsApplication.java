package ua.tools.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class QuestionsApplication {

	static {
		ApiContextInitializer.init();
	}

	public static void main(String[] args) {



		SpringApplication.run(QuestionsApplication.class, args);
	}
}
