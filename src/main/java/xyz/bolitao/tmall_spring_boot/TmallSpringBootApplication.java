package xyz.bolitao.tmall_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import xyz.bolitao.tmall_spring_boot.util.PortUtil;

/**
 * @author boli
 */
@SpringBootApplication
@EnableCaching
public class TmallSpringBootApplication {
    static {
        PortUtil.checkPort(3306, "MySQL");
        PortUtil.checkPort(6379, "Redis Server");
    }

    public static void main(String[] args) {
        SpringApplication.run(TmallSpringBootApplication.class, args);
    }
}
