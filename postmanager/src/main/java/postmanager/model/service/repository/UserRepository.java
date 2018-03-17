package postmanager.model.service.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.CrudRepository;
import postmanager.model.entities.User;

import java.util.List;


public interface UserRepository extends MybatisRepository<User, Long> {

}
