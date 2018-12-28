package demo.spring.boot.demospringboot.parse.mysql.parse.vo;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 为了处理mysql类型和java类型对应问题
 */
@Data
@ToString
@Slf4j
public class MysqlAndJavaField {
    private MysqlField mysqlField;
    private JavaField javaField;
}
