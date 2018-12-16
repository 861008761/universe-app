package com.mylovin.web.controller;

import com.mylovin.web.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/config")
public class ConfigController {
	@Resource(name = "configService")
	private ConfigService configService;

	@RequestMapping("getConfigs")
	@ResponseBody    //添加该注释之后可以直接返回json等字符串！
	public String getConfigs(HttpServletRequest request) {
		return configService.getConfigs();
	}

	//分页
	@RequestMapping("getPaginationConfigs")
	@ResponseBody    //添加该注释之后可以直接返回json等字符串！
	public String getPaginationConfigs(HttpServletRequest request) {
		return configService.getPaginationConfigs(request);
	}

	@RequestMapping("addConfig")
	@ResponseBody    //添加该注释之后可以直接返回json等字符串！
	public String addConfig(HttpServletRequest request) {
		return configService.addConfig(request);
	}

	@RequestMapping("editConfig")
	@ResponseBody    //添加该注释之后可以直接返回json等字符串！
	public String editConfig(HttpServletRequest request) {
		return configService.editConfig(request);
	}

}
