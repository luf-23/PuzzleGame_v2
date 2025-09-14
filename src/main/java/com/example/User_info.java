package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class User_info extends JFrame {
    public User_info() throws ClassNotFoundException, SQLException {
        this.setSize(488,200);
        this.setTitle("用户信息");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//3
        this.setAlwaysOnTop(true);//置顶界面
        this.setLocationRelativeTo(null);//居中
        Class.forName("com.mysql.cj.jdbc.Driver·");
        Connection connection = DriverManager.getConnection(APP.URL,APP.USERNAME,APP.PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user_infomation");
        // 获取ResultSet元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 创建DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();
        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(metaData.getColumnLabel(i));
        }
        while (resultSet.next()) {
            Vector<String> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getString(i));
            }
            model.addRow(row);
        }
        // 创建JTable并添加到JScrollPane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        this.setVisible(true);


    }
}
