package by.bsuir.giis.util.algorithm.line;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;

public class LineBREZ extends AbstractLine {

	private int newX;
	private int newY;

	private int dX;
	private int dY;

	private int incx;
	private int incy;

	int es = 0;
	int el = 0;

	private int e;


	public LineBREZ(Coordinates coordinates, int step) {

		this.beginPoint = coordinates.get(0);
		this.endPoint = coordinates.get(1);
		
		this.cells = new ArrayList<Cell>();
		
		prepare();

	}

	public LineBREZ(Point point1, Point point2) {
		// TODO Auto-generated constructor stub
		this.beginPoint = point1;
		this.endPoint = point2;
		
		this.cells = new ArrayList<Cell>();
		
		prepare();
	}

	public void prepare() {
		
		this.newX = beginPoint.x;
		this.newY = beginPoint.y;

		this.dX = (endPoint.x - beginPoint.x); // проекция на ось x
		this.dY = (endPoint.y - beginPoint.y); // проекция на ось y

		incx = sign(dX);
		/*
		 * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е.
		 * отрезок идёт справа налево по иксу, то incx будет равен -1. Это будет
		 * использоваться в цикле постороения.
		 */
		incy = sign(dY);
		/*
		 * Аналогично. Если рисуем отрезок снизу вверх - это будет отрицательный
		 * сдвиг для y (иначе - положительный).
		 */

		if (dX < 0)
			dX = -dX;// далее мы будем сравнивать: "if (dx < dy)"
		if (dY < 0)
			dY = -dY;// поэтому необходимо сделать dx = |dx|; dy = |dy|
		// эти две строчки можно записать и так: dx = Math.abs(dx); dy =
		// Math.abs(dy);

		if (dX > dY) {
			/*
			 * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он
			 * скорее длинный, чем высокий. Значит в цикле нужно будет идти по
			 * икс (строчка el = dx;), значит "протягивать" прямую по иксу надо
			 * в соответствии с тем, слева направо и справа налево она идёт (pdx
			 * = incx;), при этом по y сдвиг такой отсутствует.
			 */
			dX = incx;
			dY = 0;
			es = Math.abs(endPoint.y - beginPoint.y);
			el = Math.abs(endPoint.x - beginPoint.x);
		} else {
			// случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута
			// по оси y
			dX = 0;
			dY = incy;
			es = Math.abs(endPoint.x - beginPoint.x);
			el = Math.abs(endPoint.y - beginPoint.y);
		}
		
		e = el;

		cells.clear();

		cells.add(new Cell((int) newX, (int) newY, Color.RED));
	}

	public List<Cell> execution() {

		for (int t = 0; t < el; t++) {
			e -= 2 * es;
			if (e < 0) {
				e += 2 * el;
				newX += incx;
				newY += incy;
			} else {
				newX += dX;
				newY += dY;
			}

			cells.add(new Cell((int) newX, (int) newY, Color.RED));
		}
		return cells;
	}
}
