package by.bsuir.giis.util.algorithm.conic;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.util.Cell;

/**
 * 
 * @author Алейников Евгений
 *
 */
public class Hyperbola implements IConicAlgorithm{
	
	List<Cell> cells;
	
	private int x;
	private int y;
	
	private int a;
	private int b;
	
	private int Xi;
	private int Yi;
	
	private int Di;
	
	public Hyperbola(Point p1, Point p2) {
		
		this.x = p1.x;
		this.y = p1.y;
		this.a = p2.x;
		this.b = p2.y;
		
		cells = new ArrayList<Cell>();
		
		prepare();
	}
	
	public void prepare(){
		
		Xi = a;
		Yi = 0;
		
        Di = 2 * a * (b * b) + (b * b) - (a * a);
	}
	
	public List<Cell> execution(){
		
		while(Yi <= 200 || Xi <= 200){
			
			cells.add(new Cell(x+Xi, y+Yi, Color.ORANGE));
	        cells.add(new Cell(x+Xi, y-Yi, Color.ORANGE));

	        cells.add(new Cell(x-Xi, y+Yi, Color.ORANGE));
	        cells.add(new Cell(x-Xi, y-Yi, Color.ORANGE));
			
	        /* 1-й случай */
	        if(Di < 0){ 
				if((2 * Di - 2 * Yi * (a * a) + (a * a)) <= 0){ // Пиксель H
					Xi = Xi + 1;
					Di = Di + 2 * Xi * (b * b) + (b * b);
				}else{ // Пиксель D
					Yi = Yi + 1;
					Xi = Xi + 1;
					Di = Di - 2 * Yi * (a * a) - (a * a) + 2 * Xi * (b * b) + (b * b);
				}
			}
	        /* 2-й случай */
			if(Di > 0){
				if( (2 * Di - 2 * Xi * (b * b) - (b * b)) <= 0 ){ // Пиксель D 
					Yi = Yi + 1;
					Xi = Xi + 1;
					Di = Di - 2 * Yi * (a * a) - (a * a) + 2 * Xi * (b * b) + (b * b);
				}
				else{// Пиксель V
					Yi = Yi + 1;
					Di = Di - 2 * Yi * (a * a) + (a * a);
				}
			}
			/* 3-й случай */
			if( Di == 0 ){ // Пиксель D
				Xi = Xi + 1;
				Yi = Yi + 1;
				Di = Di - 2 * Yi * (a * a) - (a * a) + 2 * Xi * (b * b) + (b * b);
			}
		}
		
		return cells;
	}
}
