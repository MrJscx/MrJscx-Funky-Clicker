//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
package baka.MrJscx;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.io.*;
//import javax.swing.baka;


public class funky {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  //输入函数需调用
        final int UP = 10;//  点击力量基础价格
        final int autoClickerCost = 50; // baka基础价格
        final int SAVE_VERSION = 2; // 存档版本号(0.0.2)

        final int[] currentFunky = {0};
        final int[] funkyPerClick = {1};
        final int[] autoClickers = {0}; // baka数量

        JFrame windowBaka = new JFrame("Funky增量");//创建窗口 and 标题
        windowBaka.setSize(440, 500);//窗口大小
        windowBaka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 添加关闭操作
        windowBaka.setLayout(null); // 设置布局管理器
        windowBaka.setResizable(false);//禁止调整窗口大小

        JButton button = new JButton("点击我获得Funky");
        button.setBounds(5, 5, 200, 100);

        JButton button_power = new JButton("升级点击力量");
        button_power.setBounds(220, 5, 200, 100);

        // 存档按钮
        JButton button_save = new JButton("存档");
        button_save.setBounds(290, 420, 60, 40);

        // 读档按钮
        JButton button_load = new JButton("读档");
        button_load.setBounds(360, 420, 60, 40);

        JButton buttonAuto = new JButton("雇佣baka助手");
        buttonAuto.setBounds(5, 150, 200, 60);


        // 创建显示score的标签
        JLabel scoreLabel = new JLabel("Funky值: " + currentFunky[0]);
        scoreLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        scoreLabel.setBounds(5, 430, 430, 16);

        JLabel button_up = new JLabel("单次点击获得Funky值:" + funkyPerClick[0]);
        button_up.setFont(new Font("微软雅黑", Font.BOLD, 12));
        button_up.setBounds(5, 105, 430, 12);

        JLabel buttonAutoCost = new JLabel("购买baka助手花费：" + autoClickerCost * (autoClickers[0] * autoClickers[0] + 1));
        buttonAutoCost.setFont(new Font("微软雅黑", Font.BOLD, 12));
        buttonAutoCost.setBounds(5, 215, 430, 12);

        JLabel autoClickerLabel = new JLabel("baka助手数量: " + autoClickers[0]);
        autoClickerLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
        autoClickerLabel.setBounds(5, 230, 200, 12);

