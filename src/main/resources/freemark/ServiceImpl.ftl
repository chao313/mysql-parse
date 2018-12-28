package ${javaTable.basePackage};


import java.util.List;
import org.springframework.stereotype.Service;
import ${javaTable.classVoPackage};
import ${javaTable.classServicePackage};
import ${javaTable.classDAOPackage};


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
@Service
public class ${javaTable.classServiceImplName} implements ${javaTable.classServiceName}{


     private ${javaTable.classDAOName} dao;

    /**
     *  insert
     */
     @Override
     public boolean insert(${javaTable.tableName}Vo vo){

           return dao.insert(vo) > 0 ? true : false;
     }


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段包括null)
     */
     @Override
     public boolean updateAllFieldByPrimaryKey(${javaTable.tableName}Vo vo){

          return dao.updateAllFieldByPrimaryKey(vo) > 0 ? true : false;
     }


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段非null)
     */
     @Override
     public boolean updateBaseFieldByPrimaryKey(${javaTable.tableName}Vo vo){

         return dao.updateBaseFieldByPrimaryKey(vo) > 0 ? true : false;

     }


    /**
     *  根据PrimaryKey查询
     */
     @Override
     public ${javaTable.tableName}Vo queryByPrimaryKey(<#list javaTable.primaryKeys as field> ${field.type} ${field.name}<#if field_has_next>,</#if></#list>){

        return dao.queryByPrimaryKey(<#list javaTable.primaryKeys as field>  ${field.name}<#if field_has_next>,</#if></#list>);

     }

    /**
     * 查询base
     */
     @Override
     public List<${javaTable.tableName}Vo> queryBase(${javaTable.tableName}Vo query){

        return dao.queryBase(query);

     }

    /**
     *  根据PrimaryKey删除
     */
     @Override
     public boolean deleteByPrimaryKey(<#list javaTable.primaryKeys as field> ${field.type} ${field.name}<#if field_has_next>,</#if></#list>){

       return dao.deleteByPrimaryKey(<#list javaTable.primaryKeys as field>  ${field.name}<#if field_has_next>,</#if></#list>);

     }

    /**
     * 删除base
     */
    @Override
    public boolean deleteBase(${javaTable.tableName}Vo vo){

       return dao.deleteBase(vo);

    }

}
