package com.example.demo.service;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

public interface UserPermissionInfo extends Serializable {

    /**
     * 用户编号
     *
     * @return
     */
    Long getUserId();

    /**
     * 租户号
     *
     * @return
     */
    String getTenantCode();

    /**
     * app
     *
     * @return
     */
    default String getApp() {
        return "";
    }

    /**
     * 权限编码
     *
     * @return
     */
    List<String> getPermissions();

    /**
     * 需要校验权限的所有url
     * @return
     */
    List<String> getAllPermissionUrls();

    /**
     * 当前用户具有权限的url
     * @return
     */
    List<String> getCurPermissionUrls();

    public static void main(String[] args) {
        UserPermissionInfo userPermissionInfo = new UserPermissionInfo() {
            @Override
            public Long getUserId() {
                return 1L;
            }

            @Override
            public String getTenantCode() {
                return "22222";
            }

            @Override
            public List<String> getPermissions() {
                return null;
            }

            @Override
            public List<String> getAllPermissionUrls() {
                return null;
            }

            @Override
            public List<String> getCurPermissionUrls() {
                return null;
            }
        };
        System.out.println(JSON.toJSONString(userPermissionInfo));
    }
}
