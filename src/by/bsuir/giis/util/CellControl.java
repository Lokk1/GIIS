package by.bsuir.giis.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import by.bsuir.giis.gui.MainFrame;
import by.bsuir.giis.util.algorithm.conic.Circle;
import by.bsuir.giis.util.algorithm.conic.Hyperbola;
import by.bsuir.giis.util.algorithm.conic.IConicAlgorithm;
import by.bsuir.giis.util.algorithm.curve.CurveErmit;
import by.bsuir.giis.util.algorithm.curve.ICurveAlgorithm;
import by.bsuir.giis.util.algorithm.line.ILineAlgorithm;
import by.bsuir.giis.util.algorithm.line.LineBREZ;
import by.bsuir.giis.util.algorithm.line.LineCDA;
import by.bsuir.giis.util.algorithm.line.LineWU;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

public class CellControl {
	
	List<Cell> cells = null;
	List<Cell> cellsForStep = null;
	MainFrame mainFrame;
	private int step;
	
	private ILineAlgorithm lineAlgorithm;
	private IConicAlgorithm conicAlgorithm;
	private ICurveAlgorithm curveAlgorithm;
	
	private AlgorithmType algorithmType;
	private DrawingMode mode = DrawingMode.AUTO_MODE;

	public CellControl(MainFrame mainFrame) {
		cells = new ArrayList<Cell>();
		cellsForStep = new ArrayList<Cell>();
		this.mainFrame = mainFrame;
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

	public void addPointsForAlgorithm(Point p1, Point p2) {
		switch (algorithmType) {
		case CDA_LINE:
			switch (mode) {
			case AUTO_MODE:
				lineAlgorithm = new LineCDA(p1, p2);
				cells.addAll(lineAlgorithm.execution());
				break;
			case STEP_MODE:
				lineAlgorithm = new LineCDA(p1, p2);
				cellsForStep.addAll(lineAlgorithm.execution());
				break;
			}
			break;
		case BREZ_LINE:
			switch (mode) {
			case AUTO_MODE:
				lineAlgorithm = new LineBREZ(p1, p2);
				cells.addAll(lineAlgorithm.execution());
				break;
			case STEP_MODE:
				lineAlgorithm = new LineBREZ(p1, p2);
				cellsForStep.addAll(lineAlgorithm.execution());
				break;
			}
			break;
		case WU_LINE:
			switch (mode) {
			case AUTO_MODE:
				lineAlgorithm = new LineWU(p1, p2);
				cells.addAll(lineAlgorithm.execution());
				break;
			case STEP_MODE:
				lineAlgorithm = new LineWU(p1, p2);
				cellsForStep.addAll(lineAlgorithm.execution());
				break;
			}
			break;
		case CIRCLE:
			switch (mode) {
			case AUTO_MODE:
				conicAlgorithm = new Circle(p1, p2);
				cells.addAll(conicAlgorithm.execution());
				break;
			case STEP_MODE:
				conicAlgorithm = new Circle(p1, p2);
				cellsForStep.addAll(conicAlgorithm.execution());
				break;
			}
			break;
		case HYPERBOLA:
			switch (mode) {
			case AUTO_MODE:
				conicAlgorithm = new Hyperbola(p1, p2);
				cells.addAll(conicAlgorithm.execution());
				break;
			case STEP_MODE:
				conicAlgorithm = new Hyperbola(p1, p2);
				cellsForStep.addAll(conicAlgorithm.execution());
				break;
			}
			break;
		case CURVE_ERMIT:
			switch (mode) {
			case AUTO_MODE:
				curveAlgorithm = new CurveErmit(new Point(25,25),new Point(75,50),new Point(25,125),new Point(75,150));
				cells.addAll(curveAlgorithm.execution());
				break;
			case STEP_MODE:
				curveAlgorithm = new CurveErmit(new Point(25,25),new Point(75,50),new Point(25,125),new Point(75,150));
				cellsForStep.addAll(curveAlgorithm.execution());
				break;
			}
			break;
		default:
			break;
		}
	}
	
	public void addPointsForAlgorithm(Point p1, Point p2, Point p3, Point p4){
		
	}

	public void nextStep() {

		if (!cellsForStep.isEmpty()) {
			cells.add(cellsForStep.get(0));
			cellsForStep.remove(cellsForStep.get(0));
		}else JOptionPane.showMessageDialog(null, "Drawing complete!");
	}

	public void addCell(Cell cell) {
		cells.add(cell);
		System.out.println("AddCell: " + cell.getDescription());
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
}
