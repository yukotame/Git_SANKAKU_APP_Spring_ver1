package sankaku.domain.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import sankaku.domain.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {


}
