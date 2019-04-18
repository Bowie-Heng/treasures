package com.bowie.notes.framework.entity;

public class Order {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_0.id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_0.order_id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_0.user_id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_0.userName
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_0.passWord
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_0.user_sex
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    private String userSex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_0.nick_name
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    private String nickName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_0.id
     *
     * @return the value of t_order_0.id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_0.id
     *
     * @param id the value for t_order_0.id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_0.order_id
     *
     * @return the value of t_order_0.order_id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_0.order_id
     *
     * @param orderId the value for t_order_0.order_id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_0.user_id
     *
     * @return the value of t_order_0.user_id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_0.user_id
     *
     * @param userId the value for t_order_0.user_id
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_0.userName
     *
     * @return the value of t_order_0.userName
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_0.userName
     *
     * @param username the value for t_order_0.userName
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_0.passWord
     *
     * @return the value of t_order_0.passWord
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_0.passWord
     *
     * @param password the value for t_order_0.passWord
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_0.user_sex
     *
     * @return the value of t_order_0.user_sex
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public String getUserSex() {
        return userSex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_0.user_sex
     *
     * @param userSex the value for t_order_0.user_sex
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_0.nick_name
     *
     * @return the value of t_order_0.nick_name
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_0.nick_name
     *
     * @param nickName the value for t_order_0.nick_name
     *
     * @mbggenerated Wed Apr 17 16:04:50 CST 2019
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
}