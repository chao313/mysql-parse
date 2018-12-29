package demo.spring.boot.demospringboot.mybatis.service;

import demo.spring.boot.demospringboot.mybatis.vo.TablesVo;

import java.util.List;


/**
 * 对应的表名   :Tables
 * 表类型      :SYSTEM VIEW
 * 表引擎      :MEMORY
 * 表版本      :10
 * 行格式      :Fixed
 * 表创建时间   :2018-12-29
 * 表字符集    :utf8_general_ci
 * 表注释      :
 */
public interface TablesService {

    /**
     *  insert
     */
     boolean insert(TablesVo vo);


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段包括null)
     */
     boolean updateAllFieldByPrimaryKey(TablesVo vo);


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段非null)
     */
     boolean updateBaseFieldByPrimaryKey(TablesVo vo);


    /**
     *  根据PrimaryKey查询
     */
    TablesVo queryByPrimaryKey();

    /**
     * 查询base
     */
    List<TablesVo> queryBase(TablesVo query);

    /**
     *  根据PrimaryKey删除
     */
    boolean deleteByPrimaryKey();

    /**
     * 删除base
     */
    boolean deleteBase(TablesVo vo);

}