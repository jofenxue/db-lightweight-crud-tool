package com.combat.core;

import com.combat.beans.Dbsqls;
import com.combat.beans.Sql;
import com.combat.handle.config.ConfigHandler;
import com.combat.handle.config.PropertyConfigHandler;
import com.combat.handle.db.DBHandler;
import com.combat.handle.sqlxml.XMLJaxbHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;

public class Engine {
    protected static final Logger log = LogManager.getLogger(Engine.class);
    private static boolean DevMode = false;

    public static void main(String[] args) throws JAXBException {
        //获取sqlxml文件
        log.info("loading dbsqls.xml ......");
        XMLJaxbHandler h = XMLJaxbHandler.getHandler();
        String dbssql = DevMode? "src/main/resources/dbsqls.xml" : "dbsqls.xml";
        log.info("loading dbsqls.xml path: " + dbssql);
        Dbsqls sqls = (Dbsqls)h.getXMLBeans(dbssql, Dbsqls.class);

        //获取db参数
        String dbcffile = sqls.getDbconfig().getConfigpath();
        String url = sqls.getDbconfig().getDburl();
        String username = sqls.getDbconfig().getUsername();
        String password = sqls.getDbconfig().getPassword();
        log.info(url+"\n"+username);

        log.info("loading db info ......");
        ConfigHandler confiH = PropertyConfigHandler.getHandler(dbcffile);
        confiH.getPropValue(url);
        log.info(confiH.getPropValue(url));
        for(Sql sql : sqls.getSql()) {
            log.debug(sql.getContent());
        }

        /*if(true) {
            return;
        }*/

        log.info("executing patch ......");
        DBHandler dh = DBHandler.getHandler(confiH.getPropValue(url), confiH.getPropValue(username), confiH.getPropValue(password));
        dh.executeUpdate(sqls.getSql());
    }

    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }
}
