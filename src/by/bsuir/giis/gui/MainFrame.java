package by.bsuir.giis.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import by.bsuir.giis.model.DrawingMode;
import by.bsuir.giis.util.algorithm.interfaces.IGraphicsObject;
import by.bsuir.giis.util.algorithm.line.LineBREZ;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.toolbar.WebToolBar;

public class MainFrame extends JFrame {

	private MainFrame mainFrame;
	PaintPanel paintPanel;
	JLabel zoomLabel;
	private int zoom = 1;
	private JSlider zoomSlider;
	private ButtonGroup buttonGroup;
	JRadioButton automaticallyRadioButton;
	JRadioButton byStepsRadioButton;
	JRadioButton moveRadioButton;
	JButton nextStepButton;
	private AlgorithmType algorithmType = AlgorithmType.CDA_LINE;
	private JCheckBox checkBoxForGrid;
	private JLabel firstPoint;
	private JLabel secondPoint;
	
	final Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	public MainFrame() {
		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mainFrame = this;
		setFocusable(true);
		setTitle("Graphical User Interfaces in Intelligent Systems");

		JButton button = new JButton("QUIT");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		nextStepButton = new JButton("Next Step");
		nextStepButton.setEnabled(false);
		nextStepButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				paintPanel.nextStep();
			}
		});

		JPanel radioPanel = new JPanel();
		
		buttonGroup = new ButtonGroup();
		
		automaticallyRadioButton = new JRadioButton("Automatically");
		automaticallyRadioButton.setSelected(true);
		automaticallyRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextStepButton.setEnabled(false);
				paintPanel.setDrawingMode(DrawingMode.AUTO_MODE);
			}
		});
		
		buttonGroup.add(automaticallyRadioButton);
		
		radioPanel.add(automaticallyRadioButton);
		
		byStepsRadioButton = new JRadioButton("On Steps");
		byStepsRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextStepButton.setEnabled(true);
				paintPanel.setDrawingMode(DrawingMode.STEP_MODE);
			}
		});
		
		buttonGroup.add(byStepsRadioButton);
		
		radioPanel.add(byStepsRadioButton);
		
		moveRadioButton = new JRadioButton("Move Points");
		moveRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextStepButton.setEnabled(false);
				paintPanel.setDrawingMode(DrawingMode.MOVE_POINTS_MODE);
			}
		});
		
		buttonGroup.add(moveRadioButton);
		
		radioPanel.add(moveRadioButton);
		
		radioPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Drawing mode"));

		paintPanel = new PaintPanel(this);
		this.setZoom(paintPanel.getStep());

		JScrollPane scroller = new JScrollPane(paintPanel);
		scroller.setPreferredSize(screenSize);
		scroller.setWheelScrollingEnabled(false);
		
		JStatusBar statusBar = new JStatusBar(this);
		
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
					paintPanel.deleteLastShape();
				}
			}
		});

		ShapesToolBar toolBar = new ShapesToolBar(this, paintPanel);

		add(scroller, BorderLayout.CENTER);
		add(createMenu(), BorderLayout.NORTH);
		add(statusBar, BorderLayout.SOUTH);
		add(toolBar, BorderLayout.WEST);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screenSize.setSize(screenSize.getWidth() - 40,
				screenSize.getHeight() - 40);
		setSize(screenSize);
//		 setResizable(false);
		setVisible(true);
	}
	
	public void changeZoom(int zoomValue){
		
				paintPanel.updateGrid(zoomValue);
				paintPanel.setPreferredSize(new Dimension((int) screenSize
						.getWidth() * zoomValue, (int) screenSize
						.getHeight() * zoomValue));
				paintPanel.revalidate();
	}

	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Graphical User Interfaces in Intelligent Systems\n"
								+ "Autor:  Aleinikov Zhenya", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(about);

		JMenu draw = new JMenu("Ôàéë");
		
		JMenuItem clearMenuItem = new JMenuItem("Î÷èñòèòü", new ImageIcon(getImage("images/clear.png")));
		clearMenuItem.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_DELETE, KeyEvent.SHIFT_MASK ) );
		clearMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paintPanel.clearShapeList();
			}
		});
		
		JMenuItem exitMenuItem = new JMenuItem("Âûõîä", new ImageIcon(getImage("images/exit.png")));
		exitMenuItem.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_F4, KeyEvent.ALT_MASK ) );
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		draw.add(clearMenuItem);
		draw.add(new JSeparator());
		draw.add(exitMenuItem);

		menuBar.add(draw);
		menuBar.add(help);
		
		return menuBar;
	}  

	public void setFirstPointCord(String text) {
		this.firstPoint.setText(text);
	}

	public void setSecondPoint(String text) {
		this.secondPoint.setText(text);
	}

	
	private Image getImage(String path){
		ImageIcon icon = new ImageIcon(path);
		return icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	}

	public boolean checkBoxIsSelected() {
		return checkBoxForGrid.isSelected();
	}

//	public void addPointsForAlgorithm(Point p1, Point p2) {
//		paintPanel.addPointsForAlgorithm(p1, p2);
//	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getZoom() {
		return zoom;
	}

	public void setLineAlgorithm(AlgorithmType algorithmType) {
		this.paintPanel.setLineAlgorithm(algorithmType);
		System.out.println(algorithmType);
	}

	public AlgorithmType getLineAlgorithm() {
		return algorithmType;
	}

	public void setSliderOptions(int step) {
		if (step >= 1 && step <= 20) {
			zoomSlider.setValue(step);
			zoomLabel.setText("Pixel size: " + zoomSlider.getValue());
		}
	}

	public AlgorithmType getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(AlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}	
}	

