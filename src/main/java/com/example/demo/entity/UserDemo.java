package com.example.demo.entity;


import java.io.Serializable;
import java.util.Date;

public class UserDemo implements Serializable {

    private static final long serialVersionUID = 7073939192597620043L;
    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 用户GUID
     */
    private String userGuid;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 用户账号
     */
    private String userName;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 用户密码
     */
    private String userPwd;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 用户证件编码
     */
    private String userZjid;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 用户姓名
     */
    private String realName;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 1:管理员 2:普通用户(数组，一个用户多个角色)
     */
    private Integer role;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 联系电话
     */
    private String lxDh;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 是否可用1：可用 0：不可用
     */
    private Integer isValid;

    /**
     * @author <a href="mailto:zhangyu2@gtmap.cn">zhangyu2</a>
     * @version 1.0,
     * @description 创建日期
     */
    private Date createDate;

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserZjid() {
        return userZjid;
    }

    public void setUserZjid(String userZjid) {
        this.userZjid = userZjid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getLxDh() {
        return lxDh;
    }

    public void setLxDh(String lxDh) {
        this.lxDh = lxDh;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
