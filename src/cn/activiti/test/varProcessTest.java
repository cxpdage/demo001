package cn.activiti.test;

import java.util.List;

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
public class varProcessTest {
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();  //通过activiti.cfg.xml，获取数据源
	 
	//产生了一个流程模板(ProcessDefinition)
	@Test
	public void deploymentByResource() {
		
		Deployment deployment = engine.getRepositoryService()
		      .createDeployment()
		      .name("流程变量")
		      .addClasspathResource("diagram/variableProcess.bpmn")
		      .addClasspathResource("diagram/variableProcess.png")
		      .deploy();
		
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	//启动流程(产生一个流程的实例: ProcessInstances)
	// 流程定义id:  helloProcess:2:104   key:版本号:随机数
	@Test
	public void startProcess() {
		String key = "varProcess";
		ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(key);
		
		System.out.println("流程定义id:"+pi.getProcessDefinitionId());
		System.out.println("流程实例id:"+pi.getProcessInstanceId());
	}
	
	
	// 整个流程完成后,`act_ru_execution`和`act_ru_task`没有相应task数据
	@Test
	public void taskfinish() {
		String taskId = "1404";
		engine.getTaskService().complete(taskId );
		System.out.println("当前任务完成");
	}
	
	
	@Test
	public void setVarTest(){
		TaskService taskService = engine.getTaskService();
		String taskId = "1602";
//		taskService.setVariableLocal(taskId, "name", "张三");
//		taskService.setVariable(taskId , "days", 10);
//		taskService.setVariable(taskId, "remark", "年假");
		taskService.setVariable(taskId, "person", new Person(1, "李四"));  //序列化
		
	}
	
	@Test
	public void getVarTest(){
		TaskService taskService = engine.getTaskService();
		String taskId = "1602";
//		String name = (String) taskService.getVariable(taskId , "name");
//		int days = (int) taskService.getVariable(taskId, "days");
//		String remark = (String) taskService.getVariable(taskId, "remark");
//		
//		System.out.println(name);
//		System.out.println(days);
//		System.out.println(remark);
		Person person = (Person) taskService.getVariable(taskId, "person");
		System.out.println(person.getId());
		System.out.println(person.getName());
	}
	
	
	
	public void getAndSetVariable(){
		//先获取服务 
		//TaskService taskService = engine.getTaskService();
		//RuntimeService runtimeService = engine.getRuntimeService();
		
		//taskService.setVariable(taskId, key, value);
		//taskService.setVariable("1401", "days", 10);
		//taskService.setVariableLocal(taskId, key, value);  必须和某个UserTask绑定,不能传递
		//taskService.setVariables(taskId, map);
		//runtimeService.setVariable(arg0, arg1, arg2);
		
		//runtimeService.startProcessInstanceByKey(key, map);  启动流程实例时，传递流程变量
		
		//taskService.complete(taskId, map);   推进UserTask时，传递流程变量
		
		//taskService.getVariable(taskId, key);
	}
	

	
	
}
