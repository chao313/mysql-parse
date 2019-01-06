package demo.spring.boot.demospringboot.parse.mysql.parse.db;

import demo.spring.boot.demospringboot.parse.mysql.parse.util.FreemarkUtil;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.*;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "生成文件")
public class GenerateFile {

    public static JavaTable GenerateFile(String dataBase, String ptableName, String basePackage)
            throws SQLException, ClassNotFoundException, IOException, TemplateException {
        DBInfo dbInfo = new DBInfo();
        MysqlTable mysqlTable = dbInfo.obtainTableInfo(dataBase, ptableName);
        List<MysqlField> fields = dbInfo.obtainFieldsInfo(dataBase, ptableName);
        mysqlTable.setMysqlFields(fields);

        JavaTable javaTable = JavaTable.transByMysqlTable(mysqlTable, basePackage);
        log.info("[mysql解析]获取mysqlTable:{}", javaTable);
        mysqlTable = MysqlTable.transByMysqlTable(mysqlTable);
        log.info("[mysql解析]获取javaTable:{}", mysqlTable);

        //转入freemark
        Map<String, Object> map = new HashMap<>();
        map.put("mysqlTable", mysqlTable);
        map.put("javaTable", javaTable);
        File templateDirFile = ResourceUtils.getFile("classpath:freemark");
        StringBuffer voStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Vo.ftl");
        javaTable.setClassVoStr(voStr.toString());
        log.info("[mysql解析]Vo成功:{}", voStr.toString());

        StringBuffer daoStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "DAO.ftl");
        javaTable.setClassDaoStr(daoStr.toString());
        log.info("[mysql解析]DAO成功{}", daoStr.toString());

        StringBuffer serviceStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Service.ftl");
        javaTable.setClassServiceStr(serviceStr.toString());
        log.info("[mysql解析]Service成功{}", serviceStr.toString());

