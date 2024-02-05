//package com.example.demo;
//
//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//
///**
// * @author li wenwu
// * @date 2020/12/14 10:00
// */
//public class FaceDetectTest {
//
//    private static final String face_xml = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\haarcascade_frontalface_alt2.xml";
//
//    private static final String eye_xml = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\haarcascade_eye.xml";
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
//    static {
//        // 载入opencv的库
//        String opencvpath = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\";
//        String opencvDllName = opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll";
//        System.load(opencvDllName);
//    }
//
//    /**
//     * opencv实现人脸识别
//     *
//     * @param imagePath
//     * @param outFile
//     * @throws Exception
//     */
//    public static void detectFace(String imagePath, String outFile) throws Exception {
//
//        System.out.println("Running DetectFace ... ");
//        // 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
//        CascadeClassifier faceDetector = new CascadeClassifier(
//                face_xml);
//
//        Mat image = Imgcodecs.imread(imagePath);
//
//        // 在图片中检测人脸
//        MatOfRect faceDetections = new MatOfRect();
//
//        faceDetector.detectMultiScale(image, faceDetections);
//
//        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
//
//        Rect[] rects = faceDetections.toArray();
//        if (rects != null && rects.length > 1) {
//            throw new RuntimeException("超过一个脸");
//        }
//        // 在每一个识别出来的人脸周围画出一个方框
//        Rect rect = rects[0];
//
//        Imgproc.rectangle(image, new Point(rect.x - 2, rect.y - 2),
//                new Point(rect.x + rect.width, rect.y + rect.height),
//                new Scalar(0, 255, 0));
//
//        Imgcodecs.imwrite(outFile, image);
//        System.out.println(String.format("人脸识别成功，人脸图片文件为： %s", outFile));
//
//
//    }
//
//    /**
//     * opencv实现人眼识别
//     *
//     * @param imagePath
//     * @param outFile
//     * @throws Exception
//     */
//    public static void detectEye(String imagePath, String outFile) throws Exception {
//        Mat target = new Mat();
////        source= Imgcodecs.imread(imagePath,0);
////        //把图象变为灰度，大小一致输出
////        Imgcodecs.imwrite(imagePath,source);
//        Size size=new Size(204,252);
//        Imgproc.resize(Imgcodecs.imread(imagePath),target,size,0,0,Imgproc.INTER_NEAREST);
//        Imgcodecs.imwrite(imagePath,target);
//
//        //加载眼睛识别的xml
//        CascadeClassifier eyeDetector = new CascadeClassifier(
//                eye_xml);
//        //读取图片
//        Mat image = Imgcodecs.imread(imagePath);
//
//        MatOfRect faceDetections = new MatOfRect();
//        // 在图片中检测眼睛
//        eyeDetector.detectMultiScale(image, faceDetections);
//        System.out.println(String.format("Detected %s eyes", faceDetections.toArray().length));
//        Rect[] rects = faceDetections.toArray();
//        if (null != rects && rects.length > 0) {
//            for (Rect rect : faceDetections.toArray()) {
//                //眼睛周围模糊
//                Mat imgBlur = new Mat(image, rect);
//                Imgproc.blur(imgBlur, imgBlur, new Size(14, 14));
//                //在眼睛周围画线
////            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
////                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
//            }
//        }
//        Imgcodecs.imwrite(outFile, image);
//        System.out.println(String.format("人眼识别成功，人眼图片文件为： %s", outFile));
//
//    }
//
//
//}
