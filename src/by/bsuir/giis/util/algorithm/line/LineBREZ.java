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

		this.dX = (p2.x - p1.x); // �������� �� ��� x
		this.dY = (p2.y - p1.y); // �������� �� ��� y
		
		prepare();

	}

	public void prepare() {

		incx = sign(dX);
		/*
		 * ����������, � ����� ������� ����� ����� ����������. ���� dx < 0, �.�.
		 * ������� ��� ������ ������ �� ����, �� incx ����� ����� -1. ��� �����
		 * �������������� � ����� �����������.
		 */
		incy = sign(dY);
		/*
		 * ����������. ���� ������ ������� ����� ����� - ��� ����� �������������
		 * ����� ��� y (����� - �������������).
		 */

		if (dX < 0)
			dX = -dX;// ����� �� ����� ����������: "if (dx < dy)"
		if (dY < 0)
			dY = -dY;// ������� ���������� ������� dx = |dx|; dy = |dy|
		// ��� ��� ������� ����� �������� � ���: dx = Math.abs(dx); dy =
		// Math.abs(dy);

		if (dX > dY) {
			/*
			 * ���� dx > dy, �� ������ ������� "�������" ����� ��� ���, �.�. ��
			 * ������ �������, ��� �������. ������ � ����� ����� ����� ���� ��
			 * ��� (������� el = dx;), ������ "�����������" ������ �� ���� ����
			 * � ������������ � ���, ����� ������� � ������ ������ ��� ��� (pdx
			 * = incx;), ��� ���� �� y ����� ����� �����������.
			 */
			dX = incx;
			dY = 0;
			es = Math.abs(p2.y - p1.y);
			el = Math.abs(p2.x - p1.x);
		} else {
			// ������, ����� ������ ������ "�������", ��� �������, �.�. ��������
			// �� ��� y
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
		// ���������� 0, ���� �������� (x) ����� ����; -1, ���� x < 0 � 1, ����
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
