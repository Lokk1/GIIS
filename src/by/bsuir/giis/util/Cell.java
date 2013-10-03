package by.bsuir.giis.util;

import java.awt.Color;
import java.awt.Point;

public class Cell {
	private int x;
	private int y;
	private int z = 0;
	private int t = 1;
	private Color color;
	
	public Cell(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.setColor(color);
	}
	
	public Cell(Point point, Color color) {
		this.x = point.x;
		this.y = point.y;
		this.setColor(color);
	}
	
	public String getDescription(){
		return "x: " + x + " y: " + y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}