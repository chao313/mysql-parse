package demo.spring.boot.demospringboot.parse.mysql.parse.db;

import demo.spring.boot.demospringboot.parse.mysql.parse.db.util.MysqlBD;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.MysqlField;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.MysqlTable;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DBInfo {
    /**
     * 获取表的信息
     *
     * @param dataBase
     * @param ptableName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public MysqlTable obtainTableInfo(String dataBase, String ptableName) throws SQLException, ClassNotFoundException {
        MysqlBD mysqlBD = MysqlBD.getInstance(true);
        String sql = "SELECT " +
                "tab.TABLE_NAME AS TABLE_NAME, " +
                "tab.TABLE_TYPE AS TABLE_TYPE, " +
                "tab.`ENGINE` AS ENGINE , " +
                "tab.VERSION AS VERSION , " +
                "tab.ROW_FORMAT AS ROW_FORMAT, " +
                "tab.CREATE_TIME AS CREATE_TIME, " +
                "tab.TABLE_COLLATION AS TABLE_COLLATION, " +
                "tab.TABLE_COMMENT AS TABLE_COMMENT " +
                "FROM information_schema.TABLES tab " +
                "WHERE table_schema =''{0}'' and table_name = ''{1}''";

        sql = MessageFormat.format(sql, dataBase, ptableName);
        MysqlTable table = mysqlBD.executeQuery(sql, resultSet -> {
            MysqlTable resultTable = new MysqlTable();
            try {
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    String tableType = resultSet.getString("TABLE_TYPE");
                    String engine = resultSet.getString("ENGINE");
                    int version = resultSet.getInt("VERSION");
                    String rowFormat = resultSet.getString("ROW_FORMAT");
                    Date createTime = resultSet.getDate("CREATE_TIME");
                    String tableCollation = resultSet.getString("TABLE_COLLATION");
                    String tableComment = resultSet.getString("TABLE_COMMENT");

                    resultTable.setCreateTime(createTime);
                    resultTable.setEngine(engine);
                    resultTable.setRowFormat(rowFormat);
                    resultTable.setTableCollation(tableCollation);
                    resultTable.setVersion(version);
                    resultTable.setTableType(tableType);
                    resultTable.setTableName(tableName);
                    resultTable.setTableComment(tableComment);
                }
            } catch (Exception e) {
                log.info("[mysql解析][获取表信息错误]{}", e.toString(), e);
            }
            return resultTable;
        });
        log.info("[mysql解析][获取表的信息]{}", table);
        return table;
    }


    /**
     * 获取字段的信息
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<MysqlField> obtainFieldsInfo(String dataBase, String ptableName) throws SQLException, ClassNotFoundException {
        MysqlBD mysqlBD = MysqlBD.getInstance(true);
        String sql = "select * from information_schema.columns where table_schema = ''{0}'' and table_name = ''{1}''";
        sql = MessageFormat.format(sql, dataBase, ptableName);
        List<MysqlField> fields = mysqlBD.executeQuery(sql, resultSet -> {
            List<MysqlField> resultFields = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    MysqlField field = new MysqlField();
                    String name = resultSet.getString("COLUMN_NAME");
                    String type = resultSet.getString("DATA_TYPE");
                    String coment = resultSet.getString("COLUMN_COMMENT");
                    boolean isNotNull = resultSet.getString("IS_NULLABLE").equalsIgnoreCase("YES") ? true : false;
                    boolean isPRI = resultSet.getString("COLUMN_KEY").equalsIgnoreCase("PRI") ? true : false;
                    field.setName(name);
                    field.setComment(coment);
                    field.setIsNotNull(isNotNull);
                    field.setIsPRI(isPRI);
                    field.setType(type);
                    resultFields.add(field);

                }
            } catch (Exception e) {
                log.info("[mysql解析][获取字段信息错误]{}", e.toString(), e);
            }
            return resultFields;
        });
        log.info("[mysql解析][获取字段信息]{}", fields);
        return fields;
    }

}
