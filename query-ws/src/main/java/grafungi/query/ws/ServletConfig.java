package grafungi.query.ws;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class ServletConfig {
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return (container -> {
//            container.setPort(8012);
//        });
//    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            connector.setPort(8012);
        });
        return factory;
    }
}
