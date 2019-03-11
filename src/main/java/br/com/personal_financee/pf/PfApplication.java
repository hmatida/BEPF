package br.com.personal_financee.pf;

import br.com.personal_financee.pf.models.ProfileEnum;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.repositories.UserRepository;
import br.com.personal_financee.pf.utility.SendEmailBeforeVcto;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
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
//			initQuartzJob();
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


	private void initQuartzJob() {

		SchedulerFactory shedFact = new StdSchedulerFactory();
		try {
			Scheduler scheduler = shedFact.getScheduler();
			scheduler.start();
			JobDetail job = JobBuilder.newJob(SendEmailBeforeVcto.class)
					.withIdentity("validadorJOB", "grupo01")
					.build();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("validadorTRIGGER", "grupo01")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
					.build();
			scheduler.scheduleJob(job, trigger);
			System.out.println("------------Quartz em execução------------");

		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
	}


}
