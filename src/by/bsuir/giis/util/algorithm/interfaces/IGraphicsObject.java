package by.bsuir.giis.util.algorithm.interfaces;

import java.awt.Graphics;
import java.util.List;

import by.bsuir.giis.model.Cell;

public interface IGraphicsObject {
	
	boolean processMousePress(int x, int y);

    boolean processMouseRelease(int x, int y);

    boolean processMouseMove(int x, int y);

    boolean processMouseDoubleClick(int x, int y);
    
    void draw(Graphics g, int step);
    
    boolean isComplete();

	List<Cell> getPoints();
}
