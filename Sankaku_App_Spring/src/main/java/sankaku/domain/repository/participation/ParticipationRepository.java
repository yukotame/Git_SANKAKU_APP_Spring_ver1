package sankaku.domain.repository.participation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sankaku.domain.model.UserParticipation;
import sankaku.domain.model.UserParticipationId;



public interface ParticipationRepository extends JpaRepository<UserParticipation, UserParticipationId> {

	@Query(value = "SELECT p FROM UserParticipation p WHERE p.scheduleableDate.schedule.scheduleId = :sId order by p.scheduleableDate.scheduleDate asc , p.user.userId asc  " )
	List<UserParticipation> findAllParticipationByScheduleIdOrderByscheduleableDateAsc(@Param("sId")int sId);

	@Query(value = "SELECT p FROM UserParticipation p WHERE p.scheduleableDate.schedule.scheduleId = :sId order by p.user.userId asc , p.scheduleableDate.scheduleDate asc    " )
	List<UserParticipation> findAllParticipationByScheduleIdOrderByuserIdAsc(@Param("sId")int sId);


	@Query(value = "SELECT p FROM UserParticipation p WHERE p.scheduleableDate.scheduleableDateId = :sDateId" )
	List<UserParticipation> findByScheduleableDateId(Integer sDateId);



}
