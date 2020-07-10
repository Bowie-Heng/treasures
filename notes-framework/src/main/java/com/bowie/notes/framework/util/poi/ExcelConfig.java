package com.bowie.notes.framework.util.poi;

import java.util.Map;

/**
 * excel 针对业务的解析 校验 配置 与业务一对一 .
 *
 * @author Dax
 * @see CellRule
 * @since 16 :15  2018/5/17
 */
public class ExcelConfig {
    /**
     * 实际行excel索引 cxcel行号-1
     */
    private int rowNum;
    /**
     * 字段名称  按照顺序
     */
    private String[] columnNames;
    /**
     * 字段校验规则   key 为字段columnNames 对应 字段名称
     */
    private Map<String, CellRule> rules;

    /**
     * Gets rowNum.
     *
     * @return the rowNum
     */
    public int getRowNum() {
        return rowNum;
    }

    /**
     * Sets rowNum.
     *
     * @param rowNum the rowNum
     */
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    /**
     * Get column names string [ ].
     *
     * @return the string [ ]
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * Sets column names.
     *
     * @param columnNames the column names
     */
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * Gets rules.
     *
     * @return the rules
     */
    public Map<String, CellRule> getRules() {
        return rules;
    }

    /**
     * Sets rules.
     *
     * @param rules the rules
     */
    public void setRules(Map<String, CellRule> rules) {
        this.rules = rules;
    }

}
