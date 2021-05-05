package episen.si.ing1.pds.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Mapping {


	private JPanel panel = new JPanel();
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private JPanel locationSelection = new JPanel();
	private JPanel locationPlan = new JPanel();
	private JPanel equipmentSelection = new JPanel();
	private String companyId = Menu.company_id;
	private String reservation_id = "";
	private String room_id = "";
	private String imgPath = "";
	private String path = System.getenv("PDSIMG");
	private JPanel selection1 = new JPanel();
	private JPanel selection2 = new JPanel();
	private JPanel selection3 = new JPanel();
	private JPanel selection4 = new JPanel();
	private JPanel selection5 = new JPanel();
	private JPanel selection6 = new JPanel();
	private Font titlefont = new Font("font", 1, 20);

	public Mapping() {
		panel.setBackground(Color.white);
		panel.setLayout(new GridLayout(2,1));
		panel.add(p1);
		panel.add(p2);
		p1.setLayout(new GridLayout(1,2));
		p1.add(locationSelection);
		p1.add(equipmentSelection);
		p2.setLayout(new GridLayout(1,1));
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,1));
		p2.add(p);
		p.add(locationPlan);
		locationPlan.setBorder(BorderFactory.createEmptyBorder(0,220,0,0));
		locationPlan.setLayout(new BorderLayout());
		locationPlan.setBackground(Color.WHITE);
		
		equipmentSelection.setLayout(new GridLayout(3,1));
		equipmentSelection.add(selection4);
		equipmentSelection.add(selection5);
		equipmentSelection.add(selection6);
		
		selection4.setBackground(Color.WHITE);
		selection5.setBackground(Color.WHITE);
		selection6.setBackground(Color.WHITE);

		locationSelection.setLayout(new GridLayout(3,1));
		locationSelection.add(selection1);
		locationSelection.add(selection2);
		locationSelection.add(selection3);
		
		selection1.setBackground(Color.WHITE);
		selection2.setBackground(Color.WHITE);
		selection3.setBackground(Color.WHITE);
		JLabel title = new JLabel("Sélectionnez une location à configurer:");
		title.setFont(titlefont);
		selection1.add(title);
		
		try {
		Client.map.get("companyReservation").put("company_id", companyId);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		Map<String, Map<String, String>> reservations = mapper.readValue(Client.sendBd("companyReservation"),new TypeReference<Map<String, Map<String, String>>>(){});
		String[] reservationList = new String[reservations.size()];
		for(int i=0;i<reservationList.length;i++) {
			reservationList[i]="N°"+reservations.get(""+i).get("reservation_id")+" du "+reservations.get(""+i).get("start_date");
		}
		JComboBox<String> cb = new JComboBox<String>(reservationList);
		cb.setSize(100,10);
		selection1.add(cb);

		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selection2.removeAll();
				selection2.repaint();
				selection3.removeAll();
				selection3.repaint();
				reservation_id = reservations.get(cb.getSelectedIndex()+"").get("reservation_id");
				floorSelection(reservation_id);
			}
		});
		
		} catch (JsonMappingException e1) {} catch (JsonProcessingException e1) {}
	}

	public void floorSelection(String reservation_id) {
		try {
		JLabel title = new JLabel("Sélectionnez le bâtiment et l'étage à configurer:");
		title.setFont(titlefont);
		selection2.add(title);
		
		Client.map.get("reservationFloor").put("reservation_id", reservation_id);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		Map<String, Map<String, String>> floors = mapper.readValue(Client.sendBd("reservationFloor"),new TypeReference<Map<String, Map<String, String>>>(){});
		String[] floorList = new String[floors.size()];
		for(int i=0;i<floorList.length;i++) {
			floorList[i]="Bâtiment "+floors.get(""+i).get("building_name")+" étage "+floors.get(""+i).get("floor_number");
		}
		JComboBox<String> cb = new JComboBox<String>(floorList);
		cb.setSize(100,10);
		
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selection3.removeAll();
				selection3.repaint();
				roomSelection(floors.get(cb.getSelectedIndex()+"").get("floor_id"),reservation_id);
			}
		});
		
		selection2.add(cb);
		selection2.revalidate();
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {}
	}
	
	public void roomSelection(String floor_id,String reservation_id) {
		try {
			JLabel title = new JLabel("Sélectionnez la salle à configurer:");
			title.setFont(titlefont);
			selection3.add(title);
			
			Client.map.get("floorRoom").put("floor_id", floor_id);
			Client.map.get("floorRoom").put("reservation_id", reservation_id);
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> rooms = mapper.readValue(Client.sendBd("floorRoom"),new TypeReference<Map<String, Map<String, String>>>(){});
			String[] roomList = new String[rooms.size()];
			for(int i=0;i<roomList.length;i++) {
				roomList[i]=rooms.get(""+i).get("room_wording");
			}
			JComboBox<String> cb = new JComboBox<String>(roomList);
			cb.setSize(100,10);
			
			cb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					switch(rooms.get(cb.getSelectedIndex()+"").get("room_type_id")) {
					case "1":imgPath = path+"plan1.png";
						break;
					case "2":imgPath = path+"plan2.png";
						break;
					case "3":imgPath = path+"plan3.png";
						break;
					case "4":imgPath = path+"plan4.png";
						break;
					}
					room_id=rooms.get(cb.getSelectedIndex()+"").get("room_id");
					roomPlan(room_id,imgPath);
				}
			});
			
			selection3.add(cb);
			selection3.revalidate();
			} catch (JsonMappingException e) {} catch (JsonProcessingException e) {}
	}
	public void roomPlan(String room_id,String imgPath) {
		try {
			locationPlan.removeAll();
		
			
			JLabel title = new JLabel("Sélectionnez un emplacement à configurer:");
			title.setFont(titlefont);
			title.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));
			locationPlan.add(title,BorderLayout.NORTH);
			
			JPanel plan = new JPanel() {
				Image img = ImageIO.read(new File(imgPath));
				{setOpaque(false);}
				public void paintComponent(Graphics graphics) {
					graphics.drawImage(img, 0, 0, this);
					super.paintComponent(graphics);
				}
			};
			plan.setLayout(null);
			Icon greenPin = new ImageIcon(ImageIO.read(new File(path+"greenpin.png")));
			Icon redPin = new ImageIcon(ImageIO.read(new File(path+"redpin.png")));
			Icon greenSensor = new ImageIcon(ImageIO.read(new File(path+"greensensor.png")));
			Icon redSensor = new ImageIcon(ImageIO.read(new File(path+"redsensor.png")));
			
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
						equipmentSelection(m.get("location_id"),m.get("is_sensor"));
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

			

			locationPlan.add(plan,BorderLayout.CENTER);
			locationPlan.revalidate();
			
		} catch (IOException e1) {}
	}
	
	public void equipmentSelection(String location_id,String is_sensor) {
		try {
			selection4.removeAll();
			selection5.removeAll();
			selection6.removeAll();
			selection4.repaint();
			selection5.repaint();
			selection6.repaint();
			Client.map.get("setEquipment").put("location_id", location_id);
			Client.map.get("setEquipment").put("old_device_id", "");
			Client.map.get("setEquipment").put("new_device_id", "");
			Client.map.get("locationEquipment").put("location_id", location_id);
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, String> equipmentPlaced = mapper.readValue(Client.sendBd("locationEquipment"),new TypeReference<Map<String, String>>(){});
			JLabel info = new JLabel();
			
			selection4.add(info);
			if(equipmentPlaced.size()>0) {
				info.setText("L'emplacement N°"+location_id+" est actuellement occupé par:");
				JTextArea txt = new JTextArea("Id:"+equipmentPlaced.get("device_id")+" Nom:"+equipmentPlaced.get("device_wording")+" Actif:"+equipmentPlaced.get("device_active")
						+" Prix:"+equipmentPlaced.get("device_price"));
			
				selection4.add(txt);
				Client.map.get("setEquipment").put("old_device_id", equipmentPlaced.get("device_id"));
				JButton empty = new JButton("Libérer l'emplacement");
				empty.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Client.sendBd("setEquipment");
						equipmentSelection(location_id,is_sensor);
						roomPlan(room_id,imgPath);
					}
				});
				selection6.add(empty);
				selection6.revalidate();
			}else {
				info.setText("L'emplacement N°"+location_id+" est actuellement libre.");
				info.setFont(titlefont);
			}
			selection4.revalidate();
			
			JLabel deviceSelection = new JLabel("Sélectionnez un appareil à placer:");
			deviceSelection.setFont(titlefont);
			selection5.add(deviceSelection);
		
			Client.map.get("reservationEquipment").put("is_sensor", is_sensor);
			Client.map.get("reservationEquipment").put("room_id", room_id);
			Client.map.get("reservationEquipment").put("reservation_id", reservation_id);
			Map<String, Map<String, String>> equipments = mapper.readValue(Client.sendBd("reservationEquipment"),new TypeReference<Map<String, Map<String, String>>>(){});
			String[] equipmentList = new String[equipments.size()];
			for(int i=0;i<equipmentList.length;i++) {
				equipmentList[i]=equipments.get(""+i).get("device_id")+" "+equipments.get(""+i).get("device_wording")+" "+equipments.get(""+i).get("device_price");
			}
			JComboBox<String> cb = new JComboBox<String>(equipmentList);
			cb.setSize(100,10);
			
			cb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Client.map.get("setEquipment").put("new_device_id", equipments.get(cb.getSelectedIndex()+"").get("device_id"));
					System.out.println(Client.map.get("setEquipment"));
					Client.sendBd("setEquipment");
					equipmentSelection(location_id,is_sensor);
					roomPlan(room_id,imgPath);
				}
			});
			selection5.add(cb);
			selection5.revalidate();
		}catch(Exception e) {}
	}

	public JPanel getPanel() {
		return panel;
	}

}