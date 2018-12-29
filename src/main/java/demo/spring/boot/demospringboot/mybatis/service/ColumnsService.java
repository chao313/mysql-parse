package demo.spring.boot.demospringboot.mybatis.service;

import demo.spring.boot.demospringboot.mybatis.vo.ColumnsVo;

import java.util.List;


/**
 * 对应的表名   :Columns
 * 表类型      :SYSTEM VIEW
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建时间   :
 * 表字符集    :utf8_general_ci
 * 表注释      :
 */
public interface ColumnsService {

    /**
     *  insert
     */
     boolean insert(ColumnsVo vo);


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段包括null)
     */
     boolean updateAllFieldByPrimaryKey(ColumnsVo vo);


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段非null)
     */
     boolean updateBaseFieldByPrimaryKey(ColumnsVo vo);


    /**
     *  根据PrimaryKey查询
     */
    ColumnsVo queryByPrimaryKey();

    /**
     * 查询base
     */
    List<ColumnsVo> queryBase(ColumnsVo query);

    /**
     *  根据PrimaryKey删除
     */
    boolean deleteByPrimaryKey();

    /**
     * 删除base
     */
    boolean deleteBase(ColumnsVo vo);

}