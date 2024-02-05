package com.example.demo.util.jni;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author li wenwu
 * @date 2020/12/10 11:36
 */
public class FaceDetect {

    static Logger logger = LoggerFactory.getLogger(FaceDetect.class);

    //输入图片
    private static final String pic_base_dir = "/usr/share/tomcat-register/apache-tomcat-8.5.32/imgtest/7d7884fd1700ec6b8beec2e171bd552.jpg";

    //输出图片
    private static final String pic_save_base_dir = "/usr/share/tomcat-register/apache-tomcat-8.5.32/imgtest/";

    //face模型路径
    private static final String face_xml_dir = "/usr/share/tomcat-register/apache-tomcat-8.5.32/imgtest/haarcascade_frontalface_alt2.xml";

    //eye模型路径
    private static final String eye_xml_dir = "/usr/share/tomcat-register/apache-tomcat-8.5.32/imgtest/haarcascade_eye_tree_eyeglasses.xml";

    static {
        try {
            System.loadLibrary("facedetect");
            logger.info("加载facedetect");
        } catch (Exception e) {
            logger.error("加载不了libfaceDetect",e.getMessage());
        }
    }

    private native String eyes_blur(String face_xml_dir, String eye_xml_dir, String pic_base_dir, String pic_save_base_dir);

    public static void jniHandleFaceDetect() {
        try {
            FaceDetect faceDetect = new FaceDetect();
            logger.info("new了faceDetect对象");
            String path = faceDetect.eyes_blur(face_xml_dir, eye_xml_dir, pic_base_dir, pic_save_base_dir);
            logger.info("转换完成:" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
