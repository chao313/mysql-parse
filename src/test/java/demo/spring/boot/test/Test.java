package demo.spring.boot.test;


import demo.spring.boot.demospringboot.parse.mysql.parse.db.GenerateFile;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class Test {

//    @org.junit.Test
//    public void parseSql() throws Exception {
//        File file = ResourceUtils.getFile("classpath:create.sql");
//        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//        String createSql = this.parse2String(inputStream).trim();//去除首位空格
//        H2BD h2BD = H2BD.getInstance(true);
////        h2BD.execute(createSql);
//        String sql2 = "select * from information_schema.columns where  table_name = 'act_ru_job'";
//        h2BD.execute(sql2);
//        List<MysqlField> mysqlFields = h2BD.executeQuery(sql2, resultSet -> {
//            List<MysqlField> resultfields = new ArrayList<>();
//            try {
//                while (resultSet.next()) {
//                    MysqlField field = new MysqlField();
//                    String name = resultSet.getString("MysqlField");
//                    field.setName(name);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            log.info("{}", resultfields);
//            return resultfields;
//
//        });
//    }
//
//
//    private String parse2String(InputStream in) throws Exception {
//        OutputStream outputStream = new ByteArrayOutputStream();
//        int ch;
//        while ((ch = in.read()) != -1) {
//            outputStream.write(ch);
//        }
//        return outputStream.toString();
//    }
//
//    @org.junit.Test
//    public void mysqlTest() throws SQLException, ClassNotFoundException {
//        MysqlBD mysqlBD = MysqlBD.getInstance(true);
//        String sql = "select * from information_schema.columns where table_schema = 'test' and table_name = 'ts_transportationtask_journey_info'";
//        List<MysqlField> mysqlFields = mysqlBD.executeQuery(sql, resultSet -> {
//            List<MysqlField> resultFields = new ArrayList<>();
//            try {
//                while (resultSet.next()) {
//                    MysqlField field = new MysqlField();
//                    String name = resultSet.getString("COLUMN_NAME");
//                    String type = resultSet.getString("DATA_TYPE");
//                    String coment = resultSet.getString("COLUMN_COMMENT");
//                    boolean isNotNull = resultSet.getString("IS_NULLABLE").equalsIgnoreCase("YES") ? true : false;
//                    boolean isPRI = resultSet.getString("COLUMN_KEY").equalsIgnoreCase("PRI") ? true : false;
//                    field.setName(name);
//                    field.setComment(coment);
//                    field.setNotNull(isNotNull);
//                    field.setPRI(isPRI);
//                    field.setType(type);
//                    resultFields.add(field);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            log.info("{}", resultFields);
//            return resultFields;
//        });
//        log.info("{}", mysqlFields);
//    }
//
//
//    /**
//     * 获取表的信息
//     *
//     * @param dataBase
//     * @param ptableName
//     * @return
//     * @throws SQLException
//     * @throws ClassNotFoundException
//     */
//    public MysqlTable obtainTableInfo(String dataBase, String ptableName) throws SQLException, ClassNotFoundException {
//        MysqlBD mysqlBD = MysqlBD.getInstance(true);
//        String sql = "SELECT " +
//                "tab.TABLE_NAME AS TABLE_NAME, " +
//                "tab.TABLE_TYPE AS TABLE_TYPE, " +
//                "tab.`ENGINE` AS ENGINE , " +
//                "tab.VERSION AS VERSION , " +
//                "tab.ROW_FORMAT AS ROW_FORMAT, " +
//                "tab.CREATE_TIME AS CREATE_TIME, " +
//                "tab.TABLE_COLLATION AS TABLE_COLLATION, " +
//                "tab.TABLE_COMMENT AS TABLE_COMMENT " +
//                "FROM information_schema.TABLES tab " +
//                "WHERE table_schema =''{0}'' and table_name = ''{1}''";
//
//        sql = MessageFormat.format(sql, dataBase, ptableName);
//        MysqlTable table = mysqlBD.executeQuery(sql, resultSet -> {
//            MysqlTable resultTable = new MysqlTable();
//            try {
//                while (resultSet.next()) {
//                    String tableName = resultSet.getString("TABLE_NAME");
//                    String tableType = resultSet.getString("TABLE_TYPE");
//                    String engine = resultSet.getString("ENGINE");
//                    int version = resultSet.getInt("VERSION");
//                    String rowFormat = resultSet.getString("ROW_FORMAT");
//                    Date createTime = resultSet.getDate("CREATE_TIME");
//                    String tableCollation = resultSet.getString("TABLE_COLLATION");
//                    String tableComment = resultSet.getString("TABLE_COMMENT");
//
//                    resultTable.setCreateTime(createTime);
//                    resultTable.setEngine(engine);
//                    resultTable.setRowFormat(rowFormat);
//                    resultTable.setTableCollation(tableCollation);
//                    resultTable.setVersion(version);
//                    resultTable.setTableType(tableType);
//                    resultTable.setTableName(tableName);
//                    resultTable.setTableComment(tableComment);
//                }
//            } catch (Exception e) {
//                log.info("[mysql解析][获取表信息错误]{}", e.toString(), e);
//            }
//            return resultTable;
//        });
//        log.info("[mysql解析][获取表的信息]{}", table);
//        return table;
//    }
//
//
//    /**
//     * 获取字段的信息
//     *
//     * @return
//     * @throws SQLException
//     * @throws ClassNotFoundException
//     */
//    public List<MysqlField> obtainFieldsInfo(String dataBase, String ptableName) throws SQLException, ClassNotFoundException {
//        MysqlBD mysqlBD = MysqlBD.getInstance(true);
//        String sql = "select * from information_schema.columns where table_schema = ''{0}'' and table_name = ''{1}''";
//        sql = MessageFormat.format(sql, dataBase, ptableName);
//        List<MysqlField> mysqlFields = mysqlBD.executeQuery(sql, resultSet -> {
//            List<MysqlField> resultFields = new ArrayList<>();
//            try {
//                while (resultSet.next()) {
//                    MysqlField field = new MysqlField();
//                    String name = resultSet.getString("COLUMN_NAME");
//                    String type = resultSet.getString("DATA_TYPE");
//                    String coment = resultSet.getString("COLUMN_COMMENT");
//                    boolean isNotNull = resultSet.getString("IS_NULLABLE").equalsIgnoreCase("YES") ? true : false;
//                    boolean isPRI = resultSet.getString("COLUMN_KEY").equalsIgnoreCase("PRI") ? true : false;
//                    field.setName(name);
//                    field.setComment(coment);
//                    field.setNotNull(isNotNull);
//                    field.setPRI(isPRI);
//                    field.setType(type);
//                    resultFields.add(field);
//
//                }
//            } catch (Exception e) {
//                log.info("[mysql解析][获取字段信息错误]{}", e.toString(), e);
//            }
//            return resultFields;
//        });
//        log.info("[mysql解析][获取字段信息]{}", mysqlFields);
//        return mysqlFields;
//    }


//    @org.junit.Test
//    public void testT() throws SQLException, ClassNotFoundException, IOException, TemplateException {
//        DBInfo dbInfo = new DBInfo();
//        MysqlTable mysqlTable = dbInfo.obtainTableInfo("test", "ts_transportationtask_journey_info");
//        List<MysqlField> fields = dbInfo.obtainFieldsInfo("test", "ts_transportationtask_journey_info");
//        mysqlTable.setMysqlFields(fields);
//        log.info("mysqlTable:{}", mysqlTable);
//
//        JavaTable javaTable = JavaTable.transByMysqlTable(mysqlTable, "demo.my");
//        mysqlTable = MysqlTable.transByMysqlTable(mysqlTable);
//
//        //转入freemark
//        Map<String, Object> map = new HashMap<>();
//        map.put("javaTable", javaTable);
//        File templateDirFile = ResourceUtils.getFile("classpath:freemark");
//        StringBuffer voStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Vo.ftl");
//        //log.info("{}", voStr.toString());
//
//        StringBuffer daoStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "DAO.ftl");
//        //log.info("{}", daoStr.toString());
//
//        StringBuffer serviceStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Service.ftl");
//        //log.info("{}", serviceStr.toString());
//
//        StringBuffer serviceImplStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "ServiceImpl.ftl");
//        //log.info("{}", serviceImplStr.toString());
//
//        List<MysqlAndJavaField> mysqlAndJavaFields = new ArrayList<>();
//        for (int i = 0; i < mysqlTable.getMysqlFields().size(); i++) {
//            MysqlAndJavaField mysqlAndJavaField = new MysqlAndJavaField();
//            mysqlAndJavaField.setJavaField(javaTable.getJavaFields().get(i));
//            mysqlAndJavaField.setMysqlField(mysqlTable.getMysqlFields().get(i));
//            mysqlAndJavaFields.add(mysqlAndJavaField);
//        }
//        //
//        List<MysqlAndJavaField> mysqlAndJavaKeys = new ArrayList<>();
//        for (int i = 0; i < mysqlTable.getPrimaryKeys().size(); i++) {
//            MysqlAndJavaField mysqlAndJavaField = new MysqlAndJavaField();
//            mysqlAndJavaField.setJavaField(javaTable.getPrimaryKeys().get(i));
//            mysqlAndJavaField.setMysqlField(mysqlTable.getPrimaryKeys().get(i));
//            mysqlAndJavaKeys.add(mysqlAndJavaField);
//        }
//        map.put("mysqlTable", mysqlTable);
//        map.put("mysqlAndJavaFields", mysqlAndJavaFields);
//        map.put("mysqlAndJavaKeys", mysqlAndJavaKeys);
//        StringBuffer mapperStr = FreemarkUtil.generateXmlByTemplate(map, templateDirFile, "Mapper.ftl");
//        log.info("{}", mapperStr.toString());
//
//
//    }

    @org.junit.Test
    public void testT() throws SQLException, ClassNotFoundException, IOException, TemplateException {
        String dataBase = "test";
        String ptableName = "ts_transportationtask_journey_info";
        String basePackage = "demo.my";
        JavaTable javaTable = GenerateFile.GenerateFile(dataBase, ptableName, basePackage);
        log.info("javaTable:{}",javaTable);

    }

}
