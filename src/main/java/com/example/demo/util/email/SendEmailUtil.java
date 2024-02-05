package com.example.demo.util.email;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

import java.io.File;

public class SendEmailUtil {

    public static void sendEmail(File[] files,String email,String subject,String content,String ccTo){
        // 创建邮件账户对象
        MailAccount account = new MailAccount();
        // 邮件服务器的SMTP地址，可选，默认为smtp.<发件人邮箱后缀>
        account.setHost("smtp.exmail.qq.com");
        // 邮件服务器的SMTP端口，默认是25端口,ssl端口465
        account.setPort(465);
        // 是否需要用户名密码验证
        account.setAuth(true);
        // 发送方，遵循RFC-822标准
        account.setFrom("yangjuan@zirugroup.com");
        // 用户名,腾讯企业邮箱必须要设置成你自己使用邮箱的名称，否则会报错，权限认证失败
        account.setUser("yangjuan@zirugroup.com");
        // 使用客户端密码(授权码)--需提前在邮箱中配置设置
        account.setPass("DdsZMaFGegsAj8Zq");
        // 开启ssl安全连接
        account.setSslEnable(true);
        try {
            String responseStr = "";
            if(null != files && files.length > 0){
                responseStr = MailUtil.send(account, CollUtil.newArrayList(email), CollUtil.newArrayList(ccTo),null,subject , content, false,files);

            }else {
                responseStr = MailUtil.send(account, CollUtil.newArrayList(email), CollUtil.newArrayList(ccTo),null,subject , content, false);

            }
            System.out.println(email + "发送成功！");
        } catch (Exception e) {
            System.out.println(email + "发送失败！");
            e.printStackTrace();
        }
//        System.out.println(responseStr);
//        Properties props = new Properties();
//        //链接协议
//        props.put("mail.transport.protocol", "smtp");
//        //主机名	smtp.exmail.qq.com：腾讯企业邮箱		smtp.qq.com：qq邮箱
//        props.put("mail.smtp.host", "smtp.exmail.qq.com");
//        //端口号
//        props.put("mail.smtp.port", 465);
//        props.put("mail.smtp.auth", "true");
//        //使用ssl安全链接
//        props.put("mail.smtp.ssl.enable", "true");
//        //控制台打印debug信息
//        props.put("mail.debug", "true");
//        try {
//            //获得回话
//            Session session = Session.getInstance(props);
//            //获取邮件
//            Message msg = new MimeMessage(session);
//            //主题
//            msg.setSubject("主题主题");
//            //设置发件人（必须与授权地址一致）
//            msg.setFrom(new InternetAddress("yangjuan@zirugroup.com"));
//            //设置发送时间（显示）
//			msg.setSentDate(new Date());
//            //设置一个收件人，TO
//            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("865560330@qq.com"));
//            //设置多个收件人
////			msg.setRecipients(Message.RecipientType.TO, new InternetAddress[] {new InternetAddress("")});
//            //抄送，CC
////			msg.setRecipients(Message.RecipientType.CC, arg1);
//            //密送，BCC
////			msg.setRecipients(Message.RecipientType.BCC, arg1);
//            //回复
////			msg.setReplyTo(addresses);
//
//            //if需要发送附件（+文本）
//            MimeMultipart multipart = new MimeMultipart();
//            //设置附件
//            BodyPart filebodypart = new MimeBodyPart();
//            DataHandler dh = new DataHandler(new FileDataSource(file));
//            filebodypart.setDataHandler(dh);
//            //设置附件名
//            filebodypart.setFileName(file.getName());
//            multipart.addBodyPart(filebodypart);
//            //设置内容
//            BodyPart textbodypart = new MimeBodyPart();
//            textbodypart.setText("这是带附件的邮件。");
//            multipart.addBodyPart(textbodypart);
//            msg.setContent(multipart);
//
//            //else只发文本
////			msg.setText("内容内容内容内容内容\n内容内容内容内容内容内容内容---------内容");
//
//            Transport trans = session.getTransport();
//
//
//            trans.connect("yangjuan@zirugroup.com", "DdsZMaFGegsAj8Zq");
//            trans.sendMessage(msg, msg.getAllRecipients());
//            trans.close();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }


    public static void main(String[] args) {
        File[] files = new File[1];
        files[0] = new File("C:\\Users\\liwenwu\\生成的Excel\\办公室.xls");
        sendEmail(files,"865560330@qq.com",null,null,"");
    }

}
