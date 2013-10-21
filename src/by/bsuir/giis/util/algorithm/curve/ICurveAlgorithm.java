package by.bsuir.giis.util.algorithm.curve;

import java.awt.Point;
import java.util.List;

import by.bsuir.giis.model.Cell;

public interface ICurveAlgorithm {

	public void prepare();

	public List<Cell> execution();

	public void nextSegment(Point p1, Point p2, Point p3, Point p4);

}
