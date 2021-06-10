package episen.si.ing1.pds.client.Mapping;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import episen.si.ing1.pds.client.Client;

public class EquipementSelection {
	
	public static void equipmentSelection(String location_id,String is_sensor) {
		try {
			Mapping.selection4.removeAll();
			Mapping.selection5.removeAll();
			Mapping.selection6.removeAll();
			Mapping.selection4.repaint();
			Mapping.selection5.repaint();
			Mapping.selection6.repaint();
			Client.map.get("setEquipment").put("location_id", location_id);
			Client.map.get("setEquipment").put("old_device_id", "");
			Client.map.get("setEquipment").put("new_device_id", "");
			Client.map.get("locationEquipment").put("location_id", location_id);
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, String> equipmentPlaced = mapper.readValue(Client.sendBd("locationEquipment"),new TypeReference<Map<String, String>>(){});
			JLabel info = new JLabel();
			
			Mapping.selection4.add(info);
			if(equipmentPlaced.size()>0) {
				info.setText("L'emplacement N°"+location_id+" est actuellement occupé par:");
				JTextArea txt = new JTextArea("Id:"+equipmentPlaced.get("device_id")+" Nom:"+equipmentPlaced.get("device_wording")+" Actif:"+equipmentPlaced.get("device_active")
						+" Prix:"+equipmentPlaced.get("device_price"));
			
				Mapping.selection4.add(txt);
				Client.map.get("setEquipment").put("old_device_id", equipmentPlaced.get("device_id"));
				JButton empty = new JButton("Libérer l'emplacement");
				empty.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Client.sendBd("setEquipment");
						EquipementSelection.equipmentSelection(location_id,is_sensor);
						RoomPlan.roomPlan(Mapping.room_id,Mapping.imgPath);
					}
				});
				Mapping.selection6.add(empty);
				Mapping.selection6.revalidate();
			}else {
				info.setText("L'emplacement N°"+location_id+" est actuellement libre.");
				info.setFont(Mapping.titlefont);
			}
			Mapping.selection4.revalidate();
			
			JLabel deviceSelection = new JLabel("Sélectionnez un appareil à placer:");
			deviceSelection.setFont(Mapping.titlefont);
			Mapping.selection5.add(deviceSelection);
		
			Client.map.get("reservationEquipment").put("is_sensor", is_sensor);
			Client.map.get("reservationEquipment").put("room_id", Mapping.room_id);
			Client.map.get("reservationEquipment").put("reservation_id", Mapping.reservation_id);
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
					Client.sendBd("setEquipment");
					EquipementSelection.equipmentSelection(location_id,is_sensor);
					RoomPlan.roomPlan(Mapping.room_id,Mapping.imgPath);
				}
			});
			Mapping.selection5.add(cb);
			Mapping.selection5.revalidate();
		}catch(Exception e) {}
	}
}
