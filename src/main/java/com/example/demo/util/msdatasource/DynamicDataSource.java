package com.example.demo.util.msdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author li wenwu
 * @date 2020/12/22 17:19
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        if (DynamicDataSourceHolder.getDataSouce() != null) {
            return DynamicDataSourceHolder.getDataSouce();
        }
        return DataSourceType.WRITE.getType();
    }
}
