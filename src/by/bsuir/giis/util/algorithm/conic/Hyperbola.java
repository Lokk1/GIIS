package by.bsuir.giis.util.algorithm.conic;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;

/**
 * 
 * @author Алейников Евгений
 *
 */
public class Hyperbola extends AbstractConic{
	
	private int a;
	private int b;
	
	private int Xi;
	private int Yi;
	
	private int Di;
	
	public Hyperbola(Coordinates coordinates) {
		
		this.centerPoint = coordinates.get(0);
		
		this.a = coordinates.get(1).x;
		this.b = coordinates.get(1).y;
		
		cells = new ArrayList<Cell>();
		
		prepare();
	}
	
	public void prepare(){
		
		cells.clear();
		
		Xi = a;
		Yi = 0;
		
        Di = 2 * a * (int) Math.pow(b, 2) + (int) Math.pow(b, 2) - (int) Math.pow(a, 2);
        System.out.println("D1 = " + Di);
	}
	
	public List<Cell> execution(){
		
		while(Yi <= 100 || Xi <= 20){
			
			cells.add(new Cell(centerPoint.x+Xi, centerPoint.y+Yi, Color.ORANGE));
	        cells.add(new Cell(centerPoint.x+Xi, centerPoint.y-Yi, Color.ORANGE));

	        cells.add(new Cell(centerPoint.x-Xi, centerPoint.y+Yi, Color.ORANGE));
	        cells.add(new Cell(centerPoint.x-Xi, centerPoint.y-Yi, Color.ORANGE));
	        
	        System.out.print("Di:"+Di+" centerPoint.x:" + Xi + " centerPoint.y:" + Yi);
			
	        /* 1-й случай */
	        if(Di < 0){ 
				if((2 * Di - 2 * Yi * (a * a) + (a * a)) <= 0){ // Пиксель H
					Xi = Xi + 1;
					Di = Di + 2 * Xi * (b * b) + (b * b);
					System.out.println("H");
				}else{ // Пиксель D
					System.out.println("D");
					Yi = Yi + 1;
					Xi = Xi + 1;
					Di = Di - 2 * Yi * (a * a) - (a * a) + 2 * Xi * (b * b) + (b * b);
				}
			}
	        /* 2-й случай */
			if(Di > 0){
				if( (2 * Di - 2 * Xi * (b * b) - (b * b)) <= 0 ){ // Пиксель D 
					
					System.out.println("D");
					Yi = Yi + 1;
					Xi = Xi + 1;
					Di = Di - 2 * Yi * (a * a) - (a * a) + 2 * Xi * (b * b) + (b * b);
				}
				else{// Пиксель V
					System.out.println("V");
					Yi = Yi + 1;
					Di = Di - 2 * Yi * (a * a) + (a * a);
				}
			}
			/* 3-й случай */
			if( Di == 0 ){ // Пиксель D
				System.out.println("D");
				Xi = Xi + 1;
				Yi = Yi + 1;
				Di = Di - 2 * Yi * (a * a) - (a * a) + 2 * Xi * (b * b) + (b * b);
			}
		}
		
		return cells;
	}
}
