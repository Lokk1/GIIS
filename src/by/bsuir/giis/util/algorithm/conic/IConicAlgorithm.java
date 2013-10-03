package by.bsuir.giis.util.algorithm.conic;

import java.util.List;

import by.bsuir.giis.util.Cell;

/**
 * @author Алейников Евгений
 *
 */
public interface IConicAlgorithm {
 
	/**
	 * Подготовка алгоритма к работе
	 */
	public void prepare();

	/**
	 * Выполнение алгоритма
	 * 
	 * @return массив точек
	 */
	public List<Cell> execution();
	
}
