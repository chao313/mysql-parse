package ${javaTable.basePackage};


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${javaTable.classAssociationVoPackage};
import ${javaTable.classAssociationServicePackage};
import ${javaTable.classAssociationDAOPackage};
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
 *
 */
@Service
public class ${javaTable.classAssociationServiceImplName} implements ${javaTable.classAssociationServiceName} {

    @Autowired
    private ${javaTable.classAssociationDAOName} dao;

      /**
     * 查询base
     */
    public List<${javaTable.classAssociationVoName}> queryBase(<#list associationJavaTables as associationJavaTable>${associationJavaTable.classVoName?cap_first} ${associationJavaTable.classVoName?uncap_first}<#if associationJavaTable_has_next>, </#if></#list>){

        return dao.queryBase(<#list associationJavaTables as associationJavaTable>${associationJavaTable.classVoName?uncap_first}<#if associationJavaTable_has_next>, </#if></#list>);

    };

}
