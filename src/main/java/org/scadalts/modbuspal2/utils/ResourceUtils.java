package org.scadalts.modbuspal2.utils;

import java.io.InputStream;
import java.net.URL;

public final class ResourceUtils {
    public static URL getResource(String url) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(url);
    }

    public static InputStream getResourceAsStream(String url) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(url);
    }
}
