package com.example.demo.service.impl;

import com.example.demo.entity.ElectronicInvoiceDTO;
import com.example.demo.util.email.SendEmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ElectronicInvoiceServiceImpl {

    public static void main(String[] args) throws Exception {
        String userHome = System.getProperty("user.home");
        String xls = "2月新希望发票邮件发送.xlsx";
        if (args.length > 0) {
            xls = args[0];
        }
        readExcel(userHome + File.separator + xls);
    }

    public static void readExcel(String path) throws Exception {
        ExecutorService pool = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(1024),new ThreadPoolExecutor.CallerRunsPolicy());
        //读取Excel
        Workbook wb = WorkbookFactory.create(new File(path));
        //第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        //读取具体的内容
        List<ElectronicInvoiceDTO> list = getExcelData(sheet);
        Set<String> set = new HashSet<>();
//        List<Future<String>> result = new ArrayList<Future<String>>();

        //发送邮件
        for (ElectronicInvoiceDTO electronicInvoiceDTO : list) {
            Runnable run = () -> {
                //执行逻辑代码
                String content = electronicInvoiceDTO.getPrefix() + (StringUtils.isNotBlank(electronicInvoiceDTO.getAddress()) ? electronicInvoiceDTO.getAddress() : "") + "  " + (StringUtils.isNotBlank(electronicInvoiceDTO.getSuffix()) ? electronicInvoiceDTO.getSuffix() : "");
                //根据抬头找需要发送的文件
                List<File> fileList = search(new File("C:\\Users\\liwenwu\\新希望附件"),electronicInvoiceDTO.getInvoiceNo());
                List<File> fileList2 = search(new File("C:\\Users\\liwenwu\\新希望附件\\电子发票"),electronicInvoiceDTO.getElectronicInvoiceNo());
                fileList.addAll(fileList2);
                if(CollectionUtils.isEmpty(fileList)){
                    content = content.replace("数电专票3个格式文件见附件，请按需下载。","");
                }
                SendEmailUtil.sendEmail(fileList.toArray(new File[0]), electronicInvoiceDTO.getReceiver(), electronicInvoiceDTO.getSubject(),
                        content, electronicInvoiceDTO.getCcTo());
                if(!set.add(electronicInvoiceDTO.getSubject())){
                    System.out.println(electronicInvoiceDTO.getSubject() + "重复发送了！");
                }
                for (File file : fileList){
                    file.delete();
                }
            };
            pool.execute(run);

//            Callable callable = new Callable() {
//                @Override
//                public Object call() throws Exception {
//                    return null;
//                }
//            };
//            Future<String> future = pool.submit(callable);
//            result.add(future);
        }
//        for (Future<String> f : result) {
//            f.get();
//        }
//        pool.shutdown();
        if(pool.isTerminated()){
            pool.shutdown();
        }
    }

    private static List<ElectronicInvoiceDTO> getExcelData(Sheet sheet) {
        List<ElectronicInvoiceDTO> list = new ArrayList<>();
        int totalRow = sheet.getLastRowNum();
        for (int i = 1; i <= totalRow; i++) {
            //从第二行开始读
            Row row = sheet.getRow(i);
            ElectronicInvoiceDTO electronicInvoiceDTO = new ElectronicInvoiceDTO();
            Cell cell = row.getCell(0);
            electronicInvoiceDTO.setReceiver(cell.getStringCellValue());
            Cell cell1 = row.getCell(1);
            electronicInvoiceDTO.setCcTo(cell1.getStringCellValue());
            Cell cell2 = row.getCell(2);
            electronicInvoiceDTO.setSubject(cell2.getStringCellValue());
            Cell cell3 = row.getCell(3);
            if(null != cell3){
                electronicInvoiceDTO.setElectronicInvoiceNo(cell3.getStringCellValue());
            }
            Cell cell4 = row.getCell(4);
            if(null != cell4){
                electronicInvoiceDTO.setInvoiceNo(cell4.getStringCellValue());
            }
            Cell cell5 = row.getCell(5);
            electronicInvoiceDTO.setPrefix(cell5.getStringCellValue());
            Cell cell6 = row.getCell(6);
            if(null != cell6){
                electronicInvoiceDTO.setAddress(cell6.getStringCellValue());
            }
            Cell cell7 = row.getCell(7);
            if(null != cell7){
                electronicInvoiceDTO.setSuffix(cell7.getStringCellValue());
            }
            list.add(electronicInvoiceDTO);
        }
        return list;
    }

    public static List<File> search(File directory, String targetFileName) {
        List<File> list = new ArrayList<>();
        if (StringUtils.isNotBlank(targetFileName) && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if(!file.isDirectory()){
                        String fileName = file.getName();
                        //第一个_和第二个_之间为发票号
                        String[] nameArray = fileName.split("_");
                        String name = nameArray[1];
                        if (name.equals(targetFileName)) {
                            // 找到目标文件
                            list.add(file);
                        }
                    }
                }
            }
        }
        return list;
    }

    public static List<File> search2(File directory, String targetFileName) {
        List<File> list = new ArrayList<>();
        if (StringUtils.isNotBlank(targetFileName) && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileName = FilenameUtils.removeExtension(file.getName());
                    if (fileName.equals(targetFileName)) {
                        // 找到目标文件
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

}
