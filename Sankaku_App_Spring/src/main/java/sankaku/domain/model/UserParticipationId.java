package sankaku.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserParticipationId implements Serializable{

	private Integer scheduleableDateId;

	private String userId;

	public UserParticipationId() {

	}

	public UserParticipationId(Integer scheduleableDateId, String userId) {
		super();
		this.scheduleableDateId = scheduleableDateId;
		this.userId = userId;
	}



}
