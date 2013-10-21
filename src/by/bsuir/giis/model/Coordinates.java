package by.bsuir.giis.model;

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
	
	public void remove(int arg0){
		points.remove(arg0);
	}
	
	public void add(int index, Point element){
		points.add(index, element);
	}
	
	public Point get(int index){
		return points.get(index);
	}
	
	public void clear(){
		points.clear();
	}
	
	public boolean isEmpty(){
		return points.isEmpty();
	}
	
	public int size(){
		return points.size();
	}

}
