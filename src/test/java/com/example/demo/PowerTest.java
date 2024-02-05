package com.example.demo;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author sun
 * @date 2021/2/2 16:51
 */
public class PowerTest {
    public static void main(String[] args) {
        LocalDateTime nowTime = LocalDateTime.now();
        int hour = nowTime.getHour();
        System.out.println(hour);


        String s = "AQGNe3uSAYQVAhcLBSoxMjEwMTA1MjU1MDAwMQAAAAAAAAAAAAAAAAAAABEAAAAAAAAAAAAAAAAAAAAAAAAAAAAASMMAAEjDAABIwwAASMMAAAAAAAAAAAAAAAAAAAAAAAAAAMP1R0IAAEhDmplnQwAAAAAAAAAAZmaaQjOzW0PLkMhDAAAAAAAAAADctQVDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABIQwrXIz8AAAAAAAAAAA10Wj6kcD0/AAAAAAAAAAAAAAAAKVwPPgAAAAAAAAAAKVwPPgrXo7wAAAAAAAAAAArXozwpXA8+AAAAAAAAAAApXA8+AACAPwAAAAAAAAAAAACAPwrXIz0AAAAAAAAAAArXozyPwnU9zcxsQAAAAAAAAAAAAAAAAAAAAAAAAAAACtejPAAAAAAAAAAAAAAAAArXozwK1yM9AAAAAArXIz0AAAAAAAAAAAAAAAAAAAAAAAAAAArXozwCFwo6CtejPAIXCjoAAAAAAhYAAFK43j8BDxMn9GB9fQ==";
        //base64解码成byte
        byte[] bytes = Base64.getDecoder().decode(s);
        //byte编码成base64
        System.out.println(Base64.getEncoder().encodeToString(bytes));
        //byte转string
        String str = bytesToHex(bytes);
        System.out.println(str);
        //string转数组
        String[] arrs = new String[str.length() / 2];
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0) {
                arrs[j] = str.charAt(i) + "";
            } else {
                arrs[j] = arrs[j] + str.charAt(i);
                j++;
            }
        }
        System.out.println(arrs);
        System.out.println(Arrays.toString(arrs));
