package demo.spring.boot.demospringboot.parse.mysql.parse.vo;

import demo.spring.boot.demospringboot.parse.mysql.parse.db.util.ChangeType;
import demo.spring.boot.demospringboot.parse.mysql.parse.util.CamelUtils;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.sql.Date;
import java.util.ArrayList;
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
 */

@Data
@Slf4j
@ToString
public class JavaTable {
    String tableName;                  //TABLE_NAME
    String tableType;                  //TABLE_TYPE
    String engine;                     //ENGINE
    int version;                       //VERSION
    String rowFormat;                  //ROW_FORMAT
    Date createTime;                   //CREATE_TIME
    String tableCollation;             //TABLE_COLLATION
    String tableComment;               //TABLE_COMMENT
    private List<JavaField> javaFields;//java字段信息
    private List<JavaField> primaryKeys;//java key
    private String basePackage;
    private String classVoName;
    private String classVoPackage;
    private String classVoStr;
    private String classDAOName;
    private String classDAOPackage;
    private String classDaoStr;
    private String classServiceName;
    private String classServicePackage;
    private String classServiceStr;
    private String classServiceImplName;
    private String classServiceImplPackage;
    private String classServiceImplStr;
    private String mapperStr;



    /**
     * @param mysqlTable
     * @param basePackage com.demo
     * @return
     */
    public static JavaTable transByMysqlTable(MysqlTable mysqlTable, String basePackage) {
        assert (null != mysqlTable);
        assert (null != mysqlTable.getMysqlFields());
        JavaTable javaTable = new JavaTable();
        javaTable.setBasePackage(basePackage);
        BeanUtils.copyProperties(mysqlTable, javaTable);//复制到javatable里面
        javaTable.setTableName(CamelUtils.toUpperCaseFirstOne(CamelUtils.underline2Camel(javaTable.getTableName())));
        List<JavaField> javaFields = new ArrayList<>();
        mysqlTable.getMysqlFields().forEach(mysqlField -> {
            JavaField javaField = new JavaField();
            javaField.setComment(mysqlField.getComment());
            javaField.setType(ChangeType.getTypeMap().get(mysqlField.getType()).getSimpleName());
            javaField.setIsNotNull(mysqlField.getIsNotNull());
            javaField.setIsPRI(mysqlField.getIsPRI());
            javaField.setName(CamelUtils.underline2Camel(mysqlField.getName()));
            javaFields.add(javaField);
        });
        List<JavaField> primaryKeys = new ArrayList<>();
        javaFields.forEach(javaField -> {
            if (javaField.getIsPRI()) {
                //如果是主键 -> 添加
                primaryKeys.add(javaField);
            }
        });
        javaTable.setJavaFields(javaFields);
        javaTable.setPrimaryKeys(primaryKeys);
        //处理类的名字
        String baseName = javaTable.getTableName();
        javaTable.setClassDAOName(baseName + "Dao");
        javaTable.setClassVoName(baseName + "Vo");
        javaTable.setClassServiceName(baseName + "Service");
        javaTable.setClassServiceImplName(baseName + "ServiceImpl");
        //处理类的package路径
        javaTable.setClassDAOPackage(javaTable.getBasePackage() + "." + javaTable.getClassDAOName());
        javaTable.setClassVoPackage(javaTable.getBasePackage() + "." + javaTable.getClassVoName());
        javaTable.setClassServicePackage(javaTable.getBasePackage() + "." + javaTable.getClassServiceName());
        javaTable.setClassServiceImplPackage(javaTable.getBasePackage() + "." + javaTable.getClassServiceImplName());
        return javaTable;
    }
}
