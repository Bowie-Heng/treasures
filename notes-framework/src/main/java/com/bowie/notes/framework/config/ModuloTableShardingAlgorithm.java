package com.bowie.notes.framework.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Bowie on 2019/4/17 16:14
 * <p>
 * 分表策略
 **/
public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<String> {

    public String doEqualSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
        for (String each : tableNames) {
            String orderCode = shardingValue.getValue();
            int num = Integer.parseInt(orderCode.split("_")[1]);
            if (each.endsWith((num % 2) + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        for (String orderCode : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                int num = Integer.parseInt(orderCode.split("_")[1]);
                if (tableName.endsWith(num % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        Range<String> range = shardingValue.getValueRange();
        String start = range.lowerEndpoint();
        String end = range.upperEndpoint();
        for (int i = Integer.parseInt(start.split("_")[1]); i <= Integer.parseInt(end.split("_")[1]); i++) {
            for (String each : tableNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}