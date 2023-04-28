package sn.dscom.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    static Logger log= LogManager.getLogger(BackendApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        log.info(" Start applcication ");
    }
}
