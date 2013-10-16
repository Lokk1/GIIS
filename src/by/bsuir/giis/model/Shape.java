package by.bsuir.giis.model;

import java.util.ArrayList;
import java.util.List;

public class Shape {
	
	private List<Cell> points;
	
	public Shape(List<Cell> points){
		this.points = points;
	}
	
	public Shape() {
		points = new ArrayList<Cell>();
	}

	public List<Cell> getShape() {
		return points;
	}

	public void setShape(List<Cell> points) {
		this.points = points;
	}
	
	public int size(){
		return points.size();
	}
}
