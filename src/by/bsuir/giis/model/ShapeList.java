package by.bsuir.giis.model;

import java.util.ArrayList;
import java.util.List;

public class ShapeList {
	
	private List<Shape>  shapes;
	
	public ShapeList() {
		shapes = new ArrayList<Shape>();
	}

	public Shape get(int index) {
		return shapes.get(index);
	}

	public void add(Shape shape) {
		this.shapes.add(shape);
	}
	
	public int size(){
		return shapes.size();
	}
	
	public Shape getShape(int index){
		return shapes.get(index);
	}
}
