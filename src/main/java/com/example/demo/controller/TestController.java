package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.TestEntity;
import com.example.demo.service.ExcelBusinessService;
import com.example.demo.util.Md5Util;
import com.example.demo.util.ratelimiter.RedisRLimiter;
import com.example.demo.util.redis.RedisUtils;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@EnableScheduling
@RestController
@RequestMapping(value = "/api")
@Validated
public class TestController {

    /**
     * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
     */
    static {
        // 平台门户/nginx的IP和端口（必须使用https协议，https端口默认为443）
        ArtemisConfig.host = "90.0.0.4:444";
        // 秘钥appkey
        ArtemisConfig.appKey = "25999598";
        // 秘钥appSecret
        ArtemisConfig.appSecret = "Kda7wO4slJ0xabrob229";
    }

    /**
     * STEP2：设置OpenAPI接口的上下文
     */
    private static final String ARTEMIS_PATH = "/artemis";


    @GetMapping("/test")
    public void test() {
        //分页获取监控点资源
        CamerasRequest camerasRequest = new CamerasRequest();
        camerasRequest.setPageNo(1);
        camerasRequest.setPageSize(10);
        String camerasDataApi = ARTEMIS_PATH + "/api/resource/v1/cameras";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", camerasDataApi);
            }
        };
        String body = JSON.toJSONString(camerasRequest);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, "application/json");
        System.out.println("分页获取监控点资源: " + result);

        PreviewURLsRequest previewURLsRequest = new PreviewURLsRequest();
        previewURLsRequest.setExpand("transcode=0");
        previewURLsRequest.setStreamType(0);
        previewURLsRequest.setCameraIndexCode("");
        previewURLsRequest.setTransmode(0);
        previewURLsRequest.setProtocol("rtsp");
        String previewURLsDataApi = ARTEMIS_PATH + "/api/video/v1/cameras/previewURLs";
        Map<String, String> path2 = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsDataApi);
            }
        };
        String body2 = JSON.toJSONString(previewURLsRequest);
        String result2 = ArtemisHttpUtil.doPostStringArtemis(path2, body2, null, null, "application/json");
        System.out.println("视频链接：" + result2);
    }

    @Autowired
    ExcelBusinessService excelBusinessService;
    @Autowired
    RedisUtils redisUtils;
    //    @Autowired
