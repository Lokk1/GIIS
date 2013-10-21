package by.bsuir.giis.control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import by.bsuir.giis.gui.MainFrame;
import by.bsuir.giis.gui.PaintPanel;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;
import by.bsuir.giis.model.DrawingMode;
import by.bsuir.giis.model.Shape;
import by.bsuir.giis.model.ShapeList;
import by.bsuir.giis.util.algorithm.conic.AbstractConic;
import by.bsuir.giis.util.algorithm.conic.Circle;
import by.bsuir.giis.util.algorithm.conic.Hyperbola;
import by.bsuir.giis.util.algorithm.conic.IConicAlgorithm;
import by.bsuir.giis.util.algorithm.curve.AbstractCurve;
import by.bsuir.giis.util.algorithm.curve.CurveBSplain;
import by.bsuir.giis.util.algorithm.curve.CurveBezie;
import by.bsuir.giis.util.algorithm.curve.CurveErmit;
import by.bsuir.giis.util.algorithm.curve.ICurveAlgorithm;
import by.bsuir.giis.util.algorithm.line.AbstractLine;
import by.bsuir.giis.util.algorithm.line.LineBREZ;
import by.bsuir.giis.util.algorithm.line.LineCDA;
import by.bsuir.giis.util.algorithm.line.LineWU;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

public class CellControl {

	List<Cell> cells = null;
	private ShapeList shapeList = new ShapeList();
	List<Cell> cellsForStep = null;
	MainFrame mainFrame;
	PaintPanel paintPanel;
	private int step;

	Graphics gr;
	
	private AbstractLine lineAlgorithm;
	private AbstractConic conicAlgorithm;
	private AbstractCurve curveAlgorithm;

	private AlgorithmType algorithmType;
	private DrawingMode mode = DrawingMode.AUTO_MODE;

	public CellControl(MainFrame mainFrame, PaintPanel paintPanel) {
		cells = new ArrayList<Cell>();
		cellsForStep = new ArrayList<Cell>();
		this.mainFrame = mainFrame;
		this.paintPanel = paintPanel;
	}

	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);

		for (int index = 0; index < cells.size(); index++) {
			g.setColor(cells.get(index).getColor());
			g.fillRect(cells.get(index).getX() * getStep(), cells.get(index)
					.getY() * getStep(), getStep(), getStep());
			g.setColor(null);
		}
		
	}
	
	public ShapeList getShapeList(){
		return shapeList;
	}

	public void nextStep() {

		if (!cellsForStep.isEmpty()) {
			cells.add(cellsForStep.get(0));
			cellsForStep.remove(cellsForStep.get(0));
		} else
			JOptionPane.showMessageDialog(null, "Drawing complete!");
	}

	public Cell get(int i) {
		if (i < cells.size()) {
			return cells.get(i);
		}
		return null;
	}

	public void addCell(Cell cell) {
		cells.add(cell);
		System.out.println("AddCell: " + cell.getDescription());
	}
	
	public void addAll(List<Cell> cells){
		this.cells.addAll(cells);
	}
	
	public boolean isEmpty(){
		return cells.isEmpty();
	}

	public void clear() {
		cells.clear();
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getStep() {
		return step;
	}

	public void setLineAlgorithm(AlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}

	public void setDrawingMode(DrawingMode mode) {
		this.mode = mode;
	}

	public void setCootdinatesForAlgorithm(Coordinates coordinates) {
		switch (algorithmType) {
		case CDA_LINE:
			switch (mode) {
			case AUTO_MODE:
				lineAlgorithm = new LineCDA(coordinates);
				lineAlgorithm.execution();
				paintPanel.setCurrentShape(lineAlgorithm);
				break;
			case STEP_MODE:
				lineAlgorithm = new LineCDA(coordinates);
				cellsForStep.addAll(lineAlgorithm.execution());
				break;
			}
			break;
		case BREZ_LINE:
			switch (mode) {
			case AUTO_MODE:
				lineAlgorithm = new LineBREZ(coordinates, step);
				lineAlgorithm.execution();
				paintPanel.setCurrentShape(lineAlgorithm);
				break;
			case STEP_MODE:
				lineAlgorithm = new LineBREZ(coordinates, step);
				cellsForStep.addAll(lineAlgorithm.execution());
				
				break;
			case MOVE_POINTS_MODE:
				paintPanel.setGraphicsObjectConntrol(new PaintsMoveControl(lineAlgorithm));
				break;
			}
			break;
		case WU_LINE:
			switch (mode) {
			case AUTO_MODE:
				lineAlgorithm = new LineWU(coordinates);
				lineAlgorithm.execution();
				paintPanel.setCurrentShape(lineAlgorithm);
				break;
			case STEP_MODE:
				lineAlgorithm = new LineWU(coordinates);
				cellsForStep.addAll(lineAlgorithm.execution());
				break;
			}
			break;
		case CIRCLE:
			switch (mode) {
			case AUTO_MODE:
				conicAlgorithm = new Circle(coordinates);
				conicAlgorithm.execution();
				paintPanel.setCurrentShape(conicAlgorithm);
				break;
			case STEP_MODE:
				conicAlgorithm = new Circle(coordinates);
				cellsForStep.addAll(conicAlgorithm.execution());
				break;
			}
			break;
		case HYPERBOLA:
			switch (mode) {
			case AUTO_MODE:
				conicAlgorithm = new Hyperbola(coordinates);
				conicAlgorithm.execution();
				paintPanel.setCurrentShape(conicAlgorithm);
				break;
			case STEP_MODE:
				conicAlgorithm = new Hyperbola(coordinates);
				cellsForStep.addAll(conicAlgorithm.execution());
				break;
			}
			break;
		case CURVE_ERMIT:
			switch (mode) {
			case AUTO_MODE:
				curveAlgorithm = new CurveErmit(coordinates);
				curveAlgorithm.execution();
				paintPanel.setCurrentShape(curveAlgorithm);
				break;
			case STEP_MODE:
				curveAlgorithm = new CurveErmit(coordinates);
				curveAlgorithm.execution();
				break;
			}
			break;
		case CURVE_BEZIE:
			switch (mode) {
			case AUTO_MODE:
				curveAlgorithm = new CurveBezie(coordinates);
				curveAlgorithm.execution();
				paintPanel.setCurrentShape(curveAlgorithm);
				break;
			case STEP_MODE:
				curveAlgorithm = new CurveBezie(coordinates);
				curveAlgorithm.execution();
				break;
			} 
			break;
		case B_SPLAIN:
			switch (mode) {
			case AUTO_MODE:
				curveAlgorithm = new CurveBSplain(coordinates);
				curveAlgorithm.execution();
				paintPanel.setCurrentShape(curveAlgorithm);

				break;
			case STEP_MODE:
				curveAlgorithm = new CurveBSplain(coordinates);
				curveAlgorithm.execution();
				break;
			}
			break;
		default:
			break;
		}
	}
}
