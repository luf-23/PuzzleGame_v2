package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.*;
import java.util.List;

public class LoginJFrame extends JFrame implements MouseListener, ActionListener {
    public JButton rightcode = new JButton();
    public JButton login = new JButton();
    public JButton register = new JButton();
    public JTextField username = new JTextField();
    public JTextField password = new JTextField();
    public JTextField checkcode = new JTextField();
    public static List<User>allUser = new ArrayList<>();
    public static HashMap<String,String>username_to_password = new HashMap<>();
    public static HashMap<String,Integer>username_to_grade = new HashMap<>();
    public static Set<String>allUserName = new HashSet<>();
    public JMenuBar jMenuBar = new JMenuBar();
    public JMenu jMenu = new JMenu("菜单");
    public JMenuItem showUserinfo = new JMenuItem("展示用户信息");

    public LoginJFrame(){

        InitUserInfo();

        InitJFrame();

        InitImage();

        this.setVisible(true);
    }

    private void InitUserInfo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(APP.URL,APP.USERNAME,APP.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user_infomation");
            int cnt = 0;
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                Integer grade = resultSet.getInt("greatest_grade");
                username_to_grade.put(name,grade);
                allUser.add(new User(name,password));
                ++cnt;
            }
            System.out.println("size="+cnt);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        for (User user : allUser){
            allUserName.add(user.getName());
            username_to_password.put(user.getName(),user.getPassword());
        }
        showUserinfo.addActionListener(this);
        jMenu.add(showUserinfo);
        jMenuBar.add(jMenu);
        this.setJMenuBar(jMenuBar);
    }

    public void InitJFrame(){
        this.setSize(488,430);
        this.setTitle("登录");
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        //this.setLayout(null);//取消默认居中放置
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//3
    }
    public void InitImage(){
        this.getContentPane().removeAll();
        //用户名
        JLabel UserName = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\用户名.png"));
        UserName.setBounds(116, 135, 47, 17);
        this.getContentPane().add(UserName);
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);
        //密码
        JLabel PassWord = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\密码.png"));
        PassWord.setBounds(130, 195, 32, 16);
        this.add(PassWord);
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);
        //验证码
        JLabel CheckCode = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\验证码.png"));
        CheckCode.setBounds(133, 256, 50, 30);
        this.getContentPane().add(CheckCode);
        checkcode.setBounds(195, 256, 100, 30);
        this.getContentPane().add(checkcode);
        rightcode.setText(getCode());
        rightcode.setBounds(300, 256, 100, 30);
        rightcode.addMouseListener(this);
        //rightcode.setBorderPainted(false);
        //rightcode.setContentAreaFilled(false);
        this.getContentPane().add(rightcode);
        //登录
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\登录按钮.png"));
        login.setBorderPainted(false);//去除按钮边框
        login.setContentAreaFilled(false);//去除按钮背景
        login.addMouseListener(this);
        this.getContentPane().add(login);
        //注册
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\注册按钮.png"));
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.addMouseListener(this);
        this.getContentPane().add(register);

        //背景
        JLabel background = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\background.png"));
        background.setBounds(40,40,508,560);
        this.getContentPane().add(background);
    }
    public String getCode(){
        String ans = "";
        Random r1 = new Random();
        Random r2 = new Random();
        Random r3 = new Random();
        for (int i=0;i<5;i++){
            int num1 = Math.abs(r1.nextInt())%2;
            if (num1==0){
                //数字
                int num3 = Math.abs(r3.nextInt())%10;
                ans += (char)('0'+num3);
            }else{
                //字母
                int num2 = Math.abs(r2.nextInt())%2;
                int num3 = Math.abs(r3.nextInt())%26;
                if (num2==0){
                    //小写
                    ans += (char)('a'+num3);
                }else{
                    //大写
                    ans += (char)('A'+num3);
                }
            }
        }
        return ans;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj==rightcode){
            String str = getCode();
            System.out.println("更换验证码为"+str);
            rightcode.setText(str);
        }else if (obj==login){
            if (!allUserName.contains(username.getText())){
                System.out.println("用户名不存在");
                showJDialog("用户名不存在");
            }else if (!username_to_password.get(username.getText()).equals(password.getText())){
                System.out.println("密码不正确");
                showJDialog("密码不正确");
            }else if (!checkcode.getText().equals(rightcode.getText())){
                System.out.println("验证码错误");
                showJDialog("验证码错误");
            } else{
                System.out.println(username.getText()+"登陆成功");
                showJDialog("登录成功");
                this.setVisible(false);
                APP.now_user = username.getText();
                APP.now_greatest_grade = username_to_grade.get(APP.now_user);
                new GameJFrame();
            }
        }else if (obj==register){
            this.setVisible(false);
            new RegisterJFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        if (obj==login){
            System.out.println("登录游戏");
            login.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\登录按下.png"));
        }else if (obj==register){
            System.out.println("注册游戏");
            register.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\注册按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj = e.getSource();
        if (obj==login){
            login.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\登录按钮.png"));
        }else if (obj==register){
            register.setIcon(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\login\\注册按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj==showUserinfo){
            System.out.println("用户信息：");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(APP.URL,APP.USERNAME,APP.PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from user_infomation");
                String output = String.format("%-3s","id").replaceAll(" "," ")+String.format("%-12s","user_name").replaceAll(" "," ")+String.format("%-12s","password").replaceAll(" "," ")+String.format("%-5s","greatest_grade").replaceAll(" "," ") + '\n';
                System.out.println(String.format("%-3s","id").replaceAll(" "," ")+String.format("%-12s","user_name").replaceAll(" "," ")+String.format("%-12s","password").replaceAll(" "," ")+String.format("%-5s","greatest_grade").replaceAll(" "," "));
                while (resultSet.next()){
                    String now = "";
                    now += String.format("%-3s",resultSet.getInt("id")).replaceAll(" "," ");
                    now += String.format("%-12s",resultSet.getString("name")).replaceAll(" "," ");
                    now += String.format("%-12s",resultSet.getString("password")).replaceAll(" "," ");
                    now += String.format("%-5s",resultSet.getInt("greatest_grade")).replaceAll(" "," ");
                    System.out.println(now);
                    output += now+'\n';
                }
                System.out.println(output);
                showUserInformation(output);
            } catch (ClassNotFoundException | SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    private void showUserInformation(String output) {
        JDialog jDialog = new JDialog();
        jDialog.setAlwaysOnTop(true);//弹窗置顶
        jDialog.setLocationRelativeTo(null);//弹框居中
        jDialog.setModal(true);//不关闭弹窗无法进行其他操作
        int rows = 0;
        for (int i=0;i<output.length();i++){
            if (output.charAt(i)=='\n') rows++;
        }
        int width = 300;
        int height = 40;
        jDialog.setSize(width,height*(rows+5));
        int cur = 0;
        for (int i=0;i<output.length();i++){
            int j = i;
            String tmp = "";
            while (j<output.length()&&output.charAt(j)!='\n'){
                tmp += output.charAt(j);
                j++;
            }
            JLabel jLabel = new JLabel(tmp);
            jLabel.setBounds(0,height*cur,width,height);
            jDialog.getContentPane().add(jLabel);
            cur++;
            i = j;
        }
        jDialog.setVisible(true);
    }
}
