package sankaku.app.participation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ParticipationStatusAllForm implements Serializable{


	private List<ParticipationStatusEachPerson> participationStatusAllList;

	//コンストラクタ
	public ParticipationStatusAllForm() {

	}
	public ParticipationStatusAllForm(List<ParticipationStatusEachPerson> participationStatusAllList ) {
		this.participationStatusAllList = participationStatusAllList;
	}
}






