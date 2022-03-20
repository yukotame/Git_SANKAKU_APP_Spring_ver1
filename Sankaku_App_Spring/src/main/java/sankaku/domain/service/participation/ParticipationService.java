package sankaku.domain.service.participation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import sankaku.domain.model.UserParticipation;
import sankaku.domain.model.UserParticipationId;
import sankaku.domain.repository.participation.ParticipationRepository;

@Service
public class ParticipationService {

	@Autowired
	ParticipationRepository participationRepository;


	/*	private final ParticipationTestDao dao;

		public ParticipationService(ParticipationTestDao dao) {
			this.dao = dao;
		}*/


	//スケジュール検索処理
	@Transactional
	public List<UserParticipation> selectAll() {
		System.out.println("ParticipationService findAll ");
		return participationRepository.findAll();
	}

	@Transactional
	public UserParticipation registParticipation(UserParticipation userParticipation) {


		participationRepository.save(userParticipation);

		//insertをしてキーの重複でエラーになるか試す
		//dao.insert(userParticipation);

		return userParticipation;

	}

	@Transactional
	public List<UserParticipation> selectParticipationByScheduleIdOrderByscheduleableDateAsc(int schId) {
		System.out.println("ParticipationService selectParticipationByScheduleId: " + schId);
		return participationRepository.findAllParticipationByScheduleIdOrderByscheduleableDateAsc(schId);
	}

	@Transactional
	public List<UserParticipation> selectParticipationByScheduleIdOrderByuserIdAsc(int schId) {
		System.out.println("ParticipationService selectParticipationByScheduleId: " + schId);
		return participationRepository.findAllParticipationByScheduleIdOrderByuserIdAsc(schId);
	}




	@Transactional
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.user.userId")
	public void deleteparticipationAll(List<UserParticipationId> userList , String userId) {
		participationRepository.deleteAllById(userList);
	}

	@Transactional
	public List<UserParticipation> selectByScheduleableDateId(Integer scheduleableDateId) {
		return participationRepository.findByScheduleableDateId(scheduleableDateId);
	}




}
