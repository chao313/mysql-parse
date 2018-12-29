package demo.spring.boot.demospringboot.mybatis.vo;

import lombok.Data;
import lombok.ToString;
/**
 * 对应的表名   :Columns
 * 表类型      :SYSTEM VIEW
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建时间   :
 * 表字符集    :utf8_general_ci
 * 表注释      :
 */
@Data
@ToString
public class ColumnsVo {

            private String tableCatalog ; // 
            private String tableSchema ; // 
            private String tableName ; // 
            private String columnName ; // 
            private Long ordinalPosition ; // 
            private String columnDefault ; // 
            private String isNullable ; // 
            private String dataType ; // 
            private Long characterMaximumLength ; // 
            private Long characterOctetLength ; // 
            private Long numericPrecision ; // 
            private Long numericScale ; // 
            private Long datetimePrecision ; // 
            private String characterSetName ; // 
            private String collationName ; // 
            private String columnType ; // 
            private String columnKey ; // 
            private String extra ; // 
            private String privileges ; // 
            private String columnComment ; // 
            private String generationExpression ; // 


}