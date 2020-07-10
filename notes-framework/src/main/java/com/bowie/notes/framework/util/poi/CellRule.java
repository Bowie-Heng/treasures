package com.bowie.notes.framework.util.poi;

/**
 * excel 解析cell  字段规则
 *
 * @author Dax
 * @since 16:42  2018/5/17
 */
public class CellRule {
    /**
     * 是否是数字
     */
    private boolean isNumber;
    /**
     *
     */
    private int digits;
    /**
     * 是否是时间
     */
    private boolean isTime;
    /**
     *
     */
    private String pattern;
    /**
     * 是否是字符串
     */
    private boolean isStr;
    /**
     * 长度最大值
     */
    private int strLength;

    private CellRule() {
    }

    private CellRule(RuleBuilder ruleBuilder) {
        this.isNumber = ruleBuilder.isNumber;
        this.digits = ruleBuilder.digits;
        this.isTime = ruleBuilder.isTime;
        this.pattern = ruleBuilder.pattern;
        this.isStr = ruleBuilder.isStr;
        this.strLength = ruleBuilder.strLength;
    }

    public static class RuleBuilder {
        /**
         * 是否是数字
         */
        private boolean isNumber;
        /**
         * 小数位数
         */
        private int digits;
        /**
         * 是否是时间
         */
        private boolean isTime;
        /**
         * 校验格式
         */
        private String pattern;
        /**
         * 是否是字符串
         */
        private boolean isStr;
        /**
         * 长度最大值
         */
        private int strLength;

        public RuleBuilder isNumber(int digits) {
            this.isNumber = true;
            this.digits = digits;
            return this;
        }


        public RuleBuilder isTime(String pattern) {
            this.isTime = true;
            this.pattern = pattern;
            return this;
        }


        public RuleBuilder isStr(int strLength) {
            this.isStr = true;
            this.strLength = strLength;
            return this;
        }


        public CellRule builder() {
            return new CellRule(this);
        }
    }

    public boolean isNumber() {
        return isNumber;
    }

    public int getDigits() {
        return digits;
    }

    public boolean isTime() {
        return isTime;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean isStr() {
        return isStr;
    }

    public int getStrLength() {
        return strLength;
    }

    @Override
    public String toString() {
        return "{" +
                "isNumber:" + isNumber +
                ", digits:" + digits +
                ", isTime:" + isTime +
                ", pattern:" + pattern +
                ", isStr:" + isStr +
                ", strLength:" + strLength +
                '}';
    }
}
