package by.bsuir.giis.util.algorithm.curve;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;
import by.bsuir.giis.model.Matrix;

public class CurveBezie extends AbstractCurve {

	private List<Point> points = new ArrayList<Point>();

	protected float[][] data, cur_matrix;
	public final float[][] BEZE = new float[][] { new float[] { -1, 3, -3, 1 },
			new float[] { 3, -6, 3, 0 }, new float[] { -3, 3, 0, 0 },
			new float[] { 1, 0, 0, 0 } };
	private float t, dt, dx, dy;

	public CurveBezie(Coordinates coordinates) {
		this.beginPoint = coordinates.get(0);
		this.beginVector = coordinates.get(1);
		this.endVector = coordinates.get(2);
		this.endPoint = coordinates.get(3);

		
		this.cells = new ArrayList<Cell>();

		prepare();

	}

	public void prepare() {
		t = 0.0f;
		points.clear();
		cells.clear();
		points.add(beginPoint);
		points.add(beginVector);
		points.add(endVector);
		points.add(endPoint);

		data = new float[4][2];
		for (int i = 0; i < points.size(); i++) {
			data[i][0] = points.get(i).x;
			data[i][1] = points.get(i).y;
		}
		cur_matrix = Matrix.mult(BEZE, data);
		int minx = Math.min(Math.min(points.get(0).x, points.get(3).x),
				Math.min(points.get(2).x, points.get(1).x));

		int miny = Math.min(Math.min(points.get(0).y, points.get(3).y),
				Math.min(points.get(2).y, points.get(1).y));

		int maxx = Math.min(Math.max(points.get(0).x, points.get(3).x),
				Math.max(points.get(1).x, points.get(2).x));

		int maxy = Math.max(Math.min(points.get(0).y, points.get(3).y),
				Math.max(points.get(2).y, points.get(1).y));

		dx = Math.abs(maxx - minx);
		dy = Math.abs(maxy - miny);
		dt = (float) (2f / (3 * (dx + dy)));
	}

	public List<Cell> execution() {
		while (t <= 1) {
			float[][] tt = new float[][] { new float[] { (float) Math.pow(t, 3), (float) Math.pow(t, 2), t,
					1f } };
			float[][] out = Matrix.mult(tt, cur_matrix);

			t += dt;
			cells.add(new Cell((int) Math.round(out[0][0]), (int) Math
					.round(out[0][1]), Color.GREEN));
			
		}
		return cells;
	}

	@Override
	public void nextSegment(Point p1, Point p2, Point p3, Point p4) {
		// TODO Auto-generated method stub
		
	}

}
