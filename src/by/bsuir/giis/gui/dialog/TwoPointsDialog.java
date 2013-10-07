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

@SuppressWarnings("serial")
public class TwoPointsDialog extends JDialog {

	private JTextField firstPointXTextField  = null;
	private JTextField firstPointYTextField  = null;
	private JTextField secondPointXTextField = null;
	private JTextField secondPointYTextField = null;

	private JLabel firstCordLabel  = null;
	private JLabel secondCordLabel = null;

	private JLabel firstCordXLabel  = null;
	private JLabel firstCordYLabel  = null;
	private JLabel secondCordXLabel = null;
	private JLabel secondCordYLabel = null;

	private JButton okButton = null;
	
	private AlgorithmType algorithmType;

	public TwoPointsDialog(MainFrame mainFrame, AlgorithmType algorithmType) {
		setTitle("Two points dialog");
		this.algorithmType = algorithmType;
		System.out.println("Constructor: "+algorithmType);
		setupFrame();
		setSize(210, 150);
		setModal(true);
		setLocationRelativeTo(null);
		actionListener(mainFrame);
		setVisible(true);
	}

	public void createItems() {
		firstCordLabel = new JLabel("Point №1:");
		secondCordLabel = new JLabel("Point №2:");

		okButton = new JButton("Ок");

		firstCordXLabel = new JLabel("X: ");
		firstCordYLabel = new JLabel("Y: ");
		secondCordXLabel = new JLabel("X: ");
		secondCordYLabel = new JLabel("Y: ");

		firstPointXTextField = new JTextField();
		firstPointXTextField.setColumns(3);
		firstPointXTextField.setText("10");
		firstPointXTextField.selectAll();
		
		firstPointYTextField = new JTextField();
		firstPointYTextField.setColumns(3);
		firstPointYTextField.setText("10");
		firstPointYTextField.selectAll();
		
		secondPointXTextField = new JTextField();
		secondPointXTextField.setColumns(3);
		secondPointXTextField.setText("20");
		secondPointXTextField.selectAll();
		
		secondPointYTextField = new JTextField();
		secondPointYTextField.setColumns(3);
		secondPointYTextField.setText("20");
		secondPointYTextField.selectAll();
		
	}

	public void setupFrame() {
		createItems();
		
		setLayout(new FlowLayout());
		
		add(firstCordLabel);
		
		add(firstCordXLabel);
		add(firstPointXTextField);
		
		add(firstCordYLabel);
		add(firstPointYTextField);
		
		add(secondCordLabel);
		
		add(secondCordXLabel);
		add(secondPointXTextField);
		
		add(secondCordYLabel);
		add(secondPointYTextField);
		
		add(okButton);
	}
	
	public void actionListener(final MainFrame mainFrame){
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int x1 = 999999, y1 = 999999;
				int x2 = 999999, y2 = 999999;
				
				String regex = "(\\d)+";
				if(firstPointXTextField.getText().matches(regex)){
					x1 = Integer.parseInt(firstPointXTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + firstPointXTextField.getText()+" ]");
					return;
				}
				if(firstPointYTextField.getText().matches(regex)){
					y1 = Integer.parseInt(firstPointYTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + firstPointYTextField.getText()+" ]");
					return;
				}
				if(secondPointXTextField.getText().matches(regex)){
					x2 = Integer.parseInt(secondPointXTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + secondPointXTextField.getText()+" ]");
					return;
				}
				if(secondPointYTextField.getText().matches(regex)){
					y2 = Integer.parseInt(secondPointYTextField.getText());
				}else {
					JOptionPane.showMessageDialog(rootPane, "Неправильное число [ " + secondPointYTextField.getText()+" ]");
					return;
				}
				
				Point p1 = new Point(x1, y1);
                Point p2 = new Point(x2, y2);
                
                mainFrame.setLineAlgorithm(algorithmType);
//                mainFrame.addPointsForAlgorithm(p1, p2);
                
				setVisible(false);
			}
		});
	}
	
	public void showAlgorithmInputDialog(){
		Object[] possibleAlgorithm = {"CDA", "VU", "BREZENTHEM"};
		Object selectedAlgorithm = JOptionPane.showInputDialog(
				null,
				"Select Algorithm",
				"Algorithm",
				JOptionPane.INFORMATION_MESSAGE,
				null,
				possibleAlgorithm,
				possibleAlgorithm[0]);
	}
}
