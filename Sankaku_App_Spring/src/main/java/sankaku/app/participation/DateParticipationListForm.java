package sankaku.app.participation;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DateParticipationListForm implements Serializable{


	@NotBlank
	private String userName;

	@NotBlank
	private String userId;

	@Valid
	private List<DateParticipationForm> dateParticipationList;




	//コンストラクタ
	public DateParticipationListForm() {

	}


	public DateParticipationListForm(String userName, String userId,
			List<DateParticipationForm> dateParticipationList) {
		this.userName = userName;
		this.userId = userId;
		this.dateParticipationList = dateParticipationList;
	}}







