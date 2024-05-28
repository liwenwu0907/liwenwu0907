package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ImportExcelPicture {

    public static void takePicture() throws IOException {
        File file = new File("E:\\新建 XLSX 工作表 (2).xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet instanceof XSSFSheet) {
            XSSFSheet xssfSheet = (XSSFSheet) sheet;
            List<XSSFPictureData> pictures = xssfSheet.getWorkbook().getAllPictures();

            for (XSSFPictureData picture : pictures) {
                try (InputStream inputStream = picture.getPackagePart().getInputStream()) {
                    // 处理图片数据
                    // 例如，可以将图片保存到文件系统
                    String fileName = "image" + "." + picture.suggestFileExtension();
                    FileOutputStream outputStream = new FileOutputStream("E:\\" + fileName);
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    outputStream.write(bytes);
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        workbook.close();
        fileInputStream.close();
    }

    public static void generExcelKeyValue() throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // 创建下拉列表的键值对
        Map<String, String> dropdownItems = new HashMap<>();
        dropdownItems.put("1", "Item 1");
        dropdownItems.put("2", "Item 2");
        dropdownItems.put("3", "Item 3");

        String[] strArray = new String[3];
        strArray[0] = "1，Item 1";
        strArray[1] = "2，Item 2";
        strArray[2] = "3，Item 3";
        // 创建数据有效性对象
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(strArray);
        // 单元格A1
        CellRangeAddressList addressList = new CellRangeAddressList(0, 3550, 0, 0);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);

        // 设置下拉列表的输入消息和错误提示
        dataValidation.createErrorBox("Error", "You must select a valid item!");
        dataValidation.setShowErrorBox(true);

        // 应用数据有效性约束
        sheet.addValidationData(dataValidation);

        // 写入文件
        FileOutputStream outputStream = new FileOutputStream("E:\\dropdown.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public static void getExcelKeyValue() throws Exception {
        FileInputStream inputStream = new FileInputStream("E:\\dropdown.xlsx");
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Map<String, String[]> dropdownMap = new HashMap<>();

        for (Row row : sheet) {
            for (Cell cell : row) {
                // 检查是否存在下拉列表
                if (cell.getCellType() == CellType.FORMULA) {
                    String cellFormula = cell.getCellFormula();
                    // POI 解析XLSX中的下拉列表为_xlfn开头的函数公式
                    if (cellFormula.startsWith("_xlfn.")) {
                        String[] items = cellFormula.split("\\(")[1].split("\\)")[0].split(",");
                        dropdownMap.put(cell.getAddress().formatAsString(), items);
                    }
                }
            }
        }

        // 打印下拉列表的键值对
        for (Map.Entry<String, String[]> entry : dropdownMap.entrySet()) {
            System.out.println("Cell: " + entry.getKey() + " - Dropdown Items: " + String.join(", ", entry.getValue()));
        }

        workbook.close();
    }

    public static void main(String[] args) throws Exception {
        generExcelKeyValue();
//        getExcelKeyValue();
    }
}
