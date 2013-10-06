package by.bsuir.giis.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Coordinates {
	
	private List<Point> points;
	
	public Coordinates() {
		points = new ArrayList<Point>();
	}
	
	public void add( Point point ){
		points.add(point);
	}
	
	public Point get(int index){
		return points.get(index);
	}
	
	public void clear(){
		points.clear();
	}

}
