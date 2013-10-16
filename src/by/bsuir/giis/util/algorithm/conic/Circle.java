package by.bsuir.giis.util.algorithm.conic;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.views.AbstractView;

import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;

public class Circle extends AbstractConic{
	
	private Point p2;
	private int x, y, gap, delta;
	
	public Circle(Coordinates coordinates) {
		this.centerPoint = coordinates.get(0);
		this.p2 = coordinates.get(1);
		
		cells = new ArrayList<Cell>();
		
		prepare();
	}
	
	public void prepare(){
		
		cells.clear();
		x = 0;
		y = p2.x;
		gap = 0;
		delta = 2 - 2 * y;
	}
	
	public List<Cell> execution(){
		
		while(y >= 0){
			
			cells.add(new Cell(centerPoint.x + x, centerPoint.y + y, Color.GREEN));
			cells.add(new Cell(centerPoint.x + x, centerPoint.y - y, Color.GREEN));
			cells.add(new Cell(centerPoint.x - x, centerPoint.y - y, Color.GREEN));
			cells.add(new Cell(centerPoint.x - x, centerPoint.y + y, Color.GREEN));
			
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
