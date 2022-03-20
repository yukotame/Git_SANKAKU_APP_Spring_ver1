package sankaku.domain.repository.schedule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sankaku.domain.model.ScheduleableDate;



public interface ScheduleableDateRepository extends JpaRepository<ScheduleableDate, Integer> {

	@Query(value = "SELECT s FROM ScheduleableDate s WHERE s.schedule.scheduleId = :sId "
			+ "order by s.scheduleDate asc") // (1)
	ArrayList<ScheduleableDate> findByScheduleId(@Param("sId") int sId);

}

