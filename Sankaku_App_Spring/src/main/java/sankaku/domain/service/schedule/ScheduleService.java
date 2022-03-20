package sankaku.domain.service.schedule;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import sankaku.domain.model.Schedule;
import sankaku.domain.repository.schedule.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;

	//スケジュール登録処理
	@Transactional
	public Schedule regist(Schedule schedule) {

		//スケジュールテーブル(schedule)へ登録
		scheduleRepository.save(schedule);

		return schedule;

	}

	//スケジュールIDから検索処理
	@Transactional
	public Schedule selectById(int scheduleId) {
		Schedule schedule = new Schedule();
		schedule = scheduleRepository.findById(scheduleId).get();
		//スケジュールテーブル(schedule)をscheduleIdで検索
		return schedule;

	}

	//スケジュール検索処理
	@Transactional
	public List<Schedule> selectAll() {

		//スケジュールテーブル(schedule)をscheduleIdで検索
		return scheduleRepository.findAll();

	}

	//スケジュールの取り消し
	@Transactional
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.user.userId")
	public void deleteSchedule(Schedule schedule, String userId) {

		//スケジュールテーブル(schedule)を削除
		scheduleRepository.delete(schedule);
	}

}
