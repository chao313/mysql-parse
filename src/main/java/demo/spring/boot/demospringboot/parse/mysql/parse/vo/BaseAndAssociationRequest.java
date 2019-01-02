package demo.spring.boot.demospringboot.parse.mysql.parse.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 请求中的表的关联关系
 */
@Data
@ToString
public class BaseAndAssociationRequest {
    private String dataBase;
    private String basePackage;
    private String tableBase;
    private List<AssociationRequest> associationRequests;
}
