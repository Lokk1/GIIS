package by.bsuir.giis.util.algorithm.line;

import java.util.List;

import by.bsuir.giis.util.Cell;

/**
 * 
 * @author ��������� �������
 *
 */
public interface ILineAlgorithm {

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

	/**
	 * 
	 * @param num
	 *            ����� � ��������� ������
	 * @return -1, 0, 1 ��� ��������������, ��������, � �������������� 
	 * 		   ��������� ��������������
	 */
	public int sign(float num);
	
	/**
	 * 
	 * @param num
	 *            ����� �����
	 * @return -1, 0, 1 ��� ��������������, ��������, � �������������� 
	 * 		   ��������� ��������������
	 */
	public int sign(int num);
	
	
	/**
	 * 
	 * @param num ����� � ��������� ������ 
	 * @return ����� ����� ���������
	 */
	public int IPart(double num);
	
	/**
	 * 
	 * @param num ����� � ��������� ������ 
	 * @return ������� ����� ���������
	 */
	public double FPart(double num);
}