//        System.out.println("list=" + Arrays.asList(arrs));

        System.out.println(arrs.length);
        //copy消息体内容
        String[] arrs2 = new String[arrs.length - 12];
        System.arraycopy(arrs, 8, arrs2, 0, arrs.length - 12);
        System.out.println(Arrays.toString(arrs2));

        //16进制转10进制
        String yyyy = "20" + hexToDec(arrs2[0]);
        String mm = hexToDec(arrs2[1]);
        String dd = hexToDec(arrs2[2]);
        String hh = hexToDec(arrs2[3]);
        String mi = hexToDec(arrs2[4]);
        String ss = hexToDec(arrs2[5]);
        System.out.println("采集时间="+yyyy + "-" + mm + "-" + dd + " " + hh + ":" + mi + ":" + ss);

        //序列号（14个字节） 取6~19
        String[] serino = Arrays.copyOfRange(arrs2, 6, 20);
        System.out.println("序列号=" + String.join("", serino));
        System.out.println(convertHexToString(String.join("", serino)));

        //预留（14个字节） 33
        System.out.println("预留字段");

        //当前信号值
        System.out.println("当前信号值="+hexToDec(arrs2[34] + arrs2[35]));
        //电压相序标志位 Bit0:1 异常；0正常
        System.out.println("电压相序标志位="+hexStringToByte(arrs2[36] + arrs2[37]));
        //漏电温度接线故障标志位
        //Bit0：漏电通道状态位；
        //Bit1-Bit4：温度通道1-4
        //状态位；   1：异常  ；  0：正常
        System.out.println("漏电温度接线故障标志位="+hexStringToByte(arrs2[38] + arrs2[39]));
        //漏电温度报警标志位
        //Bit0：漏电通道；
        //Bit1-Bit4：温度通道1-4；
        //1：报警  ；  0：正常
        System.out.println("漏电温度报警标志位="+hexStringToByte(arrs2[40] + arrs2[41]));
        //预留
        System.out.println("预留");
        //电压状态标志位
        //Bit0：过压状态位；
        //Bit8：欠压状态位；
        //1：报警  ；  0：正常
        System.out.println("电压状态标志位="+hexStringToByte(arrs2[44] + arrs2[45]));
        //电流状态标志位
        //Bit0：过流状态位；
        //1：报警  ；  0：正常
        System.out.println("电流状态标志位="+hexStringToByte(arrs2[46] + arrs2[47]));
        //开关量输入状态
        //Bit0-Bit1:表示DI1~DI2状态；
        //1：DI闭合；0：DI打开
        System.out.println("开关量输入状态="+hexStringToByte(arrs2[48] + arrs2[49]));
        //开关量输出状态
        //Bit0-Bit1:表示DO1~DO2状态；
        //1：DO闭合；0：DO打开
        System.out.println("开关量输出状态"+hexStringToByte(arrs2[50] + arrs2[51]));

        //float类型，需高低位转换
        //剩余电流测量值   单位为mA
        System.out.println("剩余电流测量值="+hexToFloat(arrs2[52] + arrs2[53] + arrs2[54] + arrs2[55]));
        //温度1测量值    单位为℃
        System.out.println("温度1测量值="+hexToFloat(arrs2[56] + arrs2[57] + arrs2[58] + arrs2[59]));
        //温度2测量值    单位为℃
        System.out.println("温度2测量值="+hexToFloat(arrs2[60] + arrs2[61] + arrs2[62] + arrs2[63]));
        //温度3测量值    单位为℃
        System.out.println("温度3测量值="+hexToFloat(arrs2[64] + arrs2[65] + arrs2[66] + arrs2[67]));
        //温度4测量值    单位为℃
        System.out.println("温度4测量值="+hexToFloat(arrs2[68] + arrs2[69] + arrs2[70] + arrs2[71]));
        //剩余电流报警时测量值    单位为mA
        System.out.println("剩余电流报警时测量值="+hexToFloat(arrs2[72] + arrs2[73] + arrs2[74] + arrs2[75]));
        //温度1报警时测量值 单位为℃（保留小数点后1位）
        System.out.println("温度1报警时测量值="+hexToFloat(arrs2[76] + arrs2[77] + arrs2[78] + arrs2[79]));
        //温度2报警时测量值 单位为℃（保留小数点后1位）
        System.out.println("温度2报警时测量值="+hexToFloat(arrs2[80] + arrs2[81] + arrs2[82] + arrs2[83]));
        //温度3报警时测量值 单位为℃（保留小数点后1位）
        System.out.println("温度3报警时测量值="+hexToFloat(arrs2[84] + arrs2[85] + arrs2[86] + arrs2[87]));
        //温度4报警时测量值 单位为℃（保留小数点后1位）
        System.out.println("温度4报警时测量值="+hexToFloat(arrs2[88] + arrs2[89] + arrs2[90] + arrs2[91]));
        //频率    单位为Hz
        System.out.println("频率="+hexToFloat(arrs2[92] + arrs2[93] + arrs2[94] + arrs2[95]));
        //电压不平衡度    单位为%
        System.out.println("电压不平衡度="+hexToFloat(arrs2[96] + arrs2[97] + arrs2[98] + arrs2[99]));
        //A相相电压 单位为V
        System.out.println("A相相电压="+hexToFloat(arrs2[100] + arrs2[101] + arrs2[102] + arrs2[103]));
        //B相相电压 单位为V
        System.out.println("B相相电压="+hexToFloat(arrs2[104] + arrs2[105] + arrs2[106] + arrs2[107]));
        //C相相电压 单位为V
        System.out.println("C相相电压="+hexToFloat(arrs2[108] + arrs2[109] + arrs2[110] + arrs2[111]));
        //相电压平均值    单位为V
        System.out.println("相电压平均值="+hexToFloat(arrs2[112] + arrs2[113] + arrs2[114] + arrs2[115]));
        //零序电压  单位为V
        System.out.println("零序电压="+hexToFloat(arrs2[116] + arrs2[117] + arrs2[118] + arrs2[119]));
        //AB相线电压    单位为V
        System.out.println("AB相线电压="+hexToFloat(arrs2[120] + arrs2[121] + arrs2[122] + arrs2[123]));
        //BC相线电压    单位为V
        System.out.println("BC相线电压="+hexToFloat(arrs2[124] + arrs2[125] + arrs2[126] + arrs2[127]));
        //CA相线电压    单位为V
        System.out.println("CA相线电压="+hexToFloat(arrs2[128] + arrs2[129] + arrs2[130] + arrs2[131]));
        //线电压平均值    单位为V
        System.out.println("线电压平均值="+hexToFloat(arrs2[132] + arrs2[133] + arrs2[134] + arrs2[135]));
        //A相过压时报警测量值    单位为V
        System.out.println("A相过压时报警测量值="+hexToFloat(arrs2[136] + arrs2[137] + arrs2[138] + arrs2[139]));
        //B相过压时报警测量值    单位为V
        System.out.println("B相过压时报警测量值="+hexToFloat(arrs2[140] + arrs2[141] + arrs2[142] + arrs2[143]));
        //C相过压时报警测量值    单位为V
        System.out.println("C相过压时报警测量值="+hexToFloat(arrs2[144] + arrs2[145] + arrs2[146] + arrs2[147]));
        //A相欠压时报警测量值    单位为V
        System.out.println("A相欠压时报警测量值="+hexToFloat(arrs2[148] + arrs2[149] + arrs2[150] + arrs2[151]));
        //B相欠压时报警测量值    单位为V
        System.out.println("B相欠压时报警测量值="+hexToFloat(arrs2[152] + arrs2[153] + arrs2[154] + arrs2[155]));
        //C相欠压时报警测量值    单位为V
        System.out.println("C相欠压时报警测量值="+hexToFloat(arrs2[156] + arrs2[157] + arrs2[158] + arrs2[159]));
        //电流不平衡度    单位为%
        System.out.println("电流不平衡度="+hexToFloat(arrs2[160] + arrs2[161] + arrs2[162] + arrs2[163]));
        //A相电流测量值   单位为A
        System.out.println("A相电流测量值="+hexToFloat(arrs2[164] + arrs2[165] + arrs2[166] + arrs2[167]));
        //B相电流测量值   单位为A
        System.out.println("B相电流测量值="+hexToFloat(arrs2[168] + arrs2[169] + arrs2[170] + arrs2[171]));
        //C相电流测量值   单位为A
        System.out.println("C相电流测量值="+hexToFloat(arrs2[172] + arrs2[173] + arrs2[174] + arrs2[175]));
        //电流平均值 单位为A
        System.out.println("电流平均值="+hexToFloat(arrs2[176] + arrs2[177] + arrs2[178] + arrs2[179]));
        //零序电流  单位为A
        System.out.println("零序电流="+hexToFloat(arrs2[180] + arrs2[181] + arrs2[182] + arrs2[183]));
        //A相过流时报警测量值    单位为A
        System.out.println("A相过流时报警测量值="+hexToFloat(arrs2[184] + arrs2[185] + arrs2[186] + arrs2[187]));
        //B相过流时报警测量值    单位为A
        System.out.println("B相过流时报警测量值="+hexToFloat(arrs2[188] + arrs2[189] + arrs2[190] + arrs2[191]));
        //C相过流时报警测量值    单位为A
        System.out.println("C相过流时报警测量值="+hexToFloat(arrs2[192] + arrs2[193] + arrs2[194] + arrs2[195]));
        //A相有功功率    单位为kW
        System.out.println("A相有功功率="+hexToFloat(arrs2[196] + arrs2[197] + arrs2[198] + arrs2[199]));
        //B相有功功率    单位为kW
        System.out.println("B相有功功率="+hexToFloat(arrs2[200] + arrs2[201] + arrs2[202] + arrs2[203]));
        //C相有功功率    单位为kW
        System.out.println("C相有功功率="+hexToFloat(arrs2[204] + arrs2[205] + arrs2[206] + arrs2[207]));
        //总有功功率     单位为kW
        System.out.println("总有功功率="+hexToFloat(arrs2[208] + arrs2[209] + arrs2[210] + arrs2[211]));
        //A相无功功率    单位为kvar
        System.out.println("A相无功功率="+hexToFloat(arrs2[212] + arrs2[213] + arrs2[214] + arrs2[215]));
        //B相无功功率    单位为kvar
        System.out.println("B相无功功率="+hexToFloat(arrs2[216] + arrs2[217] + arrs2[218] + arrs2[219]));
        //C相无功功率    单位为kvar
        System.out.println("C相无功功率="+hexToFloat(arrs2[220] + arrs2[221] + arrs2[222] + arrs2[223]));
        //总无功功率     单位为kvar
        System.out.println("总无功功率="+hexToFloat(arrs2[224] + arrs2[225] + arrs2[226] + arrs2[227]));
        //A相视在功率    单位为kvar
        System.out.println("A相视在功率="+hexToFloat(arrs2[228] + arrs2[229] + arrs2[230] + arrs2[231]));
        //B相视在功率    单位为kvar
        System.out.println("B相视在功率="+hexToFloat(arrs2[232] + arrs2[233] + arrs2[234] + arrs2[235]));
        //C相视在功率    单位为kvar
        System.out.println("C相视在功率="+hexToFloat(arrs2[236] + arrs2[237] + arrs2[238] + arrs2[239]));
        //总视在功率     单位为kvar
        System.out.println("总视在功率="+hexToFloat(arrs2[240] + arrs2[241] + arrs2[242] + arrs2[243]));
        //A相功率因数
        System.out.println("A相功率因数="+hexToFloat(arrs2[244] + arrs2[245] + arrs2[246] + arrs2[247]));
        //B相功率因数
        System.out.println("B相功率因数="+hexToFloat(arrs2[248] + arrs2[249] + arrs2[250] + arrs2[251]));
        //C相功率因数
        System.out.println("C相功率因数="+hexToFloat(arrs2[252] + arrs2[253] + arrs2[254] + arrs2[255]));
        //总功率因数
        System.out.println("总功率因数="+hexToFloat(arrs2[256] + arrs2[257] + arrs2[258] + arrs2[259]));
        //EPI测量值    输入有功电能，单位为kWh
        System.out.println("EPI测量值="+hexToFloat(arrs2[260] + arrs2[261] + arrs2[262] + arrs2[263]));
        //EPE测量值    输出有功电能，单位为kWh
        System.out.println("EPE测量值="+hexToFloat(arrs2[264] + arrs2[265] + arrs2[266] + arrs2[267]));
        //EQL测量值    输入无功电能，单位为kvarh
        System.out.println("EQL测量值="+hexToFloat(arrs2[268] + arrs2[269] + arrs2[270] + arrs2[271]));
        //EQC测量值    输出无功电能，单位为kvarh
        System.out.println("EQC测量值="+hexToFloat(arrs2[272] + arrs2[273] + arrs2[274] + arrs2[275]));
        //ES测量值     视在电能，单位为kVAh
        System.out.println("ES测量值="+hexToFloat(arrs2[276] + arrs2[277] + arrs2[278] + arrs2[279]));
        //A相电压总谐波含量 单位%
        System.out.println("A相电压总谐波含量="+hexToFloat(arrs2[280] + arrs2[281] + arrs2[282] + arrs2[283]));
        //B相电压总谐波含量 单位%
        System.out.println("B相电压总谐波含量="+hexToFloat(arrs2[284] + arrs2[285] + arrs2[286] + arrs2[287]));
        //C相电压总谐波含量 单位%
        System.out.println("C相电压总谐波含量="+hexToFloat(arrs2[288] + arrs2[289] + arrs2[290] + arrs2[291]));
        //A相电流总谐波含量 单位%
        System.out.println("A相电流总谐波含量="+hexToFloat(arrs2[292] + arrs2[293] + arrs2[294] + arrs2[295]));
        //B相电流总谐波含量 单位%
        System.out.println("B相电流总谐波含量="+hexToFloat(arrs2[296] + arrs2[297] + arrs2[298] + arrs2[299]));
        //C相电流总谐波含量 单位%
        System.out.println("C相电流总谐波含量="+hexToFloat(arrs2[300] + arrs2[301] + arrs2[302] + arrs2[303]));
        //EPI实时需量   单位为kW
        System.out.println("EPI实时需量="+hexToFloat(arrs2[304] + arrs2[305] + arrs2[306] + arrs2[307]));
        //EPE实时需量   单位为kW
        System.out.println("EPE实时需量="+hexToFloat(arrs2[308] + arrs2[309] + arrs2[310] + arrs2[311]));
        //EQL实时需量   单位为kvar
        System.out.println("EQL实时需量="+hexToFloat(arrs2[312] + arrs2[313] + arrs2[314] + arrs2[315]));
        //EQC实时需量   单位为kvar
        System.out.println("EQC实时需量="+hexToFloat(arrs2[316] + arrs2[317] + arrs2[318] + arrs2[319]));
        //ES实时需量    单位为kVA
        System.out.println("ES实时需量="+hexToFloat(arrs2[320] + arrs2[321] + arrs2[322] + arrs2[323]));
        //当日电量EPI   单位为kWh
        System.out.println("当日电量EPI="+hexToFloat(arrs2[324] + arrs2[325] + arrs2[326] + arrs2[327]));
        //当日电量EPE   单位为kWh
        System.out.println("当日电量EPE="+hexToFloat(arrs2[328] + arrs2[329] + arrs2[330] + arrs2[331]));
        //当月电量EPI   单位为kWh
        System.out.println("当月电量EPI="+hexToFloat(arrs2[332] + arrs2[333] + arrs2[334] + arrs2[335]));
        //当月电量EPE   单位为kWh
        System.out.println("当月电量EPE="+hexToFloat(arrs2[336] + arrs2[337] + arrs2[338] + arrs2[339]));
        //上日电量EPI   单位为kWh
        System.out.println("上日电量EPI="+hexToFloat(arrs2[340] + arrs2[341] + arrs2[342] + arrs2[343]));
        //上日电量EPE   单位为kWh
        System.out.println("上日电量EPE="+hexToFloat(arrs2[344] + arrs2[345] + arrs2[346] + arrs2[347]));
        //上月电量EPI   单位为kWh
        System.out.println("上月电量EPI="+hexToFloat(arrs2[348] + arrs2[349] + arrs2[350] + arrs2[351]));
        //上月电量EPE   单位为kWh
        System.out.println("上月电量EPE="+hexToFloat(arrs2[352] + arrs2[353] + arrs2[354] + arrs2[355]));
        //当日最大需量    单位为kW
        System.out.println("当日最大需量="+hexToFloat(arrs2[356] + arrs2[357] + arrs2[358] + arrs2[359]));
        //月
        System.out.println(hexToDec(arrs2[360]));
        //日
        System.out.println(hexToDec(arrs2[361]));
        //时
        System.out.println(hexToDec(arrs2[362]));
        //分
        System.out.println(hexToDec(arrs2[363]));
        System.out.println(hexToDec(arrs2[360]) + "-" + hexToDec(arrs2[361]) + " " + hexToDec(arrs2[362]) + ":" + hexToDec(arrs2[363]));
        //当月最大需量    单位为kW
        System.out.println("当月最大需量="+hexToFloat(arrs2[364] + arrs2[365] + arrs2[366] + arrs2[367]));
        //月
        System.out.println(hexToDec(arrs2[368]));
        //日
        System.out.println(hexToDec(arrs2[369]));
        //时
        System.out.println(hexToDec(arrs2[370]));
        //分
        System.out.println(hexToDec(arrs2[371]));
        System.out.println(hexToDec(arrs2[368]) + "-" + hexToDec(arrs2[369]) + " " + hexToDec(arrs2[370]) + ":" + hexToDec(arrs2[371]));
        //上日最大需量    单位为kW
        System.out.println("上日最大需量="+hexToFloat(arrs2[372] + arrs2[373] + arrs2[374] + arrs2[375]));
        //月
        System.out.println(hexToDec(arrs2[376]));
        //日
        System.out.println(hexToDec(arrs2[377]));
        //时
        System.out.println(hexToDec(arrs2[378]));
        //分
        System.out.println(hexToDec(arrs2[379]));
        System.out.println(hexToDec(arrs2[376]) + "-" + hexToDec(arrs2[377]) + " " + hexToDec(arrs2[378]) + ":" + hexToDec(arrs2[379]));
        //上月最大需量    单位为kW
        System.out.println("上月最大需量="+hexToFloat(arrs2[380] + arrs2[381] + arrs2[382] + arrs2[383]));
        //月
        System.out.println(hexToDec(arrs2[384]));
        //日
        System.out.println(hexToDec(arrs2[385]));
        //时
        System.out.println(hexToDec(arrs2[386]));
        //分
        System.out.println(hexToDec(arrs2[387]));
        System.out.println(hexToDec(arrs2[384]) + "-" + hexToDec(arrs2[385]) + " " + hexToDec(arrs2[386]) + ":" + hexToDec(arrs2[387]));
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * ASCII码转换为16进制
     *
     * @param str
     * @return
     */
    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

    /**
     * 16进制转换为ASCII
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }

    /**
     * hex转float
     *
     * @param hex
     * @return
     */
    public static float hexToFloat(String hex) {
        return Float.intBitsToFloat(new BigInteger(reverseHex(hex), 16).intValue());
    }

    /**
     * 16进制转10进制
     *
     * @param s
     * @return
     */
    public static String hexToDec(String s) {
        return String.valueOf(Integer.parseInt(s, 16));
    }

    /**
     * 十六进制字符串高低位转换
     *
     * @param hex
     * @return
     */
    public static String reverseHex(String hex) {
        char[] charArray = hex.toCharArray();
        int length = charArray.length;
        int times = length / 2;
        for (int c1i = 0; c1i < times; c1i += 2) {
            int c2i = c1i + 1;
            char c1 = charArray[c1i];
            char c2 = charArray[c2i];
            int c3i = length - c1i - 2;
            int c4i = length - c1i - 1;
            charArray[c1i] = charArray[c3i];
            charArray[c2i] = charArray[c4i];
            charArray[c3i] = c1;
            charArray[c4i] = c2;
        }
        return new String(charArray);
    }

    /**
     * 16进制转2进制
     *
     * @param hex
     * @return
     */
    public static String hexStringToByte(String hex) {
        int i = Integer.parseInt(hex, 16);
        String str2 = Integer.toBinaryString(i);
        return str2;
    }


}


