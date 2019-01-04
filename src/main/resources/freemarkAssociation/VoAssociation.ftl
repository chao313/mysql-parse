package ${javaTable.basePackage};

import java.util.List;

import ${javaTable.classVoPackage};

/**
 * 对应的表名   :${javaTable.mysqlTable.tableName}
 * 表类型      :${javaTable.tableType}
 * 表引擎      :${javaTable.engine}
 * 表版本      :${javaTable.version}
 * 行格式      :${javaTable.rowFormat}
 * 表创建时间   :${javaTable.createTime}
 * 表字符集    :${javaTable.tableCollation}
 * 表注释      :${javaTable.tableComment}
 * 这个是${javaTable.classVoName}的扩展类，记录的是表的关系
 */


public class ${javaTable.classVoName}AssociationVo extends ${javaTable.classVoName} {

<#list javaTable.associationHashMap?keys as key>
private List<${javaTable.associationHashMap[key].classVoName}> ${javaTable.associationHashMap[key].classVoName?uncap_first}s; <#if javaTable.associationHashMap[key].tableComment?? && javaTable.associationHashMap[key].tableComment !=""> // ${javaTable.associationHashMap[key].tableComment} </#if>
</#list>

<#list javaTable.associationHashMap?keys as key>
    public ${javaTable.associationHashMap[key].field.type} get${javaTable.associationHashMap[key].classVoName?cap_first}s() {

        return ${field.name};

    }

    public void set${javaTable.associationHashMap[key].classVoName?cap_first}s(List<${javaTable.associationHashMap[key].classVoName}> ${javaTable.associationHashMap[key].classVoName?uncap_first}s) {

        this.${javaTable.associationHashMap[key].classVoName?uncap_first}s = ${javaTable.associationHashMap[key].classVoName?uncap_first}s;

    }

</#list>

}
