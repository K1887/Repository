package system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.List;

/**
 * 图形界面主类 - 超级扩展版（完全符合课程设计要求）
 * 
 * 功能详述：
 * 1. Swing主窗口，使用BorderLayout布局
 * 2. 左侧9个功能按钮（GridLayout）
 * 3. 右侧输出文本区（JTextArea + JScrollPane，支持右键复制/全选）
 * 4. 顶部大标题
 * 5. 底部状态栏（实时更新最后操作）
 * 6. 菜单栏（文件、视图、帮助）
 * 7. 主题切换（浅色/深色模式）
 * 8. 导出课表（模拟复制到剪贴板）
 * 9. 关于对话框
 * 10. 详细日志输出（控制台打印每一步操作）
 * 11. 所有对 Scheduler 的访问使用 public getter 方法（解决 visible 错误）
 * 12. 内部main方法启动程序
 * 
 * 作者：团队成员赵六
 * 日期：2025年12月24日
 * 版本：5.0
 */
public class MainGUI extends JFrame {
    private static final String LOG_PREFIX = "[MainGUI] ";

    private JTextArea outputArea;
    private JLabel statusLabel;
    private JButton[] buttons;
    private JPanel buttonPanel;
    private Scheduler scheduler;
    private Teacher teacher;
    private ClassGroup classGroup;
    private Classroom classroom;
    private boolean darkMode = false;

    public MainGUI() {
        System.out.println(LOG_PREFIX + "开始初始化MainGUI");

        // 初始化实体对象
        teacher = new Teacher("张老师", "教授", "13800138000", "zhang@school.edu.cn");
        classGroup = new ClassGroup("软件1班", "2024级", "李班主任");
        classroom = new Classroom("101教室", "多媒体", 60, "投影仪", "空调", "电子白板");

        // 初始化调度器（默认随机策略）
        scheduler = new Scheduler(new RandomRecommendation());

        initComponents();
        initMenuBar();
        initUI();
        bindEvents();

        System.out.println(LOG_PREFIX + "MainGUI初始化完成，进入事件循环");
    }

