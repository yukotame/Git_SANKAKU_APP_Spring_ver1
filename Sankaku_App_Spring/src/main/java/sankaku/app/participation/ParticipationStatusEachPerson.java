package sankaku.app.participation;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import sankaku.domain.model.Participation;

@Data
public class ParticipationStatusEachPerson implements Serializable{

	private String userId;


	private List<LocalDate> scheduleDateList;


	private List<Participation> participationStatusList;

	//コンストラクタ
	public ParticipationStatusEachPerson() {

	}
	public ParticipationStatusEachPerson(String userId, List<LocalDate> scheduleDateList,
			List<Participation> participationStatusList) {
		this.userId = userId;
		this.scheduleDateList = scheduleDateList;
		this.participationStatusList = participationStatusList;
	}




}






