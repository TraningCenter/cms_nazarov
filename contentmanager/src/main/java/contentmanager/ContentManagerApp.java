package contentmanager;

import contentmanager.model.service.repository.ContentDataRepository;
import contentmanager.model.service.repository.FileSystemContentDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "contentmanager")
@EnableJpaRepositories
@Import(SchedulingConfiguration.class)
public class ContentManagerApp {

    @Bean(name="tempRepository")
    public ContentDataRepository tempContentDataRepository() throws IOException {
        return new FileSystemContentDataRepository("temp-store");
    }

    @Bean(name="persistRepository")
    public ContentDataRepository persistContentDataRepository() throws IOException {
        return new FileSystemContentDataRepository("persist-store");
    }

    public static void main(String[] args){
        SpringApplication.run(ContentManagerApp.class, args);
    }
}
