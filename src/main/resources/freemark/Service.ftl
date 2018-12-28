package ${javaTable.basePackage};


import java.util.List;
import ${javaTable.classVoPackage};


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
     *  insert
     */
     boolean insert(${javaTable.tableName}Vo vo);


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段包括null)
     */
     boolean updateAllFieldByPrimaryKey(${javaTable.tableName}Vo vo);


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段非null)
     */
     boolean updateBaseFieldByPrimaryKey(${javaTable.tableName}Vo vo);


    /**
     *  根据PrimaryKey查询
     */
    ${javaTable.tableName}Vo queryByPrimaryKey(<#list javaTable.primaryKeys as field> ${field.type} ${field.name}<#if field_has_next>,</#if></#list>);

    /**
     * 查询base
     */
    List<${javaTable.tableName}Vo> queryBase(${javaTable.tableName}Vo query);

    /**
     *  根据PrimaryKey删除
     */
    boolean deleteByPrimaryKey(<#list javaTable.primaryKeys as field> ${field.type} ${field.name}<#if field_has_next>,</#if></#list>);

    /**
     * 删除base
     */
    boolean deleteBase(${javaTable.tableName}Vo vo);

}
