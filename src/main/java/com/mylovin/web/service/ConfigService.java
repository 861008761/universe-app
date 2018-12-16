package com.mylovin.web.service;

import com.alibaba.fastjson.JSON;
import com.mylovin.web.dao.ConfigDAO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("configService")
public class ConfigService {

	@Resource(name = "configDAO")
	private ConfigDAO configDAO;

	private List<Map<String, Object>> configs;

	@PostConstruct
	public void init() {

	}

	@Scheduled(cron = "0/20 * * * * ?")
	public void getConfigsSchedule() {
		System.out.println("--定时任务：获取数据库配置信息--");
		configs = configDAO.getConfigs();
	}

	public String getConfigs() {
		//configs = configDAO.getConfigs();
		return JSON.toJSONString(configs);
	}

	public String addConfig(HttpServletRequest request) {
		String cfg = request.getParameter("cfg");
		return configDAO.addConfig(cfg);
	}

	public String editConfig(HttpServletRequest request) {
		String cfg = request.getParameter("cfg");
		return configDAO.editConfig(cfg);
	}

	public String getPaginationConfigs(HttpServletRequest request) {
		//得到客户端传递的页码和每页记录数，并转换成int类型
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		//查询参数
		String dataSourceName = request.getParameter("dataSourceName");
		String netType = request.getParameter("netType");
		//当前起始页
		int offset = (pageNumber - 1) * pageSize;
		//定义map集合：
		Map<String, Object> mp = new HashMap();
		//分页：
		mp.put("offset", offset);
		mp.put("pageSize", pageSize);

		if (dataSourceName.length() > 0) {
			mp.put("dataSourceName", dataSourceName);
		}
		if (netType.length() > 0) {
			mp.put("netType", netType);
		}

		//查询数据库，指定偏移量和查询条数
		List<Map<String, Object>> pgCfgs = configDAO.getPaginationConfigs(mp);

		Map<String, Object> map = new HashMap<String, Object>();
		//从configs中临时取出并删除
		map.put("total", pgCfgs.remove(pgCfgs.size()-1).get("total"));
		//从数据库中查询得到的结果集
		map.put("rows", pgCfgs);
		return JSON.toJSONString(map);
	}

}
