package cn.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class CreateDatabaseTest {

	public static void main(String[] args) {
		//方法1： 纯jdbc方式
//		ProcessEngineConfiguration config = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//		
//		config.setJdbcDriver("com.mysql.jdbc.Driver");
//		config.setJdbcUrl("jdbc:mysql://localhost:3306/activitidb02?useUnicode=true&characterEncoding=UTF-8");
//		config.setJdbcUsername("agentsystem");
//		config.setJdbcPassword("888");
//		
//		config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//		
//		ProcessEngine engine = config.buildProcessEngine();
//		
//		System.out.println("创建成功:" + engine);
		
		//方法2. spring方式 , 首先创建activiti.cfg.xml
		ProcessEngine engine = ProcessEngineConfiguration
								.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
								.buildProcessEngine();
		System.out.println("创建成功:");
	}
}
