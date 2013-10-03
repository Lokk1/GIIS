package by.bsuir.giis.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import by.bsuir.giis.gui.dialog.CircleDialog;
import by.bsuir.giis.gui.dialog.EllipseDialog;
import by.bsuir.giis.gui.dialog.TwoPointsDialog;
import by.bsuir.giis.util.DrawingMode;
import by.bsuir.giis.util.PaintPanel;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

import com.alee.laf.WebLookAndFeel;

public class MainFrame extends JFrame {

	private MainFrame mainFrame;
	PaintPanel paintPanel;
	JLabel zoomLabel;
	private int zoom = 1;
	private JSlider zoomSlider;
	private ButtonGroup buttonGroup;
	JRadioButton automaticallyRadioButton;
	JRadioButton byStepsRadioButton;
	JButton nextStepButton;
	private AlgorithmType algorithmType;
	private JCheckBox checkBoxForGrid;
	private JLabel firstPoint;
	private JLabel secondPoint;
	private JComboBox cb;

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
		radioPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Drawing mode"));

		paintPanel = new PaintPanel(this);
		this.setZoom(paintPanel.getStep());

		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();

		JScrollPane scroller = new JScrollPane(paintPanel);
		scroller.setPreferredSize(screenSize);
		scroller.setWheelScrollingEnabled(false);
		// scroller.getVerticalScrollBar().setUnitIncrement(20);
		// scroller.getHorizontalScrollBar().setUnitIncrement(25);

		JPanel jPanel = new JPanel();
		JPanel sliderPanel = new JPanel();
		zoomSlider = new JSlider(1, 20, zoom);
		zoomSlider.setFocusable(false);
		zoomSlider.setMinorTickSpacing(1);
		zoomSlider.setPaintTicks(true);
		zoomSlider.setForeground(Color.BLACK);
		zoomLabel = new JLabel("Pixel size: " + zoom);
		sliderPanel.add(zoomSlider);
		sliderPanel.add(zoomLabel);

		zoomSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				paintPanel.updateGrid(zoomSlider.getValue());
				zoomLabel.setText("Pixel size: " + zoomSlider.getValue());
				paintPanel.setPreferredSize(new Dimension((int) screenSize
						.getWidth() * zoomSlider.getValue(), (int) screenSize
						.getHeight() * zoomSlider.getValue()));
				paintPanel.revalidate();
			}
		});

		checkBoxForGrid = new JCheckBox("Grid");
		checkBoxForGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintPanel.repaint();
			}
		});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_G) {
					checkBoxForGrid.doClick();
				}
			}
		});

		jPanel.add(drawButton());
		jPanel.add(checkBoxForGrid);
		jPanel.add(sliderPanel);
		jPanel.add(radioPanel);
		jPanel.add(nextStepButton);

		jPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		jPanel.add(clearButton());
		jPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		jPanel.add(button);

		add(scroller, BorderLayout.CENTER);
		add(createMenu(), BorderLayout.NORTH);
		add(jPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screenSize.setSize(screenSize.getWidth() - 40,
				screenSize.getHeight() - 40);
		setSize(screenSize);
		// setResizable(false);
		setVisible(true);
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

		JMenu draw = new JMenu("Draw");
		JMenu drawLine = new JMenu("Line");
		JMenuItem cdaAlg = new JMenuItem("CDA");
		cdaAlg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TwoPointsDialog(mainFrame, AlgorithmType.CDA_LINE);
			}
		});
		JMenuItem brezAlg = new JMenuItem("BREZENHEM");
		brezAlg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TwoPointsDialog(mainFrame, AlgorithmType.BREZ_LINE);

			}
		});
		JMenuItem vuAlg = new JMenuItem("VU");
		vuAlg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TwoPointsDialog(mainFrame, AlgorithmType.WU_LINE);
			}
		});
		drawLine.add(cdaAlg);
		drawLine.add(brezAlg);
		drawLine.add(vuAlg);

		JMenu drawConic = new JMenu("Conic");
		JMenuItem circleBREZ = new JMenuItem("Circle");
		circleBREZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CircleDialog(mainFrame, AlgorithmType.CIRCLE);
			}
		});
		JMenuItem ellipseBREZ = new JMenuItem("�llipse");
		ellipseBREZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EllipseDialog(mainFrame, AlgorithmType.HYPERBOLA);
			}
		});

		drawConic.add(circleBREZ);
		drawConic.add(ellipseBREZ);

		draw.add(drawLine);
		draw.add(drawConic);

		menuBar.add(draw);
		menuBar.add(help);
		cb = new JComboBox();
		cb.setFocusable(false);
		cb.addItem(AlgorithmType.BREZ_LINE);
		cb.addItem(AlgorithmType.CDA_LINE);
		cb.addItem(AlgorithmType.WU_LINE);
		cb.addItem(AlgorithmType.CIRCLE);
		cb.addItem(AlgorithmType.HYPERBOLA);
		cb.addItem(AlgorithmType.CURVE_ERMIT);
		cb.addItem(AlgorithmType.CURVE_BEZIE);
		cb.addItem(algorithmType.B_SPLAIN);

		cb.setMaximumSize(new Dimension(150, 500));
		menuBar.add(new JLabel("     Algorithm: "));
		menuBar.add(cb);
		menuBar.add(new JSeparator());
		return menuBar;
	}

	private JPanel drawButton() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(230, 50));
		JPanel labelPanel = new JPanel(new GridLayout(2, 2));
		JButton drawButton = new JButton("Draw");

		firstPoint = new JLabel("(0,0)");
		secondPoint = new JLabel("(0,0)");

		drawButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (automaticallyRadioButton.isSelected()) {
					paintPanel.paint();
					firstPoint.setText("(0, 0)");
					secondPoint.setText("(0, 0)");
				}
			}
		});
		labelPanel.add(new JLabel("First point: "));
		labelPanel.add(firstPoint);
		labelPanel.add(new JLabel("Second point:   "));
		labelPanel.add(secondPoint);

		panel.add(drawButton);
		panel.add(labelPanel);

		return panel;
	}

	public void setFirstPointCord(String text) {
		this.firstPoint.setText(text);
	}

	public void setSecondPoint(String text) {
		this.secondPoint.setText(text);
	}

	private JButton clearButton() {
		JButton clearAll = new JButton("Clear");
		clearAll.setFocusable(false);
		clearAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paintPanel.clearAll();
				paintPanel.clearPoints();
			}
		});
		return clearAll;
	}

	public boolean checkBoxIsSelected() {
		return checkBoxForGrid.isSelected();
	}

	public void addPointsForAlgorithm(Point p1, Point p2) {
		paintPanel.addPointsForAlgorithm(p1, p2);
	}

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
		switch ((AlgorithmType) cb.getSelectedItem()) {
		case BREZ_LINE:
			return AlgorithmType.BREZ_LINE;
		case CDA_LINE:
			return AlgorithmType.CDA_LINE;
		case WU_LINE:
			return AlgorithmType.WU_LINE;
		case CIRCLE:
			return AlgorithmType.CIRCLE;
		case HYPERBOLA:
			return AlgorithmType.HYPERBOLA;
		case CURVE_ERMIT:
			return AlgorithmType.CURVE_ERMIT;
		case CURVE_BEZIE:
			return AlgorithmType.CURVE_BEZIE;
		case B_SPLAIN:
			return AlgorithmType.B_SPLAIN;
		default:
			break;
		}
		paintPanel.clearPoints();
		return null;
	}
}
