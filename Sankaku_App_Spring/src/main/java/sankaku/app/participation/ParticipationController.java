package sankaku.app.participation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sankaku.domain.model.Participation;
import sankaku.domain.model.ScheduleableDate;
import sankaku.domain.model.UserParticipation;
import sankaku.domain.model.UserParticipationId;
import sankaku.domain.service.participation.ParticipationService;
import sankaku.domain.service.schedule.ScheduleableDateService;
import sankaku.domain.service.user.SankakuUserDetails;

@Controller
@RequestMapping("/participation")
public class ParticipationController {



	@Autowired
	ScheduleableDateService scheduleableDateService;

	@Autowired
	ParticipationService participationService;

	@ModelAttribute
	public DateParticipationListForm setUpForm(@AuthenticationPrincipal SankakuUserDetails userdetails) {
		DateParticipationListForm form = new DateParticipationListForm();

		form.setUserId(userdetails.getUser().getUserId());
		form.setUserName(userdetails.getUser().getUserName());
		return form;
	}

	@ModelAttribute
	public ParticipationStatusAllForm setUpForm2(@AuthenticationPrincipal SankakuUserDetails userdetails) {
		ParticipationStatusAllForm form = new ParticipationStatusAllForm();
		return form;
	}

	private Map<String, Participation> radioParticipation;

	private Map<String, Participation> initRadioParticipation() {
		Map<String, Participation> radio = new LinkedHashMap<>();
		radio.put("参加", Participation.Participation);
		radio.put("不参加", Participation.NonParticipation);
		radio.put("未定", Participation.Undecided);
		return radio;
	}

	@GetMapping("/menu2/{schedule_id}")
	public String Menu2Participation(@PathVariable("schedule_id") int schId,
			@AuthenticationPrincipal SankakuUserDetails userdetails,
			DateParticipationListForm form,
			Model model) {


		ArrayList<ScheduleableDate> scheduleableResultList = new ArrayList<ScheduleableDate>();
		//スケジュール日程デーブルを検索
		scheduleableResultList = scheduleableDateService.selectByScheduleId(schId);

		//参加可否入力画面に表示する情報を格納
		radioParticipation = initRadioParticipation();
		model.addAttribute("radioParticipation", radioParticipation);

		//スケジュール日程デーブルの検索結果をもとに、Participationテーブルを登録する
		List<DateParticipationForm> pList = new ArrayList<DateParticipationForm>();
		for (ScheduleableDate s : scheduleableResultList) {
			DateParticipationForm pform = new DateParticipationForm();
			pform.setScheduleableDateOutput(s);
			pform.setSelectedParticipation(Participation.Participation);
			//formに日程をセットする
			pList.add(pform);
		}
		//model.addAttribute(new DateParticipationListForm(pList));
		form.setDateParticipationList(pList);

		return "participation/participation_boot";
	}

	@PostMapping("/selected")
	public String Menu2SelectedParticipation(
			@AuthenticationPrincipal SankakuUserDetails userdetails,
			@Validated DateParticipationListForm form,
			BindingResult result,
			Model model) {



		if (result.hasErrors()) {
			return "error";
		}

		//ユーザー参加可否テーブル(user_participation)へ登録情報をセット
		for (DateParticipationForm dform : form.getDateParticipationList()) {

			ScheduleableDate scheduleableDate = new ScheduleableDate();
			scheduleableDate.setScheduleableDateId(dform.getScheduleableDateOutput().getScheduleableDateId());
			scheduleableDate.setScheduleDate(dform.getScheduleableDateOutput().getScheduleDate());

			UserParticipation userParticipation = new UserParticipation();
			UserParticipationId userParticipationId = new UserParticipationId();
			userParticipationId.setScheduleableDateId(dform.getScheduleableDateOutput().getScheduleableDateId());
			userParticipationId.setUserId(userdetails.getUser().getUserId());

			userParticipation.setUserParticipationId(userParticipationId);
			userParticipation.setScheduleableDate(scheduleableDate);
			userParticipation.setUser(userdetails.getUser());
			userParticipation.setParticipation(dform.getSelectedParticipation());
			participationService.registParticipation(userParticipation);

		}

		return "finish";
	}

