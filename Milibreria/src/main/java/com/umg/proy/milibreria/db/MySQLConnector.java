package com.umg.proy.milibreria.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLConnector {

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 👈 Registro manual del driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(DBConfig.mysqlUrl, DBConfig.mysqlUser, DBConfig.mysqlPassword);
    }

    public static void insert(String query, Object... params) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);
            stmt.executeUpdate();
            MongoLogger.log("INSERT", "Query: " + query + ", Params: " + formatParams(params));
        } catch (SQLException e) {
            MongoLogger.log("ERROR_INSERT", e.getMessage());
            throw e;
        }
    }

    public static void update(String query, Object... params) throws SQLException {
        insert(query, params); // Reutiliza insert
    }

    private static void setParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    public static List<Map<String, Object>> selectList(String query, Object... params) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= colCount; i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                    rows.add(row);
                }
            }
            MongoLogger.log("SELECT", "Query: " + query + ", Rows returned: " + rows.size());
        } catch (SQLException e) {
            MongoLogger.log("ERROR_SELECT", e.getMessage());
            throw e;
        }
        return rows;
    }

    private static String formatParams(Object... params) {
        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
    }
}