        JLabel button_power_up = new JLabel("点击力量升级花费:" + (UP * funkyPerClick[0] * funkyPerClick[0]));
        button_power_up.setFont(new Font("微软雅黑", Font.BOLD, 12));
        button_power_up.setBounds(220, 105, 430, 12);


// 存档按钮事件 - 带文件选择对话框
        button_save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择存档位置");
            fileChooser.setSelectedFile(new File("funky_save.txt"));

            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("文本文件 (*.txt)", "txt"));

            int userSelection = fileChooser.showSaveDialog(windowBaka);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                    fileToSave = new File(fileToSave.getParent(), fileToSave.getName() + ".txt");
                }

                try {
                    FileWriter writer = new FileWriter(fileToSave);
                    writer.write(SAVE_VERSION + "\n"); // 第一行：版本号
                    writer.write(currentFunky[0] + "\n");
                    writer.write(funkyPerClick[0] + "\n");
                    writer.write(autoClickers[0] + "\n"); // 助手数量
                    writer.close();
                    JOptionPane.showMessageDialog(windowBaka,
                            "存档成功！\n版本: " + SAVE_VERSION + "\n保存位置: " + fileToSave.getAbsolutePath(),
                            "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(windowBaka,
                            "存档失败！\n错误信息: " + ex.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

// 读档按钮事件 - 带文件选择对话框
        button_load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择存档文件");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("文本文件 (*.txt)", "txt"));

            int userSelection = fileChooser.showOpenDialog(windowBaka);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();

                try {
                    if (!fileToLoad.exists()) {
                        JOptionPane.showMessageDialog(windowBaka, "选择的文件不存在！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    BufferedReader readerFile = new BufferedReader(new FileReader(fileToLoad));

                    // 读取版本号
                    int saveVersion = Integer.parseInt(readerFile.readLine());

                    // 读取基础数据
                    currentFunky[0] = Integer.parseInt(readerFile.readLine());
                    funkyPerClick[0] = Integer.parseInt(readerFile.readLine());

                    // 根据版本号决定如何处理后续数据
                    if (saveVersion >= 2) {
                        // 版本2及以上：读取助手数量
                        autoClickers[0] = Integer.parseInt(readerFile.readLine());
                    } else {
                        // 版本1：没有助手数据，设为0
                        autoClickers[0] = 0;
                    }

                    readerFile.close();

                    // 更新界面显示
                    scoreLabel.setText("Funky值: " + currentFunky[0]);
                    button_up.setText("单次点击获得Funky值:" + funkyPerClick[0]);
                    button_power_up.setText("点击力量升级花费:" + (UP * funkyPerClick[0] * funkyPerClick[0]));
                    autoClickerLabel.setText("baka助手数量: " + autoClickers[0]);
                    buttonAutoCost.setText("购买baka助手花费：" + autoClickerCost * (autoClickers[0] * autoClickers[0] + 1));

                    JOptionPane.showMessageDialog(windowBaka,
                            "读档成功！\n版本号: " + saveVersion + "\n文件: " + fileToLoad.getName(),
                            "提示", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(windowBaka,
                            "读档失败！\n错误信息: " + ex.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(windowBaka,
                            "存档文件损坏或格式不正确！", "错误", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(windowBaka,
                            "读取文件时发生未知错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // 为按钮添加点击事件
        button.addActionListener(e -> {
            currentFunky[0] += funkyPerClick[0]; // 增加分数
            scoreLabel.setText("Funky值: " + currentFunky[0]); // 更新显示
        });

        button_power.addActionListener(e -> {
            if (currentFunky[0] >= (UP * funkyPerClick[0] * funkyPerClick[0])) {
                currentFunky[0] -= (UP * funkyPerClick[0] * funkyPerClick[0]);
                funkyPerClick[0]++;
                button_up.setText("单次点击获得Funky值:" + funkyPerClick[0]);
                button_power_up.setText("点击力量升级花费:" + (UP * funkyPerClick[0] * funkyPerClick[0]));
            } // 判断是否达成条件
            scoreLabel.setText("Funky值: " + currentFunky[0]); // 更新显示
        });

        buttonAuto.addActionListener(e -> {
            int cost = autoClickerCost * (autoClickers[0] * autoClickers[0] + 1);
            if (currentFunky[0] >= cost) {
                currentFunky[0] -= cost;
                autoClickers[0]++;

                // 更新所有相关显示
                scoreLabel.setText("Funky值: " + currentFunky[0]);
                autoClickerLabel.setText("baka助手数量: " + autoClickers[0]);
                buttonAutoCost.setText("购买baka助手花费：" + autoClickerCost * (autoClickers[0] * autoClickers[0] + 1));

            }
        });


        //  按钮显示
        windowBaka.add(button);
        windowBaka.add(button_power);
        windowBaka.add(button_save);
        windowBaka.add(button_load);
        windowBaka.add(buttonAuto);
        windowBaka.add(buttonAutoCost);
        windowBaka.add(autoClickerLabel);

        windowBaka.add(scoreLabel); // 在底部显示分数
        windowBaka.add(button_up);
        windowBaka.add(button_power_up);


        Timer autoTimer = new Timer(1000, e -> { // 每秒触发一次
            if (autoClickers[0] > 0) {
                currentFunky[0] += autoClickers[0]; // 每个助手每秒产生1点Funky
                scoreLabel.setText("Funky值: " + currentFunky[0]);
            }
        });
        autoTimer.start();





        windowBaka.setVisible(true);//显示窗口

//            System.out.println("请输入你想要的funky值");
//            currentFunky[0]=reader.nextInt();
//            scoreLabel.setText("Funky值: " + currentFunky[0]);//开发者测试
    }
}