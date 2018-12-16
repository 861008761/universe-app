package com.mylovin.utils.bigdata;

import org.apache.hadoop.hbase.util.Bytes;

public class Constants {
    public static final byte[] HBASE_TABLE_NAME_COMMENT = Bytes.toBytes("comments");
    public static final byte[] HBASE_TABLE_COL_FAM = Bytes.toBytes("comment");
    public static final byte[] HBASE_TABLE_COL_TITLE = Bytes.toBytes("title");
    public static final byte[] HBASE_TABLE_COL_STAR = Bytes.toBytes("star");
    public static final byte[] HBASE_TABLE_COL_COMMENT = Bytes.toBytes("comment");
}
