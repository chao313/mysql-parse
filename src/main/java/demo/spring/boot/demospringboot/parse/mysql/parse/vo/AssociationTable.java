package demo.spring.boot.demospringboot.parse.mysql.parse.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 请求中的表的关联关系
 */
@Data
@ToString
public class AssociationTable {
    private String leftTable;
    private String leftField;
    private String rightTable;
    private String rightField;
}
