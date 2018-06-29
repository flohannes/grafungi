package grafungi.query.ws;

import java.util.HashMap;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options o = new Options();
        o.addOption(Option.builder("port").hasArg().required().desc("Port of this webservice to be served").build());
        o.addOption(Option.builder("dbpath").hasArg().required().desc("database path on a local directory").build());
        o.addOption(Option.builder("dummydata").type(Boolean.class).desc("creation of dummy data").build());
        CommandLine options = parser.parse(o, args);
        String portNumber = options.getOptionValue("port");
        String dbpath = options.getOptionValue("dbpath");
        Boolean createDummyData = options.hasOption("dummydata");
        System.out.println(createDummyData);
//        System.getProperties().put("server.port", Integer.parseInt(portNumber));
        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", Integer.parseInt(portNumber));
        props.put("app.dbpath", dbpath);
        props.put("app.createdata", createDummyData);
        new SpringApplicationBuilder()
                .sources(Application.class, ServletConfig.class, GraphController.class, QueryController.class)
                .properties(props)
                .run(args);
//        SpringApplication.run(Application.class, args);
    }
}
