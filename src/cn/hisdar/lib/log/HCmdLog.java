package cn.hisdar.lib.log;

public class HCmdLog implements HLogInterface {

	@Override
	public void info(String log) {
		System.out.print(log);
	}

	@Override
	public void error(String log) {
		System.err.print(log);
	}

	@Override
	public void debug(String log) {
		System.out.print(log);
	}
}
