package by.bsuir.giis.util.algorithm.interfaces;

import java.awt.Point;
import java.util.List;

public interface IPointMoveble {
	
	
	/**
	 * @return ������ ������� �����
	 */
	List<Point> getControlPoints();
	
	/**
	 * @param points ������ ������� �����
	 */
	void setControlPoints(List<Point> points);

}
