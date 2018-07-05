package cn.activiti.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;import org.activiti.engine.task.Task;
import org.junit.Test;

import cn.activiti.bean.Person;

/**

   `act_hi_varinst`  历史表
   `act_ru_variable` 运行时
 *  
 *
 */
public class sequenceProcessTest {
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();  //通过activiti.cfg.xml，获取数据源
	 
	//产生了一个流程模板(ProcessDefinition)
	@Test
	public void deploymentByResource() {
		
		Deployment deployment = engine.getRepositoryService()
		      .createDeployment()
		      .name("连线")
		      .addClasspathResource("diagram/sequenceFlow.bpmn")
		      .addClasspathResource("diagram/sequenceFlow.png")
		      .deploy();
		
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	//启动流程(产生一个流程的实例: ProcessInstances)
	// 流程定义id:  helloProcess:2:104   key:版本号:随机数
	@Test
	public void startProcess() {
		String key = "sequenceProcess";
		ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(key);
		
		System.out.println("流程定义id:"+pi.getProcessDefinitionId());
		System.out.println("流程实例id:"+pi.getProcessInstanceId());
	}
	
	
	// 整个流程完成后,`act_ru_execution`和`act_ru_task`没有相应task数据
	@Test
	public void taskfinish() {
		String taskId = "2203";
		//${message=='不重要'}
		//Map<String, Object> varMap = new HashMap<>();
		//varMap.put("message", "重要");
		//engine.getTaskService().complete(taskId, varMap);
		engine.getTaskService().complete(taskId);
		System.out.println("当前任务完成");
	}
	
	


	
	
}
