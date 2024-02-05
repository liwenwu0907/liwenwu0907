//package com.example.demo.util.opencv;
//
//import org.apache.commons.lang3.StringUtils;
//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.ClassUtils;
//
//import java.io.File;
//
///**
// * @author li wenwu
// * @date 2020/12/11 17:15
// */
//public class FaceDetect {
//
//    static Logger log = LoggerFactory.getLogger(FaceDetect.class);
//
//    //这里图片暂存的位置是Tomcat下新建facedetect文件夹，用完图片会删除，只是作为临时存储
//    private static final String faceDetectPath = System.getProperty("catalina.home") + File.separator + "facedetect";
//
//    //face模型路径
//    private static String face_xml_dir = ClassUtils.getDefaultClassLoader().getResource("haarcascade_frontalface_alt2.xml").getPath();
//
//    //eye模型路径
//    private static String eye_xml_dir = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\haarcascade_eye.xml";
//            //ClassUtils.getDefaultClassLoader().getResource("haarcascade_eye.xml").getPath();
//
//    static {
//        //运行的环境Windows或者Linux
//        String osName = System.getProperties().getProperty("os.name");
//        log.info("OpenCV的运行环境：{}",osName);
//        if (StringUtils.isNotBlank(osName)) {
//            if (osName.startsWith("Windows")) {
//                //Windows环境需要dll文件
//                // 载入opencv的库
////                String opencvpath = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\";
////                String opencvDllName = opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll";
//                //dll文件可以放到项目目录下
//                String opencvDllName = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\opencv_java3410.dll";
//                System.load(opencvDllName);
//            } else if (StringUtils.equals("Linux", osName)) {
//                //linux需要so文件
//                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//            } else{
//                log.error("只支持Windows、linux运行环境");
//            }
//        } else {
//            log.error("无法判断运行环境");
//        }
//    }
//
//    public static void main(String[] args) {
//        String imagePath = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\1.jpg";
//        String outFile = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\2.jpg";
//        try {
//            detectEye(imagePath, outFile);
//            //detectFace(imagePath,outFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 这是Java自己调用OpenCV处理面部
//     * opencv实现人脸识别
//     *
//     * @param imagePath 输入图片的路径
//     * @param outFile 输出图片的路径
//     * @throws Exception
//     */
//    public static void detectFace(String imagePath, String outFile) {
//        // 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
//        CascadeClassifier faceDetector = new CascadeClassifier(
//                face_xml_dir);
//        Mat image = Imgcodecs.imread(imagePath);
//        // 在图片中检测人脸
//        MatOfRect faceDetections = new MatOfRect();
//        faceDetector.detectMultiScale(image, faceDetections);
//        log.info(String.format("Detected %s faces", faceDetections.toArray().length));
//        Rect[] rects = faceDetections.toArray();
//        if(null != rects && rects.length > 0){
//            for(Rect rect:rects){
//                // 在每一个识别出来的人脸周围画出一个方框
//                Imgproc.rectangle(image, new Point(rect.x - 2, rect.y - 2),
//                        new Point(rect.x + rect.width, rect.y + rect.height),
//                        new Scalar(0, 255, 0));
//            }
//            Imgcodecs.imwrite(outFile, image);
//            log.info(String.format("人脸识别成功，人脸图片文件为： %s", outFile));
//        }else{
//            log.error("没有检测到人脸");
//        }
//    }
//
//    /**
//     * opencv实现人眼识别
//     *
//     * @param imagePath
//     * @param outFile
//     * @throws Exception
//     */
//    public static void detectEye(String imagePath, String outFile) {
//        //加载眼睛识别的xml
//        CascadeClassifier eyeDetector = new CascadeClassifier(eye_xml_dir);
//        //读取图片
//        Mat image = Imgcodecs.imread(imagePath);
//        MatOfRect faceDetections = new MatOfRect();
//        // 在图片中检测眼睛
//        eyeDetector.detectMultiScale(image, faceDetections);
//        log.info(String.format("Detected %s eyes", faceDetections.toArray().length));
//        Rect[] rects = faceDetections.toArray();
//        if (null != rects && rects.length >= 1) {
//            for (Rect rect : faceDetections.toArray()) {
//                //眼睛周围模糊
//                Mat imgBlur = new Mat(image, rect);
//                Imgproc.blur(imgBlur, imgBlur, new Size(15, 15));
//                //在眼睛周围画线
////            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
////                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
//            }
//        }
//        Imgcodecs.imwrite(outFile, image);
//        log.info(String.format("人眼识别成功，人眼图片文件为： %s", outFile));
//
//    }
//}
