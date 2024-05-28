package com.example.demo.util;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 
 * @Title: IpUtils.java
 * @Description: <br>获取客户端IP工具
 *               <br>
 * @Company: Chinasofti
 * @Created on 2015-7-7 上午10:50:06
 * @author 钱崇勇<qianchongyong@chinasofti.com>
 * @version $Revision: 1.0 $
 * @since 1.0
 */
@Slf4j
public class IpUtils {

	/**
	 * 通过HttpServletRequest返回IP地址
	 * @param request HttpServletRequest
	 * @return ip String
	 * @throws Exception
	 */
	public static String getIpAddr(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
			log.info("Proxy-Client-IP地址:{}", ip);
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
			log.info("WL-Proxy-Client-IP地址:{}", ip);
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
			log.info("HTTP_CLIENT_IP地址:{}", ip);
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			log.info("HTTP_X_FORWARDED_FOR地址:{}", ip);
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
					ip= inet.getHostAddress();
				} catch (UnknownHostException e) {
					log.error("未识别到ip地址:{}", e.getMessage());
				}
			}
	    }
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		//"***.***.***.***".length() = 15
		
		log.info("切割前的IP地址:{}", ip);
		if(ip!=null && ip.length()>15){
			if(ip.indexOf(",")>0){
				ip = ip.substring(0,ip.indexOf(","));
			}
		}
		log.info("切割后的IP地址:{}", ip);
		return ip;
	}
}
