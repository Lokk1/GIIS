package by.bsuir.giis.util.algorithm.conic;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.util.Cell;

public class Circle implements IConicAlgorithm{
	
	private Point p1, p2;
	ArrayList<Cell> cells;
	private int x, y, gap, delta;
	
	public Circle(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		
		cells = new ArrayList<Cell>();
		
		prepare();
	}
	
	public void prepare(){
		x = 0;
		y = p2.x;
		gap = 0;
		delta = 2 - 2 * y;
	}
	
	public List<Cell> execution(){
		
		while(y >= 0){
			
			cells.add(new Cell(p1.x + x, p1.y + y, Color.GREEN));
			cells.add(new Cell(p1.x + x, p1.y - y, Color.GREEN));
			cells.add(new Cell(p1.x - x, p1.y - y, Color.GREEN));
			cells.add(new Cell(p1.x - x, p1.y + y, Color.GREEN));
			
			gap = 2 * (delta + y) - 1;
			
			if (delta < 0 && gap <= 0)
            {
                x++;
                delta += 2 * x + 1;
                continue;
            }
            if (delta > 0 && gap > 0)
            {
                y--;
                delta -= 2 * y + 1;
                continue;
            }
            x++;
            delta += 2 * (x - y);
            y--;
		}
		
		return cells;
	}
}
