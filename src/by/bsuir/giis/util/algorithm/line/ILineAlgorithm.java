package by.bsuir.giis.util.algorithm.line;

import java.util.List;

import by.bsuir.giis.util.Cell;

/**
 * 
 * @author јлейников ≈вгений
 *
 */
public interface ILineAlgorithm {

	/**
	 * ѕодготовка алгоритма к работе
	 */
	public void prepare();

	/**
	 * ¬ыполнение алгоритма
	 * 
	 * @return массив точек
	 */
	public List<Cell> execution();

	/**
	 * 
	 * @param num
	 *            число с плавающей точкой
	 * @return -1, 0, 1 дл€ отрицательного, нулевого, и положительного 
	 * 		   аргумента соответственно
	 */
	public int sign(float num);
	
	/**
	 * 
	 * @param num
	 *            целое число
	 * @return -1, 0, 1 дл€ отрицательного, нулевого, и положительного 
	 * 		   аргумента соответственно
	 */
	public int sign(int num);
	
	
	/**
	 * 
	 * @param num число с плавающей точкой 
	 * @return цела€ часть аргумента
	 */
	public int IPart(double num);
	
	/**
	 * 
	 * @param num число с плавающей точкой 
	 * @return дробна€ часть аргумента
	 */
	public double FPart(double num);
}
