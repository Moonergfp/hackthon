package com.hack;

import com.google.common.hash.Hashing;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

@SpringBootApplication
@ComponentScan({ "com.hack" })
@ImportResource({ "classpath:applicationContext.xml" })
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                String cookieName = null;
                try {
                    cookieName = genJSessionId();
                } catch (UnknownHostException e) {
                    LOGGER.debug("getHostError", e);
                }
                if (cookieName != null) {
                    servletContext.getSessionCookieConfig().setName(cookieName);
                } else {
                    LOGGER.warn("gen SessionCokieName error, use default");
                }
            }
        };

    }

    private String genJSessionId() throws UnknownHostException {
        return Hashing.md5().hashString(Application.class.getName() + InetAddress.getLocalHost().getHostName(),
                Charset.forName("utf-8")).toString();
    }

    @Bean
    @ConditionalOnProperty(prefix = "con.prop",name="uname1",havingValue = "false")
//    @ConditionalOnProperty(prefix = "con.prop",name="uname1",matchIfMissing = true)
    BeanTest beanTest() {
        BeanTest b = new BeanTest();
        return b;
    }

}

@Data
class BeanTest {
    private String name;

}