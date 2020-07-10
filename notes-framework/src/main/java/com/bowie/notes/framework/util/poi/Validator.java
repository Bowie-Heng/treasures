package com.bowie.notes.framework.util.poi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * 字段校验器.
 *
 * @author Dax
 * @since 14 :25  2018/5/16
 */
public class Validator {
    private static final Logger log = LoggerFactory.getLogger(Validator.class);

    private Validator() {
    }

    /**
     * 字段检查.
     *
     * @param val  the val
     * @param rule the rule
     * @return the boolean
     */
    public static boolean checkVal(String val, CellRule rule) {
        if (rule != null) {
            if (rule.isStr()) {
                return checkLength(val, rule.getStrLength());
            }

            if (rule.isNumber()) {
                return isNumber(val, rule.getDigits());
            }
            if (rule.isTime()) {
                return isDateFormat(val, rule.getPattern());
            }
        }
        return true;
    }


    /**
     * 检验长度是否符合要求
     *
     * @param val    the val
     * @param length the length
     * @return 是否符合要求 boolean
     */
    private static boolean checkLength(String val, int length) {
        if (!StringUtils.isEmpty(val)) {
            if (val.length() > length) {
                log.debug("【批量导入】 字符串 "+val+" 长度超出范围 " + length);
                return false;
            } 

        }
        return true;
    }

    /**
     * 判断是否是数字字符串  以及小数位
     *
     * @param val    the val
     * @param digits the digits
     * @return 是否符合要求 boolean
     */
    private static boolean isNumber(String val, int digits) {
        int maxNumLength = 19;

        String format = "^[0-9]+(.[0-9]{0,%d})?$";
        String digitsRegex = "[.]";
        if (!StringUtils.isEmpty(val)) {

            String regex = String.format(format, digits);
            String integerPart = val.split(digitsRegex)[0];
            //不符合正则 而且整数位不能大于19
            if (integerPart.length() > maxNumLength) {
                log.debug("【批量导入】 数字过大");
                return false;
            }
            if (!Pattern.matches(regex, val)) {
                log.debug("【批量导入】 数字不合法");
                return false;
            }
        }

        return true;
    }

    /**
     * 判断是否是数字字符串  以及小数位
     *
     * @param val the val
     * @return 是否符合要求 boolean
     */
    private static boolean isNotNull(String val) {
        if (!StringUtils.isEmpty(val)) {
            log.debug("【批量导入】 非空字段为空");
            return false;
        }
        return true;
    }

    /**
     * Is date format boolean.
     *
     * @param val     the val
     * @param pattern 默认 yyyy-MM-dd
     * @return 是否合法 boolean
     */
    private static boolean isDateFormat(String val, String pattern) {
        pattern = pattern != null ? pattern : "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(val);
        } catch (ParseException e) {
            log.debug("【批量导入】 非法日期格式");
            return false;
        }

        return true;
    }
}