	@GetMapping("/menu3/{schedule_id}")
	public String Menu3Participation(@PathVariable("schedule_id") int schId,
			ParticipationStatusAllForm form, Model model) {
		List<UserParticipation> participationResultList = new ArrayList<UserParticipation>();
		//スケジュールIDからユーザー参加可否デーブルを全件検索検索（userIdごとにソート）
		participationResultList = participationService
				.selectParticipationByScheduleIdOrderByuserIdAsc(schId);

		//画面出力用に参加者毎のリストにする。
		List<ParticipationStatusEachPerson> eachPersonFormList = new ArrayList<ParticipationStatusEachPerson>();

		List<LocalDate> date_list = new ArrayList<LocalDate>();
		List<Participation> paticipationStatus_list = new ArrayList<Participation>();
		ParticipationStatusEachPerson eachPersonForm = new ParticipationStatusEachPerson();
		String work_userId = "";
		int x = 0;
		int y = 0;
		//日ごとにソートされた参加者リストについて、出力用にフォームにセットする。
		while(y < participationResultList.size()) {
			boolean add_switch = false;
			UserParticipation p = participationResultList.get(y);
			if (y == 0) {
				//1件目の日程をワークに格納する。
				work_userId = p.getUser().getUserId();
			}

			//ワークの日程と比較し、同じ日付の参加候補者のみリストにセットする
			while(x < participationResultList.size()) {
				UserParticipation pp = participationResultList.get(x);
				if(work_userId.equals(pp.getUser().getUserId())) {
					eachPersonForm.setUserId(work_userId);
					date_list.add(pp.getScheduleableDate().getScheduleDate());
					paticipationStatus_list.add(pp.getParticipation());
					add_switch = true;
					x = x + 1;
				} else{

					work_userId = pp.getUser().getUserId();
					y = x - 1;
					break;
				}

			}

			if(add_switch) {
				eachPersonForm.setScheduleDateList(date_list);
				eachPersonForm.setParticipationStatusList(paticipationStatus_list);
				eachPersonFormList.add(eachPersonForm);
				date_list = new ArrayList<LocalDate>();
				paticipationStatus_list = new ArrayList<Participation>();
				eachPersonForm = new ParticipationStatusEachPerson();
			}
			y = y +1;
		}



		form.setParticipationStatusAllList(eachPersonFormList);



		//-------------- 参加者の集計 ------------------------------------
		List<UserParticipation> participationResultOrderByscheduleableDateIdList = new ArrayList<UserParticipation>();

		//スケジュールIDからユーザー参加可否デーブルを全件検索検索（日程ごとにソート）
		participationResultOrderByscheduleableDateIdList = participationService
				.selectParticipationByScheduleIdOrderByscheduleableDateAsc(schId);

		//画面出力用に日程毎のリストにする。
		List<Integer> participationCntList = new ArrayList<Integer>();
		List<Integer> nonParticipationCntList = new ArrayList<Integer>();
		List<Integer> undecidedCntList = new ArrayList<Integer>();

		int participationCnt = 0;
		int nonParticipationCnt = 0;
		int undecidedCnt = 0;

		LocalDate work_date = LocalDate.now();
		int xx = 0;
		int yy = 0;

		//日ごとにソートされた参加者リストについて、出力用にフォームにセットする。
		while(yy < participationResultOrderByscheduleableDateIdList.size()) {
			boolean addList_switch = false;
			UserParticipation p = participationResultOrderByscheduleableDateIdList.get(yy);
			if (yy == 0) {
				//1件目の日程をワークに格納する。
				work_date = p.getScheduleableDate().getScheduleDate();
			}

			//ワークの日程と比較し、同じ日付の参加候補者のみリストにセットする
			while(xx < participationResultOrderByscheduleableDateIdList.size()) {
				UserParticipation pp = participationResultOrderByscheduleableDateIdList.get(xx);
				if(work_date.equals(pp.getScheduleableDate().getScheduleDate())) {
					if(pp.getParticipation().equals(Participation.Participation)) {
						participationCnt = participationCnt + 1;
					}else if(pp.getParticipation().equals(Participation.NonParticipation)) {
						nonParticipationCnt = nonParticipationCnt + 1;
					}else if(pp.getParticipation().equals(Participation.Undecided)) {
						undecidedCnt = undecidedCnt + 1;
					}
					addList_switch = true;
					xx = xx + 1;
				} else{

					work_date = pp.getScheduleableDate().getScheduleDate();

					yy = xx - 1;
					break;
				}

			}

			if(addList_switch) {

				participationCntList.add(participationCnt);
				nonParticipationCntList.add(nonParticipationCnt);
				undecidedCntList.add(undecidedCnt);
				participationCnt = 0;
				nonParticipationCnt = 0;
				undecidedCnt = 0;
			}


			yy = yy +1;
		}

		model.addAttribute("participationCntList" , participationCntList);
		model.addAttribute("nonParticipationCntList" , nonParticipationCntList);
		model.addAttribute("undecidedCntList" , undecidedCntList);



		//-------------------------------------------------------------------

		return "participation/participation_status_boot";
	}

}
