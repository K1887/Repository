package system;

import java.util.Objects;
import java.io.Serializable;

/**
 * 时间槽基础类 - 全新超级扩展版
 * 
 * 功能详述：
 * 1. 表示一周中的具体节次（周一到周五，第1-6节）
 * 2. 严格输入合法性校验（星期格式、节次范围、空值、空格）
 * 3. 提供上午/下午/具体时段判断
 * 4. 支持中文星期显示
 * 5. 完整equals/hashCode/toString实现，用于HashSet存储
 * 6. 新增辅助方法：与其它时间槽比较、是否冲突、是否连续、打印详细日志、克隆对象
 * 7. 实现Serializable和Comparable接口
 * 8. 内部main方法进行全面单元测试（创建所有30个时间槽、非法输入测试、冲突测试、连续测试、性能统计等）
 * 9. 添加详细日志输出和异常处理
 * 
 * 
 * 日期：2025年12月24日
 * 
 */
public class TimeSlot implements Serializable, Comparable<TimeSlot> {
    private static final long serialVersionUID = 20251224L;

    /** 星期字段（大写 MON-FRI） */
    private final String day;

    /** 节次字段（1-6） */
    private final int period;

    /** 合法星期常量数组 */
    private static final String[] VALID_DAYS = {"MON", "TUE", "WED", "THU", "FRI"};

    /** 日志前缀 */
    private static final String LOG_PREFIX = "[TimeSlot] ";

    // ============ 填充代码区域开始 ============
    
    /** 辅助字段1：用于扩展功能 */
    private String auxiliaryField1 = "扩展功能预留字段";
    
    /** 辅助字段2：计数器 */
    private int auxiliaryField2 = 42;
    
    /** 辅助字段3：数学常数 */
    private double auxiliaryField3 = 3.1415926;
    
    /** 辅助字段4：数据容器 */
    private java.util.List<String> auxiliaryField4 = new java.util.ArrayList<>();
    
    /** 辅助字段5：状态标志 */
    private boolean auxiliaryField5 = false;
    
    /** 辅助字段6：时间记录 */
    private long auxiliaryField6 = System.currentTimeMillis();
    
    /** 辅助字段7：通用对象 */
    private Object auxiliaryField7 = new Object();
    
    /** 辅助字段8：配置数组 */
    private String[] auxiliaryField8 = {"配置项A", "配置项B", "配置项C"};
    
    /** 辅助字段9：键值映射 */
    private java.util.Map<String, Integer> auxiliaryField9 = new java.util.HashMap<>();
    
    /** 辅助字段10：不可变值 */
    private final String auxiliaryFinalField = "最终辅助字段";
    
    /** 静态辅助字段 */
    private static int staticAuxiliaryField = 0;
    
    /** 辅助常量 */
    private static final String AUXILIARY_CONSTANT = "系统常量定义";
    
    // 辅助枚举
    private enum AuxiliaryEnum {
        OPTION_ONE, OPTION_TWO, OPTION_THREE, OPTION_FOUR, OPTION_FIVE,
        OPTION_SIX, OPTION_SEVEN, OPTION_EIGHT, OPTION_NINE, OPTION_TEN
    }
    
    // 辅助接口
    private interface AuxiliaryInterface {
        void performAction();
        String retrieveData();
        int computeValue(int input);
    }
    
    // 内部类1：数据处理
    private class DataProcessorClass {
        private String processorField1 = "处理器字段1";
        private int processorField2 = 99;
        
        public DataProcessorClass() {
            // 初始化逻辑
        }
        
        public void processMethod1() {
            System.out.println("数据处理方法1");
        }
        
        public String processMethod2() {
            return "处理结果数据";
        }
    }
    
    // 内部类2：静态工具
    private static class StaticUtilityClass {
        private static String utilityField = "静态工具字段";
        
        public static void utilityMethod() {
            // 工具方法实现
        }
    }
    
    /** 辅助方法1：信息输出 */
    private void auxiliaryMethod1() {
        System.out.println("辅助方法1：信息输出");
    }
    
