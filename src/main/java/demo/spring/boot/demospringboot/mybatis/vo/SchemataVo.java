package demo.spring.boot.demospringboot.mybatis.vo;

import lombok.Data;
import lombok.ToString;
/**
 * 对应的表名   :Schemata
 * 表类型      :SYSTEM VIEW
 * 表引擎      :MEMORY
 * 表版本      :10
 * 行格式      :Fixed
 * 表创建时间   :2018-12-29
 * 表字符集    :utf8_general_ci
 * 表注释      :
 */
@Data
@ToString
public class SchemataVo {

    private String catalogName ; //
    private String schemaName ; //
    private String defaultCharacterSetName ; //
    private String defaultCollationName ; //
    private String sqlPath ; //


}