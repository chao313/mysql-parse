package demo.spring.boot.demospringboot.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * 对应的表名   :Tables
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
public class TablesVo {

            private String tableCatalog ; // 
            private String tableSchema ; // 
            private String tableName ; // 
            private String tableType ; // 
            private String engine ; // 
            private Long version ; // 
            private String rowFormat ; // 
            private Long tableRows ; // 
            private Long avgRowLength ; // 
            private Long dataLength ; // 
            private Long maxDataLength ; // 
            private Long indexLength ; // 
            private Long dataFree ; // 
            private Long autoIncrement ; // 
            private Timestamp createTime ; //
            private Timestamp updateTime ; // 
            private Timestamp checkTime ; // 
            private String tableCollation ; // 
            private Long checksum ; // 
            private String createOptions ; // 
            private String tableComment ; // 

}