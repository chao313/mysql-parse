package demo.spring.boot.demospringboot.parse.mysql.parse.db;

import demo.spring.boot.demospringboot.parse.mysql.parse.util.FreemarkUtil;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.*;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
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
     * @param associationRequests
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws TemplateException
     */
    public static JavaTable GenerateFileAssociation(String dataBase,
                                                    String tableBase,
                                                    String basePackage,
                                                    List<AssociationRequest> associationRequests)
            throws SQLException, ClassNotFoundException, IOException, TemplateException {

        //第一步 生成所有关联的table的vo对象 - 过滤出所有的表
        List<String> tables = new ArrayList<>();
        associationRequests.forEach(associationRequest -> {
            if (!tables.contains(associationRequest.getTable())) {
                tables.add(associationRequest.getTable());
            }
            if (!tables.contains(associationRequest.getTableAssociation())) {
                tables.add(associationRequest.getTableAssociation());
            }
        });







        DBInfo dbInfo = new DBInfo();
        MysqlTable tableBaseMysql = dbInfo.obtainTableInfo(dataBase, tableBase);//获取表的信息
        List<MysqlField> fields = dbInfo.obtainFieldsInfo(dataBase, tableBase);//获取字段信息
        tableBaseMysql.setMysqlFields(fields);//把字段注入表中
        tableBaseMysql = MysqlTable.transByMysqlTable(tableBaseMysql);//自己转换自己，处理一些数据
        log.info("[mysql解析]tableBaseMysql:{}", tableBaseMysql);
        //处理java对象
        AssociationJavaTable tableBaseJava = (AssociationJavaTable)
                AssociationJavaTable.transByMysqlTable(tableBaseMysql, basePackage);//把表信息(包括字段转换为java类型)
        log.info("[mysql解析]解析的表信息(包括字段):tableBaseJava:{}", tableBaseJava);
        //
        //处理关联表
        MysqlTable tableAssociationMysql = dbInfo.obtainTableInfo(dataBase, tableBase);
        List<MysqlField> fieldsAssociation = dbInfo.obtainFieldsInfo(dataBase, tableBase);
        tableAssociationMysql.setMysqlFields(fieldsAssociation);
        tableAssociationMysql = MysqlTable.transByMysqlTable(tableAssociationMysql);
        log.info("[mysql解析]获取baseTableMysql:{}", tableAssociationMysql);
        //处理关联表的java对象
        AssociationJavaTable tableAssociationJava = (AssociationJavaTable)
                AssociationJavaTable.transByMysqlTable(tableAssociationMysql, basePackage);
        log.info("[mysql解析]获取tableBaseJava:{}", tableBaseJava);
        //
        //转入freemark
        Map<String, Object> map = new HashMap<>();
        map.put("tableBaseJava", tableBaseJava);
        map.put("tableAssociationJava", tableAssociationJava);
        File templateDirFile = ResourceUtils.getFile("classpath:freemarkAssociation");
        StringBuffer voStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Vo.ftl");
        tableBaseJava.setClassVoStr(voStr.toString());
        log.info("[mysql解析]Vo成功:{}", voStr.toString());

        StringBuffer associationVoStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "VoAssociation.ftl");
        tableBaseJava.setClassAssociationVoStr(associationVoStr.toString());
        log.info("[mysql解析]AssociationVo成功:{}", associationVoStr.toString());

        StringBuffer daoStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "DAO.ftl");
        tableBaseJava.setClassDaoStr(daoStr.toString());
        log.info("[mysql解析]DAO成功{}", daoStr.toString());

        StringBuffer serviceStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Service.ftl");
        tableBaseJava.setClassServiceStr(serviceStr.toString());
        log.info("[mysql解析]Service成功{}", serviceStr.toString());

        StringBuffer serviceImplStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "ServiceImpl.ftl");
        tableBaseJava.setClassServiceImplStr(serviceImplStr.toString());
        log.info("[mysql解析]ServiceImpl成功{}}", serviceImplStr.toString());

        StringBuffer mapperStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Mapper.ftl");
        tableBaseJava.setMapperStr(mapperStr.toString());
        log.info("[mysql解析]Mapper成功{}", mapperStr.toString());
        return tableBaseJava;

    }

}
