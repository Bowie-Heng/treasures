package com.bowie.notes.framework.entity.enumeration;

/**
 * @author system
 */
public enum ExcelSheetNameEnum {
    /**
     * 获取第一个工作薄
     */
    SHEET_ONE("sheet1"),
    /**
     * 垃圾桶台帐
     */
    Bin_SHEET("binInfo"),
    /**
     * 公共机构台账
     */
    GOV_SHEET("公共机构台账"),
    /**
     * 公共场所台账
     */
    PLACE_SHEET("公共场所台账"),
    /**
     * 企业台账
     */
    ENTERPRISE_SHEET("企业台账"),
    /**
     * 运营商台账
     */
    RUN_COMPANY_SHEET("运营商台账"),
    /**
     * 会员积分台账
     */
    MEMBER_SCORE_SHEET("会员积分台账"),
    /**
     * 车辆台账
     */
    TRUCK_SHEET("车辆台账"),
    /**
     * 分拣中心台账
     */
    SORT_STATION_SHEET("分拣中心台账"),
    /**
     * 中转站台账
     */
    TRANSPORT_STATION_SHEET("中转站台账"),
    /**
     * 公厕台账
     */
    TOILET_SHEET("公厕台账"),
    /**
     * 督导员台账
     */
    SPV_SHEET("督导员台账"),
    /**
     * 车辆清收记录
     */
    TRUCK_TRANSPORT_SHEET("车辆清收记录"),
    /**
     * 分拣中心运入记录
     */
    SORT_STATION_ENTRANCE_SHEET("分拣中心运入记录"),
    /**
     * 分拣中心运出记录
     */
    SORT_STATION_EXIT_SHEET("分拣中心运出记录"),
    /**
     * 达标考核记录
     */
    SPV_ASS_SHEET("达标考核记录"),
    /**
     * 小区台账
     */
    COMMUNITY_SHEET("小区台账");
    ExcelSheetNameEnum(String sheetName){
        this.sheetName = sheetName;
    }

    private String sheetName;


    public String sheetName() {
        return this.sheetName;
    }

}
