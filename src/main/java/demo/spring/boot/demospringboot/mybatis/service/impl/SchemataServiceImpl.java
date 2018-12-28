package demo.spring.boot.demospringboot.mybatis.service.impl;

import java.util.List;

import demo.spring.boot.demospringboot.mybatis.dao.SchemataDao;
import demo.spring.boot.demospringboot.mybatis.service.SchemataService;
import demo.spring.boot.demospringboot.mybatis.vo.SchemataVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 对应的表名   :Schemata
 * 表类型      :SYSTEM VIEW
 * 表引擎      :MEMORY
 * 表版本      :10
 * 行格式      :Fixed
 * 表创建时间   :2018-12-29
 * 表字符集    :utf8_general_ci
 * 表注释      :
 */
@Service
public class SchemataServiceImpl implements SchemataService {

     @Autowired
     private SchemataDao dao;

    /**
     *  insert
     */
     @Override
     public boolean insert(SchemataVo vo){

           return dao.insert(vo) > 0 ? true : false;
     }


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段包括null)
     */
     @Override
     public boolean updateAllFieldByPrimaryKey(SchemataVo vo){

          return dao.updateAllFieldByPrimaryKey(vo) > 0 ? true : false;
     }


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段非null)
     */
     @Override
     public boolean updateBaseFieldByPrimaryKey(SchemataVo vo){

         return dao.updateBaseFieldByPrimaryKey(vo) > 0 ? true : false;

     }


    /**
     *  根据PrimaryKey查询
     */
     @Override
     public SchemataVo queryByPrimaryKey(){

        return dao.queryByPrimaryKey();

     }

    /**
     * 查询base
     */
     @Override
     public List<SchemataVo> queryBase(SchemataVo query){

        return dao.queryBase(query);

     }

    /**
     *  根据PrimaryKey删除
     */
     @Override
     public boolean deleteByPrimaryKey(){

       return dao.deleteByPrimaryKey() > 0 ? true : false;

     }

    /**
     * 删除base
     */
    @Override
    public boolean deleteBase(SchemataVo vo){

       return dao.deleteBase(vo) > 0 ? true : false;

    }

}