package sankaku.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="user_participation")
@Data
public class UserParticipation implements Serializable{

	@EmbeddedId
	private UserParticipationId userParticipationId;

	@Enumerated(EnumType.STRING)
	@Column(name = "participation")
	private Participation participation;

	//JoinColumn結合先あcolumn名を指定
	@ManyToOne
	@JoinColumn(name="scheduleable_date_id")
	@MapsId("scheduleableDateId")
	private ScheduleableDate scheduleableDate;

	@ManyToOne
	@JoinColumn(name="user_id" )
	@MapsId("userId")
	private User user;








}
