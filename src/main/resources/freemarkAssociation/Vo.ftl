package ${javaTable.basePackage};

<#list javaTable.javaFieldTypeNameUnique as fieldType><#if fieldType = "Timestamp" >import java.sql.Timestamp;
</#if><#if fieldType = "Time" >import java.sql.Time;
</#if><#if fieldType = "Date" >import java.util.Date;
</#if></#list>

/**
 * 对应的表名   :${javaTable.mysqlTable.tableName}
 * 表类型      :${javaTable.tableType}
 * 表引擎      :${javaTable.engine}
 * 表版本      :${javaTable.version}
 * 行格式      :${javaTable.rowFormat}
 * 表创建时间   :${javaTable.createTime}
 * 表字符集    :${javaTable.tableCollation}
 * 表注释      :${javaTable.tableComment}
 */


public class ${javaTable.classVoName} {

<#list javaTable.javaFields as field>
    private ${field.type} ${field.name}; <#if field.comment?? && field.comment !=""> // ${field.comment} </#if>
</#list>


<#list javaTable.javaFields as field>
    public ${field.type} get${field.name?cap_first}() {

        return ${field.name};

    }

    public void set${field.name?cap_first}(${field.type} ${field.name}) {

        this.${field.name} = ${field.name};

    }

</#list>

    @Override
    public String toString() {
        return "${javaTable.classVoName}{" +
<#list javaTable.javaFields as field>
                ", ${field.name} '" + ${field.name} +<#if field.type = 'String' > '\'' +</#if>
</#list>
                '}';
    }

}
