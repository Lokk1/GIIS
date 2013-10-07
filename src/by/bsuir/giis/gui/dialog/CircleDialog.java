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

public class CircleDialog extends JDialog {
	private JTextField pointXTextField = null;
	private JTextField pointYTextField = null;
	private JTextField radiusTextField = null;

	private JLabel cordLabel = null;
	private JLabel radiusLabel = null;

	private JLabel cordXLabel = null;
	private JLabel cordYLabel = null;

	private JButton okButton = null;

	private AlgorithmType algorithmType;

	public CircleDialog(MainFrame mainFrame, AlgorithmType algorithmType) {
		setTitle("Two points dialog");
		this.algorithmType = algorithmType;
		setupFrame();
		actionListener(mainFrame);
		setSize(250, 110);
		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void createItems() {
		okButton = new JButton("Ок");

		cordXLabel = new JLabel("X: ");
		cordYLabel = new JLabel("Y: ");
		radiusLabel = new JLabel("R: ");

		pointXTextField = new JTextField();
		pointXTextField.setColumns(3);
		pointXTextField.setText("100");
		pointXTextField.selectAll();
		pointYTextField = new JTextField();
		pointYTextField.setColumns(3);
		pointYTextField.setText("100");
		pointYTextField.selectAll();
		radiusTextField = new JTextField();
		radiusTextField.setColumns(3);
		radiusTextField.setText("20");
		radiusTextField.selectAll();
	}

	private void setupFrame() {
		createItems();

		setLayout(new FlowLayout());

		add(cordXLabel);
		add(pointXTextField);

		add(cordYLabel);
		add(pointYTextField);

		add(radiusLabel);
		add(radiusTextField);

		add(okButton);
	}
	
	public void actionListener(final MainFrame mainFrame){
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int x1 = 999999, y1 = 999999;
				int radius = 999999, y2 = 0;
				
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
				if(radiusTextField.getText().matches(regex)){
					radius = Integer.parseInt(radiusTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + radiusTextField.getText()+" ]");
					return;
				}
				
				Point p1 = new Point(x1, y1);
                Point p2 = new Point(radius, y2);
                
                mainFrame.setLineAlgorithm(algorithmType);
//                mainFrame.addPointsForAlgorithm(p1, p2);
                
				setVisible(false);
			}
		});
	}
}
