package by.bsuir.giis.control;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.List;

import by.bsuir.giis.util.algorithm.interfaces.IPointMoveble;

public class PaintsMoveControl implements IGraphicsObjectControl{
	
	private IPointMoveble object;
	private boolean pointSelected;
	private List<Point> points;
	Point currPoint;
	Point startPoint;
	Point endPoint;
	private int indexOfPoints;

	public PaintsMoveControl(IPointMoveble object) {
		this.object = object;
		this.pointSelected = false;
		points = object.getControlPoints();
	}

	@Override
	public boolean processMousePress(int x, int y, int step) {
		points = object.getControlPoints();
		
		for( int i = 0; i < points.size(); i++ ){
			int leftX = points.get(i).x * step - 30 / 2 + step / 2;
			int topY = points.get(i).y * step - 30 / 2 + step / 2 ;
			if((x > leftX && x < leftX + 30)
					&& (y > topY && y < topY + 30)) {
				pointSelected = true;
				currPoint = points.get(i);
				indexOfPoints = i;
				return true;
			}
		}
		
		pointSelected = false;
		return false;
	}

	@Override
	public boolean processMouseRelease(int x, int y, int step) {
		
		pointSelected = false;
		object.setControlPoints(points);
		return true;
		
	}

	@Override
	public boolean processMouseMove(int x, int y, int step) {
		if(pointSelected) {
			currPoint = new Point(x / step, y / step);
			
			points.remove(indexOfPoints);
			points.add(indexOfPoints, currPoint);
			
			object.setControlPoints(points);
			
		}
		return true;
	}

	@Override
	public boolean processMouseDoubleClick(int x, int y, int step) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics2D g, int step) {
		Color oldColor = g.getColor();
		Stroke oldStroke = g.getStroke();
		
		g.setColor(Color.ORANGE);
		g.setStroke(new BasicStroke(2));		
		
		for(Point point: points) {
			g.setComposite(AlphaComposite
					.getInstance(AlphaComposite.SRC_OVER, 1f));			
			g.drawRect(
					point.x*step - 30/2 + step/2, 
					point.y*step - 30/2 + step/2,
				30, 30);
			g.setComposite(AlphaComposite
					.getInstance(AlphaComposite.SRC_OVER, 0.4f));			
			g.fillRect(
					point.x*step - 30/2 + step/2,
					point.y*step - 30/2 + step/2,
				30, 30);
		}
		
		g.setColor(oldColor);
		g.setStroke(oldStroke);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

}
