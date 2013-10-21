package by.bsuir.giis.gui.tables;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CircleStepTable extends JFrame {
	
	private JPanel tablePanel = null;
	private JScrollPane tableScrollPane = null;
	private JTable table = null;
	
	public CircleStepTable() {
		initComponents();
	}

	private void initComponents() {
		
		tablePanel = new JPanel();
		tableScrollPane = new JScrollPane();
		table = new JTable();
		
		setTitle("Алгоритм построения окружности");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tablePanel.setBorder(BorderFactory.createTitledBorder("Таблица пошагового выполнения алгоритма"));

		table.setModel(new DefaultTableModel(new Object[][] {
				{ 0, 0, 0, "(0, 0)" }},
				new String[] { "i", "X", "Y", "Plot(x, y)" }));
		
		tableScrollPane.setViewportView(table);
		
		tablePanel.add(tableScrollPane);
		add(tablePanel);
		pack();
		setVisible(true);

	}

}
