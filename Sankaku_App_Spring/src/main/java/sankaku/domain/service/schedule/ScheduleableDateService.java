package sankaku.domain.service.schedule;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import sankaku.domain.model.ScheduleableDate;
import sankaku.domain.repository.schedule.ScheduleableDateRepository;

@Service
public class ScheduleableDateService {

	@Autowired
	ScheduleableDateRepository scheduleableDateRepository;

	//スケジュール日程テーブルをスケジュールIDで検索
	@Transactional
	public ArrayList<ScheduleableDate> selectByScheduleId(int scheduleId) {

		//スケジュールテーブル(schedule)をscheduleIdで検索
		return scheduleableDateRepository.findByScheduleId(scheduleId);

	}

	//スケジュール日程テーブルをスケジュール日程IDで削除
	@Transactional
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.user.userId")
	public void deleteScheduleDate(Integer scheduleableDateId , String userId) {

		scheduleableDateRepository.deleteById(scheduleableDateId);

	}

}
