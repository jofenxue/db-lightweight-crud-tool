package com.combat.handle.db;
import com.combat.beans.Sql;
import com.combat.handle.config.ConfigHandler;
import com.combat.handle.config.PropertyConfigHandler;
import com.combat.handle.sqlxml.XMLJaxbHandler;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBHandler {
    protected static final Logger log = LogManager.getLogger(DBHandler.class);
    private static DBHandler _instance = null;
    private String url = null;
    private String usernm = null;
    private String passwd = null;
    private Properties prop = null;

    private DBHandler(String url, String usernm, String passwd) {
        this.url = url;
        this.usernm = usernm;
        this.passwd = passwd;
    }

    private DBHandler() {
    }

    public static DBHandler getHandler(String url, String usernm, String passwd) {
        if(_instance == null) {
            _instance = new DBHandler(url, usernm, passwd);
        }
        return _instance;
    }

    private void loadInnerDriver() {
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        DbUtils.loadDriver(jdbcDriver);
    }

    /**
     * 执行操作
     * @param sqls
     */
    public void executeUpdate(List<Sql> sqls) {
        Connection conn = null;
        QueryRunner qRunner = new QueryRunner();

        try {
            conn = DriverManager.getConnection(this.url, this.usernm, this.passwd);
            conn.setAutoCommit(false);// Manually commit transactions

            for(Sql sql : sqls) {
                log.info("execute sql:" + sql);
                qRunner.update(conn, sql.getContent());
            }

            DbUtils.commitAndCloseQuietly(conn);
            log.info("***commit success***");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("***rollback begin***");
            DbUtils.rollbackAndCloseQuietly(conn);
            log.info("***rollback end***");
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ClassNotFoundException {

        Connection conn = null;
        String jdbcURL_oci = "jdbc:oracle:oci:@dqcsp";
        String jdbcURL_thin = "jdbc:oracle:thin:@192.168.101.104:1521:dqcsp";
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        try {
            DbUtils.loadDriver(jdbcDriver);
            conn = DriverManager.getConnection(jdbcURL_oci, "DQCSP", "DQCSP");
            conn.setAutoCommit(false);// Manually commit transactions
            QueryRunner qRunner = new QueryRunner();

            // Query by MapHandler
            System.out.println("***Using MapHandler***");
            Map map = (Map) qRunner.query(conn, "select * from web_user where id = ?", new MapHandler(), new Object[] { "6" });

            System.out.println("id ------------- name ");
            System.out.println(map.get("id") + "  ------------- " + map.get("real_name"));

            // Query by MapListHandler
            System.out.println("***Using MapListHandler***");
            List lMap = (List) qRunner.query(conn, "select * from web_user", new MapListHandler());

            System.out.println("id ------------- name ");
            for (int i = 0; i < lMap.size(); i++) {
                Map vals = (Map) lMap.get(i);
                System.out.println(vals.get("id") + "  ------------- " + vals.get("real_name"));
            }

            // Query by ArrayHandler
            System.out.println("***Using ArrayHandler***");
            Object[] array = (Object[]) qRunner.query(conn, "select * from web_user where id = ?", new ArrayHandler(), new Object[] { "5" });

            System.out.println("id ------------- name ");
            System.out.println(array[0].toString() + "  ------------- " + array[1].toString());

            // Query by ArrayListHandler
            System.out.println("***Using ArrayListHandler***");
            List lArray = (List) qRunner.query(conn, "select * from web_user", new ArrayListHandler());
            System.out.println("id ------------- name ");
            for (int i = 0; i < lArray.size(); i++) {
                Object[] var = (Object[]) lArray.get(i);
                System.out.println(var[0].toString() + "  ------------- " + var[1].toString());
            }

            // Query a column by ColumnListHandler
            System.out.println("***Using ColumnListHandler***");
            List lName = (List) qRunner
                    .query(conn, "select * from web_user where real_name <> ?", new ColumnListHandler("real_name"), new Object[] { "adasd" });
            for (int i = 0; i < lName.size(); i++) {
                String name = (String) lName.get(i);
                System.out.println(name);
            }

            // insert
            System.out.println("***Insert begin***");
            int ret = qRunner.update(conn, "insert into users (id,name)" + "values (?,?)", new Object[] { 8, "aaaa" });
            System.out.println("insert complete! affected rows: " + ret);
            System.out.println("***update end***");

            // update
            System.out.println("***update begin***");
            ret = qRunner.update(conn, "update users set name = ? where id = ?", new Object[] { "good_c", 2 });
            System.out.println("update complete! affected rows: " + ret);
            System.out.println("***update end***");

            // delete
            System.out.println("***delete begin***");
            ret = qRunner.update(conn, "delete from users where id = ?", new Object[] { 3 });
            System.out.println("delete complete! affected rows: " + ret);
            System.out.println("***delete end***");

        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                System.out.println("***rollback begin***");
                DbUtils.rollback(conn);
                System.out.println("***rollback end***");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }

}
