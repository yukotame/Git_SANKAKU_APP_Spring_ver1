package sankaku.domain.repository.participation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sankaku.domain.model.UserParticipation;

@Repository
public class ParticipationTestDaoImpl implements ParticipationTestDao {

	private final JdbcTemplate jdbcTemplate;

	public ParticipationTestDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public void insert(UserParticipation userParticipation) {

		jdbcTemplate.update("INSERT INTO sankaku_db_spring.USER_PARTICIPATION (scheduleable_date_id , user_id , participation) VALUES(?, ?, ?)",
				userParticipation.getScheduleableDate().getScheduleableDateId() ,
				userParticipation.getUser().getUserId(),
				userParticipation.getParticipation() .name() );
	}



}
