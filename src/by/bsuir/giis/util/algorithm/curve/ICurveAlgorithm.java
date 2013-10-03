package by.bsuir.giis.util.algorithm.curve;

import java.util.List;

import by.bsuir.giis.util.Cell;

public interface ICurveAlgorithm {

	/**
	 * ���������� ��������� � ������
	 */
	public void prepare();

	/**
	 * ���������� ���������
	 * 
	 * @return ������ �����
	 */
	public List<Cell> execution();

}
