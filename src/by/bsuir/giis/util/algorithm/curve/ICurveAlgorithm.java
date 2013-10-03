package by.bsuir.giis.util.algorithm.curve;

import java.util.List;

import by.bsuir.giis.util.Cell;

public interface ICurveAlgorithm {

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
