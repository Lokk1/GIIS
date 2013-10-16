package by.bsuir.giis.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import by.bsuir.giis.util.algorithm.interfaces.IGraphicsObject;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

import com.alee.laf.button.WebToggleButton;

public class ShapesToolBar extends JToolBar implements ActionListener{
	
	private final String GRID_ICON        = "images/grid_2.png";
	private final String CIRCLE_ICON      = "images/circle.png";
	private final String CDA_ICON         = "images/cda_line.png";
	private final String BREZ_ICON        = "images/brez_line.png";
	private final String WU_ICON          = "images/wu_line.png";
	private final String HYPERBOLA_ICON   = "images/hyperbola.png";
	private final String ERMIT_ICON       = "images/ermit.png";
	private final String BEZIE_ICON       = "images/bezie.png";
	
	private final String GRID_ACTION 	  = "grid";
	private final String CIRCLE_ACTION 	  = "circle";
	private final String HYPERBOLA_ACTION = "hyperbola";
	private final String CDA_ACTION 	  = "cda";
	private final String BREZ_ACTION 	  = "brez";
	private final String WU_ACTION 	      = "wu";
	private final String ERMIT_ACTION 	  = "ermit";
	private final String BEZIE_ACTION 	  = "bezie";
	
	private WebToggleButton gridToggleButton = null;
	
	private MainFrame mainFrame = null;
	private PaintPanel paintPanel = null;
	
	private IGraphicsObject graphicsObject = null;

	public ShapesToolBar(MainFrame mainFrame, PaintPanel paintPanel) {
		super(JToolBar.VERTICAL);
		this.mainFrame = mainFrame;
		this.paintPanel = paintPanel;
		initComponents();
	}

	private void initComponents() {
        
		gridToggleButton                       = createToggleButton(GRID_ICON, "�����", GRID_ACTION);
		WebToggleButton cdaToggleButton        = createToggleButton(CDA_ICON, "�������� ���", CDA_ACTION);
		WebToggleButton brezToggleButton       = createToggleButton(BREZ_ICON, "�������� �����������", BREZ_ACTION);
		WebToggleButton wuToggleButton         = createToggleButton(WU_ICON, "�������� ��", WU_ACTION);
		WebToggleButton circleToggleButton     = createToggleButton(CIRCLE_ICON, "����������", CIRCLE_ACTION);
		WebToggleButton hyperbolaToggleButton  = createToggleButton(HYPERBOLA_ICON, "���������", HYPERBOLA_ACTION);
		WebToggleButton ermitToggleButton      = createToggleButton(ERMIT_ICON, "������ ������", ERMIT_ACTION);
		WebToggleButton bezieToggleButton      = createToggleButton(BEZIE_ICON, "������ �����", BEZIE_ACTION);
		
		cdaToggleButton.setSelected(true);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(cdaToggleButton);
		buttonGroup.add(brezToggleButton);
		buttonGroup.add(wuToggleButton);
		buttonGroup.add(circleToggleButton);
		buttonGroup.add(hyperbolaToggleButton);
		buttonGroup.add(ermitToggleButton);
		buttonGroup.add(bezieToggleButton);
		
		
		add(cdaToggleButton);
		add(brezToggleButton);
		add(wuToggleButton);
		addSeparator();
		add(circleToggleButton);
		add(hyperbolaToggleButton);
		addSeparator();
		add(ermitToggleButton);
		add(bezieToggleButton);
		add(Box.createVerticalGlue());
		add(gridToggleButton);
	}
	
	private Image getImage(String path){
		ImageIcon icon = new ImageIcon(path);
		return icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	}
	
	private WebToggleButton createToggleButton(String iconPath, String toolTipText, String actionCommand){
		
		WebToggleButton button = new WebToggleButton ();
        button.setShadeToggleIcon ( true );
        button.setIcon ( new ImageIcon(getImage(iconPath)));
        button.setSelected(false);
        button.setMinimumSize(new Dimension(37, 37));
        button.setPreferredSize(new Dimension(37, 37));
        button.setMaximumSize(new Dimension(37, 37));
        button.addActionListener(this);
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        
        return button;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals(GRID_ACTION)) {
			paintPanel.showGrid(gridToggleButton.isSelected());
		}
		if (actionCommand.equals(CDA_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CDA_LINE);
		}
		if (actionCommand.equals(BREZ_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.BREZ_LINE);
		}
		if (actionCommand.equals(WU_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.WU_LINE);
		}
		if (actionCommand.equals(CIRCLE_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CIRCLE);
		}
		if (actionCommand.equals(HYPERBOLA_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.HYPERBOLA);
		}
		if (actionCommand.equals(ERMIT_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CURVE_ERMIT);
		}
		if (actionCommand.equals(BEZIE_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CURVE_BEZIE);
		}
	}
}
