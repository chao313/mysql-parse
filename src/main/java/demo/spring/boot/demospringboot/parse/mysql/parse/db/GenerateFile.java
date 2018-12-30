package demo.spring.boot.demospringboot.parse.mysql.parse.db;

import demo.spring.boot.demospringboot.parse.mysql.parse.util.FreemarkUtil;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.MysqlAndJavaField;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.MysqlField;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.MysqlTable;
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

}
