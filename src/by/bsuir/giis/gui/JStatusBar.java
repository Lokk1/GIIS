package by.bsuir.giis.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;

import com.alee.laf.button.WebButton;

public class JStatusBar extends JPanel {
	
	
	
	public JStatusBar(final MainFrame mainFrame) {
		initComponents();
		
		zoomSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mainFrame.changeZoom(zoomSlider.getValue());
				zoomLabel.setText("Размер пикселя: " + zoomSlider.getValue() + "  ");
			}
		});
		
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(zoomSlider.getValue() < 20 )
					zoomSlider.setValue(zoomSlider.getValue() + 1);
			}
		});
		
		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(zoomSlider.getValue() != 0)
					zoomSlider.setValue(zoomSlider.getValue() - 1);
			}
		});
		
	}
	
	private void initComponents() {

        jButton2 = new WebButton("+");
        zoomSlider = new JSlider();
        jButton3 = new WebButton("-");
        zoomLabel = new JLabel();
        
        zoomSlider.setValue(1);
        zoomSlider.setMinimum ( 1 );
        zoomSlider.setMaximum ( 20 );
        zoomSlider.setMinorTickSpacing ( 1 );
        zoomSlider.setMajorTickSpacing ( 10 );
        
        jButton2.setToolTipText("Крупнее");
        jButton3.setToolTipText("Мельче");
        
        zoomLabel.setText("Размер пикселя: " + zoomSlider.getValue() + "  ");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(416, Short.MAX_VALUE)
                .addComponent(zoomLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(zoomSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                	.addComponent(zoomLabel)
                    .addComponent(jButton3)
                    .addComponent(zoomSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)))
        );
    }
                    
    private JButton jButton2;
    private JButton jButton3;
    private JSlider zoomSlider;
	private JLabel zoomLabel;
      
}
