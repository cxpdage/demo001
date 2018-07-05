package cn.activiti.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * 对流程定义的操作：getRepositoryService()
 * @author Administrator
 *
 */
public class ProcessDefinitionTest {

	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();  //通过activiti.cfg.xml，获取数据源
		 
	//产生了一个流程模板(ProcessDefinition)
	@Test
	public void deploymentByZipInputstream() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagram/helloProcess.zip");
		ZipInputStream zip = new ZipInputStream(inputStream );
		Deployment deployment = engine.getRepositoryService()
		      .createDeployment()
		      .name("hello流程入门程序")
		      .addZipInputStream(zip)
		      .deploy();
		
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	//启动流程(产生一个流程的实例: ProcessInstances)
	// 流程定义id:  helloProcess:2:104   key:版本号:随机数
	@Test
	public void startProcess() {
		String key = "helloProcess";
		ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(key);
		
		System.out.println("流程定义id:"+pi.getProcessDefinitionId());
		System.out.println("流程实例id:"+pi.getProcessInstanceId());
	}
	
	// 整个流程完成后,`act_ru_execution`和`act_ru_task`没有相应task数据
	@Test
	public void taskfinish() {
		String taskId = "802";
		engine.getTaskService().complete(taskId );
		System.out.println("当前任务完成");
	}
	
	//查看流程定义信息
	@Test
	public void findProcessDefinitions() {
		List<ProcessDefinition> lsit = engine.getRepositoryService()
								.createProcessDefinitionQuery()
								.orderByProcessDefinitionName().desc()
								.list();
		for (ProcessDefinition pd : lsit) {
			System.out.println(pd.getId());
			System.out.println(pd.getKey());
			System.out.println(pd.getVersion());
			System.out.println(pd.getResourceName());
			System.out.println(pd.getDiagramResourceName());
			System.out.println("---------------------------------");
		}
	}
	
	//删除某个流程定义
	@Test
	public void deleteProcessDefinition() {
		String id = "1";
		//不带true,流程启动时，会抛异常
		engine.getRepositoryService().deleteDeployment(id);
		//带true, 流程启动时，出会删除(强行删除 )
		//engine.getRepositoryService().deleteDeployment(id,true);
		System.out.println("删除成功");
	}
	
	//查看流程定义图片
	@Test
	public void viewProcessImage() throws IOException{
		String deploymentId = "1001";
		String resourceName = "";
		List<String> list = engine.getRepositoryService()
								  .getDeploymentResourceNames(deploymentId);
		// xxxx.bpmn   xxx.png
		for (String resource : list) {
			if (resource.indexOf(".png") != -1) {
				resourceName = resource;
				break;
			}
		}
		
		InputStream inputStream = engine.getRepositoryService()
									    .getResourceAsStream(deploymentId, resourceName);
		
		
		File file = new File("d:\\" + resourceName);
		FileUtils.copyInputStreamToFile(inputStream, file );
	}
	
	
}
