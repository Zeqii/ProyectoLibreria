/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.proy.milibreria.db;

public class DBConfig {
    public static String mysqlUrl;
    public static String mysqlUser;
    public static String mysqlPassword;

    public static String mongoUri;
    public static String mongoDatabase;
    public static String mongoCollection;

    // Método para configurar MySQL
    public static void configureMySQL(String url, String user, String password) {
        mysqlUrl = url;
        mysqlUser = user;
        mysqlPassword = password;
    }

    // Método opcional para configurar MongoDB
    public static void configureMongo(String uri, String database, String collection) {
        mongoUri = uri;
        mongoDatabase = database;
        mongoCollection = collection;
    }
}
