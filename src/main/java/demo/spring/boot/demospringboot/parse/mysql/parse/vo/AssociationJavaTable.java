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
 * <p>
 * 作为 JavaTable 的扩展类
 */

@Data
@Slf4j
@ToString
public class AssociationJavaTable extends JavaTable {
    //存放扩展类的数据
    private String classAssociationVoName;
    private String classAssociationVoPackage;
    private String classAssociationVoPath;
    private String classAssociationVoStr;


}
