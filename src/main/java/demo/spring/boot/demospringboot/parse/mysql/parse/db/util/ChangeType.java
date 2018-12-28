package demo.spring.boot.demospringboot.parse.mysql.parse.db.util;

/**
 * 整数类型：BIT、TINY INT、SMALL INT、MEDIUM INT、 INT、 BIG INT
 * <p>
 * 浮点数类型：FLOAT、DOUBLE、DECIMAL
 * <p>
 * 字符串类型：CHAR、VARCHAR、TINY TEXT、TEXT、MEDIUM TEXT、LONGTEXT、TINY BLOB、BLOB、MEDIUM BLOB、LONG BLOB
 * <p>
 * 日期类型：Date、DateTime、TimeStamp、Time、Year
 * <p>
 * 其他数据类型：BINARY、VARBINARY、ENUM、SET、Geometry、Point、MultiPoint、LineString、MultiLineString、Polygon、GeometryCollection等
 * <p>
 */


import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaField;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * 其他类型暂时留作下一版本
 */

public class ChangeType {

    private final static Map<String, Class> typeMap = new HashMap<>();

    static {

        //bit
        typeMap.put("bit", Boolean.class);

        //int
        typeMap.put("tinyint", Integer.class);
        typeMap.put("smallint", Integer.class);
        typeMap.put("mediumint", Integer.class);
        typeMap.put("int", Integer.class);
        typeMap.put("bigint", Long.class);

        //double
        typeMap.put("float", Float.class);
        typeMap.put("double", Double.class);
        typeMap.put("decimal", Double.class);

        //string
        typeMap.put("char", String.class);
        typeMap.put("varchar", String.class);
        typeMap.put("tinytext", String.class);
        typeMap.put("text", String.class);
        typeMap.put("mediumtext", String.class);
        typeMap.put("longtext", String.class);

        //blob
        typeMap.put("blob", byte[].class);
        typeMap.put("tinyblob", byte[].class);
        typeMap.put("mediumblob", byte[].class);
        typeMap.put("longblob", byte[].class);

        //blob
        typeMap.put("date", Date.class);
        typeMap.put("time", Time.class);
        typeMap.put("datetime", Timestamp.class);
        typeMap.put("timestamp", Timestamp.class);
        typeMap.put("year", Date.class);

        //...
    }

    public static Map<String, Class> getTypeMap() {
        return typeMap;
    }

    public List<String> generFiledCode(List<JavaField> fields) {
        List<String> resultCode = new ArrayList<>();
        fields.forEach(field -> {
            String result = "";
            result += ChangeType.getTypeMap().get(field.getType()).getSimpleName() + field.getName() + ";";
            resultCode.add(result);
        });
        return resultCode;

    }


}
