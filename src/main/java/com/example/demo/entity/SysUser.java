package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author rhy
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户编号
     */
    @TableId("user_id")
    private Long userId;

    /**
     * 用户微信openid
     */
    @TableField("user_openid")
    private String userOpenid;

    /**
     * 手机regisID
     */
    @TableField("user_phone_id")
    private String userPhoneId;

    /**
     * 用户账号
     */
    @TableField("user_account")
    private String userAccount;

    /**
     * 用户密码
     */
    @TableField("user_password")
    private String userPassword;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;
    /**
     * 用户邮箱
     */
    @TableField("user_mail")
    private String userMail;

    /**
     * 颜色
     */
    @TableField("user_color_show")
    private String userColorShow;

    /**
     * 图标
     */
    @TableField("user_icon_show")
    private String userIconShow;

    /**
     * 字号
     */
    @TableField("user_font_show")
    private Integer userFontShow;

    /**
     * 是否默认全屏 1-全屏 0-非全屏
     */
    @TableField("user_full_screen")
    private Integer userFullScreen;

    /**
     * 菜单默认收起  1-收起  0-不收起
     */
    @TableField("user_menu_show")
    private Integer userMenuShow;

    /**
     * 账号是否锁定 0：未锁定 1：已锁定
     */
    @TableField("user_is_locked")
    private Integer userIsLocked;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人编号
     */
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建人名称
     */
    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
    private String createUserName;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 修改人编号
     */
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 创建人名称
     */
    @TableField(value = "update_user_name", fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;

    /**
     * 0：未删除 1：已删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;


}
