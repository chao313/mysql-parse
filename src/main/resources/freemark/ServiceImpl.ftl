package ${javaTable.basePackage};


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${javaTable.classVoPackage};
import ${javaTable.classServicePackage};
import ${javaTable.classDAOPackage};

<#list javaTable.primaryKeyTypeNameUnique as fieldType><#if fieldType = "Timestamp" >import java.sql.Timestamp;
</#if><#if fieldType = "Time" >import java.sql.Time;
</#if><#if fieldType = "Date" >import java.util.Date;
</#if></#list>


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
@Service
public class ${javaTable.classServiceImplName} implements ${javaTable.classServiceName} {

    @Autowired
    private ${javaTable.classDAOName} dao;

    /**
     * insert
     */
    @Override
    public boolean insert(${javaTable.tableName}Vo vo) {

        return dao.insert(vo) > 0 ? true : false;

    }

    /**
     * insert vos 批量插入
     */
    @Override
    public boolean insert(List<${javaTable.tableName}Vo> vos) {

        return dao.inserts(vos) > 0 ? true : false;

    }

    /**
     * 查询base
     */
    @Override
    public List<${javaTable.tableName}Vo> queryBase(${javaTable.tableName}Vo query) {

        return dao.queryBase(query);

    }

    /**
     * update base (exclude value is null or "")
     */
    @Override
    public boolean updateBase(${javaTable.tableName}Vo source, ${javaTable.tableName}Vo target) {

        return dao.updateBase(source, target) > 0 ? true : false;

    }

    /**
     * update base (include value is null or "")
     */
    @Override
    public boolean updateBaseIncludeNull(${javaTable.tableName}Vo source, ${javaTable.tableName}Vo target) {

        return dao.updateBaseIncludeNull(source, target) > 0 ? true : false;

    }

    /**
     * 删除base
     */
    @Override
    public boolean deleteBase(${javaTable.tableName}Vo vo) {

        return dao.deleteBase(vo) > 0 ? true : false;

    }

 <#if javaTable.primaryKeys?? && (javaTable.primaryKeys?size>0) >

    /**
     * 根据PrimaryKey查询
     * <#list javaTable.primaryKeys as field><p>
     * ${field.name}  ${field.comment}</#list>
     */
    @Override
    public ${javaTable.tableName}Vo queryByPrimaryKey(<#list javaTable.primaryKeys as field>${field.type} ${field.name}<#if field_has_next>, </#if></#list>) {

        return dao.queryByPrimaryKey(<#list javaTable.primaryKeys as field>${field.name}<#if field_has_next>, </#if></#list>);

    }

    /**
     * 根据PrimaryKey删除
     * <#list javaTable.primaryKeys as field><p>
     * ${field.name} : ${field.comment}</#list>
     */
    @Override
    public boolean deleteByPrimaryKey(<#list javaTable.primaryKeys as field>${field.type} ${field.name}<#if field_has_next>, </#if></#list>) {

        return dao.deleteByPrimaryKey(<#list javaTable.primaryKeys as field>${field.name}<#if field_has_next>, </#if></#list>) > 0 ? true : false;

    }

 </#if>
}
