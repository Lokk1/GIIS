package by.bsuir.giis.util.algorithm.interfaces;

import java.awt.Point;
import java.util.List;

public interface IPointMoveble {
	
	
	/**
	 * @return список опорных точек
	 */
	List<Point> getControlPoints();
	
	/**
	 * @param points список опорных точек
	 */
	void setControlPoints(List<Point> points);

}
