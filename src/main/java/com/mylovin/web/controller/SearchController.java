package com.mylovin.web.controller;

import com.alibaba.fastjson.JSON;
import com.mylovin.web.service.SearchService;
import com.mylovin.utils.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    /**
     * 一次加载评论数为count个
     * @param count
     */
    @RequestMapping("/load")
    public void load(@RequestParam String count) {
        System.out.println("加载数据");
        if (null != count && count.matches("[0-9]+")) {
            searchService.load(Integer.parseInt(count));
        }
    }

    @RequestMapping(value = "/search", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String search(@RequestParam String keyWord) {
        Map<String, Object> result = searchService.search(keyWord);
        PageUtil page = new PageUtil();
        page.setList((ArrayList)result.get("dataList"));
        return JSON.toJSONString(page.getList());
    }

    @RequestMapping("/search2")
    @ResponseBody
    public String search2(@RequestParam String keyWord) {
        Map<String, Object> result = searchService.search(keyWord);
        return JSON.toJSONString((ArrayList)result.get("dataList"));
    }

    @RequestMapping("/detail")
    @ResponseBody
    public String detail(@RequestParam String rowKey) {
        return "";
    }
}
