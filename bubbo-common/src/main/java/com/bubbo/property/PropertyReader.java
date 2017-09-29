package com.bubbo.property;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by DELL on 2017/9/28.
 */
public class PropertyReader {

    private String path;

    public PropertyReader(String path) {
        this.path = path;
    }

    public String get(String key) {
        final URL url = Resources.getResource(path);
        final ByteSource byteSource = Resources.asByteSource(url);
        Properties properties = new Properties();
        InputStream inputStream = null;
        String value = null;
        try {
            inputStream = byteSource.openBufferedStream();
            properties.load(inputStream);
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public String getByIO(String key) {
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url == null) {
            throw new RuntimeException("PropertyReader 无法获取资源");
        }
        String value = null;
        try {
            fileInputStream = new FileInputStream(url.getPath());
            properties.load(fileInputStream);
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}
