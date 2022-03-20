package sankaku.app.participation;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import sankaku.domain.model.Participation;
import sankaku.domain.model.ScheduleableDate;

@Data
public class DateParticipationForm implements Serializable{


	private ScheduleableDate scheduleableDateOutput;

	@NotNull
	private Participation selectedParticipation;


	public DateParticipationForm() {
	}


	public DateParticipationForm(ScheduleableDate scheduleableDateOutput, Participation selectedParticipation) {

		this.scheduleableDateOutput = scheduleableDateOutput;
		this.selectedParticipation = selectedParticipation;
	}




}
