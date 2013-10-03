package by.bsuir.giis.util.algorithm.line;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.util.Cell;

public class LineBREZ implements ILineAlgorithm {

	ArrayList<Cell> cells;

	private Point p1;
	private Point p2;

	private int newX;
	private int newY;

	private int dX;
	private int dY;

	private int incx;
	private int incy;

	int es = 0;
	int el = 0;

	private int e;


	public LineBREZ(Point p1, Point p2) {

		this.p1 = p1;
		this.p2 = p2;
		
		cells = new ArrayList<Cell>();

		this.newX = p1.x;
		this.newY = p1.y;

		this.dX = (p2.x - p1.x); // проекция на ось x
		this.dY = (p2.y - p1.y); // проекция на ось y
		
		prepare();

	}

	public void prepare() {

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
			es = Math.abs(p2.y - p1.y);
			el = Math.abs(p2.x - p1.x);
		} else {
			// случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута
			// по оси y
			dX = 0;
			dY = incy;
			es = Math.abs(p2.x - p1.x);
			el = Math.abs(p2.y - p1.y);
		}

		e = el;

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

	public int sign(int x) {
		return (x > 0) ? 1 : (x < 0) ? -1 : 0;
		// возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если
		// x > 0.
	}

	@Override
	public int sign(float num) {
		// TODO Auto-generated method stub
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
