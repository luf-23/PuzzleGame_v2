# 拼图游戏

## 项目简介
本项目是一个基于 Java Swing 实现的 4x4 拼图小游戏。玩家可以通过鼠标拖动或使用键盘方向键将碎片移动到空格处，最终还原为完整图片。支持多种图片主题切换（如动物、人物、运动等）。

## 核心玩法与功能
- 支持用户登录与注册，成绩与账号关联
- 多种图片主题自由切换（动物、girl、运动）
- 游戏进度保存，退出时会同步成绩
- 记录玩家当前步数与历史最高分
- 支持鼠标和键盘两种操作方式
- 提供作弊/自动完成入口（按下特定按键）
- 简单“关于我们”菜单

## 运行环境
- JDK 8 及以上
- 依赖 Java Swing 库
- 需本地 MySQL 数据库用于用户与成绩信息存储

## 快速开始

1. **克隆项目**
   ```bash
   git clone https://github.com/luf-23/PuzzleGame_v2.git
   ```

2. **配置数据库**
   - 修改 `src/main/java/com/example/APP.java` 中的数据库连接参数（`URL`、`USERNAME`、`PASSWORD`）。

3. **运行程序**
   进入 `src/main/java/com/example/` 目录，运行 `APP.java` 的主方法：
   ```bash
   javac com/example/*.java
   java com.example.APP
   ```

4. **体验游戏**
   - 先进行注册/登录
   - 选择图片主题并开始游戏
   - 使用鼠标或键盘进行拼图
   - 可在菜单栏切换主题或重新开始

## 操作说明
- 鼠标点击可交换空白格相邻的图片块
- 键盘方向键（↑、↓、←、→）也可移动图片块
- “更换图片”菜单可切换主题
- “保存并退出”会同步成绩到数据库
- 特殊按键功能：
  - `A` 键：刷新图片
  - `W` 键：直接拼好（作弊）

## 技术栈
- Java Swing 图形界面
- JDBC 连接 MySQL 数据库
- 事件驱动（键盘与鼠标监听）

## 目录结构
```
PuzzleGame_v2/
├── src/
│   └── main/java/com/example/
│       ├── APP.java         # 启动类，数据库配置
│       ├── GameJFrame.java  # 主游戏窗体与逻辑
│       ├── LoginJFrame.java # 登录注册窗体
│       └── ...（其他文件）
├── image/                   # 各类拼图图片资源
└── README.md
```


---
