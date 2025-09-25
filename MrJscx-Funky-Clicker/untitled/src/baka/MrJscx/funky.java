//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
package baka.MrJscx;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
//import javax.swing.baka;



public class funky {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  //输入函数需调用
            final int UP=10;
            final int[] currentFunky = {0};
            final int[] funkyPerClick = {1};

            JFrame windowBaka= new JFrame("Funky增量");//创建窗口 and 标题
            windowBaka.setSize(440, 200);//窗口大小
            windowBaka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 添加关闭操作
            windowBaka.setLayout(null); // 设置布局管理器
            windowBaka.setResizable(false);//禁止调整窗口大小

            JButton button = new JButton("点击我");
            button.setBounds(5,0,200,100);

            JButton button_power = new JButton("升级点击力量");
            button_power.setBounds(220,0,200,100);

            // 创建显示score的标签
            JLabel scoreLabel = new JLabel("Funky值: " + currentFunky[0]);
            scoreLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
            scoreLabel.setBounds(5, 140, 430, 16);

            JLabel button_up = new JLabel("单次点击获得Funky值:" + funkyPerClick[0]);
            button_up.setFont(new Font("微软雅黑", Font.BOLD, 12));
            button_up.setBounds(5, 105, 430, 12);

            JLabel button_power_up = new JLabel("点击力量升级花费:" + (UP* funkyPerClick[0] * funkyPerClick[0]));
            button_power_up.setFont(new Font("微软雅黑", Font.BOLD, 12));
            button_power_up.setBounds(220, 105, 430, 12);

            // 为按钮添加点击事件
            button.addActionListener(e -> {
                currentFunky[0]+=funkyPerClick[0]; // 增加分数
                scoreLabel.setText("Funky值: " + currentFunky[0]); // 更新显示
            });

            button_power.addActionListener(e -> {
                if (currentFunky[0] >= (UP * funkyPerClick[0] * funkyPerClick[0])) {
                    currentFunky[0] -= (UP * funkyPerClick[0] * funkyPerClick[0]);
                    funkyPerClick[0]++;
                    button_up.setText("单次点击获得Funky值:" + funkyPerClick[0]);
                    button_power_up.setText("点击力量升级花费:" + (UP* funkyPerClick[0] * funkyPerClick[0]));
                } // 判断是否达成条件
                scoreLabel.setText("Funky值: " + currentFunky[0]); // 更新显示
            });

            windowBaka.add(button);
            windowBaka.add(button_power);

            windowBaka.add(scoreLabel); // 在底部显示分数
            windowBaka.add(button_up);
            windowBaka.add(button_power_up);

            windowBaka.setVisible(true);//显示窗口

            System.out.println("请输入你想要的funky值");
            currentFunky[0]=reader.nextInt();
            scoreLabel.setText("Funky值: " + currentFunky[0]);//开发者测试
    }
}