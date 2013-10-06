package by.bsuir.giis.model;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.util.Cell;

public class Shape {
	
	private List<Cell> points;
	
	public Shape() {
		points = new ArrayList<Cell>();
	}

	public List<Cell> getShape() {
		return points;
	}

	public void setShape(List<Cell> points) {
		this.points = points;
	}
}
