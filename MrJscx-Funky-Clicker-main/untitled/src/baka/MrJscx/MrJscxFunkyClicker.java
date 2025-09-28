package baka.MrJscx;

import baka.MrJscx.save.GameData;
import baka.MrJscx.save.SaveManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class MrJscxFunkyClicker {
    private JFrame frame;
    private JLabel label_funky;
    private JLabel label_funkyPerClick;
    private JLabel label_autoClickers;
    private JButton button_click;
    private JButton button_upgrade;
    private JButton button_hireAssistant;
    private JButton button_save;
    private JButton button_load;

    // 使用数组包装，便于在匿名类中修改
    private final int[] currentFunky = {0};
    private final int[] funkyPerClick = {1};
    private final int[] autoClickers = {0};

    // 自动点击线程
    private Thread autoClickThread;
    private volatile boolean running = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MrJscxFunkyClicker().createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "启动失败: " + e.getMessage(),
                        "错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void createAndShowGUI() {
        // 创建主窗口
        frame = new JFrame("MrJscx Funky Clicker - 0.1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 400); // 设置初始大小
        frame.setLocationRelativeTo(null); // 窗口居中显示
        frame.setResizable(true); // 允许调整大小（可选）


        // 创建顶部面板 - 显示数据
        JPanel topPanel = createTopPanel();
        frame.add(topPanel, BorderLayout.NORTH);

        // 创建中间面板 - 主要操作按钮
        JPanel centerPanel = createCenterPanel();
        frame.add(centerPanel, BorderLayout.CENTER);

        // 创建底部面板 - 存档操作
        JPanel bottomPanel = createBottomPanel();
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // 设置窗口属性
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 启动自动点击线程
        startAutoClicker();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 当前Funky显示
        panel.add(new JLabel("当前Funky:"));
        label_funky = new JLabel("0");
        label_funky.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label_funky);

        // 每次点击获得Funky
        panel.add(new JLabel("每次点击获得:"));
        label_funkyPerClick = new JLabel("1");
        panel.add(label_funkyPerClick);

        // baka助手数量
        panel.add(new JLabel("baka数量:"));
        label_autoClickers = new JLabel("0");
        panel.add(label_autoClickers);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 垂直排列
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 点击按钮
        button_click = new JButton("点击获得Funky!");
        button_click.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button_click.setMaximumSize(new Dimension(250, 60));
        button_click.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        button_click.addActionListener(new ClickListener());
        panel.add(button_click);

        // 添加间距
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 升级按钮
        button_upgrade = new JButton("升级点击力量 (20 Funky)");
        button_upgrade.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_upgrade.setEnabled(false);
        button_upgrade.addActionListener(new UpgradeClickListener());
        panel.add(button_upgrade);

        // 添加间距
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 雇佣baka助手按钮
        button_hireAssistant = new JButton("雇佣baka (50 Funky)");
        button_hireAssistant.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_hireAssistant.setEnabled(false);
        button_hireAssistant.addActionListener(new HireAssistantListener());
        panel.add(button_hireAssistant);

        return panel;
    }


    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("存档管理"));

        // 保存按钮
        button_save = new JButton("保存游戏");
        button_save.addActionListener(new SaveGameListener());
        panel.add(button_save);

        // 加载按钮
        button_load = new JButton("加载游戏");
        button_load.addActionListener(new LoadGameListener());
        panel.add(button_load);

        return panel;
    }

    private void updateLabels() {
        SwingUtilities.invokeLater(() -> {
            // 格式化数字显示
            NumberFormat formatter = NumberFormat.getInstance();
            label_funky.setText(formatter.format(currentFunky[0]));
            label_funkyPerClick.setText(formatter.format(funkyPerClick[0]));
            label_autoClickers.setText(formatter.format(autoClickers[0]));

            // 更新按钮状态
            button_upgrade.setText("升级点击力量 (" + formatter.format(getUpgradeCost()) + " Funky)");
            button_hireAssistant.setText("雇佣baka (" + formatter.format(getAssistantCost()) + " Funky)");

            button_upgrade.setEnabled(currentFunky[0] >= getUpgradeCost());
            button_hireAssistant.setEnabled(currentFunky[0] >= getAssistantCost());
        });
    }

    private int getUpgradeCost() {
        return (funkyPerClick[0] + 1) * 10;
    }

    private int getAssistantCost() {
        return (autoClickers[0] + 1) * 50;
    }

    // ========== 事件监听器 ==========

    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentFunky[0] += funkyPerClick[0];
            updateLabels();
        }
    }

    private class UpgradeClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int cost = getUpgradeCost();
            if (currentFunky[0] >= cost) {
                currentFunky[0] -= cost;
                funkyPerClick[0]++;
                updateLabels();
            }
        }
    }

    private class HireAssistantListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int cost = getAssistantCost();
            if (currentFunky[0] >= cost) {
                currentFunky[0] -= cost;
                autoClickers[0]++;
                updateLabels();
            }
        }
    }

    // ========== 存档系统集成 ==========

    private class SaveGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("保存游戏");
            fileChooser.setSelectedFile(new File("funky_save.dat"));

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    // 创建游戏数据对象
                    GameData saveData = new GameData();
                    saveData.currentFunky = currentFunky[0];
                    saveData.funkyPerClick = funkyPerClick[0];
                    saveData.autoClickers = autoClickers[0];

                    // 使用新的存档管理器保存
                    SaveManager.save(fileToSave, saveData);

                    JOptionPane.showMessageDialog(frame,
                            "游戏已成功保存到:\n" + fileToSave.getAbsolutePath(),
                            "保存成功",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "保存失败: " + ex.getMessage(),
                            "错误",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    private class LoadGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("加载游戏");

            int userSelection = fileChooser.showOpenDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();

                try {
                    // 使用新的存档管理器加载
                    GameData loadedData = SaveManager.load(fileToLoad);

                    // 应用加载的数据
                    currentFunky[0] = loadedData.currentFunky;
                    funkyPerClick[0] = loadedData.funkyPerClick;
                    autoClickers[0] = loadedData.autoClickers;

                    updateLabels();

                    JOptionPane.showMessageDialog(frame,
                            "游戏已成功加载!\n" +
                                    "Funky: " + currentFunky[0] + "\n" +
                                    "点击力量: " + funkyPerClick[0] + "\n" +
                                    "baka数量: " + autoClickers[0],
                            "加载成功",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "加载失败: " + ex.getMessage(),
                            "错误",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "存档文件格式错误: " + ex.getMessage(),
                            "错误",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    // ========== 自动点击系统 ==========

    private void startAutoClicker() {
        autoClickThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000); // 每秒执行一次

                    if (autoClickers[0] > 0) {
                        // 每个baka助手每秒产生1个Funky
                        int totalAutoFunky = autoClickers[0];
                        currentFunky[0] += totalAutoFunky;

                        // 更新显示
                        updateLabels();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        autoClickThread.setDaemon(true);
        autoClickThread.start();
    }

    // 清理资源
    public void dispose() {
        running = false;
        if (autoClickThread != null) {
            autoClickThread.interrupt();
        }
        if (frame != null) {
            frame.dispose();
        }
    }
}