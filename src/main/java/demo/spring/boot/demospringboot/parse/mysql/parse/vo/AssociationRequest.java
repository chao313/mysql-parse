package demo.spring.boot.demospringboot.parse.mysql.parse.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

/**
 * 请求中的表的关联关系
 */
@Data
@ToString
public class AssociationRequest {
    @ApiParam(value = "table")
    private String table;
    private String field;
    private String tableAssociation;
    private String fieldAssociation;
}
