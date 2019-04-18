package com.bowie.notes.framework.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Bowie on 2019/4/17 16:11
 * <p>
 * 分库策略
 **/
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<String> {

    /**
     * sql == 规则
     */
    public String doEqualSharding(Collection<String> databaseNames, ShardingValue<String> shardingValue) {
        //shardingValue内容是DataSourceConfig中配置的分库的时候以哪个字段来分库，这里是UserId_*
        //userId以偶数几位的话，存test_msg1表
        for (String each : databaseNames) {
            String userId = shardingValue.getValue();
            String numer = userId.split("_")[1];
            //userId_后面可能是0，1，2，3
            //而dataSource只有俩，分别是1和2结尾的
            if (each.endsWith(((Integer.parseInt(numer) % 2) + 1) + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * sql in 规则
     */
    public Collection<String> doInSharding(Collection<String> databaseNames, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(databaseNames.size());
        for (String userId : shardingValue.getValues()) {
            String numer = userId.split("_")[1];
            for (String tableName : databaseNames) {
                if (tableName.endsWith(((Integer.parseInt(numer) % 2) + 1) + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    /**
     * sql between 规则
     */
    public Collection<String> doBetweenSharding(Collection<String> databaseNames, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(databaseNames.size());
        Range<String> range = shardingValue.getValueRange();
        String begin = range.lowerEndpoint();
        String end = range.upperEndpoint();
        Integer beginNum = Integer.parseInt(begin.split("_")[1]);
        Integer endNum = Integer.parseInt(end.split("_")[1]);
        for (int i = beginNum; i <= endNum; i++) {
            for (String each : databaseNames) {
                if (each.endsWith(((i % 2) + 1) + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}