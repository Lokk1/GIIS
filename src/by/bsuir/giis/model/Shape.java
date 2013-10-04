package by.bsuir.giis.model;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.util.Cell;

public class Shape {
	
	private List<Cell> shape;
	
	public Shape() {
		setShape(new ArrayList<Cell>());
	}

	public List<Cell> getShape() {
		return shape;
	}

	public void setShape(List<Cell> shape) {
		this.shape = shape;
	}
}
