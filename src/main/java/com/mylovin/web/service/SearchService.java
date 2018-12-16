package com.mylovin.web.service;

import com.mylovin.web.model.Comment;
import com.mylovin.utils.bigdata.Constants;
import com.mylovin.utils.bigdata.ElasticSearchUtil;
import com.mylovin.utils.bigdata.HBaseUtil;
import com.mylovin.utils.file.FileRead;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class SearchService {
    public void load(int count) {
        //创建comment表
        HBaseUtil.createTable();
        //读文件
        String file = "/Users/mylovin/Documents/IntelliJ IDEA/universe-app/src/main/resources/comments.txt";
        String[] lines = FileRead.readFileByLinesWithLength(file, count);
        //写hbase和es
        for (String line :lines) {
            String rowKey = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            String[] arr = line.split("##");
            HBaseUtil.put(Constants.HBASE_TABLE_NAME_COMMENT, rowKey, arr[0], arr[1], arr[2]);
            Comment comment = new Comment(rowKey, arr[0], arr[1], arr[2]);
            ElasticSearchUtil.addIndex("comments", "comment", comment);
        }
    }

    public Map<String, Object> search(String key) {
        String index = "comments";
        String type = "comment";
        int start = 0;
        int row = 100;
        Map<String, Object> result = ElasticSearchUtil.search(key, index, type, start, row);
        return result;
    }
}
