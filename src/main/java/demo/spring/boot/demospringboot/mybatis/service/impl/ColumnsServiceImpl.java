package demo.spring.boot.demospringboot.mybatis.service.impl;

import java.util.List;

import demo.spring.boot.demospringboot.mybatis.dao.ColumnsDao;
import demo.spring.boot.demospringboot.mybatis.service.ColumnsService;
import demo.spring.boot.demospringboot.mybatis.vo.ColumnsVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


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
@Service
public class ColumnsServiceImpl implements ColumnsService {

     @Autowired
     private ColumnsDao dao;

    /**
     *  insert
     */
     @Override
     public boolean insert(ColumnsVo vo){

           return dao.insert(vo) > 0 ? true : false;
     }


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段包括null)
     */
     @Override
     public boolean updateAllFieldByPrimaryKey(ColumnsVo vo){

          return dao.updateAllFieldByPrimaryKey(vo) > 0 ? true : false;
     }


    /**
     *  update all field by PrimaryKey
     *  会更新指定主键的所有非主键字段(字段非null)
     */
     @Override
     public boolean updateBaseFieldByPrimaryKey(ColumnsVo vo){

         return dao.updateBaseFieldByPrimaryKey(vo) > 0 ? true : false;

     }


    /**
     *  根据PrimaryKey查询
     */
     @Override
     public ColumnsVo queryByPrimaryKey(){

        return dao.queryByPrimaryKey();

     }

    /**
     * 查询base
     */
     @Override
     public List<ColumnsVo> queryBase(ColumnsVo query){

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
    public boolean deleteBase(ColumnsVo vo){

       return dao.deleteBase(vo) > 0 ? true : false;

    }

}