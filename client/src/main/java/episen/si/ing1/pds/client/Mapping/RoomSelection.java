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
import episen.si.ing1.pds.client.Ihm;

public class RoomSelection {

	public static void roomSelection(String floor_id,String reservation_id) {
		try {
			JLabel title = new JLabel("Sélectionnez la salle à configurer:");
			title.setFont(Mapping.titlefont);
			Mapping.selection3.add(title);
			
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
					case "1":Mapping.imgPath = Ihm.path+"plan1.png";
						break;
					case "2":Mapping.imgPath = Ihm.path+"plan2.png";
						break;
					case "3":Mapping.imgPath = Ihm.path+"plan3.png";
						break;
					case "4":Mapping.imgPath = Ihm.path+"plan4.png";
						break;
					}
					Mapping.room_id=rooms.get(cb.getSelectedIndex()+"").get("room_id");
					RoomPlan.roomPlan(Mapping.room_id,Mapping.imgPath);
				}
			});
			
			Mapping.selection3.add(cb);
			Mapping.selection3.revalidate();
			} catch (JsonMappingException e) {} catch (JsonProcessingException e) {}
	}
}
