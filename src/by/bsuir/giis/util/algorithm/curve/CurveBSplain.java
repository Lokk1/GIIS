package by.bsuir.giis.util.algorithm.curve;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;

public class CurveBSplain extends AbstractCurve {
	
	private List<Point> points;
	private List<Cell> cells;
	
	private int iterationCount = 2000;
	
	private static final Matrix SPLINE_MATRIX = new Matrix(
			new double[][] { 
					{ -1, 3, -3, 1 }, 
					{ 3, -6, 3, 0 },
					{ -3, 0, 3, 0 }, 
					{ 1, 4, 1, 0 } }, 4, 4);

	public CurveBSplain(Coordinates coordinates) {
		
		
		this.points = new ArrayList<Point>();
		cells = new ArrayList<Cell>();
		
		this.beginPoint = coordinates.get(0);
		this.beginVector = coordinates.get(1);
		this.endVector = coordinates.get(2);
		this.endPoint = coordinates.get(3);
		
		this.object = this;
		
		prepare();

	}
	
	public CurveBSplain() {
		this.points = new ArrayList<Point>();
		this.cells = new ArrayList<Cell>();
		
		this.object = this;
	}

	@Override
	public List<Point> getControlPoints() {		
		return points;
	}

	@Override
	public void setControlPoints(List<Point> points) {
		this.points = points;
//		prepare();
		execution();
	}
	
	@Override
	public boolean processMousePress(int x, int y) {
		if(!complete) {
			points.add(new Point(x, y));
			execution();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void draw(Graphics g, int step) {
//		super.draw(g, step);
		
		if (!complete) {

			for (Cell cell : cells) {
				g.setColor(cell.getColor());
				g.fillRect(cell.getX() * step, cell.getY() * step, step, step);
				g.setColor(null);
			}
		}
	}

	@Override
	public void execution() {
		
		cells.clear();
		
		if(points.size() < 4)
			return;
		
		for (int j = 1; j < points.size() - 2; j++) {

			Matrix hermitGeometryVector = new Matrix(new double[][] {
					{ points.get(j - 1).x, points.get(j - 1).y },
					{ points.get(j).x, points.get(j).y },
					{ points.get(j + 1).x, points.get(j + 1).y },
					{ points.get(j + 2).x, points.get(j + 2).y } }, 4, 2);
			
			for (int i = 0; i <= iterationCount; i++) {
				double t = (double) i / iterationCount;
				Matrix tMatrix = new Matrix(new double[][] { { Math.pow(t, 3),
						Math.pow(t, 2), Math.pow(t, 1), Math.pow(t, 0) } }, 1,
						4);
				Matrix point = tMatrix.times(SPLINE_MATRIX)
						.times(hermitGeometryVector).times(1f / 6f);
				cells.add(new Cell((int) point.get(0, 0),
						(int) point.get(0, 1), Color.BLACK));
			}
		}
	}

	@Override
	public void prepare() {
		
		

	}

	public void nextSegment() {
		points.clear();	
	}

	@Override
	public void nextSegment(Point p1, Point p2, Point p3, Point p4) {
		
	}

}