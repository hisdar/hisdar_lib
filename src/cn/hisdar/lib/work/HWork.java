package cn.hisdar.lib.work;

import javax.swing.Icon;

import cn.hisdar.lib.adapter.StringAdapter;
import cn.hisdar.lib.log.HLog;

public class HWork implements HWorkActionListener {

	private HProgressDialog progressDialog;
	private HWorkActionListener workActionListener;
	
	private HTask task;
	private String title;
	private HTaskFinishInterface taskFinishInterface;
	private TaskResult taskResult = TaskResult.TASK_SUCCESS;
	
	public HWork() {
		initHWork(null);
	}
	
	public HWork(HTask task) {
		initHWork(task);
	}
	
	private void initHWork(HTask task) {
		this.title = null;
		this.task = task;
		progressDialog = new HProgressDialog();
	}
	
	/**
	 * @description start work </br>
	 * @return if success, return TaskResult.TASK_SUCCESS,</br>
	 *         if fail, return TaskResult.TASK_FAIL</br>
	 *         if user pressed the cancel button, return TaskResult.TASK_CANCLE</br>
	 */
	public TaskResult startWork() {
		return startWork(task);
	}
	
	/**
	 * @description start work</br>
	 * @return if success, return TaskResult.TASK_SUCCESS,</br>
	 *         if fail, return TaskResult.TASK_FAIL</br>
	 *         if user pressed the cancel button, return TaskResult.TASK_CANCLE</br>
	 */
	public TaskResult startWork(HTask task) {
		this.task = task;
		
		TaskRunnable taskRunnable = new TaskRunnable(this);
		Thread taskThread = new Thread(taskRunnable);
		if (title != null) {
			taskThread.setName(title);
		}
		
		taskThread.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		
		try {
			progressDialog.setVisible(true);
		} catch (Exception e) {
			HLog.el(e);
		}
		
		return taskResult;
	}
	
	public void setProgressDialogLocation(int x, int y) {
		progressDialog.setLocation(x, y);
	}
	
	public void setTitle(String title) {
		this.title = title;
		progressDialog.setTitle(title);
	}
	
	public void setLogo(Icon logoIcon) {
		progressDialog.setLogo(logoIcon);
	}
	
	public void setProgressDialogModal(boolean modal) {
		progressDialog.setModal(modal);
	}
	
	public void setProgressIndeterminate(boolean indeterminate) {
		progressDialog.setProgressIndeterminate(indeterminate);
	}
	
	public void setProgressValue(float progress) throws NumberOutOfRangeException {
		progressDialog.setProgressValue(progress);
	}
	
	public void setMessage(String message) {
		progressDialog.setMessage(message);
	}
	
	private class TaskRunnable implements Runnable {

		private HWork currentWork;
		
		public TaskRunnable(HWork work) {
			currentWork = work;
		}
		
		public void run() {
			int functionResult = 0;
			if (task != null) {
				try {
					functionResult = task.task(currentWork);
				} catch (Exception e) {
					HLog.el("HWork.run: task run fail:" + e.getMessage());
					HLog.el("HWork.run: task run fail:\n" + StringAdapter.toString(e.getStackTrace()));
					taskResult = TaskResult.TASK_FAIL;
				}
				
				if (taskResult != TaskResult.TASK_CANCLE && taskResult != TaskResult.TASK_FAIL) {
					taskResult = TaskResult.TASK_SUCCESS;
				}
				
				if (taskFinishInterface != null) {
					taskFinishInterface.taskFinishEvent(task, functionResult);
				}
			}
			
			progressDialog.setModal(false);
			progressDialog.setVisible(false);
			progressDialog.dispose();
			progressDialog = new HProgressDialog();
			HLog.il("set progress dialog visible false");
		}
	}

	public HTaskFinishInterface getTaskFinishInterface() {
		return taskFinishInterface;
	}

	public void setTaskFinishInterface(HTaskFinishInterface taskFinishInterface) {
		this.taskFinishInterface = taskFinishInterface;
	}
	
	public void setHWorkActionListener(HWorkActionListener workActionListener) {
		this.workActionListener = workActionListener;
		if (workActionListener != null) {
			progressDialog.setHWorkActionListener(this);
		}
	}

	@Override
	public boolean cancelTaskEvent() {
		boolean workAction = workActionListener.cancelTaskEvent();
		if (workAction) {
			taskResult = TaskResult.TASK_CANCLE;
		}
		
		return workAction;
	}
}
