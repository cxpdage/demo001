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
 *  `act_re_deployment`   流程部署表
 *  `act_re_procdef`      流程定义表 :  流程定义相当于一个流程模板
 *   act_ge_bytearray     流程资源表:  xxx.bpmn  xxx.png
 *  `act_ge_property`     id生成策略表
 *  
 *  2. 启动流程
 *  `act_ru_execution`    流程执行表
 *  `act_ru_task`         任务表
 *  
 *  3. 整个流程结束
 *  `act_hi_actinst`   表示任何节点的历史操作
 *  `act_hi_taskinst`  针对的是UserTask节点 的历史操作
 *  `act_hi_procinst`  查看流程实例表:   end_time字段
 *  
 * @author Administrator
 *
 */
public class HelloTest {
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();  //通过activiti.cfg.xml，获取数据源
	 
	//产生了一个流程模板(ProcessDefinition)
	@Test
	public void deploymentByResource() {
		
		Deployment deployment = engine.getRepositoryService()
		      .createDeployment()
		      .name("hello流程入门程序")
		      .addClasspathResource("diagram/helloProcess.bpmn")
		      .addClasspathResource("diagram/helloProcess.png")
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
	
	//查看任务人的任务列表
	//一般情况 ，流程实例id和执行对象id是相同，如果一个流程实例中有分支（多个执行对象）
	@Test
	public void findTaskList() {
		String assignee = "jack";
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
		String taskId = "802";
		engine.getTaskService().complete(taskId );
		System.out.println("当前任务完成");
	}
	
	//判断流程实例的状态：1. 进行中   2. 结束
	@Test
	public void isProcessFininshed(){
		String pid = "601";
		ProcessInstance pi = engine.getRuntimeService()
						.createProcessInstanceQuery()
						.processInstanceId(pid)
						.singleResult();
		if (pi != null) {
			System.out.println("流程正在进行中.....");
		} else {
			System.out.println("流程已经结束");
		}
	}
	
	@Test
	public void findHistoryTaskList(){
		String assignee = "jack";
		List<HistoricTaskInstance> list = engine.getHistoryService()
							//.createHistoricProcessInstanceQuery()
							.createHistoricTaskInstanceQuery()
							.taskAssignee(assignee)
							.list();
		for (HistoricTaskInstance task : list) {
			System.out.println(task.getId());
			System.out.println(task.getName());
			System.out.println(task.getProcessDefinitionId());
			System.out.println(task.getProcessInstanceId());
			System.out.println(task.getDurationInMillis());
			System.out.println(task.getEndTime());
			System.out.println("----------------------------------------------");
		}
	}
	
	
	
}
