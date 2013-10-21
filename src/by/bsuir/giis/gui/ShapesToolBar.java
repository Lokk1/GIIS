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
	
//	private final String GRID_ICON        = "grid_2.png";
//	private final String MOVE_ICON        = "move.png";
//	private final String CIRCLE_ICON      = "circle.png";
//	private final String CDA_ICON         = "cda_line.png";
//	private final String BREZ_ICON        = "brez_line.png";
//	private final String WU_ICON          = "wu_line.png";
//	private final String HYPERBOLA_ICON   = "hyperbola.png";
//	private final String ERMIT_ICON       = "ermit.png";
//	private final String BEZIE_ICON       = "bezie.png";
	
	private final String GRID_ICON        = "images/grid_2.png";
	private final String MOVE_ICON        = "images/move.png";
	private final String CIRCLE_ICON      = "images/circle.png";
	private final String CDA_ICON         = "images/cda_line.png";
	private final String BREZ_ICON        = "images/brez_line.png";
	private final String WU_ICON          = "images/wu_line.png";
	private final String HYPERBOLA_ICON   = "images/hyperbola.png";
	private final String ERMIT_ICON       = "images/ermit.png";
	private final String BEZIE_ICON       = "images/bezie.png";
	private final String SPLINE_ICON       = "images/spline.png";
	
	private final String GRID_ACTION 	  = "grid";
	private final String MOVE_ACTION 	  = "move";
	private final String CIRCLE_ACTION 	  = "circle";
	private final String HYPERBOLA_ACTION = "hyperbola";
	private final String CDA_ACTION 	  = "cda";
	private final String BREZ_ACTION 	  = "brez";
	private final String WU_ACTION 	      = "wu";
	private final String ERMIT_ACTION 	  = "ermit";
	private final String BEZIE_ACTION 	  = "bezie";
	private final String SPLINE_ACTION 	  = "spline";
	
	private WebToggleButton gridToggleButton = null;
	private WebToggleButton moveToggleButton = null;
	
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
        
		gridToggleButton                       = createToggleButton(GRID_ICON, "Сетка", GRID_ACTION);
		WebToggleButton cdaToggleButton        = createToggleButton(CDA_ICON, "Алгоритм ЦДА", CDA_ACTION);
		WebToggleButton brezToggleButton       = createToggleButton(BREZ_ICON, "Алгоритм Брезентхема", BREZ_ACTION);
		WebToggleButton wuToggleButton         = createToggleButton(WU_ICON, "Алгоритм Ву", WU_ACTION);
		WebToggleButton circleToggleButton     = createToggleButton(CIRCLE_ICON, "Окружность", CIRCLE_ACTION);
		WebToggleButton hyperbolaToggleButton  = createToggleButton(HYPERBOLA_ICON, "Гипербола", HYPERBOLA_ACTION);
		WebToggleButton ermitToggleButton      = createToggleButton(ERMIT_ICON, "Кривая Ермита", ERMIT_ACTION);
		WebToggleButton bezieToggleButton      = createToggleButton(BEZIE_ICON, "Кривая Бизье", BEZIE_ACTION);
		WebToggleButton splineToggleButton      = createToggleButton(SPLINE_ICON, "B-сплайн", SPLINE_ACTION);
		moveToggleButton      				   = createToggleButton(MOVE_ICON, "Перетаскивание", MOVE_ACTION);
		
		cdaToggleButton.setSelected(true);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(cdaToggleButton);
		buttonGroup.add(brezToggleButton);
		buttonGroup.add(wuToggleButton);
		buttonGroup.add(circleToggleButton);
		buttonGroup.add(hyperbolaToggleButton);
		buttonGroup.add(ermitToggleButton);
		buttonGroup.add(bezieToggleButton);
		buttonGroup.add(splineToggleButton);
		
		
		add(cdaToggleButton);
		add(brezToggleButton);
		add(wuToggleButton);
		addSeparator();
		add(circleToggleButton);
		add(hyperbolaToggleButton);
		addSeparator();
		add(ermitToggleButton);
		add(bezieToggleButton);
		add(splineToggleButton);
		add(Box.createVerticalGlue());
		addSeparator();
		add(gridToggleButton);
		add(moveToggleButton);
	}
	
	private Image getImage(String path){
//		ImageIcon icon = new ImageIcon(ShapesToolBar.class.getClassLoader().getResource(path).getPath());
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
        button.setFocusable(false);
        
        return button;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals(GRID_ACTION)) {
			paintPanel.showGrid(gridToggleButton.isSelected());
		}
		else if (actionCommand.equals(CDA_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CDA_LINE);
		}
		else if (actionCommand.equals(BREZ_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.BREZ_LINE);
		}
		else if (actionCommand.equals(WU_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.WU_LINE);
		}
		else if (actionCommand.equals(CIRCLE_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CIRCLE);
		}
		else if (actionCommand.equals(HYPERBOLA_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.HYPERBOLA);
		}
		else if (actionCommand.equals(ERMIT_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CURVE_ERMIT);
		}
		else if (actionCommand.equals(BEZIE_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.CURVE_BEZIE);
		}
		else if (actionCommand.equals(SPLINE_ACTION)) {
			mainFrame.setAlgorithmType(AlgorithmType.B_SPLAIN);
		}
		else if(actionCommand.equals(MOVE_ACTION)){
			paintPanel.showControlPoints(moveToggleButton.isSelected());
		}
	}
}
