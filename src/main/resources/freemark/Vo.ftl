package ${javaTable.basePackage};


import lombok.Data;
import lombok.ToString;
/**
 * 对应的表名   :${javaTable.tableName}
 * 表类型      :${javaTable.tableType}
 * 表引擎      :${javaTable.engine}
 * 表版本      :${javaTable.version}
 * 行格式      :${javaTable.rowFormat}
 * 表创建时间   :${javaTable.createTime}
 * 表字符集    :${javaTable.tableCollation}
 * 表注释      :${javaTable.tableComment}
 */
@Data
@ToString
public class ${javaTable.classVoName} {

<#list javaTable.javaFields as field>
            private ${field.type} ${field.name} ; // ${field.comment}
</#list>


}
