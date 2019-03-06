package br.com.personal_financee.pf;

import br.com.personal_financee.pf.models.ProfileEnum;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PfApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PfApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PfApplication.class);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			initUser(userRepository, passwordEncoder);
		};
	}

	private void initUser(UserRepository userRepository, PasswordEncoder passwordEncoder){
		Users admin =  new Users();
		admin.setLogin("admin");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfileEnum(ProfileEnum.ROLE_ADMIN);

		Users find = userRepository.findByLogin(admin.getLogin());
		if (find == null){
			userRepository.save(admin);
		}
	}


}
