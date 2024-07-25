package com.example.demo.service.impl;


import com.example.demo.service.ExcelBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelBusinessServiceImpl implements ExcelBusinessService {

    public static void main(String[] args) throws Exception {
        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
        String xls = "重庆农商行5月账单——拆分版.xls";
        if(args.length > 0){
            xls = args[0];
        }
        readExcelV2(userHome + File.separator + xls,userHome + File.separator + "生成的Excel");
    }

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void readExcel(String path, String outPath) throws Exception {

    }

    public static void readExcelV2(String path, String outPath) throws Exception {
        if(!new File(outPath).exists()){
            new File(outPath).mkdirs();
        }
        //读取Excel
        Workbook wb = WorkbookFactory.create(new File(path));
        //第一个sheet
        //Sheet sheet=wb.getSheetAt(0);
        //根据名称读取
        Sheet totalSheet = wb.getSheet("总计");
        //月结机票sheet
        Sheet monthPlaneTicketSheet = wb.getSheet("月结机票");
        //现结机票sheet
        Sheet nowPlaneTicketSheet = wb.getSheet("现结机票");
        //月结火车票sheet
        Sheet monthTrainTicketSheet = wb.getSheet("月结火车票");
        //现结火车票sheet
        Sheet nowTrainTicketSheet = wb.getSheet("现结火车票");
        //月结酒店sheet
        Sheet monthHotelSheet = wb.getSheet("月结酒店");
        //现结酒店sheet
        Sheet nowHotelSheet = wb.getSheet("现结酒店");
        //月结用车sheet
        Sheet monthCarSheet = wb.getSheet("月结用车");
        //现结用车sheet
        Sheet nowCarSheet = wb.getSheet("现结用车");

        /*
          处理月结机票，根据月结机票的部门分组，根据分组的部门去其他sheet查询是否存在相同部门，
          若存在相同部门则添加相应的数据，不存在则不处理
         */
        //所有的部门，也就是后面需要生成多少个Excel
        Set<String> allOrg = new HashSet<>();
        Map<String, Object> monthPlaneTicketMap = handleMonthPlaneTicket(monthPlaneTicketSheet);
        Map<String, Object> nowPlaneTicketMap = handleMonthPlaneTicket(nowPlaneTicketSheet);
        Map<String, Object> monthTrainTicketMap = handleTrainTicket(monthTrainTicketSheet);
        Map<String, Object> nowTrainTicketMap = handleNowTrainTicket(nowTrainTicketSheet);
        Map<String, Object> monthHotelMap = handleTrainTicket(monthHotelSheet);
        Map<String, Object> nowHotelMap = handleTrainTicket(nowHotelSheet);
        Map<String, Object> monthCarMap = handleMonthCar(monthCarSheet);
        Map<String, Object> nowCarMap = handleMonthCar(nowCarSheet);
        Set<String> monthPlaneTicketOrg = (Set<String>) monthPlaneTicketMap.get("orgSet");
        Set<String> nowPlaneTicketOrg = (Set<String>) nowPlaneTicketMap.get("orgSet");
        Set<String> monthTrainTicketOrg = (Set<String>) monthTrainTicketMap.get("orgSet");
        Set<String> nowTrainTicketOrg = (Set<String>) nowTrainTicketMap.get("orgSet");
        Set<String> monthHotelOrg = (Set<String>) monthHotelMap.get("orgSet");
        Set<String> nowHotelOrg = (Set<String>) nowHotelMap.get("orgSet");
        Set<String> monthCarOrg = (Set<String>) monthCarMap.get("orgSet");
        Set<String> nowCarOrg = (Set<String>) nowCarMap.get("orgSet");
        allOrg.addAll(monthPlaneTicketOrg);
        allOrg.addAll(nowPlaneTicketOrg);
        allOrg.addAll(monthTrainTicketOrg);
        allOrg.addAll(nowTrainTicketOrg);
        allOrg.addAll(monthHotelOrg);
        allOrg.addAll(nowHotelOrg);
        allOrg.addAll(monthCarOrg);
        allOrg.addAll(nowCarOrg);

        //开始生成不同的Excel
        for (String org : allOrg) {
            Workbook saveWb = new HSSFWorkbook();
            //依次生成不同的sheet，但是要注意没有对应sheet数据的不需要生成，（列如：没有现结机票数据，不生成现结机票sheet）
            //生成月结机票sheet
            String str1 = createMonthPlaneTicketSheet(saveWb,monthPlaneTicketMap,org,monthPlaneTicketSheet,"月结机票");
            //生成现结机票sheet
            String str2 = createMonthPlaneTicketSheet(saveWb,nowPlaneTicketMap,org,nowPlaneTicketSheet,"现结机票");
            //生成月结火车票sheet
            String str3 = createMonthPlaneTicketSheet(saveWb,monthTrainTicketMap,org,monthTrainTicketSheet,"月结火车票");
            //生成现结火车票sheet
            String str4 = createMonthPlaneTicketSheet(saveWb,nowTrainTicketMap,org,nowTrainTicketSheet,"现结火车票");
            //生成月结酒店sheet
            String str5 = createMonthPlaneTicketSheet(saveWb,monthHotelMap,org,monthHotelSheet,"月结酒店");
            //生成现结酒店sheet
            String str6 = createMonthPlaneTicketSheet(saveWb,nowHotelMap,org,nowHotelSheet,"现结酒店");
            //生成月结用车sheet
            String str7 = createMonthPlaneTicketSheet(saveWb,monthCarMap,org,monthCarSheet,"月结用车");
            //生成现结用车sheet
            String str8 = createMonthPlaneTicketSheet(saveWb,nowCarMap,org,nowCarSheet,"现结用车");
              /*
            合计=现结+实际欠款，
            现结=现结机票+现结火车+现结酒店+现结用车，
            实际欠款=应还欠款=月结机票+月结火车+月结酒店+月结用车
             */
            BigDecimal now = new BigDecimal(str2).add(new BigDecimal(str4)).add(new BigDecimal(str6)).add(new BigDecimal(str8));
            BigDecimal actual = new BigDecimal(str1).add(new BigDecimal(str3)).add(new BigDecimal(str5)).add(new BigDecimal(str7));
            BigDecimal sum = now.add(actual);
            //最后生成总计sheet
            createTotalSheet(saveWb,totalSheet,org,now,actual,sum);
            OutputStream fileOut = new FileOutputStream(outPath+ "\\" + org +".xls");
            saveWb.write(fileOut);
            fileOut.close();
            saveWb.close();

        }
        wb.close();
    }

    /**
     * 生成月结机票sheet
     * @author  liwenwu
     * @date  2022/5/16
     **/
    private static String createMonthPlaneTicketSheet(Workbook saveWb,Map<String,Object> monthPlaneTicketMap,String orgName,Sheet monthPlaneTicketSheet,String sheetName){
        String resultSTR = "";
        if(null != monthPlaneTicketMap && monthPlaneTicketMap.size() > 0){
            Map<String,Object> dataMap = (Map<String, Object>) monthPlaneTicketMap.get("data");
            if(dataMap.containsValue(orgName)){
                //有对应的部门才去生成对应的sheet
                Sheet saveSheet = saveWb.createSheet(sheetName);
                Map<Object,List<Map.Entry<String,Object>>> allMap = dataMap.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue));
                //因为前4行都是表头数据，所有直接复制
                saveSheet = createTableHead(saveWb,saveSheet,monthPlaneTicketSheet,sheetName);
                for(int i=0;i< allMap.get(orgName).size();i++){
                    Integer rowNum = Integer.parseInt(allMap.get(orgName).get(i).getKey());
                    //rowNum就是需要复制的行
                    Row saveRow = saveSheet.createRow(i+4);
                    CellStyle cellStyle = saveWb.createCellStyle();
                    cellStyle = formatCell(cellStyle,saveRow,false);
                    Row originRow = monthPlaneTicketSheet.getRow(rowNum);
                    if (null != originRow) {
                        for (int j = 0; j <= originRow.getLastCellNum(); j++) {
                            if (null != originRow.getCell(j)) {
                                Cell cell = saveRow.createCell(j);
                                if(sheetName.contains("酒店")){
                                    System.out.println("1111");
                                }
                                if (originRow.getCell(j).getCellType() == CellType.NUMERIC) {
                                    // 对数字值的处理
//                                    DecimalFormat df = new DecimalFormat("0.00");
//                                    cellStr = df.format(originRow.getCell(j).getNumericCellValue());
//                                    if(cellStr.endsWith(".00")){
//                                        //没有小数则去除小数
//                                        cellStr = cellStr.replace(".00","");
//                                    }
                                    if(check(originRow.getCell(j))){
                                        Date date = DateUtil.getJavaDate(originRow.getCell(j).getNumericCellValue());
                                        if(j == 0){
                                            cell.setCellValue(i + 1);
                                        }else {
                                            cell.setCellValue(simpleDateFormat.format(date));
                                        }
                                    }else {

                                        //第二列的序号要重新排，不能直接使用原序号
                                        if(j == 0){
                                            cell.setCellValue(i + 1);
                                        }else {
                                            cell.setCellValue(originRow.getCell(j).getNumericCellValue());
                                        }
                                    }

                                } else if(originRow.getCell(j).getCellType() == CellType.FORMULA){

                                }else {
                                    // 其余按照字符串处理
                                    String cellStr = originRow.getCell(j).getStringCellValue();
                                    if(check(originRow.getCell(j))){
                                        Date date = null;
                                        try {
                                            date = DateUtil.getJavaDate(Double.parseDouble(cellStr));
                                        } catch (NumberFormatException e) {
                                            try {
                                                date = DateUtils.parseDate(cellStr,"yyyy-MM-dd");
                                            } catch (ParseException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                        if(j == 0){
                                            cell.setCellValue(i + 1);
                                        }else {
                                            cell.setCellValue(simpleDateFormat.format(date));
                                        }
                                    }else{

                                        //第二列的序号要重新排，不能直接使用原序号
                                        if(j == 0){
                                            cell.setCellValue(i + 1);
                                        }else {
                                            cell.setCellValue(cellStr);
                                        }
                                    }


                                }
//                                if (null != originRow.getCell(j).getCellStyle()) {
//                                    cellStyle.cloneStyleFrom(originRow.getCell(j).getCellStyle());
//                                }
                                cell.setCellStyle(cellStyle);
                                saveSheet.setColumnWidth(j, monthPlaneTicketSheet.getColumnWidth(j));
                            }
                        }
                    }
                }
                int lastRowNum = saveSheet.getLastRowNum();
                Row lastRow = saveSheet.createRow(lastRowNum + 1);
                CellStyle cellStyle = saveWb.createCellStyle();
                cellStyle = formatCell(cellStyle,lastRow,true);

                /*
                   最后一行加上单元格格式
                 */
                Row needRow = saveSheet.getRow(lastRowNum-1);
                for(int m=0;m<needRow.getLastCellNum();m++){
                    Cell cell = lastRow.createCell(m);
                    cell.setCellStyle(cellStyle);
                }

                FormulaEvaluator evaluator = saveWb.getCreationHelper().createFormulaEvaluator();
                DecimalFormat decimalFormat = new DecimalFormat("####################.###########");
                //所有数据完成复制之后，需要对其求和
                if(sheetName.contains("机票")){
                    //第10列到19列求和
                    for(int i=8;i<18;i++){
                        Cell cell = lastRow.createCell(i);
                        String colTag = CellReference.convertNumToColString(i);
                        //sum函数 ， 下面的2 表示 从第二行开始，(lastRowNum+1) 表示 最后一行
                        String formula = "SUM(" + colTag + "2:" + colTag + (lastRowNum + 1) + ")";
                        cell.setCellFormula(formula);
                        cell.setCellStyle(cellStyle);
                        if(i == 17){
                            //获取实收/实付的金额，后面总计需要计算
                            resultSTR = decimalFormat.format(evaluator.evaluate(cell).getNumberValue());
                        }
                    }
                }else if(sheetName.contains("火车票")){
                    //第11列到15列求和
                    for(int i=9;i<14;i++){
                        Cell cell = lastRow.createCell(i);
                        String colTag = CellReference.convertNumToColString(i);
                        //sum函数 ， 下面的2 表示 从第二行开始，(lastRowNum+1) 表示 最后一行
                        String formula = "SUM(" + colTag + "2:" + colTag + (lastRowNum + 1) + ")";
                        cell.setCellFormula(formula);
                        cell.setCellStyle(cellStyle);
                        if(i == 13){
                            //获取实收/实付的金额，后面总计需要计算
                            resultSTR = decimalFormat.format(evaluator.evaluate(cell).getNumberValue());
                        }
                    }
                }else if(sheetName.contains("酒店")){
                    //第15列求和
                    for(int i=13;i<14;i++){
                        Cell cell = lastRow.createCell(i);
                        String colTag = CellReference.convertNumToColString(i);
                        //sum函数 ， 下面的2 表示 从第二行开始，(lastRowNum+1) 表示 最后一行
                        String formula = "SUM(" + colTag + "2:" + colTag + (lastRowNum + 1) + ")";
                        cell.setCellFormula(formula);
                        cell.setCellStyle(cellStyle);
                        if(i == 13){
                            //获取实收/实付的金额，后面总计需要计算
                            resultSTR = decimalFormat.format(evaluator.evaluate(cell).getNumberValue());
                        }
                    }
                }else if(sheetName.contains("用车")){
                    //第10列到11列求和
                    for(int i=8;i<10;i++){
                        Cell cell = lastRow.createCell(i);
                        String colTag = CellReference.convertNumToColString(i);
                        //sum函数 ， 下面的2 表示 从第二行开始，(lastRowNum+1) 表示 最后一行
                        String formula = "SUM(" + colTag + "2:" + colTag + (lastRowNum + 1) + ")";
                        cell.setCellFormula(formula);
                        cell.setCellStyle(cellStyle);
                        if(i == 9){
                            //获取实收/实付的金额，后面总计需要计算
                            resultSTR = decimalFormat.format(evaluator.evaluate(cell).getNumberValue());
                        }
                    }
                }
            }
        }
        if(StringUtils.isBlank(resultSTR)){
            resultSTR = "0";
        }
        return resultSTR;
    }

    /**
     * 设置单元格格式
     * @author  liwenwu
     * @date  2022/5/16
     **/
    private static CellStyle formatCell(CellStyle cellStyle,Row row,boolean bold){
        Font font= row.getSheet().getWorkbook().createFont();
        row.setHeightInPoints(20);
        font.setBold(bold);
        //默认字体为宋体
        font.setFontName("微软雅黑");
//        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //设置上边框线条类型
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置右边框线条类型
        cellStyle.setBorderRight(BorderStyle.THIN);
        //设置下边框线条类型
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //设置左边框线条类型
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //设置水平对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }



    /**
     * 因为前4行都是表头数据，所有直接复制表头
     * @author  liwenwu
     * @date  2022/5/16
     **/
    private static Sheet createTableHead(Workbook saveWb,Sheet saveSheet,Sheet originSheet,String sheetName){
        //合并单元格
        saveSheet.addMergedRegion(new CellRangeAddress(1, 1, 0, originSheet.getRow(1).getLastCellNum() - 1));

        for(int i=0;i<4;i++){
            Row saveRow = saveSheet.createRow(i);
            Row originRow = originSheet.getRow(i);
            if (null != originRow) {
                for (int j = 0; j <= originRow.getLastCellNum(); j++) {
                    if (null != originRow.getCell(j)) {
                        CellStyle cellStyle = saveWb.createCellStyle();
                        Cell cell = saveRow.createCell(j);
                        cell.setCellValue(originRow.getCell(j).getStringCellValue());
                        if (null != originRow.getCell(j).getCellStyle()) {
                            cellStyle.cloneStyleFrom(originRow.getCell(j).getCellStyle());
                            cell.setCellStyle(cellStyle);
                        }
                        saveSheet.setColumnWidth(j, originSheet.getColumnWidth(j));
                    }
                }
            }
        }
        return saveSheet;
    }

    /**
     * 生成总计sheet
     *
     * @author liwenwu
     * @date 2022/5/13
     **/
    private static void createTotalSheet(Workbook saveWb,Sheet totalSheet,String orgName,BigDecimal now,BigDecimal actual,BigDecimal sum) {
        Sheet saveSheet = saveWb.createSheet("总计");
        now = now.setScale(2, RoundingMode.HALF_UP);
        actual = actual.setScale(2, RoundingMode.HALF_UP);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i <= totalSheet.getLastRowNum(); i++) {
            CellStyle cellStyle = saveWb.createCellStyle();
            Row saveRow = saveSheet.createRow(i);
            Row originRow = totalSheet.getRow(i);
            if (null != originRow) {
                for (int j = 0; j <= originRow.getLastCellNum(); j++) {
                    if (null != originRow.getCell(j)) {
                        Cell cell = saveRow.createCell(j);
                        //总计的第5行第二列，需要补上部门名称
                        if(i==4 && j==1){
                            cell.setCellValue(originRow.getCell(j).getStringCellValue() + "——" + orgName);
                        }else if(i==11 && j==1){
                            //第12行第二列，sum
                            cell.setCellValue("合计：" + sum.toPlainString() + "元");
                        }else if(i==12 && j==1){
                            //第13行第二列，now
                            cell.setCellValue("现结：" + now.toPlainString() + "元");

                        }else if(i==13 && j==1){
                            //第14行第二列，actual
                            cell.setCellValue("实际欠款：" + actual.toPlainString() + "元");

                        }else if(i==15 && j==1){
                            //第16行第二列，actual
                            cell.setCellValue("应还欠款：" + actual.toPlainString() + "元");

                        }else {
                            cell.setCellValue(originRow.getCell(j).getStringCellValue());
                        }
                        if (null != originRow.getCell(j).getCellStyle()) {
                            cellStyle.cloneStyleFrom(originRow.getCell(j).getCellStyle());
                            cell.setCellStyle(cellStyle);
                        }
                        saveSheet.setColumnWidth(j, totalSheet.getColumnWidth(j));
                    }
                }
            }
        }
        //总计sheet移到第一位
        saveWb.setSheetOrder("总计",0);

    }


    /**
     * 处理月结机票sheet，返回集合数据和部门集合
     *
     * @author liwenwu
     * @date 2022/5/12
     **/
    private static Map<String, Object> handleMonthPlaneTicket(Sheet sheet) {
        Map<String, Object> result = new HashMap<>();
        Set<String> orgSet = new HashSet<>();
        Map<String, Object> map = new LinkedHashMap<>();
        if(null != sheet){
            //从第5行开始读取,第20列是部门
            for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(null != row){
                    Cell cell = row.getCell(18);
                    if (null != cell) {
                        String orgName = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(orgName)) {
                            orgSet.add(orgName);
                            map.put(i + "", orgName);
                        }
                    }
                }

            }
        }
        result.put("data", map);
        result.put("orgSet", orgSet);
        return result;
    }

    /**
     * 处理月结火车票
     *
     * @author liwenwu
     * @date 2022/5/13
     **/
    private static Map<String, Object> handleTrainTicket(Sheet sheet) {
        Map<String, Object> result = new HashMap<>();
        Set<String> orgSet = new HashSet<>();
        Map<String, Object> map = new LinkedHashMap<>();
        if(null != sheet){
            //从第5行开始读取,第16列是部门
            for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(null != row){
                    Cell cell = row.getCell(14);
                    if (cell != null) {
                        String orgName = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(orgName)) {
                            orgSet.add(orgName);
                            map.put(i + "", orgName);
                        }
                    }
                }

            }
        }

        result.put("data", map);
        result.put("orgSet", orgSet);
        return result;
    }

    /**
     * 处理现结火车票
     *
     * @author liwenwu
     * @date 2022/5/13
     **/
    private static Map<String, Object> handleNowTrainTicket(Sheet sheet) {
        Map<String, Object> result = new HashMap<>();
        Set<String> orgSet = new HashSet<>();
        Map<String, Object> map = new LinkedHashMap<>();
        if(null != sheet){
            //从第5行开始读取,第17列是部门
            for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(row != null){
                    Cell cell = row.getCell(15);
                    if (null != cell) {
                        String orgName = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(orgName)) {
                            orgSet.add(orgName);
                            map.put(i + "", orgName);
                        }
                    }
                }

            }
        }

        result.put("data", map);
        result.put("orgSet", orgSet);
        return result;
    }

    /**
     * 处理月结用车
     *
     * @author liwenwu
     * @date 2022/5/13
     **/
    private static Map<String, Object> handleMonthCar(Sheet sheet) {
        Map<String, Object> result = new HashMap<>();
        Set<String> orgSet = new HashSet<>();
        Map<String, Object> map = new LinkedHashMap<>();
        if(null != sheet){
            //从第5行开始读取,第12列是部门
            for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                System.out.println(i);
                Row row = sheet.getRow(i);
                if(null != row){
                    Cell cell = row.getCell(10);
                    if (null != cell) {
                        String orgName = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(orgName)) {
                            orgSet.add(orgName);
                            map.put(i + "", orgName);
                        }
                    }
                }

            }
        }

        result.put("data", map);
        result.put("orgSet", orgSet);
        return result;
    }

    private static boolean check(Cell cell){
        boolean flag = false;
        if(null != cell){
            short format = cell.getCellStyle().getDataFormat();
            //根据format值，将其转换成对应的时间样式
            if (format == 14 || format == 31 || format == 57 || format == 58 || format == 180 || format == 181 || format == 177){
                flag = true;
            }
        }
        return flag;
    }
}
