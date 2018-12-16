package com.mylovin.web.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mylovin.web.model.Config;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("configDAO")
public class ConfigDAO {
	@Resource(name = "dataSourceDruid")
	private DruidDataSource dataSource;

	@Resource(name = "configBean")
	private Config config;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> getConfigs() {
		//jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> configs = jdbcTemplate.queryForList("select * from db_meta_info");
		return configs;
	}

	public Config getConfigFromStr(String cfgStr) {
		try {
			JSONObject jsonObj = JSON.parseObject(cfgStr);
			config.setRowID(Integer.parseInt((String) jsonObj.get("rowID")));
			config.setDataSourceName((String) jsonObj.get("dataSourceName"));
			config.setNetType((String) jsonObj.get("netType"));
			config.setDynamicType((String) jsonObj.get("dynamicType"));
			config.setDbType((String) jsonObj.get("dbType"));
			config.setDbIP((String) jsonObj.get("dbIP"));
			config.setDbPort((String) jsonObj.get("dbPort"));
			config.setDbName((String) jsonObj.get("dbName"));
			config.setUrl((String) jsonObj.get("url"));
			config.setUsername((String) jsonObj.get("username"));
			config.setPassword((String) jsonObj.get("password"));
		}catch (Exception e) {
			System.out.println("字符串转java对象失败！");
			throw e;
		}
		return config;
	}

	public String addConfig(String cfg) {
		String result = "1";
		//Config config = new Config();
		try {
			config = getConfigFromStr(cfg);
			List<Map<String, Object>> tmp = jdbcTemplate.queryForList("select * from db_meta_info where dataSourceName = ?", new Object[]{config.getDataSourceName()});
			if (tmp.size() > 0) {
				result = "-1";//表示已经数据库已经有记录
				return result;
			} else {
				int r = jdbcTemplate.update("insert into db_meta_info(dataSourceName, netType, dynamicType, dbType, dbIP, dbPort, dbName, url, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
						config.getDataSourceName(),
						config.getNetType(),
						config.getDynamicType(),
						config.getDbType(),
						config.getDbIP(),
						config.getDbPort(),
						config.getDbName(),
						config.getUrl(),
						config.getUsername(),
						config.getPassword()
				);
				result = r+"";
			}
		} catch (Exception e) {
			System.out.println("数据格式异常，退出！");
			e.printStackTrace();
			result = "-2";//配置解析成json的时候异常或访问数据库异常！
		}
		return result;
	}

	public String editConfig(String cfg) {
		String result = "1";
		try {
			config = getConfigFromStr(cfg);
			List<Map<String, Object>> tmp = jdbcTemplate.queryForList("select * from db_meta_info where dataSourceName = ? and rowID != ?", new Object[]{config.getDataSourceName(), config.getRowID()});
			if (tmp.size() > 0) {
				result = "-1";//表示已经数据库已经有相同的dataSourceName  加上rowID为了判断其他行是否存在，因为修改当前行除了dataSourceName的列一定会有一条记录
				return result;
			} else {
				int r = jdbcTemplate.update("update db_meta_info set dataSourceName = ?, netType = ?, dynamicType = ?, dbType = ?, dbIP = ?, dbPort = ?, dbName = ?, url = ?, username = ?, password = ? where rowID = ?",
						config.getDataSourceName(),
						config.getNetType(),
						config.getDynamicType(),
						config.getDbType(),
						config.getDbIP(),
						config.getDbPort(),
						config.getDbName(),
						config.getUrl(),
						config.getUsername(),
						config.getPassword(),
						config.getRowID()
				);
				result = r+"";
			}
		} catch (Exception e) {
			System.out.println("数据格式异常，退出！");
			e.printStackTrace();
			result = "-2";//配置解析成json的时候异常或访问数据库异常！
		}
		return result;
	}

	public List<Map<String, Object>> getPaginationConfigs(Map<String, Object> map) {
		StringBuilder sqlBuf = new StringBuilder();
		sqlBuf.append("select rowID, dataSourceName, netType, dynamicType, dbType, dbIP, dbPort, dbName, url, username, password from db_meta_info");
		if (map.get("dataSourceName") != null) {
			sqlBuf.append(" where dataSourceName = " + map.get("dataSourceName"));
		}
		if (map.get("netType") != null) {
			sqlBuf.append(" and netType = " + map.get("netType"));
		}
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//先统计行数
		List<Map<String, Object>> configs = jdbcTemplate.queryForList(sqlBuf.toString());
		Map<String, Object> totalRows = new HashMap<>();
		totalRows.put("total", configs.size());
		//再获取当前页数据
		sqlBuf.append(" limit " + map.get("offset") + ", " + map.get("pageSize"));
		configs = jdbcTemplate.queryForList(sqlBuf.toString());
		//把行数先加入configs中，在最终返回结果之前取出行数并从结果集configs中删除
		configs.add(totalRows);
		return configs;
	}
}
