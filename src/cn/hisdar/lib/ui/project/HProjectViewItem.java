package cn.hisdar.lib.ui.project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import cn.hisdar.lib.log.HLog;

public class HProjectViewItem extends JTree {

	private static final long serialVersionUID = 4893989320457109464L;
	
	private HProjectViewItemNode rootNode;
	private ProjectTreeCellRenderer treeCellRenderer;
	private DefaultTreeModel treeModel;
	private String srcFolderPath = null;
	
	public HProjectViewItem(String title) {
		super();
		initProjectView(title);
	}
	
	private void initProjectView(String viewTitle) {
		rootNode = new HProjectViewItemNode(viewTitle);
		
		treeModel = new DefaultTreeModel(rootNode);
		setModel(treeModel);
		treeCellRenderer = new ProjectTreeCellRenderer();
		setCellRenderer(treeCellRenderer);
		setOpaque(false);
		//setRowHeight(getRowHeight() + 2);
		setFont(new Font("微软雅黑", Font.PLAIN, 12));
	}
	
	public HProjectViewItemNode getRootNode() {
		return rootNode;
	}
	
	public void addNode(HProjectViewItemNode projectViewNode) {
		rootNode.add(projectViewNode);
	}

	public void removeNode(HProjectViewItemNode projectViewNode) {
		treeModel.removeNodeFromParent(projectViewNode);
	}
	
	public boolean isSelected() {
		ArrayList<HProjectViewItemNode> selectedNodes = getSelectedNode(rootNode);
		if (selectedNodes.size() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public HProjectViewItemNode[] getSelectedNodes() {
		ArrayList<HProjectViewItemNode> selectedNodes = getSelectedNode(rootNode);
		if (selectedNodes.size() <= 0) {
			return null;
		}
		
		// 如果是根节点被选中，根节点不作为被选中的节点
		if (selectedNodes.size() == 1 && selectedNodes.get(0) == rootNode) {
			return null;
		}
		
		HProjectViewItemNode[] selectedProjectViewNodes = new HProjectViewItemNode[selectedNodes.size()];
		for (int i = 0; i < selectedProjectViewNodes.length; i++) {
			selectedProjectViewNodes[i] = selectedNodes.get(i);
		}
		
		return selectedProjectViewNodes;
	}
	
	private ArrayList<HProjectViewItemNode> getSelectedNode(HProjectViewItemNode projectViewNode) {
		ArrayList<HProjectViewItemNode> selectedNodes = new ArrayList<>();
		
		//System.out.println("Check:" + new TreePath(projectViewNode.getPath()));
		//System.out.println("ChildCount:" + projectViewNode.getChildCount());
		// 如果当前几点有子节点，先判断子节点是否被选中
		if (projectViewNode.getChildCount() > 0) {
			for (int i = 0; i < projectViewNode.getChildCount(); i++) {
				selectedNodes.addAll(getSelectedNode((HProjectViewItemNode)projectViewNode.getChildAt(i)));
			}
		}
		
		// 如果当前节点的子节点有被选中，那么不管当前节点有没有被选中，都不再做判断
		if (selectedNodes.size() <= 0) {
			TreePath currentNodePath = new TreePath(projectViewNode.getPath());
			if (isPathSelected(currentNodePath)) {
				//System.err.println("selected node:" + currentNodePath);
				selectedNodes.add(projectViewNode);
			} else {
				//System.out.println("not selected node:" + currentNodePath);
			}
		}
		
		return selectedNodes;
	}
	
	public void loadProjectFromFileSystem(String projectPath) {
		if (!new File(projectPath).exists()) {
			HLog.el("loadProjectFromFileSystem: project path is not exist:" + projectPath);
			return;
		}
		
		File projectFile = new File(projectPath);
		if (!projectFile.isDirectory()) {
			HLog.el("loadProjectFromFileSystem: project path error(not a directory):" + projectPath);
			return;
		}
		
		srcFolderPath = projectPath;
		
		File[] projectFiles = projectFile.listFiles();
		//projectFiles = ProjectLoader.sortLogFiles(projectFiles);
		for (int i = 0; i < projectFiles.length; i++) {
			addProjectNodeFromFileSystem(rootNode, projectFiles[i]);
		}
	}
	
	private void addProjectNodeFromFileSystem(HProjectViewItemNode parentNode, File srcFile) {
		
		// 首先，为当前路径创建节点
		HProjectViewItemNode currentNode = new HProjectViewItemNode(srcFile.getName(), srcFile.getPath());
		parentNode.add(currentNode);
		
		// 如果当前路径是个目录的话，继续创建
		if (srcFile.isDirectory()) {
			currentNode.setFile(false);
			File[] childFiles = srcFile.listFiles();
			//childFiles = ProjectLoader.sortLogFiles(childFiles);
			for (int i = 0; i < childFiles.length; i++) {
				addProjectNodeFromFileSystem(currentNode, childFiles[i]);
			}
		} else {
			currentNode.setFile(true);
		}
	}
	
	private class ProjectTreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = -8569195343353129611L;

		public ProjectTreeCellRenderer() {
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
			Icon projectIcon = new ImageIcon("./image/project_icon.png");
			Icon fileIcon = new ImageIcon("./image/file_icon.png");
			HProjectViewItemNode node = (HProjectViewItemNode) value;
			if (node.isRoot()) {
				setIcon(projectIcon);
			} else if (leaf) {
				if (!node.isFile()) {
					
				} else {
					setIcon(fileIcon);
				}
			} else {
				
			}
			
			this.setText(value.toString());
			return this;
		}
		
		@Override
		public Color getBackgroundNonSelectionColor() {
			return (null);
		}

		@Override
		public Color getBackground() {
			return (null);
		}
	}

	public String getSrcFolderPath() {
		return srcFolderPath;
	}

	public void setSrcFolderPath(String srcFolderPath) {
		this.srcFolderPath = srcFolderPath;
	}
}
