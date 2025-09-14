package com.example;

public class APP {
    public static String now_user;
    public static int now_greatest_grade;
    public static String URL = "jdbc:mysql://127.0.0.1:3306/your_database";
    public static String USERNAME = "your_username";
    public static String PASSWORD = "your_password";
    public static void main(String []args) throws Exception{
        //new User_info();
        new LoginJFrame();
        //new GameJFrame();


    }
}
