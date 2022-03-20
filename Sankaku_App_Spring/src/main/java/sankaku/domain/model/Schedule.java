package sankaku.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Schedule implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="schedule_id", nullable = false)
	private Integer scheduleId;


	@Column(name="schedule_name")
	private String scheduleName;

	@ManyToOne
	@JoinColumn(name = "originator_id")
	private User user;


	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
	private List<ScheduleableDate> scheduleableDateList;



}
