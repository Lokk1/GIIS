package by.bsuir.giis.gui.dialog;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import by.bsuir.giis.gui.MainFrame;
import by.bsuir.giis.util.algorithm.type.AlgorithmType;

public class EllipseDialog extends JDialog {
	private JTextField pointXTextField = null;
	private JTextField pointYTextField = null;
	private JTextField aTextField = null;
	private JTextField bTextField = null;

	private JLabel cordLabel = null;
	private JLabel abLabel = null;
	

	private JLabel cordXLabel = null;
	private JLabel cordYLabel = null;
	private JLabel aLabel = null;
	private JLabel bLabel = null;

	private JButton okButton = null;

	private AlgorithmType algorithmType;

	public EllipseDialog(MainFrame mainFrame, AlgorithmType algorithmType) {
		setTitle("Ellipse dialog");
		this.algorithmType = algorithmType;
		setupFrame();
		actionListener(mainFrame);
		setSize(150, 150);
		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void createItems() {
		okButton = new JButton("Ок");

		cordXLabel = new JLabel("x: ");
		cordYLabel = new JLabel("y: ");
		
		aLabel = new JLabel("a: ");
		bLabel = new JLabel("b: ");

		pointXTextField = new JTextField();
		pointXTextField.setColumns(3);
		pointXTextField.setText("400");
		pointXTextField.selectAll();
		pointYTextField = new JTextField();
		pointYTextField.setColumns(3);
		pointYTextField.setText("300");
		pointYTextField.selectAll();
		aTextField = new JTextField();
		aTextField.setColumns(3);
		aTextField.setText("100");
		aTextField.selectAll();
		bTextField = new JTextField();
		bTextField.setColumns(3);
		bTextField.setText("47");
		bTextField.selectAll();
	}

	private void setupFrame() {
		createItems();

		setLayout(new FlowLayout());

		add(cordXLabel);
		add(pointXTextField);

		add(cordYLabel);
		add(pointYTextField);

		add(aLabel);
		add(aTextField);
		add(bLabel);
		add(bTextField);

		add(okButton);
	}
	
	public void actionListener(final MainFrame mainFrame){
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int x1 = 999999, y1 = 999999;
				int a = 999999, b = 999999;
				
				String regex = "(\\d)+";
				if(pointXTextField.getText().matches(regex)){
					x1 = Integer.parseInt(pointXTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + pointXTextField.getText()+" ]");
					return;
				}
				if(pointYTextField.getText().matches(regex)){
					y1 = Integer.parseInt(pointYTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + pointYTextField.getText()+" ]");
					return;
				}
				if(aTextField.getText().matches(regex)){
					a = Integer.parseInt(aTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + aTextField.getText()+" ]");
					return;
				}
				if(bTextField.getText().matches(regex)){
					b = Integer.parseInt(bTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + bTextField.getText()+" ]");
					return;
				}
				
				Point p1 = new Point(x1, y1);
                Point p2 = new Point(a, b);
                
                mainFrame.setLineAlgorithm(algorithmType);
                mainFrame.addPointsForAlgorithm(p1, p2);
                
				setVisible(false);
			}
		});
	}
}
