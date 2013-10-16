package by.bsuir.giis.util.algorithm.curve;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;

public class CurveBSplain extends AbstractCurve {

	private List<Point> points;
	private int iterationCount = 2000;
	private int segmentCount = 2;
	
	private static final Matrix SPLINE_MATRIX = new Matrix(
			new double[][] { 
					{ -1, 3, -3, 1 }, 
					{ 3, -6, 3, 0 },
					{ -3, 0, 3, 0 }, 
					{ 1, 4, 1, 0 } }, 4, 4);

	public CurveBSplain(Coordinates coordinates) {
		points = new ArrayList<Point>();
		cells = new ArrayList<Cell>();
		this.beginPoint = coordinates.get(0);
		this.beginVector = coordinates.get(1);
		this.endVector = coordinates.get(2);
		this.endPoint = coordinates.get(3);

		prepare();

	}

	// Матрица для сплайна

	@Override
	public List<Cell> execution() {
		for (int j = 1; j < points.size() - 2; j++) {

			Matrix hermitGeometryVector = new Matrix(new double[][] {
					{ points.get(j - 1).x, points.get(j - 1).y },
					{ points.get(j).x, points.get(j).y },
					{ points.get(j + 1).x, points.get(j + 1).y },
					{ points.get(j + 2).x, points.get(j + 2).y } }, 4, 2);
			
			for (int i = 0; i <= iterationCount; i++) {
				double t = (double) i / iterationCount;
				Matrix tMatrix = new Matrix(new double[][] { { Math.pow(t, 3),
						Math.pow(t, 2), Math.pow(t, 1), Math.pow(t, 0) } }, 1,
						4);
				Matrix point = tMatrix.times(SPLINE_MATRIX)
						.times(hermitGeometryVector).times(1f / 6f);
				cells.add(new Cell((int) point.get(0, 0),
						(int) point.get(0, 1), Color.BLACK));
			}
//			nextSegment(beginPoint, beginVector, endVector, endPoint);
			if (segmentCount == 2){
				nextSegment(beginVector, endVector, endPoint, beginPoint);
				segmentCount++;
			}else if(segmentCount ==3 ){
				nextSegment(endVector, endPoint, beginPoint, beginVector);
				segmentCount++;
			}else nextSegment(endPoint, beginPoint, beginVector, endVector);
		}

		return cells;
	}

	@Override
	public void prepare() {
		points.clear();
		cells.clear();
		points.add(beginPoint);
		points.add(beginVector);
		points.add(endVector);
		points.add(endPoint);

	}

	public void nextSegment(Point beginPoint, Point beginVector, Point endVector, Point endPoint) {
		points.clear();
//		cells.clear();
		points.add(beginPoint);
		points.add(beginVector);
		points.add(endVector);
		points.add(endPoint);
		
		execution();
	}
	
	public void nextSegment() {
		points.clear();	
	}

}