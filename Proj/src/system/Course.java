package system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

/**
 * 课程实体类 - 超级扩展版
 * 
 * 功能详述：
 * 1. 表示一门完整的课程信息
 * 2. 包含课程名、类型（必修/选修）、学分、总课时、描述、开课学期、优先级
 * 3. 关联教师、班级、教室、时间槽
 * 4. 自定义异常 ScheduleConflictException（带冲突类型）
 * 5. 实现 Comparable 接口，支持按时间 + 优先级排序
 * 6. 提供多种辅助方法：详细打印、简短显示、校验、克隆
 * 7. 详细日志输出和输入校验
 * 8. 内部 main 方法进行全面单元测试（创建课程、排序、异常测试、性能测试）
 * 
 * 作者：团队成员王五
 * 日期：2025年12月24日
 * 版本：4.0
 */
class ScheduleConflictException extends Exception {
    private static final long serialVersionUID = 20251224L;

    private final String conflictType; // 冲突类型：教师/班级/教室

    public ScheduleConflictException(String message, String conflictType) {
        super(message);
        this.conflictType = conflictType;
        System.out.println("[ScheduleConflictException] " + conflictType + "冲突: " + message);
    }

    public ScheduleConflictException(String message) {
        this(message, "未知");
    }

    public String getConflictType() {
        return conflictType;
    }

    @Override
    public String toString() {
        return "课程冲突异常 [" + conflictType + "]: " + getMessage();
    }
}

/**
 * 课程类 - 核心实体
 */
class Course implements Comparable<Course>, Serializable {
    private static final long serialVersionUID = 20251224L;

    /** 课程名称 */
    private final String name;
    /** 课程类型：必修或选修 */
    private final String type;
    /** 学分 */
    private final double credit;
    /** 总课时数 */
    private final int hours;
    /** 课程描述 */
    private final String description;
    /** 开课学期，如 "2025-2026-1" */
    private final String semester;
    /** 授课教师 */
    private final Teacher teacher;
    /** 上课班级 */
    private final ClassGroup classGroup;
    /** 上课教室 */
    private final Classroom classroom;
    /** 上课时间槽 */
    private final TimeSlot slot;
    /** 课程优先级（1最高，10最低） */
    private final int priority;

    /** 日志前缀 */
    private static final String LOG_PREFIX = "[Course] ";

    /**
     * 完整构造函数（推荐使用）
     */
    public Course(String name, String type, double credit, int hours, String description, String semester,
                  Teacher teacher, ClassGroup classGroup, Classroom classroom, TimeSlot slot, int priority) {
        System.out.println(LOG_PREFIX + "开始创建课程: " + name);

        // 名称校验
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("课程名称不能为空");
        }

        // 类型校验
        if (type == null || !(type.equals("必修") || type.equals("选修"))) {
            throw new IllegalArgumentException("课程类型必须是 '必修' 或 '选修'");
        }

        // 学分校验
        if (credit < 0.5 || credit > 10.0) {
            throw new IllegalArgumentException("学分必须在0.5-10.0之间");
        }

        // 课时校验
        if (hours < 16 || hours > 128) {
            throw new IllegalArgumentException("总课时必须在16-128之间");
        }

