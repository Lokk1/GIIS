package by.bsuir.giis.util.algorithm.line;

import java.awt.Color;
import java.awt.Point;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;

import by.bsuir.giis.util.Cell;
import by.bsuir.giis.util.CellControl;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

public class LineWU implements ILineAlgorithm{

	private Point p0;
	private Point p1;

	private int dX;
	private int dY;

	List<Cell> cells;

	int incx = 0;
	int incy = 0;
	int es = 0;
	int el = 0;
	double intensity;
	double iter = 0;

	boolean steep;

	int temp;

	public LineWU(Point p0, Point p1) {

		this.p0 = p0;
		this.p1 = p1;
		
		cells = new ArrayList<Cell>();
		
		prepare();
	}

	public void prepare() {
		
		//���������� ��������� ���������
        dX = p1.x - p0.x;
        dY = p1.y - p0.y;
	}

	public List<Cell> execution() {

		//��� �-����� (����������� ������� < 1)
        if (Math.abs(dY) < Math.abs(dX))
        {
            //������ ����� ������ ����� ������� ���������� �
            if (p1.x < p0.x)
            {
                p1.x += p0.x; p0.x = p1.x - p0.x; p1.x -= p0.x;
                p1.y += p0.y; p0.y = p1.y - p0.y; p1.y -= p0.y;
            }
            //������������� ��������� ���������� Y
            float grad = (float)dY / dX;
            //������������� ���������� ��� Y
            float intery = p0.y + grad;
            //������ �����
            cells.add(new Cell(p0.x, p0.y, Color.BLUE));

            for (int x = p0.x + 1; x < p1.x; x++)
            {
                //������� �����
                Color color = new Color(0, 0, 255, (int)(255 - FPart(intery) * 255));
                cells.add(new Cell(x, IPart(intery), color));
                
                //������ �����
                color = new Color(0, 0, 255, (int)(FPart(intery) * 255));
                cells.add(new Cell(x, IPart(intery) + 1, color));
                
                //��������� ���������� Y
                intery += grad;
            }
            //��������� �����
            cells.add(new Cell(p1.x, p1.y, Color.BLUE));
        }
        //��� Y-����� (����������� ������� > 1)
        else
        {
            //������ ����� ������ ����� ������� ���������� Y
            if (p1.y < p0.y)
            {
                p1.x += p0.x; p0.x = p1.x - p0.x; p1.x -= p0.x;
                p1.y += p0.y; p0.y = p1.y - p0.y; p1.y -= p0.y;
            }
            //������������� ��������� ���������� X
            float grad = (float)dX / dY;
            //������������� ���������� ��� X
            float interx = p0.x + grad;
            //������ �����
            cells.add(new Cell(p0.x, p0.y, Color.BLUE));

            for (int y = p0.y + 1; y < p1.y; y++)
            {
                //������� �����
                Color color = new Color(0, 0, 255, 255 - (int)(FPart(interx) * 255));
                cells.add(new Cell(IPart(interx), y, color));
                
                //������ �����
                color = new Color(0, 0, 255, (int)(FPart(interx) * 255));
                cells.add(new Cell(IPart(interx) + 1, y, color));
                
                //��������� ���������� X
                interx += grad;
            }
            //��������� �����
            cells.add(new Cell(p1.x, p1.y, Color.BLUE));
        }
		
		return cells;
	}
	

	public int IPart(double num) {
		return (int) num;
	}


	public double FPart(double num) {
		return (((double) (num)) - (double) IPart(num));
	}

	
	public int sign(int num) {
        if (num > 0) {
            return 1;
        }
        if (num == 0) {
            return 0;
        }
        if (num < 0) {
            return -1;
        }
        return 0;
    }

	@Override
	public int sign(float num) {
		// TODO Auto-generated method stub
		return 0;
	}

}
