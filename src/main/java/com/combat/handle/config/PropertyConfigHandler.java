package com.combat.handle.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * @author JofenXue
 * 此版本对多线程支持不好，若在多线程环境下需优化
 *
 */

public class PropertyConfigHandler implements ConfigHandler {
    private static PropertyConfigHandler _instance = null;
    private Properties prop = null;

    public PropertyConfigHandler(String path) {
        loadProperty(path);
    }

    private PropertyConfigHandler() {
    }

    public static ConfigHandler getHandler(String path) {
        if(_instance == null) {
            _instance = new PropertyConfigHandler(path);
        }
        return _instance;
    }

    /**
     *
     * @param keyName key
     * @return vlaue
     */
    public String getPropValue(String keyName) {
        return prop.getProperty(keyName);
    }

    private void loadProperty(String path) {
        InputStream inputStream = null;
        try {
            prop = new Properties();
            //String propFileName = "config.properties";
            inputStream = new FileInputStream(path);
            //inputStream = getClass().getClassLoader().getResourceAsStream(path);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + path + "' not found in the classpath");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}