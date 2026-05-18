package com.tt1.simserver.config;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Gestor de configuración que facilita leer propiedades del archivo application.properties de la carpeta resources.
 */
public class ConfigManager {
    private static ConfigManager instance;
    private final Properties properties;

    /**
     * Construye un gestor de configuración cargando las propiedades del archivo application.properties. Muestra un
     * mensaje de advertencia si no puede acceder al archivo application.properties y usará valores por defecto.
     */
    private ConfigManager() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Advertencia: No se encontró el archivo application.properties. Se usarán valores " +
                        "por defecto.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene la instancia Singleton del gestor de configuración.
     *
     * @return la instancia única de {@code ConfigManager}.
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Obtiene en formato cadena la propiedad {@code key}. Si no existe una propiedad con ese nombre devuelve el valor
     * por defecto.
     *
     * @param key          el nombre de la propiedad.
     * @param defaultValue el valor por defecto de la propiedad.
     * @return el valor de la propiedad si existe una propiedad con el nombre indicado, el valor por defecto en caso
     * contrario.
     */
    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Obtiene en formato double la propiedad {@code key}. Si no existe una propiedad con ese nombre o no se puede
     * interpretar como double, devuelve el valor por defecto.
     *
     * @param key          el nombre de la propiedad.
     * @param defaultValue el valor por defecto de la propiedad.
     * @return el valor de la propiedad si existe una propiedad con el nombre indicado y se puede interpretar como
     * double, el valor por defecto en caso contrario.
     */
    public double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Obtiene en formato int la propiedad {@code key}. Si no existe una propiedad con ese nombre o no se puede
     * interpretar como int, devuelve el valor por defecto.
     *
     * @param key          el nombre de la propiedad.
     * @param defaultValue el valor por defecto de la propiedad.
     * @return el valor de la propiedad si existe una propiedad con el nombre indicado y se puede interpretar como int,
     * el valor por defecto en caso contrario.
     */
    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Obtiene en formato lista de cadenas la propiedad {@code key}. Si no existe una propiedad con ese nombre devuelve
     * el valor por defecto.
     *
     * @param key          el nombre de la propiedad.
     * @param defaultValue el valor por defecto de la propiedad.
     * @return el valor de la propiedad si existe una propiedad con el nombre indicado, el valor por defecto en caso
     * contrario.
     */
    public List<String> getStringList(String key, List<String> defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;

        String[] valuesList = value.split(",");
        return Arrays.asList(valuesList);
    }

    /**
     * Obtiene en formato lista de doubles la propiedad {@code key}. Si no existe una propiedad con ese nombre o no se
     * puede interpretar como lista de doubles, devuelve el valor por defecto.
     *
     * @param key          el nombre de la propiedad.
     * @param defaultValue el valor por defecto de la propiedad.
     * @return el valor de la propiedad si existe una propiedad con el nombre indicado y se puede interpretar como lista
     * de doubles, el valor por defecto en caso contrario.
     */
    public List<Double> getDoubleList(String key, List<Double> defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;

        String[] valuesList = value.split(",");
        Double[] values = new Double[valuesList.length];
        for (int i = 0; i < valuesList.length; i++) {
            try {
                values[i] = Double.parseDouble(valuesList[i]);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        return Arrays.asList(values);
    }

    /**
     * Obtiene en formato lista de ints la propiedad {@code key}. Si no existe una propiedad con ese nombre o no se
     * puede interpretar como lista de ints, devuelve el valor por defecto.
     *
     * @param key          el nombre de la propiedad.
     * @param defaultValue el valor por defecto de la propiedad.
     * @return el valor de la propiedad si existe una propiedad con el nombre indicado y se puede interpretar como lista
     * de ints, el valor por defecto en caso contrario.
     */
    public List<Integer> getIntList(String key, List<Integer> defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;

        String[] valuesList = value.split(",");
        Integer[] values = new Integer[valuesList.length];
        for (int i = 0; i < valuesList.length; i++) {
            try {
                values[i] = Integer.parseInt(valuesList[i]);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        return Arrays.asList(values);
    }
}