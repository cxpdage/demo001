package cn.activiti.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 *  1. 部署

 *
 */
public class paralleGatewayTest {
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();  //通过activiti.cfg.xml，获取数据源
	 
	//产生了一个流程模板(ProcessDefinition)
	@Test
	public void deploymentByResource() {
		
		Deployment deployment = engine.getRepositoryService()
		      .createDeployment()
		      .name("并行网关")
		      .addClasspathResource("diagram/paralleGateway.bpmn")
		      .addClasspathResource("diagram/paralleGateway.png")
		      .deploy();
		
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	//启动流程(产生一个流程的实例: ProcessInstances)
	// 流程定义id:  helloProcess:2:104   key:版本号:随机数
	@Test
	public void startProcess() {
		String key = "paralleGateway";
		ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(key);
		
		System.out.println("流程定义id:"+pi.getProcessDefinitionId());
		System.out.println("流程实例id:"+pi.getProcessInstanceId());
	}
	
	//查看任务人的任务列表
	//一般情况 ，流程实例id和执行对象id是相同，如果一个流程实例中有分支（多个执行对象）
	@Test
	public void findTaskList() {
		String assignee = "买家";
		List<Task> taskList =engine.getTaskService()
							.createTaskQuery()
							.taskAssignee(assignee )
							.list();
		if (taskList != null && taskList.size()>0) {
			for (Task task : taskList) {
				System.out.println("任务id: "+task.getId());
				System.out.println("时间：" + task.getCreateTime());
				System.out.println("流程定义id: "+task.getProcessDefinitionId());
				System.out.println("流程实例id: "+ task.getProcessInstanceId());
				System.out.println("执行对象：" + task.getExecutionId());
			}
		}
	}
	
	// 整个流程完成后,`act_ru_execution`和`act_ru_task`没有相应task数据
	@Test
	public void taskfinish() {
		String taskId = "3502";
		engine.getTaskService().complete(taskId );
		System.out.println("当前任务完成");
	}
	
	
	
}
