package com.combat.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class Engine {
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
