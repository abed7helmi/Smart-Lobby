package episen.si.ing1.pds.client.Mapping;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import episen.si.ing1.pds.client.Client;
import episen.si.ing1.pds.client.Ihm;

public class RoomPlan {

	public static void roomPlan(String room_id,String imgPath) {
		try {
			Mapping.locationPlan.removeAll();
		
			
			JLabel title = new JLabel("Sélectionnez un emplacement à configurer:");
			title.setFont(Mapping.titlefont);
			title.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
			Mapping.locationPlan.add(title,BorderLayout.NORTH);
			
			JPanel plan = new JPanel() {
				Image img = ImageIO.read(new File(imgPath));
				{setOpaque(false);}
				public void paintComponent(Graphics graphics) {
					graphics.drawImage(img, 0, 0, this);
					super.paintComponent(graphics);
				}
			};
			plan.setLayout(null);
			Icon greenPin = new ImageIcon(ImageIO.read(new File(Ihm.path+"greenpin.png")));
			Icon redPin = new ImageIcon(ImageIO.read(new File(Ihm.path+"redpin.png")));
			Icon greenSensor = new ImageIcon(ImageIO.read(new File(Ihm.path+"greensensor.png")));
			Icon redSensor = new ImageIcon(ImageIO.read(new File(Ihm.path+"redsensor.png")));
			
			Client.map.get("roomLocation").put("room_id", room_id);
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> locations = mapper.readValue(Client.sendBd("roomLocation"),new TypeReference<Map<String, Map<String, String>>>(){});
			
			for(Map<String, String> m : locations.values()) {
				JLabel pin = new JLabel(m.get("location_id"));
				if(m.get("is_sensor").equals("t")) {
					if(m.get("occupied_location").equals("t")) {
						pin.setIcon(redSensor);
					}else {
						pin.setIcon(greenSensor);
					}
				}else {
					if(m.get("occupied_location").equals("t")) {
						pin.setIcon(redPin);
					}else {
						pin.setIcon(greenPin);
					}
				}
				pin.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						EquipementSelection.equipmentSelection(m.get("location_id"),m.get("is_sensor"));
					}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
				});

				plan.add(pin);
				pin.setBounds(Integer.valueOf(m.get("x_position")),Integer.valueOf(m.get("y_position")), 60, 30);
			}

			Mapping.locationPlan.add(plan,BorderLayout.CENTER);
			
			JButton refresh = new JButton();
			refresh.setIcon(new ImageIcon(ImageIO.read(new File(Ihm.path+"actualiser.png"))));
			refresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RoomPlan.roomPlan(room_id,imgPath);
				}
			});
			JPanel p = new JPanel();
			p.setBackground(Color.WHITE);
			p.add(refresh);
			p.setBorder(BorderFactory.createEmptyBorder(200,0,0,0));
			System.out.println("display images");
			Mapping.locationPlan.add(p,BorderLayout.WEST);
			Mapping.locationPlan.invalidate();
			Mapping.locationPlan.revalidate();
			Mapping.locationPlan.repaint();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
