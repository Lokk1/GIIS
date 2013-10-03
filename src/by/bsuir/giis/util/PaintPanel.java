package by.bsuir.giis.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.bsuir.giis.gui.MainFrame;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

public class PaintPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	MainFrame mainFrame;

	private Color gridColor = Color.LIGHT_GRAY;

	private int width;
	private int height;
	private int step = 1;

	private boolean clickedEnd = false;

	private Point p1 = null;
	private Point p2 = null;
	private Point p3 = null;
	private Point p4 = null;

	BufferedImage img;
	Graphics graphics;

	AlgorithmType algorithmType;

	public int getStep() {
		return step;
	}

	private List<Cell> clickedCells = new ArrayList<Cell>();
	private List<Cell> cells = null;
	private CellControl cellControl = null;

	PaintPanel paintPanel = this;

	public PaintPanel(final MainFrame mainFrame) {

		cells = new ArrayList<Cell>();
		cellControl = new CellControl(mainFrame);
		cellControl.setStep(step);
		this.mainFrame = mainFrame;

		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() == 1 && (step + 1) >= 1
						&& (step + 1) <= 20) {
					updateGrid(step + 1);
					paintPanel.setPreferredSize(new Dimension((int) width + 1,
							(int) height + 1));
					paintPanel.revalidate();
					mainFrame.setSliderOptions(step);
				}
				if (e.getWheelRotation() == -1 && (step - 1) >= 1
						&& (step - 1) <= 20) {
					updateGrid(step - 1);
					paintPanel.setPreferredSize(new Dimension((int) width - 1,
							(int) height - 1));
					paintPanel.revalidate();
					mainFrame.setSliderOptions(step);
				}
			}

		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				System.out.println("[" + e.getX() + "], [" + e.getY() + "]");

				if (p1 == null) {
					clickedFirstPoint(e);
					repaint();
				} else if (p2 == null && p1 != null) {
					clickedSecondPoint(e);
					repaint();

				} else if (p3 == null && p2 != null && p1 != null) {
					clickedThirdPoint(e);
					repaint();
				} else if (p4 == null && p3 != null && p2 != null && p1 != null) {
					clickedFouthPoint(e);
					repaint();
				}

				// if (p1 != null && p2 != null && p3 == null && p4 == null) {
				// paintPanel.setLineAlgorithm(mainFrame.getAlgorithmType());
				// paintPanel.addPointsForAlgorithm(p1, p2);
				// clickedCells.clear();
				// clearPoints();
				// }
				// else
				if (p1 != null && p2 != null && p3 != null && p4 != null) {
					paintPanel.setLineAlgorithm(mainFrame.getAlgorithmType());
					paintPanel.addPointsForAlgorithm(p1, p2, p3, p4);
					clickedCells.clear();
					clearPoints();
				}

			}
		});

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		width = getWidth();
		height = getHeight();

		if (mainFrame.checkBoxIsSelected() && (step > 2))
			drawGrid(g);

		if (!clickedCells.isEmpty()) {
			for (Cell cell : clickedCells) {
				g.fillRect(cell.getX() * step, cell.getY() * step, step, step);
			}
		}

		if (cellControl != null) {
			cellControl.draw(g);
		}

	}

	protected void drawGrid(Graphics g) {
		g.setColor(gridColor);

		int progress = step;
		for (int count = 0; progress < width; count++) {
			g.drawLine(progress, 0, progress, height);
			progress = count * step;

		}

		progress = step;
		for (int count = 0; progress < height; count++) {
			g.drawLine(0, progress, width, progress);
			progress = count * step;
		}
	}

	public void updateGrid(final int step) {
		this.step = step;
		cells.clear();
		cellControl.setStep(step);
		repaint();
	}

	public void addPointsForAlgorithm(Point p1, Point p2) {
		cellControl.addPointsForAlgorithm(p1, p2);
	}

	public void addPointsForAlgorithm(Point p1, Point p2, Point p3, Point p4) {
		cellControl.addPointsForAlgorithm(p1, p2, p3, p4);
	}

	public void paint() {
		repaint();
	}

	public void nextStep() {
		cellControl.nextStep();
		repaint();
	}

	public void setLineAlgorithm(AlgorithmType algorithmType) {
		this.cellControl.setLineAlgorithm(algorithmType);
		System.out.println(algorithmType);
	}

	public void clearAll() {
		this.cellControl.clear();
		clickedCells.clear();
		repaint();

	}

	public void clearPoints() {
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
	}

	private int inputRadiusDialog() {
		int radius = 70;

		String tempValue = JOptionPane.showInputDialog(paintPanel,
				"Input radius");

		if (tempValue != null)

			if (!tempValue.isEmpty())
				return radius = Integer.parseInt(tempValue);
			else
				JOptionPane.showMessageDialog(paintPanel,
						"Will be selected by default");

		else
			JOptionPane.showMessageDialog(paintPanel,
					"Will be selected by default");

		return radius;
	}

	private Point hiperbolaInputDialog() {

		Point point;

		int a = 20;
		int b = 20;

		JTextField aValue = new JTextField(3);
		JPanel aPanel = new JPanel();
		aPanel.add(new JLabel("Input a: "));
		aPanel.add(aValue);
		JTextField bValue = new JTextField(3);
		JPanel bPanel = new JPanel();
		aPanel.add(new JLabel("Input b: "));
		aPanel.add(bValue);

		Object hiperbolaTextFields[] = { "Input values", aPanel, bPanel };

		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage(hiperbolaTextFields);
		optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		JDialog dialog = optionPane.createDialog(paintPanel, "Hiperbola");
		dialog.setModal(true);
		dialog.setVisible(true);

		if (aValue.getText() != null)
			if (!aValue.getText().isEmpty())
				a = Integer.parseInt(aValue.getText());

		if (bValue.getText() != null)
			if (!bValue.getText().isEmpty())
				b = Integer.parseInt(bValue.getText());

		if (a == 20 && b == 20)
			JOptionPane.showMessageDialog(paintPanel,
					"Will be selected by default");
		if (a == 20 && b != 20)
			JOptionPane.showMessageDialog(paintPanel,
					"Will be selected by default for \"a\"");
		if (a != 20 && b == 20)
			JOptionPane.showMessageDialog(paintPanel,
					"Will be selected by default for \"b\"");

		point = new Point(a, b);
		System.out.println(point);

		return point;
	}

	private void clickedFirstPoint(MouseEvent e) {

		int wProgress = step;
		int hProgress = step;
		for (int i = 0; wProgress < width; i++) {
			hProgress = step;
			for (int j = 0; hProgress < height; j++) {
				if (j == 0) {
					Rectangle rectangle = new Rectangle(wProgress, j, step,
							step);
					if (rectangle.contains(e.getX(), e.getY())
							&& mainFrame.getAlgorithmType() != AlgorithmType.CIRCLE
							&& mainFrame.getAlgorithmType() != AlgorithmType.HYPERBOLA) {
						p1 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setFirstPointCord("(" + e.getX() / step
								+ ", " + e.getY() / step + ")");
						clickedCells.add(new Cell(p1, Color.LIGHT_GRAY));
					} else if (rectangle.contains(e.getX(), e.getY())
							&& mainFrame.getAlgorithmType() == AlgorithmType.CIRCLE
							&& mainFrame.getAlgorithmType() != AlgorithmType.HYPERBOLA) {
						p1 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setFirstPointCord("(" + e.getX() / step
								+ ", " + e.getY() / step + ")");
						clickedCells.add(new Cell(p1, Color.LIGHT_GRAY));
						if (p2 == null)
							p2 = new Point(inputRadiusDialog(), 0);
					} else if (rectangle.contains(e.getX(), e.getY())
							&& mainFrame.getAlgorithmType() == AlgorithmType.HYPERBOLA
							&& mainFrame.getAlgorithmType() != AlgorithmType.CIRCLE) {
						p1 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setFirstPointCord("(" + e.getX() / step
								+ ", " + e.getY() / step + ")");
						clickedCells.add(new Cell(p1, Color.LIGHT_GRAY));
						if (p2 == null)
							p2 = new Point(hiperbolaInputDialog());
					}

				} else if (j > 0) {
					Rectangle rectangle = new Rectangle(wProgress, hProgress,
							step, step);
					if (rectangle.contains(e.getX(), e.getY())
							&& mainFrame.getAlgorithmType() != AlgorithmType.CIRCLE
							&& mainFrame.getAlgorithmType() != AlgorithmType.HYPERBOLA
					/*
					 * && mainFrame.getAlgorithmType() ==
					 * AlgorithmType.CURVE_ERMIT
					 */) {
						p1 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setFirstPointCord("(" + e.getX() / step
								+ ", " + e.getY() / step + ")");
						clickedCells.add(new Cell(p1, Color.LIGHT_GRAY));
					}
					if (rectangle.contains(e.getX(), e.getY())
							&& mainFrame.getAlgorithmType() == AlgorithmType.CIRCLE
							&& mainFrame.getAlgorithmType() != AlgorithmType.HYPERBOLA
					/*
					 * && mainFrame.getAlgorithmType() !=
					 * AlgorithmType.CURVE_ERMIT
					 */) {
						p1 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setFirstPointCord("(" + e.getX() / step
								+ ", " + e.getY() / step + ")");
						clickedCells.add(new Cell(p1, Color.LIGHT_GRAY));
						if (p2 == null)
							p2 = new Point(inputRadiusDialog(), 0);
					}
					if (rectangle.contains(e.getX(), e.getY())
							&& mainFrame.getAlgorithmType() == AlgorithmType.HYPERBOLA
							&& mainFrame.getAlgorithmType() != AlgorithmType.CIRCLE
					/*
					 * && mainFrame.getAlgorithmType() ==
					 * AlgorithmType.CURVE_ERMIT
					 */) {
						p1 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setFirstPointCord("(" + e.getX() / step
								+ ", " + e.getY() / step + ")");
						clickedCells.add(new Cell(p1, Color.LIGHT_GRAY));
						if (p2 == null)
							p2 = new Point(hiperbolaInputDialog());
					}
				}
				hProgress = j * step;
			}
			wProgress = i * step;
		}
	}

	private void clickedSecondPoint(MouseEvent e) {
		int wProgress = step;
		int hProgress = step;
		for (int i = 0; wProgress < width; i++) {
			hProgress = step;
			for (int j = 0; hProgress < height; j++) {
				if (j == 0) {
					Rectangle rectangle = new Rectangle(wProgress, j, step,
							step);
					if (rectangle.contains(e.getX(), e.getY())) {
						p2 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setSecondPoint("(" + e.getX() / step + ", "
								+ e.getY() / step + ")");
						clickedCells.add(new Cell(p2, Color.LIGHT_GRAY));
					}
				} else {
					Rectangle rectangle = new Rectangle(wProgress, hProgress,
							step, step);
					if (rectangle.contains(e.getX(), e.getY())) {
						p2 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setSecondPoint("(" + e.getX() / step + ", "
								+ e.getY() / step + ")");
						clickedCells.add(new Cell(p2, Color.LIGHT_GRAY));
					}
				}
				hProgress = j * step;
			}
			wProgress = i * step;
		}
	}

	public void clickedThirdPoint(MouseEvent e) {
		int wProgress = step;
		int hProgress = step;
		for (int i = 0; wProgress < width; i++) {
			hProgress = step;
			for (int j = 0; hProgress < height; j++) {
				if (j == 0) {
					Rectangle rectangle = new Rectangle(wProgress, j, step,
							step);
					if (rectangle.contains(e.getX(), e.getY())) {
						p3 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setSecondPoint("(" + e.getX() / step + ", "
								+ e.getY() / step + ")");
						clickedCells.add(new Cell(p3, Color.LIGHT_GRAY));
					}
				} else {
					Rectangle rectangle = new Rectangle(wProgress, hProgress,
							step, step);
					if (rectangle.contains(e.getX(), e.getY())) {
						p3 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setSecondPoint("(" + e.getX() / step + ", "
								+ e.getY() / step + ")");
						clickedCells.add(new Cell(p3, Color.LIGHT_GRAY));
					}
				}
				hProgress = j * step;
			}
			wProgress = i * step;
		}
	}

	public void clickedFouthPoint(MouseEvent e) {
		int wProgress = step;
		int hProgress = step;
		for (int i = 0; wProgress < width; i++) {
			hProgress = step;
			for (int j = 0; hProgress < height; j++) {
				if (j == 0) {
					Rectangle rectangle = new Rectangle(wProgress, j, step,
							step);
					if (rectangle.contains(e.getX(), e.getY())) {
						p4 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setSecondPoint("(" + e.getX() / step + ", "
								+ e.getY() / step + ")");
						clickedCells.add(new Cell(p4, Color.LIGHT_GRAY));
					}
				} else {
					Rectangle rectangle = new Rectangle(wProgress, hProgress,
							step, step);
					if (rectangle.contains(e.getX(), e.getY())) {
						p4 = new Point(e.getX() / step, e.getY() / step);
						mainFrame.setSecondPoint("(" + e.getX() / step + ", "
								+ e.getY() / step + ")");
						clickedCells.add(new Cell(p4, Color.LIGHT_GRAY));
					}
				}
				hProgress = j * step;
			}
			wProgress = i * step;
		}
	}

	public void setDrawingMode(DrawingMode mode) {
		cellControl.setDrawingMode(mode);
	}
}