        // 优先级校验
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("优先级必须在1-10之间（1最高）");
        }

        this.name = name.trim();
        this.type = type;
        this.credit = credit;
        this.hours = hours;
        this.description = description == null ? "" : description.trim();
        this.semester = semester == null ? "未知学期" : semester.trim();
        this.teacher = teacher;
        this.classGroup = classGroup;
        this.classroom = classroom;
        this.slot = slot;
        this.priority = priority;

        System.out.println(LOG_PREFIX + "成功创建课程: " + toShortString());
    }

    /**
     * 简化构造函数（默认值）
     */
    public Course(String name, Teacher teacher, ClassGroup classGroup, Classroom classroom, TimeSlot slot) {
        this(name, "选修", 4.0, 64, "暂无描述", "2025-2026-1", teacher, classGroup, classroom, slot, 5);
    }

    /**
     * 带优先级的简化构造函数（用于 Scheduler 调用）
     */
    public Course(String name, Teacher teacher, ClassGroup classGroup, Classroom classroom, TimeSlot slot, int priority) {
        this(name, "选修", 4.0, 64, "暂无描述", "2025-2026-1", teacher, classGroup, classroom, slot, priority);
    }

    // Getter 方法
    public String getName() { return name; }
    public String getType() { return type; }
    public double getCredit() { return credit; }
    public int getHours() { return hours; }
    public String getDescription() { return description; }
    public String getSemester() { return semester; }
    public Teacher getTeacher() { return teacher; }
    public ClassGroup getClassGroup() { return classGroup; }
    public Classroom getClassroom() { return classroom; }
    public TimeSlot getSlot() { return slot; }
    public int getPriority() { return priority; }

    /** 简短字符串表示（用于列表显示） */
    public String toShortString() {
        return String.format("%s (%s, %.1f学分, 优先级%d)", name, type, credit, priority);
    }

    /** 完整字符串表示 */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("课程名称: ").append(name).append("\n");
        sb.append("  类型: ").append(type).append(" | 学分: ").append(credit).append(" | 课时: ").append(hours).append("\n");
        sb.append("  优先级: ").append(priority).append(" | 学期: ").append(semester).append("\n");
        sb.append("  描述: ").append(description.isEmpty() ? "无" : description).append("\n");
        sb.append("  教师: ").append(teacher.getName()).append("\n");
        sb.append("  班级: ").append(classGroup.getName()).append("\n");
        sb.append("  教室: ").append(classroom.getName()).append("\n");
        sb.append("  时间: ").append(slot).append("\n");
        return sb.toString();
    }

    /** 打印课程详细信息 */
    public void printDetail() {
        System.out.println("=".repeat(70));
        System.out.println("课程详细信息");
        System.out.println(toString());
        System.out.println("=".repeat(70));
    }

    /** 默认排序：先时间，后优先级 */
    @Override
    public int compareTo(Course o) {
        if (o == null) return 1;
        // 时间排序
        int timeCmp = this.slot.compareTo(o.slot);
        if (timeCmp != 0) return timeCmp;
        // 优先级排序（数字小优先级高）
        return Integer.compare(this.priority, o.priority);
    }

    /** 按优先级排序比较 */
    public int compareByPriority(Course o) {
        if (o == null) return -1;
        return Integer.compare(this.priority, o.priority);
    }

    /** 按学分排序比较 */
    public int compareByCredit(Course o) {
        if (o == null) return -1;
        return Double.compare(this.credit, o.credit);
    }

    /** 克隆课程对象 */
    public Course cloneCourse() {
        System.out.println(LOG_PREFIX + "克隆课程: " + name);
        return new Course(name, type, credit, hours, description, semester, teacher, classGroup, classroom, slot, priority);
    }

    /**
     * Course 类独立单元测试main方法
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("Course 类全面单元测试开始");
        System.out.println("=".repeat(80));

        Teacher teacher = new Teacher("张老师", "教授", "13800138000", "zhang@school.edu.cn");
        ClassGroup classGroup = new ClassGroup("软件1班", "2024级", "李班主任");
        Classroom classroom = new Classroom("101教室", "多媒体", 60);

        java.util.List<Course> courses = new java.util.ArrayList<>();

        String[] names = {"Java程序设计", "数据结构", "操作系统", "计算机网络", "数据库原理",
                          "软件工程", "人工智能", "机器学习", "编译原理", "计算机组成原理"};
        String[] types = {"必修", "选修", "必修", "必修", "选修", "必修", "选修", "选修", "必修", "必修"};
        double[] credits = {4.0, 3.5, 4.0, 3.0, 4.0, 3.0, 3.5, 4.0, 4.0, 3.5};
        int[] priorities = {1, 3, 2, 4, 1, 2, 5, 3, 1, 4};

        long startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            try {
                TimeSlot slot = new TimeSlot("MON", (i % 6) + 1);
                Course course = new Course(names[i], types[i], credits[i], 64, "课程描述" + (i+1), "2025-2026-1",
                                           teacher, classGroup, classroom, slot, priorities[i]);
                courses.add(course);
                if (i < 3) course.printDetail();
            } catch (Exception e) {
                System.out.println("创建课程失败: " + e.getMessage());
            }
        }
        long endTime = System.nanoTime();
        System.out.println("创建10门课程用时: " + (endTime - startTime)/1_000_000 + " ms\n");

        System.out.println("排序前:");
        courses.forEach(c -> System.out.println("  " + c.toShortString()));

        Collections.sort(courses);
        System.out.println("\n默认排序（时间+优先级）:");
        courses.forEach(c -> System.out.println("  " + c.toShortString()));

        courses.sort((a, b) -> a.compareByPriority(b));
        System.out.println("\n按优先级排序:");
        courses.forEach(c -> System.out.println("  " + c.toShortString() + " 优先级:" + c.getPriority()));

        courses.sort((a, b) -> a.compareByCredit(b));
        System.out.println("\n按学分排序:");
        courses.forEach(c -> System.out.println("  " + c.toShortString() + " 学分:" + c.getCredit()));

        System.out.println("\n非法输入测试");
        try {
            new Course("", "必修", 4.0, 64, "", "", teacher, classGroup, classroom, new TimeSlot("MON", 1), 5);
        } catch (Exception e) {
            System.out.println("正确捕获: " + e.getMessage());
        }

        Course original = courses.get(0);
        Course clone = original.cloneCourse();
        System.out.println("\n克隆测试:");
        System.out.println("原课程: " + original.toShortString());
        System.out.println("克隆课程: " + clone.toShortString());
        System.out.println("equals结果: " + original.equals(clone));

        System.out.println("\n性能测试：创建10000门课程");
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            new Course("性能测试课程" + i, teacher, classGroup, classroom, new TimeSlot("MON", 1), 5);
        }
        endTime = System.nanoTime();
        System.out.println("创建10000门课程用时: " + (endTime - startTime)/1_000_000 + " ms");

        System.out.println("\nCourse 测试完成");
        System.out.println("=".repeat(80));
    }
}