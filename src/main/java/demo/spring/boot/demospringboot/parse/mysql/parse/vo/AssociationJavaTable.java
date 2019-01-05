package demo.spring.boot.demospringboot.parse.mysql.parse.vo;

import demo.spring.boot.demospringboot.parse.mysql.parse.db.util.ChangeType;
import demo.spring.boot.demospringboot.parse.mysql.parse.util.CamelUtils;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 整数类型：BIT、BOOL、TINY INT、SMALL INT、MEDIUM INT、 INT、 BIG INT
 * <p>
 * 浮点数类型：FLOAT、DOUBLE、DECIMAL
 * <p>
 * 字符串类型：CHAR、VARCHAR、TINY TEXT、TEXT、MEDIUM TEXT、LONGTEXT、TINY BLOB、BLOB、MEDIUM BLOB、LONG BLOB
 * <p>
 * 日期类型：Date、DateTime、TimeStamp、Time、Year
 * <p>
 * 其他数据类型：BINARY、VARBINARY、ENUM、SET、Geometry、Point、MultiPoint、LineString、MultiLineString、Polygon、GeometryCollection等
 * <p>
 * 作为 JavaTable 的扩展类
 */

@Data
@Slf4j
@ToString
public class AssociationJavaTable extends JavaTable {
    private List<AssociationTable> associations;
    //存放扩展类的数据
    private String classAssociationVoName;
    private String classAssociationVoPackage;
    private String classAssociationVoPath;
    private String classAssociationVoStr;

    private String classAssociationDAOName;
    private String classAssociationDAOPackage;
    private String classAssociationDAOPath;
    private String classAssociationDAOStr;

    private String classAssociationServiceName;
    private String classAssociationServicePackage;
    private String classAssociationServicePath;
    private String classAssociationServiceStr;

    private String classAssociationServiceImplName;
    private String classAssociationServiceImplPackage;
    private String classAssociationServiceImplPath;
    private String classAssociationServiceImplStr;

    private String associationMapperName;
    private String associationMapperPackage;
    private String associationMapperPath;
    private String associationMapperStr;

    private HashMap<String, JavaTable> associationHashMap = new HashMap<>();//记录外键对应的JavaTable

    public static AssociationJavaTable parse(AssociationJavaTable associationJavaTable, List<String> leftTables) {
        AssociationJavaTable resultAssociationJavaTable = new AssociationJavaTable();
        BeanUtils.copyProperties(associationJavaTable, resultAssociationJavaTable);

        if (leftTables.contains(resultAssociationJavaTable.getMysqlTable().getTableName())) {
            //处理类名
            resultAssociationJavaTable.setClassAssociationVoName(resultAssociationJavaTable.getTableName() + "AssociationVo");
            resultAssociationJavaTable.setClassAssociationDAOName(resultAssociationJavaTable.getTableName() + "AssociationDao");
            resultAssociationJavaTable.setClassAssociationServiceName(resultAssociationJavaTable.getTableName() + "AssociationService");
            resultAssociationJavaTable.setClassAssociationServiceImplName(resultAssociationJavaTable.getTableName() + "AssociationServiceImpl");
            resultAssociationJavaTable.setAssociationMapperName(resultAssociationJavaTable.getTableName() + "AssociationMapper");

            //处理类的package路径
            resultAssociationJavaTable.setClassAssociationVoPackage(resultAssociationJavaTable.getBasePackage() + "." + resultAssociationJavaTable.getClassAssociationVoName());
            resultAssociationJavaTable.setClassAssociationDAOPackage(resultAssociationJavaTable.getBasePackage() + "." + resultAssociationJavaTable.getClassAssociationDAOName());
            resultAssociationJavaTable.setClassAssociationServicePackage(resultAssociationJavaTable.getBasePackage() + "." + resultAssociationJavaTable.getClassAssociationServiceName());
            resultAssociationJavaTable.setClassAssociationServiceImplPackage(resultAssociationJavaTable.getBasePackage() + "." + resultAssociationJavaTable.getClassAssociationServiceImplName());

            //处理文件的path
            resultAssociationJavaTable.setClassAssociationVoPath(resultAssociationJavaTable.getClassAssociationVoPackage().replace(".", "/") + ".java");
            resultAssociationJavaTable.setClassAssociationDAOPath(resultAssociationJavaTable.getClassAssociationDAOPackage().replace(".", "/") + ".java");
            resultAssociationJavaTable.setClassAssociationServicePath(resultAssociationJavaTable.getClassAssociationServicePackage().replace(".", "/") + ".java");
            resultAssociationJavaTable.setClassAssociationServiceImplPath(resultAssociationJavaTable.getClassAssociationServiceImplPackage().replace(".", "/") + ".java");
            resultAssociationJavaTable.setAssociationMapperPath(resultAssociationJavaTable.getBasePackage().replace(".", "/") + "/" + resultAssociationJavaTable.getAssociationMapperName() + ".xml");

        }


        List<MysqlAndJavaField> mysqlAndJavaFields = new ArrayList<>();
        for (int i = 0; i < associationJavaTable.getMysqlTable().getMysqlFields().size(); i++) {
            MysqlAndJavaField mysqlAndJavaField = new MysqlAndJavaField();
            mysqlAndJavaField.setJavaField(associationJavaTable.getJavaFields().get(i));
            mysqlAndJavaField.setMysqlField(associationJavaTable.getMysqlTable().getMysqlFields().get(i));
            mysqlAndJavaFields.add(mysqlAndJavaField);
        }
        //
        List<MysqlAndJavaField> mysqlAndJavaKeys = new ArrayList<>();
        for (int i = 0; i < associationJavaTable.getMysqlTable().getPrimaryKeys().size(); i++) {
            MysqlAndJavaField mysqlAndJavaField = new MysqlAndJavaField();
            mysqlAndJavaField.setJavaField(associationJavaTable.getPrimaryKeys().get(i));
            mysqlAndJavaField.setMysqlField(associationJavaTable.getMysqlTable().getPrimaryKeys().get(i));
            mysqlAndJavaKeys.add(mysqlAndJavaField);
        }
        resultAssociationJavaTable.setMysqlAndJavaFields(mysqlAndJavaFields);
        resultAssociationJavaTable.setMysqlAndJavaKeys(mysqlAndJavaKeys);
        return resultAssociationJavaTable;

    }
}