        StringBuffer serviceImplStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "ServiceImpl.ftl");
        javaTable.setClassServiceImplStr(serviceImplStr.toString());
        log.info("[mysql解析]ServiceImpl成功{}}", serviceImplStr.toString());

        List<MysqlAndJavaField> mysqlAndJavaFields = new ArrayList<>();
        for (int i = 0; i < mysqlTable.getMysqlFields().size(); i++) {
            MysqlAndJavaField mysqlAndJavaField = new MysqlAndJavaField();
            mysqlAndJavaField.setJavaField(javaTable.getJavaFields().get(i));
            mysqlAndJavaField.setMysqlField(mysqlTable.getMysqlFields().get(i));
            mysqlAndJavaFields.add(mysqlAndJavaField);
        }
        //
        List<MysqlAndJavaField> mysqlAndJavaKeys = new ArrayList<>();
        for (int i = 0; i < mysqlTable.getPrimaryKeys().size(); i++) {
            MysqlAndJavaField mysqlAndJavaField = new MysqlAndJavaField();
            mysqlAndJavaField.setJavaField(javaTable.getPrimaryKeys().get(i));
            mysqlAndJavaField.setMysqlField(mysqlTable.getPrimaryKeys().get(i));
            mysqlAndJavaKeys.add(mysqlAndJavaField);
        }
        map.put("mysqlAndJavaFields", mysqlAndJavaFields);
        map.put("mysqlAndJavaKeys", mysqlAndJavaKeys);
        StringBuffer mapperStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Mapper.ftl");
        javaTable.setMapperStr(mapperStr.toString());
        log.info("[mysql解析]Mapper成功{}", mapperStr.toString());
        return javaTable;

    }

    /**
     * 生成具有关系
     *
     * @param dataBase
     * @param tableBase
     * @param basePackage
     * @param associations
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws TemplateException
     */
    public static List<AssociationJavaTable> GenerateFileAssociation(String dataBase,
                                                                     String tableBase,
                                                                     String basePackage,
                                                                     List<AssociationTable> associations)
            throws SQLException, ClassNotFoundException, IOException, TemplateException {

        //第一步 生成所有关联的table的vo对象 - 过滤出所有的表
        List<String> tables = new ArrayList<>();
        associations.forEach(associationRequest -> {
            if (!tables.contains(associationRequest.getLeftTable())) {
                tables.add(associationRequest.getLeftTable());
            }
            if (!tables.contains(associationRequest.getRightTable())) {
                tables.add(associationRequest.getRightTable());
            }
        });

        List<String> leftTables = new ArrayList<>();
        associations.forEach(associationRequest -> {
            if (!leftTables.contains(associationRequest.getLeftTable())) {
                leftTables.add(associationRequest.getLeftTable());
            }
        });


        DBInfo dbInfo = new DBInfo();
        List<AssociationJavaTable> associationJavaTables = new ArrayList<>();
        for (String table : tables) {
            //处理所有关联的数据表 -> 生成AssociationJavaTable
            MysqlTable tableBaseMysql = dbInfo.obtainTableInfo(dataBase, table);//获取表的信息
            List<MysqlField> fields = dbInfo.obtainFieldsInfo(dataBase, table);//获取字段信息
            tableBaseMysql.setMysqlFields(fields);//把字段注入表中
            tableBaseMysql = MysqlTable.transByMysqlTable(tableBaseMysql);//自己转换自己，处理一些数据
            JavaTable javaTable =
                    AssociationJavaTable.transByMysqlTable(tableBaseMysql, basePackage);//把表信息(包括字段转换为java类型)
            AssociationJavaTable associationJavaTable = new AssociationJavaTable();
            BeanUtils.copyProperties(javaTable, associationJavaTable);
            associationJavaTable = AssociationJavaTable.parse(associationJavaTable, leftTables);//数据转换
            log.info("[mysql解析]解析的表信息(包括字段):tableBaseJava:{}", associationJavaTable);
            associationJavaTables.add(associationJavaTable);
            associationJavaTable.setAssociations(associations);
        }

        //把关联关系写入表中
        for (AssociationJavaTable associationJavaTable : associationJavaTables) {
            String tableName = associationJavaTable.getMysqlTable().getTableName();
            for (AssociationTable association : associations) {
                if (association.getLeftTable().equals(tableName)) {
                    //表明这个表中有关联字段
                    for (AssociationJavaTable javaTable : associationJavaTables) {
                        if (javaTable.getMysqlTable().getTableName().equals(association.getRightTable())) {
                            //匹配到了对应的table
                            AssociationJavaTable javaTableTmp = new AssociationJavaTable();
                            BeanUtils.copyProperties(javaTable, javaTableTmp);
                            javaTableTmp.setAssociationHashMap(null);
                            associationJavaTable.getAssociationHashMap().put(association.getRightField(), javaTableTmp);
                        }
                    }
                }
            }
        }
        File templateDirFile = ResourceUtils.getFile("classpath:freemarkAssociation");
        //处理Vo VoAssociation
        for (AssociationJavaTable associationJavaTable : associationJavaTables) {
            Map<String, Object> map = new HashMap<>();
            map.put("javaTable", associationJavaTable);
            map.put("associationJavaTables", associationJavaTables);//用于处理所有的table
            StringBuffer voStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Vo.ftl");
            associationJavaTable.setClassVoStr(voStr.toString());
            log.info("[mysql解析]Vo成功:{}", voStr.toString());
            if (associationJavaTable.getAssociationHashMap().size() > 0) {
                StringBuffer associationVoStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "VoAssociation.ftl");
                associationJavaTable.setClassAssociationVoStr(associationVoStr.toString());
                log.info("[mysql解析]associationVoStr成功:{}", associationVoStr.toString());

            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("javaTables", associationJavaTables);
        map.put("javaTable", associationJavaTables.get(0));
        map.put("associationJavaTables", associationJavaTables);//用于处理所有的table

        StringBuffer associationDAOStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "DAOAssociation.ftl");
        associationJavaTables.get(0).setClassAssociationDAOStr(associationDAOStr.toString());
        log.info("[mysql解析]associationDAOStr成功:{}", associationDAOStr.toString());

        StringBuffer associationServiceStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "ServiceAssociation.ftl");
        associationJavaTables.get(0).setClassAssociationServiceStr(associationServiceStr.toString());
        log.info("[mysql解析]associationServiceStr成功:{}", associationServiceStr.toString());

        StringBuffer associationServiceImplStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "ServiceImplAssociation.ftl");
        associationJavaTables.get(0).setClassAssociationServiceImplStr(associationServiceImplStr.toString());
        log.info("[mysql解析]associationServiceImplStr成功:{}", associationServiceImplStr.toString());




        StringBuffer associationMapperStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "MapperAssociation.ftl");
        associationJavaTables.get(0).setMapperStr(associationMapperStr.toString());
        log.info("[mysql解析]associationServiceImplStr成功:{}", associationMapperStr.toString());

        return associationJavaTables;

    }

}
