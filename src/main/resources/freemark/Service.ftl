package ${javaTable.basePackage};


import java.util.List;

import ${javaTable.classVoPackage};
<#list javaTable.primaryKeyTypeNameUnique as fieldType><#if fieldType = "Timestamp" >import java.sql.Timestamp;
</#if><#if fieldType = "Time" >import java.sql.Time;
</#if><#if fieldType = "Date" >import java.util.Date;
</#if></#list>


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
public interface ${javaTable.classServiceName} {

    /**
     * insert
     */
    boolean insert(${javaTable.tableName}Vo vo);


    /**
     * insert vos 批量插入
     */
    boolean insert(List<${javaTable.tableName}Vo> vos);


    /**
     * 查询base
     */
    List<${javaTable.tableName}Vo> queryBase(${javaTable.tableName}Vo query);


    /**
     * update base
     */
    boolean updateBase(${javaTable.tableName}Vo source, ${javaTable.tableName}Vo target);

    /**
     * 删除base
     */
    boolean deleteBase(${javaTable.tableName}Vo vo);

 <#if javaTable.primaryKeys?? && (javaTable.primaryKeys?size>0) >

    /**
     * update all field by PrimaryKey
     * <p>
     * 会更新指定主键的所有非主键字段(字段包括null)
     * <#list javaTable.primaryKeys as field><p>
     * ${field.name} : ${field.comment}</#list>
     */
    boolean updateAllFieldByPrimaryKey(${javaTable.tableName}Vo vo);

    /**
     * 根据PrimaryKey查询
     * <#list javaTable.primaryKeys as field><p>
     * ${field.name} : ${field.comment}</#list>
     */
    ${javaTable.tableName}Vo queryByPrimaryKey(<#list javaTable.primaryKeys as field>${field.type} ${field.name}<#if field_has_next>,</#if></#list>);

    /**
     * update all field by PrimaryKey
     * <p>
     * 会更新指定主键的所有非主键字段(字段非null)
     * <#list javaTable.primaryKeys as field><p>
     * ${field.name}  ${field.comment}</#list>
     */
    boolean updateBaseFieldByPrimaryKey(${javaTable.tableName}Vo vo);

    /**
     * 根据PrimaryKey删除
     * <#list javaTable.primaryKeys as field><p>
     * ${field.name} : ${field.comment}</#list>
     */
    boolean deleteByPrimaryKey(<#list javaTable.primaryKeys as field>${field.type} ${field.name}<#if field_has_next>,</#if></#list>);

 </#if>

}
