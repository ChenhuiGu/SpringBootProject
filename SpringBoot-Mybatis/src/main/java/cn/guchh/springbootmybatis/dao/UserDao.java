package cn.guchh.springbootmybatis.dao;

import cn.guchh.springbootmybatis.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    Student queryById(Integer id);

    int insert(Student student);

}
