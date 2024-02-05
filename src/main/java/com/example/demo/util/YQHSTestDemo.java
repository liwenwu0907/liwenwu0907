package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * 油气回收接口demo
 * @author li wenwu
 * @date 2021/4/28 10:00
 */
public class YQHSTestDemo {

    public static void main(String[] args) {
//        String url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
//        String nameSpace = "http://WebXml.com.cn/";
//        Map map = new HashMap();
//        map.put("theCityName","南京");
//        String str = callWebservice(url,nameSpace,"getWeatherbyCityName",map,String[].class);
//        System.out.println(str);
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        System.out.println(assemblingConfigWrite(list));
    }

    /**
     * 公共的头报文
     * @author li wenwu
     * @date 2021/4/28
     * @param type 业务报文类型（2位），参考附录A3 businessContent 业务数据（如果SEC为1则数据需转化为base64编码）
     * @return
     */
    public static String getHeaderMessage(String type,String businessContent){
        //通信协议版本
        String version = "";
        //数据序号（6位），自动记录当前最新序号（不同类别的数据分别排序）
        String dataId = "";
        //区域代码表示（6位）+加油站标识（4位）
        String userId = "";
        //在线监控系统当前时间（YYYYMMDDhhmmss）
        String time = DateFormatUtils.format(new Date(),"yyyyMMddHHmmss");
        //加密标识（1表示业务报文数据为密文传输，0表示明文）
        String sec = "1";
        //HMAC校验码（预留）
        String hmac = "";
        String head = "<VERSION>" + version + "</VERSION>" +
                "<DATAID>" + dataId + "</DATAID>" +
                "<USERID>" + userId + "</USERID>" +
                "<TIME>" + time + "</TIME>" +
                "<TYPE>" + type + "</TYPE>" +
                "<SEC>" + sec + "</SEC>" +
                "<BUSINESSCONTENT>" + businessContent + "</BUSINESSCONTENT>" +
                "<HMAC>" + hmac + "</HMAC>";
        return head;
    }

    /**
     * 配置数据写入
     * @author li wenwu
     * @date 2021/4/28
     * @param id 记录ID jyqs 加油枪数量 pvz PV阀正向压力值 pvf PV阀负向压力值 hclk 后处理装置开启压力值
     *           hclt 后处理装置停止压力值 yzqh 安装液阻传感器加油机编号
     * @return
     */
    public static StringBuilder configWrite(String id, Integer jyqs, BigDecimal pvz,BigDecimal pvf,BigDecimal hclk,BigDecimal hclt,
                                   BigDecimal yzqh){
        //组织业务报文
        StringBuilder rowStringBuilder = new StringBuilder("<ROW>");
        rowStringBuilder.append("<ID>").append(id).append("</ID>");
        rowStringBuilder.append("<DATE>").append(DateFormatUtils.format(new Date(),"YYYYMMDDhhmmss")).append("</DATE>");
        rowStringBuilder.append("<JYQS>").append(jyqs).append("</JYQS>");
        rowStringBuilder.append("<PVZ>").append(pvz).append("</PVZ>");
        rowStringBuilder.append("<PVF>").append(pvf).append("</PVF>");
        rowStringBuilder.append("<HCLK>").append(hclk).append("</HCLK>");
        rowStringBuilder.append("<HCLT>").append(hclt).append("</HCLT>");
        rowStringBuilder.append("<YZQH>").append(yzqh).append("</YZQH>");
        rowStringBuilder.append("</ROW>");
        return rowStringBuilder;
    }

    /**
     * 组装配置数据并base64加密
     * @author li wenwu
     * @date 2021/4/28
     * @param
     * @return
     */
    public static <T> String assemblingConfigWrite(List<T> list){
        String responseStr = "";
        if(CollectionUtils.isNotEmpty(list)){
            StringBuilder rows = new StringBuilder("<ROWS>");
            for (T t:list){
                StringBuilder row = configWrite(null,null,null,null,null,null,null);
                rows.append(row);
            }
            rows.append("</ROWS>");
            //将rows转base64
            String base64 = Base64.getEncoder().encodeToString(rows.toString().getBytes());
            //将base64放入安全报文
            responseStr = getHeaderMessage("01",base64);
        }
        return responseStr;
    }

    /**
     * 请求Webservice接口
     * @author li wenwu
     * @date 2021/4/28
     * @param
     * @return
     */
    public static String callWebservice(String url, String namespace, String method, Map<String, String> ParamMap,Class clazz){
        String responseXml = "";
        if (StringUtils.isNoneBlank(url, method)) {
            try {
                URL serviceUrl = new URL(url);
                Service service = new Service();
                Call call = (org.apache.axis.client.Call) service.createCall();
                call.setTargetEndpointAddress(serviceUrl);
                call.setOperationName(new QName(namespace, method));
                call.setUseSOAPAction(true);
                call.setSOAPActionURI(namespace + method);
                call.setReturnClass(clazz);
                call.setProperty(org.apache.axis.client.Call.CONNECTION_TIMEOUT_PROPERTY, 100000);
                call.setTimeout(100000);
                for (String name : ParamMap.keySet()) {
                    call.addParameter(new QName(namespace,name),XMLType.XSD_STRING,ParameterMode.IN);
                }
                String[] paramValArray = new String[ParamMap.size()];
                int i = 0;
                for (String name : ParamMap.values()) {
                    paramValArray[i] = name;
                    i++;
                }
                Object object =  call.invoke(paramValArray);
                responseXml = JSON.toJSONString(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseXml;
    }

}
