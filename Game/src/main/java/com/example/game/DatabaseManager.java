package com.example.game;

import java.sql.*;
import java.util.*;

public class DatabaseManager {

    // database stuff
    public List<Map<String, Object>> fetchAll(String tableName) {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM " + tableName; // simple select

        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            ResultSetMetaData meta = rs.getMetaData();
            int count = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                // loop through columns
                for (int i = 1; i <= count; i++) {
                    map.put(meta.getColumnName(i), rs.getObject(i));
                }
                rows.add(map);
            }
            con.close(); // always close!
        } catch (Exception e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
        return rows;
    }

    public void insert(String tableName, Map<String, Object> data) {
        String cols = "";
        String vals = "";
        List<Object> list = new ArrayList<>();

        // build the query string manually
        int count = 0;
        for (String key : data.keySet()) {
            if (count > 0) {
                cols += ", ";
                vals += ", ";
            }
            cols += key;
            vals += "?";
            list.add(data.get(key));
            count++;
        }

        String sql = "INSERT INTO " + tableName + " (" + cols + ") VALUES (" + vals + ")";
        // System.out.println(sql); // for debugging

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            for (int i = 0; i < list.size(); i++) {
                pst.setObject(i + 1, list.get(i));
            }
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(String tableName, Map<String, Object> data, Map<String, Object> keys) {
        String set = "";
        List<Object> list = new ArrayList<>();

        int c = 0;
        for (String k : data.keySet()) {
            if (c > 0) set += ", ";
            set += k + " = ?";
            list.add(data.get(k));
            c++;
        }

        String where = "";
        int c2 = 0;
        for (String k : keys.keySet()) {
            if (c2 > 0) where += " AND ";
            where += k + " = ?";
            list.add(keys.get(k));
            c2++;
        }

        String sql = "UPDATE " + tableName + " SET " + set + " WHERE " + where;

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            for (int i = 0; i < list.size(); i++) {
                pst.setObject(i + 1, list.get(i));
            }
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println("Update failed");
            e.printStackTrace();
        }
    }

    public void delete(String tableName, Map<String, Object> keys) {
        String where = "";
        List<Object> list = new ArrayList<>();
        
        int c = 0;
        for (String k : keys.keySet()) {
            if (c > 0) where += " AND ";
            where += k + " = ?";
            list.add(keys.get(k));
            c++;
        }

        String sql = "DELETE FROM " + tableName + " WHERE " + where;

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            for (int i = 0; i < list.size(); i++) {
                pst.setObject(i + 1, list.get(i));
            }
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get the dropdown items
    public Map<String, String> fetchLookup(String table, String idCol, String nameCol) {
        Map<String, String> res = new HashMap<>();
        String q = "SELECT " + idCol + ", " + nameCol + " FROM " + table;
        
        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                res.put(String.valueOf(rs.getObject(1)), rs.getString(2));
            }
            con.close();
        } catch (Exception e) {
            // ignore
        }
        return res;
    }
}
