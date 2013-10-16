package by.bsuir.giis.util.algorithm.line;

import java.awt.Color;
import java.awt.Point;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;

import by.bsuir.giis.control.CellControl;
import by.bsuir.giis.model.Cell;
import by.bsuir.giis.model.Coordinates;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

public class LineWU  extends AbstractLine {

	private int dX;
	private int dY;

	int incx = 0;
	int incy = 0;
	int es = 0;
	int el = 0;
	double intensity;
	double iter = 0;

	boolean steep;

	int temp;

	public LineWU(Coordinates coordinates) {

		this.beginPoint = coordinates.get(0);
		this.endPoint = coordinates.get(1);;
		
		this.cells = new ArrayList<Cell>();
		
		prepare();
	}

	public void prepare() {
		
		//Вычисление изменения координат
        dX = endPoint.x - beginPoint.x;
        dY = endPoint.y - beginPoint.y;
        
        cells.clear();
	}

	public List<Cell> execution() {

		//Для Х-линии (коэффициент наклона < 1)
        if (Math.abs(dY) < Math.abs(dX))
        {
            //Первая точка должна иметь меньшую координату Х
            if (endPoint.x < beginPoint.x)
            {
                endPoint.x += beginPoint.x; beginPoint.x = endPoint.x - beginPoint.x; endPoint.x -= beginPoint.x;
                endPoint.y += beginPoint.y; beginPoint.y = endPoint.y - beginPoint.y; endPoint.y -= beginPoint.y;
            }
            //Относительное изменение координаты Y
            float grad = (float)dY / dX;
            //Промежуточная переменная для Y
            float intery = beginPoint.y + grad;
            //Первая точка
            cells.add(new Cell(beginPoint.x, beginPoint.y, Color.BLUE));

            for (int x = beginPoint.x + 1; x < endPoint.x; x++)
            {
                //Верхняя точка
                Color color = new Color(0, 0, 255, (int)(255 - FPart(intery) * 255));
                cells.add(new Cell(x, IPart(intery), color));
                
                //Нижняя точка
                color = new Color(0, 0, 255, (int)(FPart(intery) * 255));
                cells.add(new Cell(x, IPart(intery) + 1, color));
                
                //Изменение координаты Y
                intery += grad;
            }
            //Последняя точка
            cells.add(new Cell(endPoint.x, endPoint.y, Color.BLUE));
        }
        //Для Y-линии (коэффициент наклона > 1)
        else
        {
            //Первая точка должна иметь меньшую координату Y
            if (endPoint.y < beginPoint.y)
            {
                endPoint.x += beginPoint.x; beginPoint.x = endPoint.x - beginPoint.x; endPoint.x -= beginPoint.x;
                endPoint.y += beginPoint.y; beginPoint.y = endPoint.y - beginPoint.y; endPoint.y -= beginPoint.y;
            }
            //Относительное изменение координаты X
            float grad = (float)dX / dY;
            //Промежуточная переменная для X
            float interx = beginPoint.x + grad;
            //Первая точка
            cells.add(new Cell(beginPoint.x, beginPoint.y, Color.BLUE));

            for (int y = beginPoint.y + 1; y < endPoint.y; y++)
            {
                //Верхняя точка
                Color color = new Color(0, 0, 255, 255 - (int)(FPart(interx) * 255));
                cells.add(new Cell(IPart(interx), y, color));
                
                //Нижняя точка
                color = new Color(0, 0, 255, (int)(FPart(interx) * 255));
                cells.add(new Cell(IPart(interx) + 1, y, color));
                
                //Изменение координаты X
                interx += grad;
            }
            //Последняя точка
            cells.add(new Cell(endPoint.x, endPoint.y, Color.BLUE));
        }
		
		return cells;
	}

}
