package cn.activiti.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;import org.activiti.engine.task.Task;
import org.junit.Test;

/**

 *
 */
public class AssigneeTest02 {
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();  //通过activiti.cfg.xml，获取数据源
	 
	//产生了一个流程模板(ProcessDefinition)
	@Test
	public void deploymentByResource() {
		
		Deployment deployment = engine.getRepositoryService()
		      .createDeployment()
		      .name("分配任务人(程序)")
		      .addClasspathResource("diagram/assigneeProcess02.bpmn")
		      .addClasspathResource("diagram/assigneeProcess02.png")
		      .deploy();
		
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	//启动流程(产生一个流程的实例: ProcessInstances)
	// 流程定义id:  helloProcess:2:104   key:版本号:随机数
	@Test
	public void startProcess() {
		String key = "assigneeProcess02";
		ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(key);
		System.out.println("流程定义id:"+pi.getProcessDefinitionId());
		System.out.println("流程实例id:"+pi.getProcessInstanceId());
	}
	

	// 整个流程完成后,`act_ru_execution`和`act_ru_task`没有相应task数据
	@Test
	public void taskfinish() {
		String taskId = "4304";
		engine.getTaskService().complete(taskId );
		System.out.println("当前任务完成");
	}
	
	
	
}
