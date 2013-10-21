package by.bsuir.giis.util.algorithm.curve;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.control.CellControl;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;
import by.bsuir.giis.util.algorithm.interfaces.IGraphicsObject;
import by.bsuir.giis.util.algorithm.interfaces.IPointMoveble;

public abstract class AbstractCurve implements IGraphicsObject, IPointMoveble {
	
	protected IGraphicsObject object;
	
	protected Point beginPoint;
	protected Point endPoint;
	protected Point beginVector;
	protected Point endVector;
	
	protected boolean complete = false;
	
	protected List<Cell> cells;
	
	protected List<Point> points;
	
	protected Coordinates coordinates = null;
	

	public abstract void prepare();

	public abstract void execution();

	public abstract void nextSegment(Point p1, Point p2, Point p3, Point p4);


	@Override
	public boolean processMouseRelease(int x, int y) {
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

			for (Cell cell : cells) {
				g.setColor(cell.getColor());
				g.fillRect(cell.getX() * step, cell.getY() * step, step, step);
				g.setColor(null);
			}
	}

	@Override
	public boolean isComplete() {
		return complete;
	}
	
	@Override
	public List<Cell> getPoints() {
		return cells;
	}


}
