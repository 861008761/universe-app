package com.mylovin.utils.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtil {
    public static Configuration getConfig() {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        return config;
    }

    public static void createTable() {
        Configuration config = HBaseUtil.getConfig();
        Connection connection = null;
        Admin admin = null;
        byte[] tableName = Constants.HBASE_TABLE_NAME_COMMENT;
        try {
            connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();
            if (!admin.isTableAvailable(TableName.valueOf(tableName))) {
                HTableDescriptor hbaseTable = new HTableDescriptor(TableName.valueOf(tableName));
                hbaseTable.addFamily(new HColumnDescriptor(Constants.HBASE_TABLE_COL_FAM));
                admin.createTable(hbaseTable);
            }else {
                System.out.println(tableName + " already exists!");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (admin != null) {
                    admin.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void put(byte[] tableName, String rowKey, String title, String star, String comment) {
        try {
            Configuration config = HBaseUtil.getConfig();
            Connection connection = ConnectionFactory.createConnection(config);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Constants.HBASE_TABLE_COL_FAM, Constants.HBASE_TABLE_COL_TITLE, Bytes.toBytes(title));
            put.addColumn(Constants.HBASE_TABLE_COL_FAM, Constants.HBASE_TABLE_COL_STAR, Bytes.toBytes(star));
            put.addColumn(Constants.HBASE_TABLE_COL_FAM, Constants.HBASE_TABLE_COL_COMMENT, Bytes.toBytes(comment));
            table.put(put);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 单条查询 Get
    public void getData(String rowKey) throws Exception {
        try {
            Configuration config = HBaseUtil.getConfig();
            Connection connection = ConnectionFactory.createConnection(config);
            Table table = connection.getTable(TableName.valueOf(Constants.HBASE_TABLE_NAME_COMMENT));
            Get get = new Get(Bytes.toBytes("1001"));
            // get.addFamily(Bytes.toBytes("info")); //指定获取某个列族
            // get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name")); //指定获取某个列族中的某个列
            Result result = table.get(get);
            System.out.println(result.getExists());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
