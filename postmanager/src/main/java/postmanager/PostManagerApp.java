package postmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import postmanager.model.entities.User;
import postmanager.model.service.repository.ContentRepository;
import postmanager.model.service.repository.PostRepository;
import postmanager.model.service.repository.UserRepository;

import java.util.stream.Stream;

@SpringBootApplication(scanBasePackages = "postmanager")
public class PostManagerApp {

    public static void main(String[] args){
        SpringApplication.run(PostManagerApp.class, args);
    }

    @Bean
    public CommandLineRunner dummyCLR(UserRepository userRepository, ContentRepository contentRepository, PostRepository postRepository) {
        return args -> {

            userRepository.findAll().forEach(System.out::println);
            contentRepository.findAll().forEach(System.out::println);
            postRepository.findAll().forEach(System.out::println);

        };
    }
}
