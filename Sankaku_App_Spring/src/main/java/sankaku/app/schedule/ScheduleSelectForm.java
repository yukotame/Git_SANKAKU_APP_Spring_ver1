package sankaku.app.schedule;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ScheduleSelectForm implements Serializable {

	@NotNull
	private int scheduleId;

	//コンストラクタ
	public ScheduleSelectForm() {

	}
	public ScheduleSelectForm(int scheduleId) {
		super();
		this.scheduleId = scheduleId;
	}

}
