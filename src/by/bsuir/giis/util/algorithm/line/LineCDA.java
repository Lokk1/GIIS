package by.bsuir.giis.util.algorithm.line;

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
public class LineCDA implements ILineAlgorithm {

	ArrayList<Cell> cells;

	private Point p1, p2;

	private float length;
	private float dX;
	private float dY;
	private float newX;
	private float newY;
	int count = 0;

	public LineCDA(Point p1, Point p2) {

		this.p1 = p1;
		this.p2 = p2;

		cells = new ArrayList<Cell>();
		
		prepare();
	}

	@Override
	public void prepare() {

		length = Math.max(Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));

		dX = (p2.x - p1.x) / length;
		dY = (p2.y - p1.y) / length;

		newX = (float) (p1.x + 0.5 * sign(dX));
		newY = (float) (p1.y + 0.5 * sign(dY));

		cells.add(new Cell((int) newX, (int) newY, Color.ORANGE));
	}

	@Override
	public List<Cell> execution() {

		for (; count < length; count++) {

			newX = newX + dX;
			newY = newY + dY;

			cells.add(new Cell((int) newX, (int) newY, Color.ORANGE));

		}

		return cells;
	}

	@Override
	public int sign(float num) {
		if (num > 0) {
			return 1;
		}
		if (num == 0) {
			return 0;
		}
		if (num < 0) {
			return -1;
		}
		return 0;
	}

	@Override
	public int sign(int num) {
		return 0;
	}

	@Override
	public int IPart(double num) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double FPart(double num) {
		// TODO Auto-generated method stub
		return 0;
	}
}
