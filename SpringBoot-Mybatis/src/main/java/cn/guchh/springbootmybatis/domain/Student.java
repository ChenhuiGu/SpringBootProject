package cn.guchh.springbootmybatis.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Student implements Serializable {
    private static final long serialVersionUID = -91969758749726312L;
    /**
     * 唯一标识id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
}
