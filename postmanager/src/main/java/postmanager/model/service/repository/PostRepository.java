package postmanager.model.service.repository;

import org.springframework.data.mybatis.repository.support.MybatisRepository;
import postmanager.model.entities.Post;
import postmanager.model.entities.User;

public interface PostRepository extends MybatisRepository<Post, Long> {
}
