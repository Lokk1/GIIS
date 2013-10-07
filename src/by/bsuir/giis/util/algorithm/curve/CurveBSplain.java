package by.bsuir.giis.util.algorithm.curve;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import by.bsuir.giis.util.Cell;
import by.bsuir.giis.util.Coordinates;

public class CurveBSplain implements ICurveAlgorithm {

	private List<Cell> cells;
	private List<Point> points;
	private Point p1, p2, p3, p4;
	private int iterationCount = 2000;

	public CurveBSplain(Coordinates coordinates) {
		points = new ArrayList<Point>();
		cells = new ArrayList<Cell>();
		this.p1 = coordinates.get(0);
		this.p2 = coordinates.get(1);
		this.p3 = coordinates.get(2);
		this.p4 = coordinates.get(3);

		prepare();

	}

	// Матрица для сплайна
	private static final Matrix SPLINE_MATRIX = new Matrix(
			new double[][] { { -1, 3, -3, 1 }, { 3, -6, 3, 0 },
					{ -3, 0, 3, 0 }, { 1, 4, 1, 0 } }, 4, 4);

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
		}

		return cells;
	}

	@Override
	public void prepare() {
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);

	}

	public void nextSegment(Point p1, Point p2, Point p3, Point p4) {
		points.clear();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
	}
	
	public void nextSegment() {
		points.clear();
		
		
		
	}

}