package by.bsuir.giis.util.algorithm.conic;

import java.util.List;

import by.bsuir.giis.model.Cell;

/**
 * @author ��������� �������
 *
 */
public interface IConicAlgorithm {
 
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
