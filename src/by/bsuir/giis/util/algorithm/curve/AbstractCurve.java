package by.bsuir.giis.util.algorithm.curve;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.control.CellControl;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.util.algorithm.interfaces.IGraphicsObject;
import by.bsuir.giis.util.algorithm.interfaces.IPointMoveble;

public abstract class AbstractCurve implements IGraphicsObject, IPointMoveble {
	
	protected Point beginPoint;
	protected Point endPoint;
	protected Point beginVector;
	protected Point endVector;
	
	protected boolean complete = false;
	
	protected List<Cell> cells;
	

	public abstract void prepare();

	public abstract List<Cell> execution();

	public abstract void nextSegment(Point p1, Point p2, Point p3, Point p4);

	@Override
	public List<Point> getControlPoints() {
		List<Point> points = new ArrayList<Point>();
		points.add(beginVector);
		points.add(endVector);
		points.add(beginPoint);
		points.add(endPoint);
		
		return points;
	}

	@Override
	public void setControlPoints(List<Point> points) {
		beginVector = points.get(0);
		endVector = points.get(1);
		beginPoint = points.get(2);
		endPoint = points.get(3);
		prepare();
		execution();
	}

	@Override
	public boolean processMousePress(int x, int y) {
		
		if(!complete){
			
		}
		
		return true;
	}

	@Override
	public boolean processMouseRelease(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processMouseMove(int x, int y) {
		return false;
	}

	@Override
	public boolean processMouseDoubleClick(int x, int y) {
		complete = true;
		return true;
	}

	@Override
	public void draw(Graphics g, int step) {
		for(Cell cell: cells){
			g.setColor(cell.getColor());
			g.fillRect(cell.getX() * step, cell.getY() * step, step, step);
			g.setColor(null);
		}

	}

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Cell> getPoints() {
		return cells;
	}


}
