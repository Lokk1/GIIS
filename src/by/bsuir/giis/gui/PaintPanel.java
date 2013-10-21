package by.bsuir.giis.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RepaintManager;

import by.bsuir.giis.control.CellControl;
import by.bsuir.giis.control.IGraphicsObjectControl;
import by.bsuir.giis.control.PaintsMoveControl;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;
import by.bsuir.giis.model.DrawingMode;
import by.bsuir.giis.util.algorithm.curve.AbstractCurve;
import by.bsuir.giis.util.algorithm.curve.CurveBSplain;
import by.bsuir.giis.util.algorithm.interfaces.IGraphicsObject;
import by.bsuir.giis.util.algorithm.interfaces.IPointMoveble;
import by.bsuir.giis.util.algorithm.line.AbstractLine;
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
	
	private AbstractCurve curve;

	private AlgorithmType algorithmType = AlgorithmType.CDA_LINE;
	
	private IGraphicsObject currentShape;
	
	private List<IGraphicsObject> shapeList;
	
	private IGraphicsObjectControl graphicsObjectControl;

	public int getStep() {
		return step;
	}

	private List<Cell> clickedCells = new ArrayList<Cell>();
	private List<Cell> cells = null;
	private CellControl cellControl = null;

	PaintPanel paintPanel = this;

	private boolean showControlPoints = false;

	public PaintPanel(final MainFrame mainFrame) {

		cells = new ArrayList<Cell>();
		
		shapeList = new ArrayList<IGraphicsObject>();
		
		cellControl = new CellControl(mainFrame, this);
		cellControl.setStep(step);
		coordinates = new Coordinates();

		this.mainFrame = mainFrame;
		
		setBackground(Color.white);

		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.isControlDown()) {
					if (e.getWheelRotation() == 1 && (step + 1) >= 1
							&& (step + 1) <= 20) {
						updateGrid(step + 1);
						paintPanel.setPreferredSize(new Dimension(
								(int) width + 1, (int) height + 1));
						paintPanel.revalidate();
					}
					if (e.getWheelRotation() == -1 && (step - 1) >= 1
							&& (step - 1) <= 20) {
						updateGrid(step - 1);
						paintPanel.setPreferredSize(new Dimension(
								(int) width - 1, (int) height - 1));
						paintPanel.revalidate();
					}
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
		
		if(shapeList != null)
			for (IGraphicsObject object : shapeList)
				object.draw(g, step);
		
		if(showControlPoints && currentShape != null)
			if (graphicsObjectControl != null)
				graphicsObjectControl.draw((Graphics2D) g, step);
	}

	private void drawGrid( Graphics g ) {
		g.setColor( gridColor );

		int progress = step;
		for ( int count = 0; progress < width; count++ ) {
			g.drawLine( progress, 0, progress, height );
			progress = count * step;
		}

		progress = step;
		for ( int count = 0; progress < height; count++ ) {
			g.drawLine( 0, progress, width, progress );
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
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(graphicsObjectControl != null) {
					graphicsObjectControl.processMouseMove(e.getX(), e.getY(), step);
				}
				repaint();
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			private boolean endClicked = false;
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				if(graphicsObjectControl != null) {
					boolean res = graphicsObjectControl.processMousePress(e.getX(), e.getY(), step);
					if(!res) {
					}
				}
				
//				if(mainFrame.getAlgorithmType() == AlgorithmType.B_SPLAIN){
//					if(curve == null)
//						curve = new CurveBSplain();
//					if(!curve.isComplete() && e.getClickCount() != 2)
//						curve.processMousePress(e.getX()/step, e.getY()/step);
//					else{
//						repaint();
//					}
//					
//					
////					setGraphicsObjectConntrol(new PaintsMoveControl((IPointMoveble) curve));
//				}
//				setCurrentShape(curve);
//				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(graphicsObjectControl != null) {
					graphicsObjectControl.processMouseRelease(e.getX(), e.getY(), step);
				}
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getButton() == MouseEvent.BUTTON3){
					try {
						currentShape = getShapeByCoord(e.getX(), e.getY());
					} catch (NullPointerException xzExeprion) {
						System.out
								.println("Что то тут не так, но что *** знает!");
					}
					if(currentShape != null)
						setGraphicsObjectConntrol(new PaintsMoveControl((IPointMoveble) currentShape));
					clickedCells.clear();
					return;
				}
				
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
					case B_SPLAIN:
						if(e.getClickCount() == 2){
							if( curve != null )
								curve.processMouseDoubleClick(0, 0);
						}
						break;
					case CURVE_BEZIE:
					
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
		repaint();
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
	
	public void showControlPoints(boolean showControlPoints){
		this.showControlPoints  = showControlPoints;
		
		if(showControlPoints && currentShape != null)
			setGraphicsObjectConntrol(new PaintsMoveControl((IPointMoveble) currentShape));
		else 
			graphicsObjectControl = null;
		
		repaint();
	}

	public void setGraphicsObjectConntrol(IGraphicsObjectControl pintsMoveControl) {
		graphicsObjectControl  = pintsMoveControl;
		repaint();
	}
	
	public void clearShapeList(){
		shapeList.clear();
		graphicsObjectControl = null;
		repaint();
	}

	public void setCurrentShape(IGraphicsObject object) {
		
		shapeList.add(object);
		currentShape = object;
	}
	
	public IGraphicsObject getShapeByCoord(int x, int y){
		
		for(IGraphicsObject object: shapeList){
			
			List<Cell> cells = (ArrayList<Cell>) object.getPoints();
			for(Cell cell: cells)
				if( cell.getX() == x / step && cell.getY() == y / step )
					return object;
			
		}
		return currentShape;
	}

	public void deleteLastShape() {
		if(shapeList.size() != 0){
			shapeList.remove(shapeList.size() - 1);
			if(shapeList.size() >= 1){
				currentShape = shapeList.get(shapeList.size() - 1);
				setGraphicsObjectConntrol(new PaintsMoveControl((IPointMoveble) currentShape));
			}
			else {
				graphicsObjectControl = null;
				currentShape = null;
			}
		}
		repaint();
	}
}
