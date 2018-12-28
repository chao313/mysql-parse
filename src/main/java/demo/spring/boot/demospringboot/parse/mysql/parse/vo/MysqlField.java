package demo.spring.boot.demospringboot.parse.mysql.parse.vo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MysqlField {
    private String name;
    private String type;
    private String comment;
    private Boolean isNotNull;
    private Boolean isPRI;
}
