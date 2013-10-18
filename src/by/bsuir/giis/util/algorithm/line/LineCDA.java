package by.bsuir.giis.util.algorithm.line;

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
public class LineCDA  extends AbstractLine  {

	private float length;
	private float dX;
	private float dY;
	private float newX;
	private float newY;
	int count = 0;

	public LineCDA(Coordinates coordinates) {

		this.beginPoint = coordinates.get(0);
		this.endPoint = coordinates.get(1);;

		cells = new ArrayList<Cell>();
		
		prepare();
	}

	@Override
	public void prepare() {

		length = Math.max(Math.abs(endPoint.x - beginPoint.x), Math.abs(endPoint.y - beginPoint.y));

		dX = (endPoint.x - beginPoint.x) / length;
		dY = (endPoint.y - beginPoint.y) / length;

		newX = (float) (beginPoint.x + 0.5 * sign(dX));
		newY = (float) (beginPoint.y + 0.5 * sign(dY));
		
		cells.clear();

		cells.add(new Cell((int) newX, (int) newY, Color.ORANGE));
	}

	@Override
	public List<Cell> execution() {
		
		long time = System.nanoTime();
		
		for (count = 0; count < length; count++) {

			newX = newX + dX;
			newY = newY + dY;

			cells.add(new Cell((int) newX, (int) newY, Color.ORANGE));

		}
		System.out.println(System.nanoTime() - time);
		return cells;
	}
}