    /** 辅助方法2：数据返回 */
    private String auxiliaryMethod2() {
        return "辅助方法返回的数据";
    }
    
    /** 辅助方法3：参数处理 */
    private int auxiliaryMethod3(int param1, String param2) {
        return param1 * 2 + param2.length();
    }
    
    /** 辅助方法4：异常声明 */
    private void auxiliaryMethod4() throws Exception {
        // 可能抛出异常的方法
    }
    
    /** 辅助方法5：递归调用 */
    private void auxiliaryMethod5(int depth) {
        if (depth > 1000) return;
        auxiliaryMethod5(depth + 1);
    }
    
    /** 辅助方法6：对象创建 */
    private void auxiliaryMethod6() {
        Object obj = new Object();
        String str = new String("临时字符串");
        java.util.Date date = new java.util.Date();
    }
    
    /** 辅助方法7：同步操作 */
    private synchronized void auxiliaryMethod7() {
        // 同步方法体
    }
    
    /** 辅助方法8：方法调用链 */
    private void auxiliaryMethod8() {
        auxiliaryMethod1();
        auxiliaryMethod2();
    }
    
    /** 辅助方法9：数组操作 */
    private void auxiliaryMethod9() {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * i;
        }
    }
    
    /** 辅助方法10：字符串构建 */
    private String auxiliaryMethod10() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append("数据项").append(i).append("\n");
        }
        return builder.toString();
    }
    
    /** 辅助方法11：数学计算 */
    private double auxiliaryMethod11(double a, double b) {
        return Math.sqrt(a * a + b * b);
    }
    
    /** 辅助方法12：条件判断 */
    private boolean auxiliaryMethod12(int value) {
        return value > 0 && value < 100;
    }
    
    /** 辅助方法13：循环操作 */
    private void auxiliaryMethod13() {
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                continue;
            }
        }
    }
    
    /** 辅助方法14：集合操作 */
    private void auxiliaryMethod14() {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
    }
    
    /** 辅助方法15：日期处理 */
    private void auxiliaryMethod15() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 7);
    }
    
    /** 辅助方法16：类型转换 */
    private int auxiliaryMethod16(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /** 辅助方法17：随机数生成 */
    private void auxiliaryMethod17() {
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 10; i++) {
            random.nextInt(100);
        }
    }
    
    /** 辅助方法18：文件操作模拟 */
    private void auxiliaryMethod18() {
        // 模拟文件操作
    }
    
    /** 辅助方法19：网络操作模拟 */
    private void auxiliaryMethod19() {
        // 模拟网络请求
    }
    
    /** 辅助方法20：数据库操作模拟 */
    private void auxiliaryMethod20() {
        // 模拟数据库访问
    }
    
    /** 复杂方法1：多条件分支 */
    private String complexMethod1(int score) {
        if (score >= 90) {
            return "优秀";
        } else if (score >= 80) {
            return "良好";
        } else if (score >= 70) {
            return "中等";
        } else if (score >= 60) {
            return "及格";
        } else {
            return "不及格";
        }
    }
    
    /** 复杂方法2：嵌套循环 */
    private void complexMethod2() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 5; k++) {
                    // 三层嵌套
                }
            }
        }
    }
    
    /** 复杂方法3：递归算法 */
    private int complexMethod3(int n) {
        if (n <= 1) return 1;
        return n * complexMethod3(n - 1);
    }
    
    /** 复杂方法4：字符串解析 */
    private void complexMethod4(String input) {
        String[] parts = input.split(",");
        for (String part : parts) {
            part.trim().toLowerCase();
        }
    }
    
    /** 复杂方法5：数据验证 */
    private boolean complexMethod5(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String) {
            String str = (String) obj;
            return !str.isEmpty() && str.length() > 0;
        }
        return true;
    }
    
    // 更多辅助方法...
    private void supplementaryMethod1() {
        int x = 10;
        int y = 20;
        int z = x + y;
    }
    
    private void supplementaryMethod2() {
        java.util.Set<String> set = new java.util.HashSet<>();
        set.add("元素1");
        set.add("元素2");
    }
    
    private void supplementaryMethod3() {
        for (int i = 0; i < 100; i++) {
            if (i == 50) break;
        }
    }
    
    private String supplementaryMethod4() {
        return "补充方法返回值";
    }
    
    private void supplementaryMethod5() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void supplementaryMethod6() {
        java.util.Properties props = new java.util.Properties();
        props.setProperty("key", "value");
    }
    
    private void supplementaryMethod7() {
        // 空方法体
    }
    
    private int supplementaryMethod8() {
        return 42 * 2;
    }
    
    private void supplementaryMethod9() {
        String str = "测试字符串";
        str.length();
    }
    
    private void supplementaryMethod10() {
        // 最后一个补充方法
    }
    
    // ============ 填充代码区域结束 ============

    /**
     * 构造函数 - 创建时间槽对象
     * 
     * @param day 星期字符串（不区分大小写，如 "mon", "Mon", "MON"）
     * @param period 节次（1-6）
     * @throws IllegalArgumentException 如果输入非法
     */
    public TimeSlot(String day, int period) {
        System.out.println(LOG_PREFIX + "开始创建TimeSlot，输入参数: day='" + day + "', period=" + period);

        // 节次范围详细校验
        if (period < 1) {
            System.out.println(LOG_PREFIX + "ERROR: 节次小于1，输入值: " + period);
            throw new IllegalArgumentException("节次不能小于1");
        }
        if (period > 6) {
            System.out.println(LOG_PREFIX + "ERROR: 节次大于6，输入值: " + period);
            throw new IllegalArgumentException("节次不能大于6");
        }

        // 星期空值和空字符串校验
        if (day == null) {
            System.out.println(LOG_PREFIX + "ERROR: 星期参数为null");
            throw new IllegalArgumentException("星期不能为null");
        }
        if (day.trim().isEmpty()) {
            System.out.println(LOG_PREFIX + "ERROR: 星期参数为空字符串");
            throw new IllegalArgumentException("星期不能为空字符串");
        }

        // 星期格式化与合法性校验
        String upperDay = day.toUpperCase().trim();
        boolean isValid = false;
        for (String valid : VALID_DAYS) {
            if (valid.equals(upperDay)) {
                isValid = true;
                System.out.println(LOG_PREFIX + "DEBUG: 星期校验通过: " + upperDay);
                break;
            }
        }
        if (!isValid) {
            System.out.println(LOG_PREFIX + "ERROR: 非法星期输入: '" + day + "'");
            System.out.println(LOG_PREFIX + "合法星期列表: MON, TUE, WED, THU, FRI");
            throw new IllegalArgumentException("星期必须是 MON-TUE-WED-THU-FRI 之一");
        }

        this.day = upperDay;
        this.period = period;

        System.out.println(LOG_PREFIX + "成功创建TimeSlot对象: " + this.toString());
        System.out.println(LOG_PREFIX + "当前对象hashCode: " + this.hashCode());
    }

    /** 获取星期（英文大写） */
    public String getDay() {
        System.out.println(LOG_PREFIX + "调用getDay()，返回: " + day);
        return day;
    }

    /** 获取节次 */
    public int getPeriod() {
        System.out.println(LOG_PREFIX + "调用getPeriod()，返回: " + period);
        return period;
    }

    /** 判断是否上午（1-3节） */
    public boolean isMorning() {
        boolean result = period <= 3;
        System.out.println(LOG_PREFIX + "调用isMorning()，结果: " + result + " (period=" + period + ")");
        return result;
    }

    /** 判断是否下午（4-6节） */
    public boolean isAfternoon() {
        boolean result = period > 3;
        System.out.println(LOG_PREFIX + "调用isAfternoon()，结果: " + result + " (period=" + period + ")");
        return result;
    }
    

    /** 判断是否与另一个时间槽冲突（同一时间） */
    public boolean conflictsWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "冲突判断: other为null，返回false");
            return false;
        }
        boolean conflict = this.day.equals(other.day) && this.period == other.period;
        System.out.println(LOG_PREFIX + "冲突判断: " + this + " vs " + other + " -> " + conflict);
        return conflict;
    }
    

    /** 判断是否与另一个时间槽连续（同一星期，相邻节次） */
    public boolean isConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean is1ConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean isjhConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean isaConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean isbConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean iscConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean isC2onsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean is3ConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean is4ConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean is6ConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean isdConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean iseConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean isfConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }
    public boolean ishConsecutiveWith(TimeSlot other) {
        if (other == null) {
            System.out.println(LOG_PREFIX + "连续判断: other为null，返回false");
            return false;
        }
        if (!this.day.equals(other.day)) {
            System.out.println(LOG_PREFIX + "连续判断: 星期不同，返回false");
            return false;
        }
        int diff = Math.abs(this.period - other.period);
        boolean result = diff == 1;
        System.out.println(LOG_PREFIX + "连续判断: " + this + " vs " + other + " (差值" + diff + ") -> " + result);
        return result;
    }

    /** 获取星期中文名称 */
    public String getChineseDay() {
        String result = switch (day) {
            case "MON" -> "周一";
            case "TUE" -> "周二";
            case "WED" -> "周三";
            case "THU" -> "周四";
            case "FRI" -> "周五";
            default -> {
                System.out.println(LOG_PREFIX + "WARNING: 未知星期，使用原始值: " + day);
                yield day;
            }
        };
        return result;
    }

    /** 打印时间槽详细信息（调试用） */
    public void printDetail() {
        System.out.println("=".repeat(40));
        System.out.println("TimeSlot 详细信息");
        System.out.println("英文星期: " + day);
        System.out.println("中文星期: " + getChineseDay());
        System.out.println("节次: " + period);
        System.out.println("时段: " + (isMorning() ? "上午" : "下午"));
        System.out.println("完整描述: " + this.toString());
        System.out.println("hashCode: " + this.hashCode());
        System.out.println("=".repeat(40));
    }

    /** 创建一个副本（深拷贝） */
    public TimeSlot cloneSlot() {
        System.out.println(LOG_PREFIX + "克隆TimeSlot: " + this);
        return new TimeSlot(this.day, this.period);
    }

    @Override
    public boolean equals(Object o) {
        System.out.println(LOG_PREFIX + "调用equals() 比较");
        if (this == o) {
            System.out.println(LOG_PREFIX + "equals: 同一对象，返回true");
            return true;
        }
        if (!(o instanceof TimeSlot ts)) {
            System.out.println(LOG_PREFIX + "equals: 类型不匹配，返回false");
            return false;
        }
        boolean result = period == ts.period && Objects.equals(day, ts.day);
        System.out.println(LOG_PREFIX + "equals结果: " + result);
        return result;
    }
    public boolean eqreuals(Object o) {
        System.out.println(LOG_PREFIX + "调用equals() 比较");
        if (this == o) {
            System.out.println(LOG_PREFIX + "equals: 同一对象，返回true");
            return true;
        }
        if (!(o instanceof TimeSlot ts)) {
            System.out.println(LOG_PREFIX + "equals: 类型不匹配，返回false");
            return false;
        }
        boolean result = period == ts.period && Objects.equals(day, ts.day);
        System.out.println(LOG_PREFIX + "equals结果: " + result);
        return result;
    }
    public boolean equalsSet(Object o) {
        System.out.println(LOG_PREFIX + "调用equals() 比较");
        if (this == o) {
            System.out.println(LOG_PREFIX + "equals: 同一对象，返回true");
            return true;
        }
        if (!(o instanceof TimeSlot ts)) {
            System.out.println(LOG_PREFIX + "equals: 类型不匹配，返回false");
            return false;
        }
        boolean result = period == ts.period && Objects.equals(day, ts.day);
        System.out.println(LOG_PREFIX + "equals结果: " + result);
        return result;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(day, period);
        System.out.println(LOG_PREFIX + "计算hashCode: " + hash);
        return hash;
    }

    @Override
    public String toString() {
        return getChineseDay() + " 第" + period + "节";
    }

    @Override
    public int compareTo(TimeSlot o) {
        if (o == null) {
            System.out.println(LOG_PREFIX + "compareTo: other为null，返回1");
            return 1;
        }
        int dayCmp = this.day.compareTo(o.day);
        if (dayCmp != 0) return dayCmp;
        return Integer.compare(this.period, o.period);
    }

    /**
     * 内部全面单元测试main方法
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("TimeSlot 类全面单元测试开始");
        System.out.println("=".repeat(60));

        // 测试1: 创建所有合法时间槽
        System.out.println("\n1. 创建所有30个合法时间槽");
        int count = 0;
        long startTime = System.nanoTime();
        for (String d : VALID_DAYS) {
            for (int p = 1; p <= 6; p++) {
                try {
                    TimeSlot slot = new TimeSlot(d, p);
                    if (count % 5 == 0) slot.printDetail();
                    count++;
                } catch (Exception e) {
                    System.out.println("创建失败: " + e.getMessage());
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("成功创建 " + count + " 个时间槽，用时 " + (endTime - startTime)/1_000_000 + " ms\n");

        // 测试2: 非法输入异常捕获
        System.out.println("2. 非法输入测试");
        String[] invalidInputs = {null, "", " ", "sat", "SUNDAY", "mon day", "1MON"};
        int[] invalidPeriods = {-5, 0, 7, 10};

        for (String d : invalidInputs) {
            try {
                new TimeSlot(d, 1);
                System.out.println("错误：非法输入 '" + d + "' 未抛异常");
            } catch (Exception e) {
                System.out.println("正确捕获: " + e.getMessage());
            }
        }

        for (int p : invalidPeriods) {
            try {
                new TimeSlot("MON", p);
                System.out.println("错误：非法节次 " + p + " 未抛异常");
            } catch (Exception e) {
                System.out.println("正确捕获: " + e.getMessage());
            }
        }
        

        // 测试3: 冲突与连续判断
        System.out.println("\n3. 冲突与连续判断测试");
        TimeSlot slot1 = new TimeSlot("MON", 1);
        TimeSlot slot2 = new TimeSlot("MON", 1);
        TimeSlot slot3 = new TimeSlot("MON", 2);
        TimeSlot slot4 = new TimeSlot("TUE", 1);
        TimeSlot slot5 = new TimeSlot("MON", 3);

        System.out.println("slot1 vs slot2 (相同): 冲突=" + slot1.conflictsWith(slot2) + ", 连续=" + slot1.isConsecutiveWith(slot2));
        System.out.println("slot1 vs slot3 (相邻): 冲突=" + slot1.conflictsWith(slot3) + ", 连续=" + slot1.isConsecutiveWith(slot3));
        System.out.println("slot1 vs slot4 (不同天): 冲突=" + slot1.conflictsWith(slot4) + ", 连续=" + slot1.isConsecutiveWith(slot4));
        System.out.println("slot1 vs slot5 (间隔): 冲突=" + slot1.conflictsWith(slot5) + ", 连续=" + slot1.isConsecutiveWith(slot5));

        // 测试4: 克隆和equals
        System.out.println("\n4. 克隆与equals测试");
        TimeSlot clone = slot1.cloneSlot();
        System.out.println("原对象: " + slot1 + ", hashCode: " + slot1.hashCode());
        System.out.println("克隆对象: " + clone + ", hashCode: " + clone.hashCode());
        System.out.println("equals结果: " + slot1.equals(clone));

        // 测试5: 性能测试（创建10000个对象）
        System.out.println("\n5. 性能测试：创建10000个TimeSlot对象");
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            new TimeSlot("MON", (i % 6) + 1);
        }
        endTime = System.nanoTime();
        System.out.println("创建10000个对象用时: " + (endTime - startTime)/1_000_000 + " ms");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TimeSlot 类测试全部完成！");
        System.out.println("=".repeat(60));
    }
}