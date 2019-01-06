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
    List<${javaTable.classAssociationVoName}> queryBase(<#list associationJavaTables as associationJavaTable>${associationJavaTable.classVoName?cap_first} ${associationJavaTable.classVoName?uncap_first}<#if associationJavaTable_has_next>, </#if></#list>);
}
