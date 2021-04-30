package episen.si.ing1.pds.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Mapping {


	private JPanel panel = new JPanel();
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private JPanel locationSelection = new JPanel();
	private JPanel locationPlan = new JPanel();
	private JPanel equipmentInfo = new JPanel();
	private JPanel equipmentSelection = new JPanel();

	public Mapping() {
		panel.setBackground(Color.white);
		panel.setLayout(new GridLayout(2,1));
		panel.add(p1);
		panel.add(p2);
		p1.setLayout(new BorderLayout());
		p1.add(locationSelection, BorderLayout.NORTH);
		p1.add(locationPlan);
		p2.setLayout(new GridLayout(1,2));
		p2.add(equipmentInfo);
		p2.add(equipmentSelection);
		locationSelection.setBackground(Color.RED);
		locationPlan.setBackground(Color.GREEN);
		equipmentSelection.setBackground(Color.BLUE);
	

		locationSelection.setLayout(new FlowLayout());
		JLabel title = new JLabel("Sélectionnez une location à configurer:");
		Font titlefont = new Font("font", 1, 20);
		title.setFont(titlefont);
		locationSelection.add(title);

		String[] locationNameList = { "test", "toto", "rdaazd","azdazd"};
		JComboBox<String> cb = new JComboBox<String>(locationNameList);
		cb.setSize(100,10);
		locationSelection.add(cb);

		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				locationPlan();
			}
		});
		
	}

	public void locationPlan() {
		try {
			locationPlan.removeAll();
			locationPlan.setBackground(Color.white);
			locationPlan.setLayout(new BorderLayout());

			JLabel title = new JLabel("Sélectionnez un emplacement à configurer:");
			locationPlan.add(title, BorderLayout.NORTH);

			JPanel plan = new JPanel() {
				Image img = ImageIO.read(new File(System.getenv("PLAN")));
				{
					setOpaque(false);
				}

				public void paintComponent(Graphics graphics) {
					graphics.drawImage(img, 0, 0, this);
					super.paintComponent(graphics);
				}
			};

			Icon green = new ImageIcon(ImageIO.read(new File(System.getenv("GREEN"))));
			JLabel greenpin = new JLabel();
			greenpin.setIcon(green);
			

			Icon red = new ImageIcon(ImageIO.read(new File(System.getenv("RED"))));
			JLabel redpin = new JLabel();
			redpin.setIcon(red);

			greenpin.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("test");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});
			plan.add(greenpin);
			plan.add(redpin);
			
			locationPlan.add(plan);
			locationPlan.revalidate();

		} catch (IOException e1) {}
	}
	


	public JPanel getPanel() {
		return panel;
	}

}
