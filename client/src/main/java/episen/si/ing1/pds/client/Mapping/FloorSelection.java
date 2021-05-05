package episen.si.ing1.pds.client.Mapping;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import episen.si.ing1.pds.client.Client;

public class FloorSelection {
	

	public static void floorSelection(String reservation_id) {
		try {
		JLabel title = new JLabel("Sélectionnez le bâtiment et l'étage à configurer:");
		title.setFont(Mapping.titlefont);
		Mapping.selection2.add(title);
		
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
				Mapping.selection3.removeAll();
				Mapping.selection3.repaint();
				RoomSelection.roomSelection(floors.get(cb.getSelectedIndex()+"").get("floor_id"),reservation_id);
			}
		});
		
		Mapping.selection2.add(cb);
		Mapping.selection2.revalidate();
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {}
	}
	
}
