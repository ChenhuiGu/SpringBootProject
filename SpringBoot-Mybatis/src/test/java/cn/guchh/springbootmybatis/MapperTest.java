package cn.guchh.springbootmybatis;

import cn.guchh.springbootmybatis.dao.UserDao;
import cn.guchh.springbootmybatis.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    @Autowired
    private UserDao userDao;
    @Test
    public void queryById(){
        Student student = userDao.queryById(1);
        System.out.print(student);
    }

    @Test
    public void insert(){
        Student student = Student.builder()
                .name("zhangsan")
                .age(15).build();
        int i = userDao.insert(student);
        System.out.print("影响行数:" + i);
    }
}
