package ${javaTable.basePackage};


import java.util.List;

import ${javaTable.classAssociationVoPackage};
import ${javaTable.classVoPackage};


/**
 * 对应的表名   :${mysqlTable.tableName}
 * 表类型      :${javaTable.tableType}
 * 表引擎      :${javaTable.engine}
 * 表版本      :${javaTable.version}
 * 行格式      :${javaTable.rowFormat}
 * 表创建时间   :${javaTable.createTime}
 * 表字符集    :${javaTable.tableCollation}
 * 表注释      :${javaTable.tableComment}
 */
public interface ${javaTable.classAssociationServiceName} {

      /**
     * 查询base
     */
    List<${javaTable.classAssociationVoName}> queryBase(${javaTable.classVoName?cap_first} ${javaTable.classVoName?uncap_first},<#list javaTable.associationHashMap?keys as key>${javaTable.associationHashMap[key].classVoName} ${javaTable.associationHashMap[key].classVoName?uncap_first} <#if key_has_next>, </#if></#list>);
}
