package com.mylovin.web.model;

import org.springframework.stereotype.Component;

/*
create table db_meta_info(dataSourceName varchar(255), netType varchar(255), dynamicType varchar(255),
dbType varchar(40), dbIP varchar(40), dbPort varchar(40), dbName varchar(255)
);
 */
@Component("configBean")
public class Config {
	//这部分作为用户自定义配置
	private int rowID;
	private String dataSourceName;//dataSource名称
	private String netType;//网系 光缆网  同一个网系不能出现多个动态数据的类型 光缆网不能出现两个告警的信息
	private String dynamicType;//动态数据的类型 告警、故障
	private String dbType;//数据库类型oracle mysql->得到driveClassName
	private String dbIP;//数据库IP地址 1.8.67.70
	private String dbPort;//数据库端口 1521 3306
	private String dbName;//数据库名称或Sid orcl
	private String url;//数据库连接的字符串，类似jdbc:oracle:thin:@localhost:1521:orcl jdbc:mysql://127.0.0.1:3306/test
	private String username;//用户名 zc100831
	private String password;//密码 zc100831
	//这部分作为默认配置
	private String initialSize = "1";//初始化连接数
	private String minIdle = "1";//最小连接数
	private String maxActive = "10";//最大连接数
	private String maxWait = "10000";//配置获取连接等待超时的时间
	private String timeBetweenEvictionRunsMillis = "60000";//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
	private String minEvictableIdleTimeMillis = "300000";//配置一个连接在池中最小生存的时间，单位是毫秒
	private String validationQuery = "";//验证连接有效与否的SQL，不同的数据配置不同
	private String testWhileIdle = "true";//连接空闲时检测
	private String testOnBorrow = "true";//使用时检测
	private String testOnReturn = "false";//返回时检测
	private String poolPreparedStatements = "true";//打开PSCache
	private String maxPoolPreparedStatementPerConnectionSize = "20";//指定每个连接上PSCache的大小
	private String filters = "stat";//过滤器 配置servlet访问

	public int getRowID() {
		return rowID;
	}

	public void setRowID(int rowID) {
		this.rowID = rowID;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getDynamicType() {
		return dynamicType;
	}

	public void setDynamicType(String dynamicType) {
		this.dynamicType = dynamicType;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbIP() {
		return dbIP;
	}

	public void setDbIP(String dbIP) {
		this.dbIP = dbIP;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(String initialSize) {
		this.initialSize = initialSize;
	}

	public String getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

	public String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(String testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(String testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(String poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public String getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(String maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
}
