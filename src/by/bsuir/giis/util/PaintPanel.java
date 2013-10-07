package by.bsuir.giis.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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

	private boolean showGrid = false;

	private Point point = null;
	
	private Coordinates coordinates;

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
		coordinates = new Coordinates();

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

		addMouseListener();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		width = getWidth();
		height = getHeight();

		if (showGrid && (step > 2))
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
	
	public void addMouseListener(){
		this.addMouseListener(new MouseAdapter() {
			private boolean endClicked = false;

			@Override
			public void mouseClicked(MouseEvent e) {
				
				switch (mainFrame.getAlgorithmType()) {
					case BREZ_LINE:
					case CDA_LINE:
					case WU_LINE:
					case CURVE_ERMIT:
						if(coordinates.isEmpty()){
							point = new Point(e.getX() / step, e.getY() / step);
							coordinates.add(point);
							clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
							repaint();
						} else if(!coordinates.isEmpty() && coordinates.size() < 2){
							point = new Point(e.getX() / step, e.getY() / step);
							coordinates.add(point);
							clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
							repaint();
							endClicked = true;
						}
					break;
					case CIRCLE:
						point = new Point(e.getX() / step, e.getY() / step);
						coordinates.add(point);
						clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
						coordinates.add(new Point(inputRadiusDialog(), 0));
						repaint();
						endClicked = true;
						break;
					case HYPERBOLA:
						point = new Point(e.getX() / step, e.getY() / step);
						coordinates.add(point);
						clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
						coordinates.add(hiperbolaInputDialog());
						repaint();
						endClicked = true;
						break;
					case CURVE_BEZIE:
					case B_SPLAIN:
						if(coordinates.isEmpty()){
							point = new Point(e.getX() / step, e.getY() / step);
							coordinates.add(point);
							clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
							repaint();
						} else if(!coordinates.isEmpty() && coordinates.size() < 2){
							point = new Point(e.getX() / step, e.getY() / step);
							coordinates.add(point);
							clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
							repaint();
						}else if(!coordinates.isEmpty() && coordinates.size() < 3){
							point = new Point(e.getX() / step, e.getY() / step);
							coordinates.add(point);
							clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
							repaint();
						}else if(!coordinates.isEmpty() && coordinates.size() < 4){
							point = new Point(e.getX() / step, e.getY() / step);
							coordinates.add(point);
							clickedCells.add(new Cell(point, Color.LIGHT_GRAY));
							repaint();
							endClicked = true;
						}
						break;
				default:
					break;
				}
				
				if (endClicked) {
					cellControl.setLineAlgorithm(mainFrame.getAlgorithmType());
					cellControl.setCootdinatesForAlgorithm(coordinates);
					coordinates.clear();
					clickedCells.clear();
					endClicked = false;
				}
			}
		});
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

	public void setDrawingMode(DrawingMode mode) {
		cellControl.setDrawingMode(mode);
	}

	public void showGrid(boolean showGrid) {
		this.showGrid = showGrid;
		repaint();
	}
}
