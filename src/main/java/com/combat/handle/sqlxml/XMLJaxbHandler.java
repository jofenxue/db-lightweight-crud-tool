package com.combat.handle.sqlxml;

import com.combat.beans.Dbsqls;
import com.combat.beans.Sql;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class XMLJaxbHandler {
    private static final XMLJaxbHandler _instance = new XMLJaxbHandler();

    private XMLJaxbHandler() {
    }

    public static XMLJaxbHandler getHandler() {
        return _instance;
    }

    /**
     *
     * @param path sqlxml文件路径
     * @param cls 需要转换的目标类
     * @return
     * @throws JAXBException
     */
    public Object getXMLBeans(String path, Class cls) throws JAXBException {

        File xmlFile = new File(path);
        // create JAXB context and unmarshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Object sqls = jaxbUnmarshaller.unmarshal(xmlFile);

        return sqls;
    }

    public static void main(String[] args) throws JAXBException {
        XMLJaxbHandler h = XMLJaxbHandler.getHandler();
        Dbsqls sqls = (Dbsqls)h.getXMLBeans("dbsqls.xml", Dbsqls.class);
        List<Sql> sql = sqls.getSql();
        System.out.println(sqls.getDbconfig().getConfigpath());
        for(Sql sql1 : sql) {
            System.out.println(sql1.getContent());
        }
    }
}
