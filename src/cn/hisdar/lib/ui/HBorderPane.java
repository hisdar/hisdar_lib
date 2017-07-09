package cn.hisdar.lib.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class HBorderPane extends JPanel {

	
	private final static int DEFAULT_HPANEL_BORDER   = 20;
	private final static int DEFAULT_HPANEL_START    = 15;
	private final static int DEFAULT_PART_TITLE_SIZE = 40;
	
	private final static int DEFAULT_HPANEL_BORDER_COLOR = 0xE0E8EE;
	private final static int DEFAULT_HPANEL_BORDER_LINE_COLOR = 0xA0A8AA;
	private final static int DEFAULT_PART_TITLE_COLOR    = 0x808888;
	
	private final static int DEFAULT_HPANEL_BORDER_ARC = 10;
	
	public final static int PART_WEST    = 0;
	public final static int PART_EAST    = 1;
	public final static int PART_NORTH   = 2;
	public final static int PART_SOUTH   = 3;
	public final static int PART_CENTER  = 4;
	private final static int PART_COUNT  = 5;
	
	private Vector<Component> westPartList   = null;
	private Vector<Component> eastPartList   = null;
	private Vector<Component> northPartList  = null;
	private Vector<Component> southPartList  = null;
	private Vector<Component> centerPartList = null;
	
	private Component westComponent = null;
	
	private HRect panelRect = null;
	private HRect[] partRect = null;
	
	public HBorderPane() {
		westPartList   = new Vector<Component>();
		eastPartList   = new Vector<Component>();
		northPartList  = new Vector<Component>();
		southPartList  = new Vector<Component>();
		centerPartList = new Vector<Component>();
		
		panelRect = new HRect();
		
		partRect = new HRect[5];
		for (int i = 0; i < partRect.length; i++) {
			partRect[i] = new HRect();
		}
	}

	private void initPanelRect() {
		panelRect.x = DEFAULT_HPANEL_START;
		panelRect.y = DEFAULT_HPANEL_START;
		panelRect.width = getWidth() - DEFAULT_HPANEL_START * 2;
		panelRect.height = getHeight() - DEFAULT_HPANEL_START * 2;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		initPanelRect();
		
		g.setColor(new Color(DEFAULT_HPANEL_BORDER_COLOR));
		//g.setColor(Color.RED);
		
		// 绘制边框
		g.fillRect(0, 0, DEFAULT_HPANEL_BORDER, getHeight());
		g.fillRect(0, 0, getWidth(), DEFAULT_HPANEL_BORDER);
		g.fillRect(getWidth() - DEFAULT_HPANEL_BORDER, 0, DEFAULT_HPANEL_BORDER, getHeight());
		g.fillRect(0, getHeight() - DEFAULT_HPANEL_BORDER, getWidth(), DEFAULT_HPANEL_BORDER);
		g.setColor(Color.WHITE);

		//g.fillRoundRect(panelRect.x, panelRect.y, panelRect.width, panelRect.height, DEFAULT_HPANEL_BORDER_ARC, DEFAULT_HPANEL_BORDER_ARC);
		
		g.setColor(new Color(DEFAULT_HPANEL_BORDER_LINE_COLOR));
		
		// 绘制part的标题
		computePartRect();
		if (westPartList.size() > 0) {
			g.setColor(new Color(DEFAULT_PART_TITLE_COLOR));
			g.fillRoundRect(partRect[PART_WEST].x, partRect[PART_WEST].y, 
							partRect[PART_WEST].width, DEFAULT_PART_TITLE_SIZE, 
							DEFAULT_HPANEL_BORDER_ARC, DEFAULT_HPANEL_BORDER_ARC);
		}
		
		// 在边框上加一条线
		g.drawRoundRect(panelRect.x, panelRect.y, panelRect.width, panelRect.height, DEFAULT_HPANEL_BORDER_ARC, DEFAULT_HPANEL_BORDER_ARC);
		if (westComponent != null) {
			westComponent.setBounds(panelRect.x, panelRect.y, panelRect.width, panelRect.height);
		}
	}
	
	private void computePartRect() {
		int partCount = getPartCount();
		
		initPanelRect();
		System.out.println("part count = " + partCount);
		if (partCount == 1) {
			if (westPartList.size() > 0) {
				partRect[PART_WEST].setRect(panelRect);
			}
		}
		
		System.err.println("West part rect:" + partRect[PART_WEST]);
	}
	
	public void addPart(Component component, int part) {
		if (component == null) {
			return ;
		}
		
		Vector<Component> partComponents = null;
		
		switch (part) {
		case PART_WEST:
			partComponents = westPartList;
			break;
		case PART_EAST:
			partComponents = eastPartList;
			break;
		case PART_NORTH:
			partComponents = northPartList;
			break;
		case PART_SOUTH:
			partComponents = southPartList;
			break;
		case PART_CENTER:
			partComponents = centerPartList;
			break;
		default:
			partComponents = centerPartList;
			break;
		}
		
		westComponent = component;
		partComponents.add(component);
		computePartRect();
		//System.out.println(panelRect);
		//component.setBounds(panelRect.x, panelRect.y, panelRect.width, panelRect.height);
		//add(component);
		//validate();
	}
	
	public void printPartCount() {
		System.err.println("=======" + westPartList.size());
	}
	
	private int getPartCount() {
		int partCount = 0;
		if (eastPartList.size() > 0) {
			partCount += 1;
		}
		
		System.out.println("west component count = " + westPartList.size());
		if (westPartList.size() > 0) {
			partCount += 1;
		}
		
		if (northPartList.size() > 0) {
			partCount += 1;
		}
		
		if (southPartList.size() > 0) {
			partCount += 1;
		}
		
		if (centerPartList.size() > 0) {
			partCount += 1;
		}
		
		return partCount;
	}
}
