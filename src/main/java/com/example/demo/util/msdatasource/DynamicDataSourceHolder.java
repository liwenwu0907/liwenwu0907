package com.example.demo.util.msdatasource;

/**
 * 保存本地多数据源
 * @author li wenwu
 * @date 2020/12/22 11:48
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void putDataSource(DataSourceType dataSourceType) {
        holder.set(dataSourceType.getType());
    }

    public static String getDataSouce() {
        return holder.get();
    }


    public static void clearDbType() {
        holder.remove();
    }

}
