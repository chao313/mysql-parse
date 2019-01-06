package ${javaTable.basePackage};


import java.util.List;

<#if javaTable.classAssociationVoStr??>import ${javaTable.classAssociationVoPackage};<#else>import ${javaTable.classVoPackage};</#if>

import org.apache.ibatis.annotations.Param;


/**
 * 对应的表名   :${javaTable.mysqlTable.tableName}
 * 表类型      :${javaTable.tableType}
 * 表引擎      :${javaTable.engine}
 * 表版本      :${javaTable.version}
 * 行格式      :${javaTable.rowFormat}
 * 表创建时间   :${javaTable.createTime}
 * 表字符集    :${javaTable.tableCollation}
 * 表注释      :${javaTable.tableComment}
 * 这个作为关联接口存在
 */
public interface ${javaTable.classAssociationDAOName} {


    /**
     * 查询base
     */
    List<<#if javaTable.classAssociationVoStr??>${javaTable.classAssociationVoName}<#else>${javaTable.classVoName}</#if>> queryBase(<#list associationJavaTables as associationJavaTable>@Param(value = "${associationJavaTable.classVoName?uncap_first}") ${associationJavaTable.classVoName?cap_first} ${associationJavaTable.classVoName?uncap_first}<#if associationJavaTable_has_next>, </#if></#list>);


}