//    Producer producer;
//    @Autowired(required = false)
//    private FanoutProducer fanoutProducer;
//    @Autowired
//    MqttConsumer mqttConsumer;
//    @Autowired
//    MasterSlaveService masterSlaveService;
//    @Autowired
//    KafkaProducer kafkaProducer;
//没有配置时的默认公钥
    private static final String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg3CXSqLU/jo4AFyjb3J5jzjS7OerCWT2\n" +
            "DpYJf4woDifyGrJnPomVa2R8ggPrG6bb0fDe/++7plqyB4hdWLjcpINJQLPJYHEHrkm06SZ6MknA\n" +
            "9/f5h9ks7VAlemUAOyz/n7WUR3mjm5VrXE82Hv51ez6nsGrpE4ZaSU9oMD9KrKTbaVq8a+dX0ZDG\n" +
            "qDxra9saGepXXshFMgO363OOFh+qfK+z5hkIqAfQVP/qKIxA6HT1/bJTqn3EHOYEnibb7XcJXdim\n" +
            "ipQ9bh2d09sIXqL97GHwyU9wKYsodqDGIS4vKt2oAfGkpLEiwXaAywOtIYJu+4K2UofvYyhjxxR7\n" +
            "R/vSgQIDAQAB";
    @Value("${jfh.internal.pubKey:" + pubKey + "}")
    protected String internalPubKey;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 按日顺序生成编号（字母+年月日+四位编号）
     *
     * @author liwenwu
     * @date 2022/11/28
     **/
    public String generateBusinessNumber() {
        String number = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());
        Long serialNum = redisTemplate.opsForValue().increment("P" + date, 1L);
        redisTemplate.expire("P" + date, 1, TimeUnit.DAYS);
        number = "P" + date + String.format("%04d", serialNum);
        return number;
    }


    public synchronized void methodA(String a) {
        System.out.println("a");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodA() {
        System.out.println("a");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/test/validate")
    @ResponseBody
    @PostMapping
    public void testValidate(
            @RequestBody(required = false) @Validated TestEntity testEntity) {
        System.out.println(internalPubKey);
        System.out.println(JSON.toJSONString(testEntity));
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public void test(@RequestParam(value = "pageSize", required = false) Integer pageSize, Map map) throws Exception {
        System.out.println(map);
        System.out.println(pageSize);
//        Map<String,Object> result = sign();
//        String timestamp = result.get("timestamp").toString();
//        String sign = result.get("sign").toString();
//        StringBuilder url = new StringBuilder("https://oapi.dingtalk.com/robot/send?access_token=f7f19ecd5830707a611fde4f3302d65f8341a2af0a97fc522864da43417ef4a8");
//        url.append("&timestamp=").append(timestamp).append("&sign=").append(sign);
//        DingTalkClient client = new DefaultDingTalkClient(url.toString());
//        OapiRobotSendRequest request = new OapiRobotSendRequest();
//        request.setMsgtype("text");
//        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//        text.setContent("测试文本消息");
//        request.setText(text);
//        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
////        at.setAtMobiles(Arrays.asList("132xxxxxxxx"));
//// isAtAll类型如果不为Boolean，请升级至最新SDK
//        at.setIsAtAll(true);
////        at.setAtUserIds(Arrays.asList("109929","32099"));
//        request.setAt(at);
//        OapiRobotSendResponse response = client.execute(request);
//        if(response.isSuccess()){
//            System.out.println("发送成功");
//        }else {
//            System.out.println("发送失败："+ response.getErrmsg());
//        }
//        Random random = new Random();
//        for(int i=0;i<5;i++){
//            String test = str;
//            new Thread(()->{
//                synchronized (test){
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(test);
//                }
//            }).start();
//        }

//        FaceDetect.jniHandleFaceDetect();
    }

    private Map<String, Object> sign() throws Exception {
        Long timestamp = System.currentTimeMillis();
        String secret = "SEC59e1f6e2fd1159cd83fc95683044c9a613af2d367fc5acea28601edbbf3fcb8e";
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new BASE64Encoder().encode(signData), "UTF-8");
        System.out.println(sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        map.put("timestamp", timestamp);
        return map;
    }

    @RequestMapping(value = "/java/test")
    @ResponseBody
    public void javaTest() {
        String imagePath = "/usr/local/apache-tomcat-8.5.9/imgtest/1.jpg";
        String outFile = "/usr/local/apache-tomcat-8.5.9/imgtest/2.jpg";
//        try {
//            com.example.demo.util.opencv.FaceDetect.detectEye(imagePath, outFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @RequestMapping(value = "/freemarker")
    public String freemarker() {
        return "freemarker";
    }

    //@Scheduled(cron="0/5 * * * * ?")
    public void job() {
        //示例：https://www.jb51.net/article/160555.htm
        System.out.println("每五秒执行一次");
    }


    @RequestMapping(value = "rabbitmq/testProducer")
    @ResponseBody
    public void testProducer() {
//        for(int i=0;i<50;i++){
//            JSONObject jsonObject=new JSONObject();
//            jsonObject.put("taskId",i);
//            fanoutProducer.send(jsonObject.toJSONString());
//        }

    }

    @RequestMapping(value = "mqtt/test")
    public void testMqtt() throws Exception {
//        String topic = "yltx/mqtt/test";
//        String msg = "李文武的测试";
//        mqttConsumer.publishMessage(topic,msg,0);

    }

    @RequestMapping(value = "masterslave/read")
    @ResponseBody
    public Object masterslaveRead() {
//        return masterSlaveService.getData();
        return null;
    }

    @RequestMapping(value = "masterslave/write")
    @ResponseBody
    public Object masterslaveWrite() {
//        masterSlaveService.saveData();
        return "success";
    }


    @RequestMapping(value = "websocket/page")
    public String websocketPage() {
        return "websocket";
    }

//    @RequestMapping("/push/{toUserId}")
//    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
//        WebSocketServer.sendInfo(message, toUserId);
//        return ResponseEntity.ok("MSG SEND SUCCESS");
//    }

    @RequestMapping(value = "nettywebsocket/page")
    public String nettywebsocketPage() {
        return "nettyWebsocket";
    }

//    @RequestMapping(value = "rocketmq/test")
//    public void rocketmqTest(){
//        List<String> mesList = new ArrayList<>();
//        mesList.add("AA");
//        mesList.add("BB");
//        mesList.add("CC");
//        mesList.add("DD");
//        for (String s : mesList) {
//            //创建生产信息
//            Message message = new Message(RocketMqConfig.TOPIC, "testtag", ("小小一家人的称谓:" + s).getBytes());
//            //发送
//            SendResult sendResult = null;
//            try {
//                sendResult = producer.getProducer().send(message);
//            } catch (MQClientException e) {
//                e.printStackTrace();
//            } catch (RemotingException e) {
//                e.printStackTrace();
//            } catch (MQBrokerException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("输出生产者信息={"+sendResult+"}");
//        }
//    }

    @RequestMapping("kafka/test")
    public ResponseEntity<String> kafkaTest() {
//        kafkaProducer.send("THIS IS A TOPIC MESSAGE");
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    @RequestMapping("datas/receive")
    public void datasReceive() {
        String secret = "d6w82tzzd369vbsvjys4u3x47s47hrw2";
        String access_token = "aptgkcmsrf422qq3pc37ycdkrptwe3yg";
        String timestamp = System.currentTimeMillis() + "";
        String sign = Md5Util.stringToMD5(access_token + "-" + secret + "-" + timestamp).toLowerCase();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", access_token);
        param.put("timestamp", timestamp);
        param.put("sign", sign);
        Map<String, Object> datas = new HashMap<>();
        String caseStr = "[\n" +
                "{\n" +
                "\"case_code\":\"2362c1b7e3891f1d297cf407a2f57d3a\",\n" +
                "\"case_name\":\"行业专用软件项目\",\n" +
                "\"anno_content_url\":\"https://hunan.zcygov.cn/luban/announcement/detail?encryptId=o1rvTBPlSzXPPcDZUDqmxw==\",\n" +
                "\"project_number\":\"1767962000014265896\",\n" +
                "\"location\":\"430100\",\n" +
                "\"publish_time\":\"2022-03-18\",\n" +
                "\"bid_winning_amount\":null,\n" +
                "\"pur_method\":\"000001\",\n" +
                "\"case_source\":\"湖南省政府采购电子卖场\",\n" +
                "\"pur_unit_name\":\"湖南图书馆\",\n" +
                "\"owned_item\":[\n" +
                "\"IP051605\",\n" +
                "\"IS0399\",\n" +
                "\"PI1803\",\n" +
                "\"PU1403\",\n" +
                "\"TL0002\"\n" +
                "],\n" +
                "\"package_info\":[\n" +
                "{\n" +
                "\"subcontract_no\":\"default\",\n" +
                "\"subcontract_tag\":[\n" +
                "],\n" +
                "\"subcontract_content\":[\n" +
                "\"网上超市\"\n" +
                "],\n" +
                "\"subcontract_content_software\":[\n" +
                "],\n" +
                "\"subcontract_budget\":null,\n" +
                "\"subcontract_win_amount\":null,\n" +
                "\"subcontract_supplier\":[\n" +
                "\"北京博通壹图信息技术有限公司\"\n" +
                "],\n" +
                "\"subcontract_supplier_address\":[\n" +
                "],\n" +
                "\"other_suppliers\":[\n" +
                "],\n" +
                "\"bid_qualification\":null,\n" +
                "\"is_abandoned_bid\":null,\n" +
                "\"abandon_reason\":null,\n" +
                "\"other_suppliers_uuid\":[\n" +
                "],\n" +
                "\"subcontract_supplier_uuid\":[\n" +
                "\"c34f92a9-1217-e679-da65-d264f9dfb754\"\n" +
                "]\n" +
                "}\n" +
                "],\n" +
                "\"notice_content\":null,\n" +
                "\"attachment_info\":{\n" +
                "\"bidding_file\":[\n" +
                "],\n" +
                "\"eval_file\":[\n" +
                "],\n" +
                "\"other_file\":[\n" +
                "]\n" +
                "},\n" +
                "\"craw_time\":null,\n" +
                "\"gmt_create\":\"2022-04-18T12:21:07\",\n" +
                "\"gmt_update\":\"2022-04-18T12:21:07\",\n" +
                "\"provice_code\":\"430000\",\n" +
                "\"provice_name\":\"湖南省\",\n" +
                "\"city_code\":\"430100\",\n" +
                "\"city_name\":\"长沙市\",\n" +
                "\"district_code\":null,\n" +
                "\"district_name\":null,\n" +
                "\"is_filter\":1,\n" +
                "\"state\":1,\n" +
                "\"correct_url\":null,\n" +
                "\"case_type\":\"010003\",\n" +
                "\"project_type\":\"case\",\n" +
                "\"name\":\"行业专用软件项目\"\n" +
                "}\n" +
                "]";
        datas.put("case", JSON.parseArray(caseStr));
        param.put("datas", datas);
        HttpEntity<String> httpEntity = new HttpEntity<String>(JSON.toJSONString(param), headers);
        String responseString = restTemplate.postForObject("https://eagle-api-dev.jfh.com/api/v2/base/external/datas/receive", httpEntity, String.class);
        System.out.println(responseString);
    }

    @RequestMapping("datas/query")
    public void datasQuery() {
        String secret = "d6w82tzzd369vbsvjys4u3x47s47hrw2";
        String access_token = "aptgkcmsrf422qq3pc37ycdkrptwe3yg";
        String timestamp = System.currentTimeMillis() + "";
        String sign = Md5Util.stringToMD5(access_token + "-" + secret + "-" + timestamp).toLowerCase();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", access_token);
        param.put("timestamp", timestamp);
        param.put("sign", sign);
        List<String> list = new ArrayList<>();
        list.add("f6b5492682bf6103e7aad0d2abe6a6e9");
        param.put("task_id", list);
        HttpEntity<String> httpEntity = new HttpEntity<String>(JSON.toJSONString(param), headers);
        String responseString = null;
        try {
            responseString = restTemplate.postForEntity("https://eagle-api-dev.jfh.com/api/v2/base/external/datas/query", httpEntity, String.class).getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        System.out.println(responseString);
    }


    @RequestMapping("singleLogin")
    public void singleLogin(@RequestHeader(value = "referer", required = false) final String referer) {
        List<String> list = new ArrayList<>();
        list.add("/ophtml/serviceManagement.html#/");
        System.out.println(list.contains("ophtml/serviceManagement.html"));
        System.out.println(list.contains("/ophtml/serviceManagement.html"));
        System.out.println(list.contains("/ophtml/serviceManagement.html#/"));
        String code = "e27d0b8c375eb236d38c96436eca8853";
        String url = "http://180.108.205.61:8080/sst-smartcity-sso";
        String redirectUri = "https://dpark.suzhou.com.cn/sslogin.html";
        String clientId = "4140a09c-f5b1-4ca3-a84e-8aeac34bee52";
        String clientSecret = "7af46916-5f6e-4892-89e2-1583c39efe66";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

        param.add("code", code);
        param.add("client_id", clientId);
        param.add("grant_type", "authorization_code");
        param.add("redirect_uri", redirectUri);
        param.add("client_secret", clientSecret);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        String resultJsonString = restTemplate.postForObject(url + "/rest/oauth2/token", httpEntity, String.class);
        System.out.println(resultJsonString);
        JSONObject jsonObject = JSON.parseObject(resultJsonString);
        if (jsonObject.containsKey("access_token")) {
            //获取到access_token
            String accessToken = jsonObject.getString("access_token");
            String responseString = restTemplate.postForObject(url + "/rest/smartcitysso/getCurrentLoginUser?access_token=" + accessToken, null, String.class);
            System.out.println(responseString);
            JSONObject userObject = JSON.parseObject(responseString);
            if (userObject.containsKey("status") && "1".equals(userObject.getJSONObject("status").getString("code"))) {
                //成功获取user
                JSONObject userInfo = userObject.getJSONObject("custom").getJSONObject("user");
                String mobile = userInfo.getString("mobile");
                String userguid = userInfo.getString("userguid");
                String nickName = userInfo.getString("nc");
                String userName = userInfo.getString("displayname");
                System.out.println(mobile);
            } else {
                //获取失败，抛出异常
            }
        } else {
            //获取失败，抛出异常
        }

    }

    @RequestMapping(value = "excel")
    @GetMapping
    public Object excel(String a, HttpServletResponse response) throws Exception {

        //筛选存在于数据库的图片
        List<String> dbUrls = new ArrayList<>();
        dbUrls.add("https://imgdev.jfh.com/085eff46a909432ca54f220fd68d053a.png");
        dbUrls.add("https://imgdev.jfh.com/270fad93358440ab9182148a95e27f96.jpg");
        dbUrls.add("https://imgdev.jfh.com/direct-url/80c31cb561fa4b209d920c9e3cb3c39d.jpg");
        //处理文件
        String zipName = "111 " + ".zip";
        zipName = new String(zipName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + zipName);
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (String iUrl : dbUrls) {
                URL url = new URL(iUrl);
                zipOut.putNextEntry(new ZipEntry(FilenameUtils.getName(iUrl)));
                InputStream in = new BufferedInputStream(url.openStream());
//                byte[] bytes = readInputStream(in);
                zipOut.write(in.read());
                zipOut.closeEntry();
                in.close();
            }
        } catch (IOException e) {

        }

        System.out.println(a);
//        excelBusinessService.readExcel("C:\\Users\\liwenwu\\Desktop\\重庆农村商业银行股份有限公司\\重庆农村商业银行股份有限公司账单模板.xls","C:\\Users\\liwenwu\\Desktop\\生成的Excel");
        return "生成Excel结束";
    }

    @RequestMapping("redisRLimiter")
    @RedisRLimiter(name = "resource", count = 1, period = 1, key = "key")
    public void redisRLimiter() {
        System.out.println(1);
    }
}