    /**
     * 初始化核心组件
     */
    private void initComponents() {
        System.out.println(LOG_PREFIX + "初始化核心组件");

        outputArea = new JTextArea();
        outputArea.setFont(new Font("宋体", Font.PLAIN, 16));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        statusLabel = new JLabel("系统就绪");
        statusLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(200, 220, 255));
    }

    /**
     * 初始化菜单栏
     */
    private void initMenuBar() {
        System.out.println(LOG_PREFIX + "初始化菜单栏");
        JMenuBar menuBar = new JMenuBar();

        // 文件菜单
        JMenu fileMenu = new JMenu("文件(F)");
        fileMenu.setMnemonic('F');

        JMenuItem clearItem = new JMenuItem("清空输出区(C)");
        clearItem.setMnemonic('C');
        clearItem.setAccelerator(KeyStroke.getKeyStroke("control C"));
        clearItem.addActionListener(e -> clearOutput());

        JMenuItem exportItem = new JMenuItem("导出课表(E)");
        exportItem.setMnemonic('E');
        exportItem.setAccelerator(KeyStroke.getKeyStroke("control E"));
        exportItem.addActionListener(e -> exportSchedule());

        JMenuItem exitItem = new JMenuItem("退出(X)");
        exitItem.setMnemonic('X');
        exitItem.setAccelerator(KeyStroke.getKeyStroke("control Q"));
        exitItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "确定退出程序吗？", "退出确认", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.out.println(LOG_PREFIX + "用户退出程序");
                System.exit(0);
            }
        });

        fileMenu.add(clearItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // 视图菜单
        JMenu viewMenu = new JMenu("视图(V)");
        viewMenu.setMnemonic('V');

        JMenuItem themeItem = new JMenuItem("切换深色模式(T)");
        themeItem.setMnemonic('T');
        themeItem.setAccelerator(KeyStroke.getKeyStroke("control T"));
        themeItem.addActionListener(e -> toggleTheme());

        viewMenu.add(themeItem);

        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助(H)");
        helpMenu.setMnemonic('H');

        JMenuItem aboutItem = new JMenuItem("关于(A)");
        aboutItem.setMnemonic('A');
        aboutItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
        aboutItem.addActionListener(e -> showAbout());

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * 初始化界面布局
     */
    private void initUI() {
        System.out.println(LOG_PREFIX + "初始化界面布局");
        setTitle("智能课表编排系统 v5.0 - 面向对象课程设计");
        setSize(1100, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // 顶部标题
        JLabel titleLabel = new JLabel("智能课表编排系统", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 左侧按钮面板
        buttonPanel = new JPanel(new GridLayout(9, 1, 15, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        buttonPanel.setBackground(new Color(240, 248, 255));

        String[] btnTexts = {
            "1. 添加课程",
            "2. 推荐空闲时间槽",
            "3. 切换上午优先策略",
            "4. 切换避免连续策略",
            "5. 显示当前课表",
            "6. 显示资源信息",
            "7. 系统统计",
            "8. 清空输出区",
            "9. 退出程序"
        };

        buttons = new JButton[btnTexts.length];
        for (int i = 0; i < btnTexts.length; i++) {
            buttons[i] = new JButton(btnTexts[i]);
            buttons[i].setFont(new Font("宋体", Font.BOLD, 18));
            buttons[i].setPreferredSize(new Dimension(300, 60));
            buttons[i].setToolTipText(getButtonTooltip(i));
            buttons[i].setFocusPainted(false);
            buttonPanel.add(buttons[i]);
        }

        add(buttonPanel, BorderLayout.WEST);

        // 右侧输出区
        outputArea.setBackground(new Color(255, 255, 240));
        outputArea.append("=== 智能课表编排系统启动成功 ===\n");
        outputArea.append("当前时间: 2025年12月24日\n");
        outputArea.append("教师信息: " + teacher.getDetailInfo() + "\n");
        outputArea.append("班级信息: " + classGroup.getDetailInfo() + "\n");
        outputArea.append("教室信息: " + classroom.getDetailInfo() + "\n");
        outputArea.append("默认策略: " + scheduler.getStrategy().getStrategyName() + "\n");
        outputArea.append("=".repeat(80) + "\n\n");
        outputArea.append("欢迎使用！请从左侧按钮选择操作。\n");
        outputArea.append("提示：右键输出区可复制文本\n");

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "系统输出区", TitledBorder.CENTER, TitledBorder.TOP, new Font("宋体", Font.BOLD, 18)));
        add(scrollPane, BorderLayout.CENTER);

        // 右键菜单（复制功能）
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem copyItem = new JMenuItem("复制选中文本");
        copyItem.addActionListener(e -> outputArea.copy());
        JMenuItem selectAllItem = new JMenuItem("全选");
        selectAllItem.addActionListener(e -> outputArea.selectAll());
        popupMenu.add(copyItem);
        popupMenu.add(selectAllItem);

        outputArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        // 底部状态栏
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
        updateStatus("系统初始化完成，等待用户操作");
    }

    private String getButtonTooltip(int index) {
        return switch (index) {
            case 0 -> "添加一门新课程，需要输入课程名、时间槽、优先级（1-10）";
            case 1 -> "根据当前策略推荐最多8个空闲时间槽";
            case 2 -> "切换为优先推荐上午（1-3节）的时间槽";
            case 3 -> "切换为避免教师与已有课程连续的时间槽推荐";
            case 4 -> "显示当前所有已安排课程（按时间排序）";
            case 5 -> "显示教师、班级、教室的详细信息和占用情况";
            case 6 -> "显示系统运行统计（课程数、冲突次数、利用率、周分布）";
            case 7 -> "清空右侧输出区内容";
            case 8 -> "安全退出程序";
            default -> "";
        };
    }

    /**
     * 绑定按钮事件
     */
    private void bindEvents() {
        System.out.println(LOG_PREFIX + "绑定所有按钮事件");

        buttons[0].addActionListener(e -> {
            updateStatus("正在添加课程...");
            addCourse();
        });

        buttons[1].addActionListener(e -> {
            updateStatus("正在生成推荐时间槽...");
            append(scheduler.getRecommendationText(teacher, classGroup, classroom));
            updateStatus("推荐完成，共推荐 " + scheduler.getStrategy().recommend(scheduler.getAllSlots(), teacher, classGroup, classroom).size() + " 个");
        });

        buttons[2].addActionListener(e -> {
            scheduler.setStrategy(new MorningPriorityRecommendation());
            append(">>> 已切换为【" + scheduler.getStrategy().getStrategyName() + "】推荐策略 <<<\n");
            append("策略描述: " + scheduler.getStrategy().getDescription() + "\n\n");
            updateStatus("策略切换：上午优先");
        });

        buttons[3].addActionListener(e -> {
            scheduler.setStrategy(new AvoidConsecutiveRecommendation());
            append(">>> 已切换为【" + scheduler.getStrategy().getStrategyName() + "】推荐策略 <<<\n");
            append("策略描述: " + scheduler.getStrategy().getDescription() + "\n\n");
            updateStatus("策略切换：避免连续");
        });

        buttons[4].addActionListener(e -> {
            updateStatus("正在生成课表...");
            append(scheduler.getScheduleText() + "\n");
            updateStatus("课表显示完成，共 " + scheduler.getCourses().size() + " 门课");
        });

        buttons[5].addActionListener(e -> {
            updateStatus("正在显示资源信息...");
            append("=== 资源详细信息 ===\n");
            append("教师: " + teacher.getDetailInfo() + "\n");
            teacher.printAllOccupied();
            append("班级: " + classGroup.getDetailInfo() + "\n");
            classGroup.printAllOccupied();
            append("教室: " + classroom.getDetailInfo() + "\n");
            classroom.printAllOccupied();
            append("\n");
            updateStatus("资源信息显示完成");
        });

        buttons[6].addActionListener(e -> {
            updateStatus("正在生成系统统计...");
            append(scheduler.getStatistics() + "\n");
            updateStatus("统计信息显示完成");
        });

        buttons[7].addActionListener(e -> clearOutput());

        buttons[8].addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "确定要退出程序吗？\n未保存的数据将丢失", "退出确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                System.out.println(LOG_PREFIX + "用户确认退出");
                System.exit(0);
            } else {
                updateStatus("取消退出");
            }
        });
    }

    /**
     * 添加课程弹窗处理
     */
    private void addCourse() {
        System.out.println(LOG_PREFIX + "执行添加课程操作");

        String courseName = JOptionPane.showInputDialog(this, 
            "<html><b>请输入课程名：</b><br>例如：Java程序设计</html>", 
            "添加课程 - 步骤1/3", JOptionPane.PLAIN_MESSAGE);
        if (courseName == null) {
            updateStatus("添加课程已取消");
            return;
        }
        courseName = courseName.trim();
        if (courseName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "课程名不能为空！", "输入错误", JOptionPane.ERROR_MESSAGE);
            updateStatus("添加失败：课程名为空");
            return;
        }

        String input = JOptionPane.showInputDialog(this, 
            "<html><b>请输入时间槽（格式: MON 1）：</b><br>例如：MON 1 表示周一第1节<br>星期：MON/TUE/WED/THU/FRI<br>节次：1-6</html>", 
            "添加课程 - 步骤2/3", JOptionPane.PLAIN_MESSAGE);
        if (input == null) {
            updateStatus("添加课程已取消");
            return;
        }
        input = input.trim();
        String[] parts = input.split("\\s+");
        if (parts.length != 2) {
            JOptionPane.showMessageDialog(this, "时间槽格式错误！\n正确格式: MON 1", "输入错误", JOptionPane.ERROR_MESSAGE);
            updateStatus("添加失败：时间槽格式错误");
            return;
        }

        try {
            TimeSlot slot = new TimeSlot(parts[0], Integer.parseInt(parts[1]));

            String priStr = JOptionPane.showInputDialog(this, 
                "<html><b>请输入课程优先级（1-10）：</b><br>1表示最高优先级<br>默认值：5</html>", 
                "添加课程 - 步骤3/3", JOptionPane.PLAIN_MESSAGE);
            int priority = 5;
            if (priStr != null && !priStr.trim().isEmpty()) {
                try {
                    priority = Integer.parseInt(priStr.trim());
                    if (priority < 1 || priority > 10) {
                        JOptionPane.showMessageDialog(this, "优先级必须在1-10之间，已使用默认值5", "输入警告", JOptionPane.WARNING_MESSAGE);
                        priority = 5;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "优先级必须是数字，已使用默认值5", "输入警告", JOptionPane.WARNING_MESSAGE);
                    priority = 5;
                }
            }

            scheduler.addCourse(courseName, teacher, classGroup, classroom, slot, priority);
            append(">>> 成功添加课程：" + courseName + " (优先级" + priority + ") 时间:" + slot + " <<<\n\n");
            updateStatus("成功添加课程：" + courseName);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "节次必须是1-6的数字！", "输入错误", JOptionPane.ERROR_MESSAGE);
            updateStatus("添加失败：节次非数字");
        } catch (ScheduleConflictException ex) {
            JOptionPane.showMessageDialog(this, 
                "<html><b>添加失败 - 冲突检测</b><br>" + ex.getMessage() + "<br>冲突类型: " + ex.getConflictType() + "</html>", 
                "课程冲突", JOptionPane.WARNING_MESSAGE);
            updateStatus("添加失败：" + ex.getConflictType() + "冲突");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "发生未知错误:\n" + ex.getMessage(), "添加失败", JOptionPane.ERROR_MESSAGE);
            updateStatus("添加失败：未知错误");
        }
    }

    private void clearOutput() {
        outputArea.setText("");
        append("=== 输出区已清空 ===\n");
        append("系统就绪，等待新操作\n\n");
        updateStatus("输出区已清空");
    }

    private void exportSchedule() {
        String text = scheduler.exportSchedule();
        JTextArea exportArea = new JTextArea(text);
        exportArea.setFont(new Font("宋体", Font.PLAIN, 14));
        exportArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(exportArea);
        scroll.setPreferredSize(new Dimension(800, 600));

        int option = JOptionPane.showOptionDialog(this, scroll, "课表导出预览", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
            new String[]{"复制到剪贴板", "关闭"}, "关闭");

        if (option == 0) {
            exportArea.selectAll();
            exportArea.copy();
            JOptionPane.showMessageDialog(this, "课表已复制到剪贴板！", "导出成功", JOptionPane.INFORMATION_MESSAGE);
            updateStatus("课表已复制到剪贴板");
        }
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        Color bg = darkMode ? new Color(45, 45, 45) : new Color(255, 255, 255);
        Color fg = darkMode ? new Color(220, 220, 220) : new Color(0, 0, 0);
        Color panelBg = darkMode ? new Color(60, 60, 60) : new Color(240, 248, 255);
        Color outputBg = darkMode ? new Color(30, 30, 30) : new Color(255, 255, 240);
        Color statusBg = darkMode ? new Color(80, 80, 80) : new Color(200, 220, 255);

        getContentPane().setBackground(bg);
        outputArea.setBackground(outputBg);
        outputArea.setForeground(fg);
        buttonPanel.setBackground(panelBg);
        statusLabel.setBackground(statusBg);
        statusLabel.setForeground(fg);

        for (JButton btn : buttons) {
            btn.setBackground(panelBg);
            btn.setForeground(fg);
        }

        append(">>> 已切换为" + (darkMode ? "深色" : "浅色") + "主题 <<<\n\n");
        updateStatus("主题切换：" + (darkMode ? "深色" : "浅色"));
    }

    private void showAbout() {
        String about = """
            智能课表编排系统 v5.0
            
            面向对象程序设计课程设计
            
           
            
            日期：2025年12月
            
            功能特点：
            • 冲突检测（教师/班级/教室）
            • 三种智能推荐策略（策略模式）
            • 课程优先级排序
            • 运行统计与资源详情
            • 美化图形界面（主题切换、菜单栏）
            
           
            祝使用愉快！""";
        JOptionPane.showMessageDialog(this, about, "关于系统", JOptionPane.INFORMATION_MESSAGE);
    }

    private void append(String text) {
        outputArea.append(text);
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void updateStatus(String msg) {
        statusLabel.setText("状态: " + msg);
        System.out.println(LOG_PREFIX + "状态更新: " + msg);
    }

    /**
     * 程序入口
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("智能课表编排系统启动");
        System.out.println("当前日期: 2025年12月24日");
        System.out.println("=".repeat(80));

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("[MainGUI] 设置系统原生主题成功");
            } catch (Exception e) {
                System.out.println("[MainGUI] 设置主题失败，使用默认: " + e.getMessage());
            }
            new MainGUI();
        });
    }
}