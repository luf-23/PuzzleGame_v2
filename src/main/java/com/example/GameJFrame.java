package com.example;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener,MouseListener {
    public int [][]data = new int[4][4];
    public int [][]correct = new int[4][4];
    public JLabel [][]jLabels = new JLabel[4][4];
    public int zerox,zeroy;

    public static final int GIRL_CNT = 11;
    public static final int ANIMAL_CNT = 8;
    public static final int SPORT_CNT = 10;

    public static final int LEFT = 37;
    public static final int UP = 38;
    public static final int RIGHT = 39;
    public static final int DOWN = 40;
    public static final int CHECK_A = 65;
    public static final int CHEAT_W = 87;
    public int step = 0;
    public String Path = "..\\PuzzleGame_Ultimate\\image\\animal\\animal1\\";
    public JMenuBar jMenuBar = new JMenuBar();

    public JMenu function = new JMenu("功能");
    public JMenu change = new JMenu("更换图片");
    public JMenuItem animal = new JMenuItem("动物");
    public JMenuItem girl = new JMenuItem("girl");
    public JMenuItem sport = new JMenuItem("运动");
    public JMenuItem replay = new JMenuItem("重新游戏");
    public JMenuItem relogin = new JMenuItem("重新登录");
    public JMenuItem close = new JMenuItem("保存并退出");
    public JMenu about = new JMenu("关于我们");
    public JMenuItem qq = new JMenuItem("QQ");
    public JMenuItem wechat = new JMenuItem("微信");
    public GameJFrame(){
        //初始化界面
        InitJFrame();
        //初始化菜单
        InitJMenuBar();

        //初始化数据
        InitData();
        //初始化图片
        InitImage();

        this.setVisible(true);
    }

    private void InitJMenuBar() {
        //给菜单绑定鼠标事件
        //change.addActionListener(this);
        animal.addActionListener(this);
        girl.addActionListener(this);
        sport.addActionListener(this);

        replay.addActionListener(this);
        relogin.addActionListener(this);
        close.addActionListener(this);
        qq.addActionListener(this);
        wechat.addActionListener(this);
        change.add(animal);
        change.add(girl);
        change.add(sport);

        function.add(change);
        function.add(replay);
        function.add(relogin);
        function.add(close);

        about.add(qq);
        about.add(wechat);

        jMenuBar.add(function);
        jMenuBar.add(about);
        this.setJMenuBar(jMenuBar);
    }

    private void InitJFrame() {
        this.setSize(603,680);
        this.setTitle("拼图游戏");
        this.setAlwaysOnTop(true);//置顶界面
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//3
        this.setLayout(null);//取消默认居中放置
        this.addKeyListener(this);//给游戏窗口绑定键盘事件
    }

    private void InitData() {
        int []tempArr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
        for (int i=0;i<16;i++) correct[i/4][i%4] = tempArr[i];
        Random r = new Random();
        for (int i=0;i<16;i++){
            int index = Math.abs(r.nextInt())%tempArr.length;
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        for (int i=0;i<16;i++){
            data[i/4][i%4] = tempArr[i];
            if (tempArr[i]==0){
                zerox = i/4;
                zeroy = i%4;
            }
        }
    }

    private void InitImage() {
        this.getContentPane().removeAll();//清空当前页面

        if (isvictory()){
            JLabel win = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\win.png"));
            win.setBounds(203,283,197,73);
            this.getContentPane().add(win);
            if (APP.now_greatest_grade==0) APP.now_greatest_grade = step;
            else APP.now_greatest_grade = Math.min(APP.now_greatest_grade,step);
        }

        JLabel Step = new JLabel("步数："+step);
        Step.setBounds(50,30,100,20);
        this.getContentPane().add(Step);

        JLabel account = new JLabel("当前用户："+APP.now_user+"    "+"最高得分："+APP.now_greatest_grade);
        account.setBounds(320,30,260,20);
        this.getContentPane().add(account);

        //设置游戏图片加载位置
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int x = 105 * j;
                int y = 105 * i;
                int dx = 83;
                int dy = 134;
                Integer num = data[i][j];
                ImageIcon imageIcon = new ImageIcon(Path + num.toString() + ".jpg");
                jLabels[i][j] = new JLabel(imageIcon);
                jLabels[i][j].setBounds(x + dx, y + dy, 105, 105);
                jLabels[i][j].setBorder(new BevelBorder(BevelBorder.LOWERED));//0凸起，1下凹
                jLabels[i][j].addMouseListener(this);
                this.getContentPane().add(jLabels[i][j]);
            }
        }
        //设置背景边框
        JLabel background = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);
        this.getContentPane().repaint();//刷新页面
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code==CHECK_A){
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(Path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            JLabel background = new JLabel(new ImageIcon("..\\PuzzleGame_Ultimate\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isvictory()) return;
        int code = e.getKeyCode();
        if (code==LEFT){
            System.out.println("向左交换");
            if (zeroy==0) return;
            step++;
            data[zerox][zeroy] = data[zerox][zeroy-1];
            data[zerox][zeroy-1] = 0;
            zeroy--;
            InitImage();
        }else if (code==UP){
            System.out.println("向上交换");
            if (zerox==0) return;
            step++;
            data[zerox][zeroy] = data[zerox-1][zeroy];
            data[zerox-1][zeroy] = 0;
            zerox--;
            InitImage();
        }else if (code==RIGHT){
            System.out.println("向右交换");
            if (zeroy==3) return;
            step++;
            data[zerox][zeroy] = data[zerox][zeroy+1];
            data[zerox][zeroy+1] = 0;
            zeroy++;
            InitImage();
        }else if (code==DOWN){
            System.out.println("向下交换");
            if (zerox==3) return;
            step++;
            data[zerox][zeroy] = data[zerox+1][zeroy];
            data[zerox+1][zeroy] = 0;
            zerox++;
            InitImage();
        }else if (code==CHECK_A){
            InitImage();
        }else if (code==CHEAT_W){
            for (int i=0;i<4;i++){
                for (int j=0;j<4;j++){
                    data[i][j] = correct[i][j];
                }
            }
            InitImage();
        }
    }

    public boolean isvictory(){
        boolean ok = true;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (data[i][j]!=correct[i][j]){
                    ok = false;
                }
            }
        }
        return ok;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj==change){
            System.out.println("更换图片");
        }else if (obj==replay){
            System.out.println("重新游戏");
            InitData();
            step = 0;
            InitImage();
        }else if (obj==relogin){
            System.out.println("重新登录");
            this.setVisible(false);
            new LoginJFrame();
        }else if (obj==close){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(APP.URL,APP.USERNAME,APP.PASSWORD);
                Statement statement = connection.createStatement();
                statement.executeUpdate("update user_infomation set greatest_grade="+APP.now_greatest_grade+" where name='"+APP.now_user+"'");
            } catch (ClassNotFoundException | SQLException ee) {
                throw new RuntimeException(ee);
            }
            System.out.println("保存并退出");
            System.exit(0);
        }else if (obj==qq){
            System.out.println("qq");
        }else if (obj==wechat){
            System.out.println("wechat");
        }else if (obj==animal){
            System.out.println("更换动物");
            Random r = new Random();
            int num = Math.abs(r.nextInt())%ANIMAL_CNT + 1;
            System.out.println(num);
            Path = "..\\PuzzleGame_Ultimate\\image\\animal\\animal" + num + "\\";
            InitData();
            step = 0;
            InitImage();
        }else if (obj==girl){
            System.out.println("更换girl");
            Random r = new Random();
            int num = Math.abs(r.nextInt())%GIRL_CNT + 1;
            System.out.println(num);
            Path = "..\\PuzzleGame_Ultimate\\image\\girl\\girl" + num + "\\";
            InitData();
            step = 0;
            InitImage();
        }else if (obj==sport){
            System.out.println("更换运动");
            Random r = new Random();
            int num = Math.abs(r.nextInt())%SPORT_CNT + 1;
            System.out.println(num);
            Path = "..\\PuzzleGame_Ultimate\\image\\sport\\sport" + num + "\\";
            InitData();
            step = 0;
            InitImage();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    private List<List<Integer>>neighbor(int x, int y){
        List<List<Integer>>ans = new ArrayList<>();
        int []dx = new int[]{-1,0,1,0};
        int []dy = new int[]{0,1,0,-1};
        for (int k=0;k<4;k++){
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (nx<0||ny<0||nx>3||ny>3) continue;
            List<Integer>tempList = new ArrayList<>();
            tempList.add(nx);
            tempList.add(ny);
            ans.add(tempList);
        }
        return ans;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        List<List<Integer>>coordinate = neighbor(zerox,zeroy);
        for (List<Integer>now : coordinate){
            int nowx = now.getFirst();
            int nowy = now.getLast();
            if (obj==jLabels[nowx][nowy]){
                System.out.println("鼠标按下事件");
                step++;
                int tempval = data[nowx][nowy];
                data[nowx][nowy] = data[zerox][zeroy];
                data[zerox][zeroy] = tempval;
                zerox = nowx;
                zeroy = nowy;
                InitImage();
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object obj = e.getSource();
        if (obj instanceof JLabel){
            ((JLabel) obj).setBorder(new BevelBorder(BevelBorder.RAISED));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object obj = e.getSource();
        if (obj instanceof JLabel){
            ((JLabel) obj).setBorder(new BevelBorder(BevelBorder.LOWERED));
        }
    }
}
