package com.example.demo.util;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;

/**
 * @Description: doc转docx
 * @Author: liwenwu
 * @Date: 2024/2/28
 */
@Slf4j
public class JavaDocToDocxConverter {

    /**
     * @Description: srcPath 源地址 descPath 目标地址
     * @Author: liwenwu
     * @Date: 2024/2/28
     */
    public static void converter(String srcPath,String descPath){
        File srcFile = new File(srcPath);
        File descFile = new File(descPath);
        try (InputStream inputStream = new FileInputStream(srcFile);
             OutputStream outputStream = new FileOutputStream(descFile);
             ){
            Document document = new Document(inputStream);
            document.save(outputStream, SaveFormat.DOCX);
            document.cleanup();
        } catch (Exception e) {
            log.error("doc转docx异常。", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.join(new ArrayList<>(),","));
//        converter("E:\\1.doc","E:\\2.docx");
    }
}
