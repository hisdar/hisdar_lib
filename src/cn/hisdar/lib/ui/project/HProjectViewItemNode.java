package cn.hisdar.lib.ui.project;

import javax.swing.tree.DefaultMutableTreeNode;

public class HProjectViewItemNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 4441582540662259057L;
	private boolean isFile = true;
	private String srcFilePath = null;

	public HProjectViewItemNode() {
		super();
	}
	
	public HProjectViewItemNode(Object userObject) {
		super(userObject);
	}
	
	public HProjectViewItemNode(Object userObject, String srcFilePath) {
		super(userObject);
		this.srcFilePath = srcFilePath;
	}

	public HProjectViewItemNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}
	
	public HProjectViewItemNode(Object userObject, String srcFilePath, boolean allowsChildren) {
		super(userObject, allowsChildren);
		this.srcFilePath = srcFilePath;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public String getSrcFilePath() {
		return srcFilePath;
	}

	public void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}
	
}
