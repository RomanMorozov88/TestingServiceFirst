package morozov.ru.configs;

import morozov.ru.services.bdutil.ErrorServices;
import morozov.ru.services.bdutil.HibernateErrorService;
import morozov.ru.services.bdutil.HibernateProfileService;
import morozov.ru.services.bdutil.ProfileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("morozov.ru")
public class AppConfig {

    @Bean
    ProfileService profileService() {
        return new HibernateProfileService();
    }

    @Bean
    ErrorServices errorServices() {
        return new HibernateErrorService();
    }

}
