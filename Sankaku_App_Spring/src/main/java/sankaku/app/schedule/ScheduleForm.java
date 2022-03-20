package sankaku.app.schedule;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@NotEmptyAny(message = "日付を入力してください")
@NotSameDate(message = "同じ日付が入力されています")
public class ScheduleForm implements Serializable {

	@NotBlank
	private String originatorName;

	@NotBlank
	private String originatorId;

	@NotBlank
	private String scheduleName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate scheduleDate1;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate scheduleDate2;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate scheduleDate3;

	//コンストラクタ
	public ScheduleForm( ) {

	}
	public ScheduleForm(@NotBlank String originatorName, @NotBlank String originatorId, @NotBlank String scheduleName,
			LocalDate scheduleDate1, LocalDate scheduleDate2, LocalDate scheduleDate3) {
		super();
		this.originatorName = originatorName;
		this.originatorId = originatorId;
		this.scheduleName = scheduleName;
		this.scheduleDate1 = scheduleDate1;
		this.scheduleDate2 = scheduleDate2;
		this.scheduleDate3 = scheduleDate3;
	}



}
