package com.example;

import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RegisterJFrame extends JFrame implements MouseListener {
    public JTextField username = new JTextField();
    public JTextField password = new JTextField();
    public JTextField confirm = new JTextField();
    public JButton register = new JButton();
    public JButton reset = new JButton();

    public RegisterJFrame(){
        InitJFrame();

        InitImage();

        this.setVisible(true);
    }

    private void InitImage() {
        this.getContentPane().removeAll();

        JLabel UserName = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\注册用户名.png"));
        UserName.setBounds(100,120,80,18);
        this.getContentPane().add(UserName);

        username.setBounds(200,115,200,30);
        this.getContentPane().add(username);

        JLabel PassWord = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\注册密码.png"));
        PassWord.setBounds(100,165,80,18);
        this.getContentPane().add(PassWord);

        password.setBounds(200,165,200,30);
        this.getContentPane().add(password);

        JLabel Confirm = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\再次输入密码.png"));
        Confirm.setBounds(90,215,100,18);
        this.getContentPane().add(Confirm);

        confirm.setBounds(200,215,200,30);
        this.getContentPane().add(confirm);

        register.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\注册按钮.png"));
        register.setBorderPainted(false);//去除按钮边框
        register.setContentAreaFilled(false);//去除按钮背景
        register.addMouseListener(this);
        register.setBounds(90,270,128,47);
        this.getContentPane().add(register);

        reset.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\重置按钮.png"));
        reset.setBorderPainted(false);//去除按钮边框
        reset.setContentAreaFilled(false);//去除按钮背景
        reset.addMouseListener(this);
        reset.setBounds(270,270,128,47);
        this.getContentPane().add(reset);

        JLabel background = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\background.png"));
        background.setBounds(40,40,508,560);
        this.getContentPane().add(background);
    }
    private void showJDialog(String content) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(200,150);
        jDialog.setAlwaysOnTop(true);//弹窗置顶
        jDialog.setLocationRelativeTo(null);//弹框居中
        jDialog.setModal(true);//不关闭弹窗无法进行其他操作
        JLabel jLabel = new JLabel(content);
        jLabel.setBounds(0,0,200,150);
        jDialog.getContentPane().add(jLabel);
        JLabel Dialogbackground = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\Dialogbackground.png"));
        Dialogbackground.setBounds(0,0,200,150);
        jDialog.getContentPane().add(Dialogbackground);
        jDialog.setVisible(true);
    }

    private void InitJFrame() {
        this.setSize(488,430);
        this.setTitle("注册");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//3
        this.setAlwaysOnTop(true);//置顶界面
        this.setLocationRelativeTo(null);//居中
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj==reset){
            username.setText("");
            password.setText("");
            confirm.setText("");
            showJDialog("重置成功");
        }else if (obj==register){
            Set<String> NameSet = new HashSet<>();
            String Name = username.getText();
            String PassWord = password.getText();
            String Confirm = confirm.getText();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(APP.URL,APP.USERNAME,APP.PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from user_infomation");
                while (resultSet.next()){
                    NameSet.add(resultSet.getString("name"));
                }
            } catch (ClassNotFoundException  | SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (Name.isEmpty()){
                System.out.println("用户名不能为空");
                showJDialog("用户名不能为空");
            } else if (PassWord.isEmpty()){
                System.out.println("密码不能为空");
                showJDialog("密码不能为空");
            } else if (NameSet.contains(Name)){
                System.out.println("用户名已存在");
                showJDialog("用户名已存在");
            }else if (!PassWord.equals(Confirm)){
                System.out.println("密码不一致");
                showJDialog("密码不一致");
            }else{
                System.out.println("注册成功");
                showJDialog("注册成功");
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(APP.URL,APP.USERNAME,APP.PASSWORD);
                    Statement statement = connection.createStatement();
                    int cnt = NameSet.size() + 1;
                    System.out.println("insert into user_infomation(id,name,password) values("+cnt+","+"'"+Name+"'"+','+"'"+PassWord+"')");
                    statement.executeUpdate("insert into user_infomation(id,name,password) values("+cnt+","+"'"+Name+"'"+','+"'"+PassWord+"')");
                } catch (ClassNotFoundException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
                this.setVisible(false);
                new LoginJFrame();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        if (obj==register){
            System.out.println("注册账号");
            register.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\注册按下.png"));
        }else if (obj==reset){
            System.out.println("重置");
            reset.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\重置按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj= e.getSource();
        if (obj==register){
            register.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\注册按钮.png"));
        }else if (obj==reset){
            reset.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\register\\重置按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

