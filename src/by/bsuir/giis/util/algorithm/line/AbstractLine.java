package by.bsuir.giis.util.algorithm.line;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.giis.control.CellControl;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.util.algorithm.interfaces.IGraphicsObject;
import by.bsuir.giis.util.algorithm.interfaces.IPointMoveble;

public abstract class AbstractLine implements IPointMoveble, IGraphicsObject{
	
	protected Point beginPoint;
	protected Point endPoint;
	
	protected CellControl cControl;
	
	protected List<Cell> cells;
	/**
	 * Подготовка алгоритма к работе
	 */
	public abstract void prepare();

	/**
	 * Выполнение алгоритма
	 * 
	 * @return массив точек
	 */
	public abstract List<Cell> execution();

	/**
	 * 
	 * @param num
	 *            число с плавающей точкой
	 * @return -1, 0, 1 для отрицательного, нулевого, и положительного 
	 * 		   аргумента соответственно
	 */
	public int sign(float num){
		return (num > 0) ? 1 : (num < 0) ? -1 : 0;
	}
	
	/**
	 * 
	 * @param num
	 *            целое число
	 * @return -1, 0, 1 для отрицательного, нулевого, и положительного 
	 * 		   аргумента соответственно
	 */
	public int sign(int num){
		return (num > 0) ? 1 : (num < 0) ? -1 : 0;
	}
	
	/**
	 * 
	 * @param num число с плавающей точкой 
	 * @return целая часть аргумента
	 */
	public int IPart(double num) {
		return (int) num;
	}

	/**
	 * 
	 * @param num число с плавающей точкой 
	 * @return дробная часть числа
	 */
	public double FPart(double num) {
		return (((double) (num)) - (double) IPart(num));
	}
	

	@Override
	public List<Point> getControlPoints() {
		List<Point> points = new ArrayList<Point>();
		points.add(beginPoint);
		points.add(endPoint);
		return points;
	}

	@Override
	public void setControlPoints(List<Point> points) {
		beginPoint = points.get(0);
		endPoint = points.get(1);
		prepare();
		execution();
	}
	
	@Override
	public boolean processMousePress(int x, int y) {
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

	@Override
	public boolean processMouseRelease(int x, int y) {
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

	@Override
	public boolean processMouseMove(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processMouseDoubleClick(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g, int step) {
		for(Cell cell: cells){
			g.setColor(cell.getColor());
			g.fillRect(cell.getX() * step, cell.getY() * step, step, step);
			g.setColor(null);
		}
	}

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Cell> getPoints() {
		// TODO Auto-generated method stub
		return cells;
	}

	
}
