package cn.hisdar.lib.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.hisdar.lib.work.HTask;
import cn.hisdar.lib.work.HTaskFinishInterface;
import cn.hisdar.lib.work.HWork;
import cn.hisdar.lib.work.HWorkActionListener;
import cn.hisdar.lib.work.NumberOutOfRangeException;
import cn.hisdar.lib.work.TaskResult;

public class Test extends JFrame {

	public Test() {
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		JButton testButton = new JButton();
		testButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final HWork hWork = new HWork();
				
				hWork.setHWorkActionListener(new HWorkActionListener() {
					
					@Override
					public boolean cancelTaskEvent() {
						// TODO Auto-generated method stub
						return true;
					}
				});
			
				hWork.setTitle("测试任务");
				
				hWork.setTaskFinishInterface(new HTaskFinishInterface() {
					
					@Override
					public void taskFinishEvent(HTask taskResult, int functionResult) {
						// TODO Auto-generated method stub
						
					}
				});
				
				HTask task = new HTask() {
					
					@Override
					public int task(HWork work) {
						for (int i = 0; i < 10; i++) {
							try {
								Thread.sleep(1000);
								hWork.setMessage("正在执行：" + ((i + 1) * 100 / 10) + "%");
								try {
									hWork.setProgressValue((i + 1) / 10.f);
								} catch (NumberOutOfRangeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						return 0;
					}
				};
				
				hWork.startWork(task);
			}
		});
		add(testButton);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new Test();
	}
}
