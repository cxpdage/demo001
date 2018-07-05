package cn.activiti.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class UserTaskListener implements TaskListener {

	@Override
	public void notify(DelegateTask task) {
		System.out.println("正在分配任务人......");
		task.setAssignee("Jack");
	}

}
