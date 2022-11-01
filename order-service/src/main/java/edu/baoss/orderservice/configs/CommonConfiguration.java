package edu.baoss.orderservice.configs;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class CommonConfiguration {

    @Autowired
    private DataSource dataSource;

    @Value("${baoss.orders.clean-install:false}")
    private boolean cleanInstall;


    @Bean(value = "flyway")
    Flyway createFlyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .cleanDisabled(false)
                .baselineOnMigrate(true)
                .locations("classpath:db/migration/postgresql")
                .load();
        if (cleanInstall) {
            flyway.clean();
        }

        flyway.migrate();
        return flyway;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
