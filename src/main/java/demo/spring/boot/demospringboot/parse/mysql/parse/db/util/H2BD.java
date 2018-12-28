package demo.spring.boot.demospringboot.parse.mysql.parse.db.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;
import java.util.function.Function;

/**
 * 内置的H2 database
 * maven
 * <dependency>
 * <groupId>com.h2database</groupId>
 * <artifactId>h2</artifactId>
 * <version>1.4.197</version>
 * </dependency>
 */

@Slf4j
public class H2BD {

    //数据库连接URL，当前连接的是当前项目的dbdir:下的mydb.mv.db
    private static final String JDBC_URL = "jdbc:h2:dbdir:/mydb";
    //自定义账号
    private static final String USER = "root";
    //自定义密码
    private static final String PASSWORD = "123456";
    //连接H2数据库时使用的驱动类
    private static final String DRIVER_CLASS = "org.h2.Driver";


    public static H2BD h2BD;

    private H2BD() {

    }

    /**
     * 单例模式 -> 双重校验锁
     *
     * @return
     */
    public static H2BD getInstance(boolean proxyLog) {
        if (null == h2BD) {
            synchronized (H2BD.class) {
                if (null == h2BD) {
                    if (proxyLog) {
                        h2BD = new H2BD();
                    } else {
                        h2BD = ProxyModel.getInstance(new H2BD(), ((method, objects) -> {
                            Logger logger = LoggerFactory.getLogger(H2BD.class);
                            logger.info("args：{}", objects);
                            logger.info("method：{}", method);
                        }));
                    }
                }
            }
        }
        return h2BD;
    }

    /**
     * 使用泛型模板，处理返回集
     *
     * @param sql
     * @param function
     * @param <R>
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public <R> R executeQuery(String sql, Function<ResultSet, R> function)
            throws ClassNotFoundException, SQLException {
        //加载驱动
        Class.forName(DRIVER_CLASS);
        //根据连接URL，用户名，密码，获取数据库连接
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        //如果存在USER_INFO表就先删除USER_INFO表
        ResultSet resultSet = stmt.executeQuery(sql);
        R result = function.apply(resultSet);
        //释放资源
        stmt.close();
        //关闭连接
        conn.close();
        return result;
    }

    /**
     * 执行 添加-更新-删除 操作
     */
    public int executeInsertUpdateDelete(String sql)
            throws ClassNotFoundException, SQLException {
        //加载驱动
        Class.forName(DRIVER_CLASS);
        //根据连接URL，用户名，密码，获取数据库连接
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        //执行更新操作
        int result = stmt.executeUpdate(sql);
        //释放资源
        stmt.close();
        //关闭连接
        conn.close();
        return result;
    }

    /**
     * 执行sql -> 只返回执行成功与否
     */
    public boolean execute(String sql)
            throws ClassNotFoundException, SQLException {
        //加载驱动
        Class.forName(DRIVER_CLASS);
        //根据连接URL，用户名，密码，获取数据库连接
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        //执行更新操作
        boolean result = stmt.execute(sql);
        //释放资源
        stmt.close();
        //关闭连接
        conn.close();
        return result;
    }


    public static void main(String[] args) throws Exception {
        //加载驱动
        Class.forName(DRIVER_CLASS);
        //根据连接URL，用户名，密码，获取数据库连接
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        //如果存在USER_INFO表就先删除USER_INFO表
        stmt.execute("DROP TABLE IF EXISTS USER_INFO");
        //创建USER_INFO表
        stmt.execute("CREATE TABLE USER_INFO(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100),sex VARCHAR(4))");
        //新增
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','大日如来','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','青龙','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','白虎','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','朱雀','女')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','玄武','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','苍狼','男')");
        //删除
        stmt.executeUpdate("DELETE FROM USER_INFO WHERE name='大日如来'");
        //修改
        stmt.executeUpdate("UPDATE USER_INFO SET name='孤傲苍狼' WHERE name='苍狼'");
        //查询
        ResultSet rs = stmt.executeQuery("SELECT * FROM USER_INFO");
        //遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name") + "," + rs.getString("sex"));
        }
        //释放资源
        stmt.close();
        //关闭连接
        conn.close();

    }

}
