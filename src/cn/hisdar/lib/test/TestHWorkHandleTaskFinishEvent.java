package cn.hisdar.lib.test;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.hisdar.lib.work.HTask;
import cn.hisdar.lib.work.HWork;

public class TestHWorkHandleTaskFinishEvent implements HTask {

	private HWork testWork;
	
	public TestHWorkHandleTaskFinishEvent() {
		
	}
	
	public void startTest() {
		testWork = new HWork();
		testWork.setTitle("测试任务");
		testWork.startWork(this);
	}

	@Override
	public int task(HWork work) {
		for (int i = 0; i < 10; i++) {
			System.out.println("Step:" + (i + 1));
			float progress = (i + 1) / 10f;
			testWork.setMessage("测试任务：" + progress * 100 + "%");
			testWork.setProgressValue(progress);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new TestHWorkHandleTaskFinishEvent().startTest();
	}
}
