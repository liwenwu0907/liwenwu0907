package com.example.demo.service.impl;

import com.example.demo.util.email.SendEmailUtil;
import com.example.demo.util.mythreadpool.Future;
import com.example.demo.util.mythreadpool.ThreadPool;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EmailBusinessServiceImpl {

    static Map<String, String> emailMap = new HashMap<String, String>() {{
        put("安全保卫部", "zhwangli@cqrcb.com");
        put("办公室", "zhoumi@cqrcb.com");
        put("财务会计部", "14182840@qq.com");
        put("村镇银行管理部", "409606512@qq.com");
        put("公司信贷部", "183028705@qq.com");
        put("党群工作部", "361418951@qq.com");
        put("党委办公室", "liruoling@cqrcb.com");
        put("数字渠道部", "357363870@qq.com");
        put("董事会办公室", "411502496@qq.com");
        put("风险管理部", "289411526@qq.com");
        put("普惠金融部", "hcliuchang@cqrcb.com");
        put("公司金融部", "952985654@qq.com");
        put("交易金融部", "yangsil@cqrcb.com");
        put("合规及消费者权益管理部", "253668097@qq.com");
        put("后勤管理部", "3168239@qq.com");
        put("监事会办公室", "842617637@qq.com");
        put("数字金融部", "nayangyang@cqrcb.com");
        put("金融市场业务管理部", "zhzhengmy@cqrcb.com");
        put("科技管理部", "liujin@cqrcb.com");
        put("培训中心", "705023776@qq.com");
        put("渠道管理部", "1053354522@qq.com");
        put("人力资源部", "1546573931@qq.com");
        put("审计稽核部", "wangwq@cqrcb.com");
        put("授信审批部", "zhangfangl@cqrcb.com");
        put("数据资产部", "pengqj@cqrcb.com");
        put("投资银行部", "459656676@qq.com");
        put("乡村振兴金融部", "852035382@qq.com");
        put("小微金融部", "csyuanjn@cqrcb.com");
        put("信贷管理部", "yangrh@cqrcb.com");
        put("信用卡及消费金融部", "guanhf@cqrcb.com");
        put("软件开发部", "yull@cqrcb.com");
        put("运营管理部", "yaojuan@cqrcb.com");
        put("网络金融部", "taoyu@cqrcb.com");
        put("驻重庆农商行纪检监察组", "747087456@qq.com");
        put("资产负债管理部", "yinxy@cqrcb.com");
        put("资产经营管理部", "67165195@qq.com");
        put("资产托管部", "439465120@qq.com");
        put("资金营运部", "leiyu@cqrcb.com");
        put("总行营业部", "18523409811@163.com");
        put("重庆农村商业银行股份有限公司巴南支行", "178544259@qq.com");
        put("重庆农村商业银行股份有限公司北碚支行", "bbzhouql@cqrcb.com");
        put("重庆农村商业银行股份有限公司璧山支行", "bsleixm@cqrcb.com");
        put("重庆农村商业银行股份有限公司城口支行", "158004788@qq.com");
        put("重庆农村商业银行股份有限公司大渡口支行", "1002716643@qq.com");
        put("重庆农村商业银行股份有限公司大足支行", "364370325@qq.com");
        put("重庆农村商业银行股份有限公司垫江支行", "baijiang@cqrcb.com");
        put("重庆农村商业银行股份有限公司丰都支行", "pengting@cqrcb.com");
        put("重庆农村商业银行股份有限公司奉节支行", "65147651@qq.com");
        put("重庆农村商业银行股份有限公司涪陵分行", "yinyq@cqrcb.com");
        put("重庆农村商业银行股份有限公司合川分行", "26799578@qq.com");
        put("重庆农村商业银行股份有限公司江北支行", "jblitong@cqrcb.com");
        put("重庆农村商业银行股份有限公司江津分行", "553219292@qq.com");
        put("重庆农村商业银行股份有限公司九龙坡支行", "394116292@qq.com");
        put("重庆农村商业银行股份有限公司开州支行", "543770702@qq.com");
        put("重庆农村商业银行股份有限公司科学城分行", "wangzr@cqrcb.com");
        put("重庆农村商业银行股份有限公司梁平支行", "610647201@qq.com");
        put("重庆农村商业银行股份有限公司两江分行", "chuym@cqrcb.com");
        put("重庆农村商业银行股份有限公司南岸支行", "707804844@qq.com");
        put("重庆农村商业银行股份有限公司南川支行", "172831936@qq.com");
        put("重庆农村商业银行股份有限公司彭水支行", "douxf@cqrcb.com");
        put("重庆农村商业银行股份有限公司綦江支行", "L17783265885@163.com");
        put("重庆农村商业银行股份有限公司黔江支行", "2374719343@qq.com");
        put("重庆农村商业银行股份有限公司曲靖分行", "783582451@qq.com");
        put("重庆农村商业银行股份有限公司荣昌支行", "z15736158527@163.com");
        put("重庆农村商业银行股份有限公司沙坪坝支行", "wangyr@cqrcb.com");
        put("重庆农村商业银行股份有限公司石柱支行", "szheyi@cqrcb.com");
        put("重庆农村商业银行股份有限公司铜梁支行", "tlrenqin@cqrcb.com");
        put("重庆农村商业银行股份有限公司潼南支行", "yangbl@cqrcb.com");
        put("重庆农村商业银行股份有限公司万盛支行", "2609727227@qq.com");
        put("重庆农村商业银行股份有限公司万州分行", "411372729@qq.com");
        put("重庆农村商业银行股份有限公司巫山支行", "zengql@cqrcb.com");
        put("重庆农村商业银行股份有限公司巫溪支行", "qiuzc@cqrcb.com");
        put("重庆农村商业银行股份有限公司武隆支行", "wllist@cqrcb.com");
        put("重庆农村商业银行股份有限公司秀山支行", "243489453@qq.com");
        put("重庆农村商业银行股份有限公司宣威支行", "1245594505@qq.com");
        put("重庆农村商业银行股份有限公司永川支行", "626373632@qq.com");
        put("重庆农村商业银行股份有限公司酉阳支行", "809513834@qq.com");
        put("重庆农村商业银行股份有限公司渝北支行", "zengxh@cqrcb.com");
        put("重庆农村商业银行股份有限公司渝中支行", "yzluoling1@cqrcb.com");
        put("重庆农村商业银行股份有限公司云阳支行", "zhoucz@cqrcb.com");
        put("重庆农村商业银行股份有限公司长寿支行", "15320828777@163.com");
        put("重庆农村商业银行股份有限公司忠县支行", "283440364@qq.com");

    }};

    public static void sendEmail(String path, Integer month) throws InterruptedException {
        ThreadPool<String> threadPool = new ThreadPool<>(20, 10, 200);
        List<Future<String>> futures = new LinkedList<>();
        //扫描文件夹下的所有文件，然后根据文件名称匹配邮箱地址
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    //去除后缀[
                    final String fileName = FilenameUtils.removeExtension(file1.getName());
                    if (emailMap.get(fileName) != null) {
                        String email = emailMap.get(fileName);
                        try {
                            Future<String> f = threadPool.addTask(() -> {
                                //执行逻辑代码
                                File[] fileArray = new File[1];
                                fileArray[0] = file1;
                                String content = "您好：\n" +
                                        "附件是" + month +"月账单，请查收。\n" +
                                        "请确认账单并填写伙食补贴和交通补贴，没有则填0，在3个工作日内返回账单，感谢您的支持与配合，谢谢！";
                                SendEmailUtil.sendEmail(fileArray, email, "重庆农商行 - " + fileName + month + "月账单", content, "xuyan@zirugroup.com");
                                return null;
                            });
                            futures.add(f);
                        } catch (Exception e) {
                            System.out.println("邮件发送失败，" + fileName);
                            e.printStackTrace();
                        }
                    } else {
                        //没匹配上，打印文件名
                        System.out.println("文件没有匹配上，" + fileName);
                    }
                }
                for (Future<String> f : futures) {
                    f.get();
                }
                threadPool.close();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String userHome = System.getProperty("user.home");
        String path = userHome + File.separator + "生成的Excel";
        sendEmail(path, 1);
    }
}
