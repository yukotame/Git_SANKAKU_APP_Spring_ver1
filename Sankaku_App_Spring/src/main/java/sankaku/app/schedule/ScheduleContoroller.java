package sankaku.app.schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sankaku.domain.model.Schedule;
import sankaku.domain.model.ScheduleableDate;
import sankaku.domain.model.UserParticipation;
import sankaku.domain.model.UserParticipationId;
import sankaku.domain.service.participation.ParticipationService;
import sankaku.domain.service.schedule.ScheduleService;
import sankaku.domain.service.schedule.ScheduleableDateService;
import sankaku.domain.service.user.SankakuUserDetails;

@Controller
@RequestMapping("schedule")

public class ScheduleContoroller {

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	ScheduleableDateService scheduleableDateService;

	@Autowired
	ParticipationService participationService;


	@ModelAttribute
	public ScheduleForm setScheduleForm(@AuthenticationPrincipal SankakuUserDetails userdetails) {
		ScheduleForm form = new ScheduleForm();
		form.setOriginatorId(userdetails.getUser().getUserId());
		form.setOriginatorName(userdetails.getUser().getUserName());
		return form;
	}

	@ModelAttribute
	public ScheduleSelectForm setScheduleSelectForm() {
		ScheduleSelectForm form = new ScheduleSelectForm();
		form.setScheduleId(999);
		return form;
	}



	//スケジュール作成画面へ
	@GetMapping("/menu1")
	public String menu1(Model model) {
		model.addAttribute("menuId","menu1");

		return "schedule/create_schedule_boot";
	}

	//Menu1のスケジュール登録処理
	@PostMapping("/register")
	public String register(Model model,
							@Validated ScheduleForm scheduleForm ,
							BindingResult result ,
							@AuthenticationPrincipal SankakuUserDetails userdetails) {

		if(result.hasErrors()) {
			model.addAttribute("error", "スケジュール登録情報に誤りがあります。");
			return "schedule/create_schedule_boot";
		}
		//入力フォームからscheduleエンティティーに代入
		//（１対多の１側のセット）
		Schedule schedule = new Schedule();
		schedule.setScheduleName(scheduleForm.getScheduleName());
		schedule.setUser(userdetails.getUser());

		//Formから取得した日程データをArrayListに格納する
		//(３つの日程入力欄の日程データをまとめる。穴あきで入力されている場合を考慮)
		ArrayList<LocalDate> DateList = new ArrayList<LocalDate>();
		if(scheduleForm.getScheduleDate1() != null) {
			DateList.add(scheduleForm.getScheduleDate1());
		}
		if(scheduleForm.getScheduleDate2() != null) {
			DateList.add(scheduleForm.getScheduleDate2());
		}
		if(scheduleForm.getScheduleDate3() != null) {
			DateList.add(scheduleForm.getScheduleDate3());
		}

		//入力された日程データをscheduleableDateにセットし、scheduleDateListに追加
		List<ScheduleableDate> scheduleDateList = new ArrayList<ScheduleableDate>();
		for(LocalDate sdate : DateList) {


			//（１対多の多側のセット）
			ScheduleableDate scheduleableDate = new ScheduleableDate();
			//Formからの日付をscheduleableDate型に格納する
			scheduleableDate.setScheduleDate(sdate);
			//scheduleableDateにscheduleをセット
			scheduleableDate.setSchedule(schedule);

			//１対多の１側のセットするArrayList作成
			scheduleDateList.add(scheduleableDate);



		}
		//入力された日程データをScheduleに紐づくScheduleableDate型のArrayListのにセット（１対多）
		//（１対多の１側のセット）
		schedule.setScheduleableDateList(scheduleDateList);

		try {
			//スケジュールテーブル(schedule)とスケジュール候補日テーブル(scheduleable_date)に登録する
			scheduleService.regist(schedule);
		}catch(Exception e){

			model.addAttribute("error" , e.getMessage());
			return menu1(model);
		}

		model.addAttribute("resultScheduleId",schedule.getScheduleId());
		return "schedule/create_schedule_result_boot";
	}


	//スケジュール検索画面から参加可否入力画面へ
	@GetMapping("/menu2")
	public String menu2(Model model) {
		model.addAttribute("menuId","menu2");
		return "schedule/select_schedule_boot";
	}


	//スケジュール検索画面から参加一覧確認画面へ
	@GetMapping("/menu3")
	public String menu3(Model model) {
		model.addAttribute("menuId","menu3");
		return "schedule/select_schedule_boot";
	}

	//スケジュール検索画面から参加一覧確認画面へ
	@GetMapping("/menu4")
	public String menu4(Model model) {
		List<Schedule> allSchedule = scheduleService.selectAll();

		model.addAttribute("menuId","menu4");
		model.addAttribute("form_switch" , true);
		model.addAttribute("all_schedule" , allSchedule);
		return "schedule/select_schedule_result_boot";
	}



	//Menu2参加可否入力からスケジュール検索＆結果画面の表示
	@GetMapping("/select/menu2")
	public String selectMenu2(Model model ,
			@Validated ScheduleSelectForm form ,BindingResult result) {

		int scheduleId = form.getScheduleId();

		if(result.hasErrors()) {
			return "schedule/select_schedule_boot";
		}


		//スケジュールIDから検索できない場合の例外処理
		Schedule schedule = new Schedule();

		try {

			schedule = scheduleService.selectById(scheduleId);

		}catch(NoSuchElementException  e) {
			model.addAttribute("menuId","menu2");
			model.addAttribute("message" , "スケジュールが検索できませんでした。");
			return "/error/CustomPage";

		}

		model.addAttribute("scheduleName" , schedule.getScheduleName());
		model.addAttribute("scheduleId" , schedule.getScheduleId());
		model.addAttribute("menuId","menu2");
		model.addAttribute("form_switch" , false);

		return "schedule/select_schedule_result_boot";
	}

	//Menu2参加可否入力からスケジュール全件検索＆結果画面の表示
	@GetMapping("/selectAll/menu2")
	public String selectAllMenu2(Model model ) {
		List<Schedule> allSchedule = scheduleService.selectAll();

		model.addAttribute("menuId","menu2");
		model.addAttribute("all_schedule" , allSchedule);
		model.addAttribute("form_switch" , false);
		return "schedule/select_schedule_result_boot";
	}


	//Menu3参加可否確認からスケジュール検索画面の表示
	@GetMapping("/select/menu3")
	public String selectMenu3(Model model , @Validated ScheduleSelectForm form ,BindingResult result) {


		if(result.hasErrors()) {
			return "schedule/select_schedule_boot";
		}


		int scheduleId = form.getScheduleId();

		Schedule schedule = new Schedule();
		try {
			schedule = scheduleService.selectById(scheduleId);
		}catch(NoSuchElementException  e) {
			model.addAttribute("menuId","menu3");
			model.addAttribute("message" , "スケジュールが検索できませんでした☆☆。");
			return "/error/CustomPage";

		}
		model.addAttribute("scheduleName" , schedule.getScheduleName());
		model.addAttribute("scheduleId" , schedule.getScheduleId());
		model.addAttribute("menuId","menu3");
		model.addAttribute("form_switch" , false);
		return "schedule/select_schedule_result_boot";
	}

	//Menu3参加可否確認からスケジュール全件検索画面の表示
	@GetMapping("/selectAll/menu3")
	public String selectAllMenu3(Model model ) {
		List<Schedule> allSchedule = scheduleService.selectAll();

		model.addAttribute("menuId","menu3");
		model.addAttribute("all_schedule" , allSchedule);
		model.addAttribute("form_switch" , false);
		return "schedule/select_schedule_result_boot";
	}


	@PostMapping(path="/delete"  , params="cancel")
	String cancel(@AuthenticationPrincipal SankakuUserDetails userDetails,
			@RequestParam("scheduleId")Integer scheduleId ,
			Model model) {

		Schedule schedule = new Schedule();

		try {
			schedule = scheduleService.selectById(scheduleId);

		}catch(IllegalStateException e){
		//}catch(AccessDeniedException e){
			model.addAttribute("error" , e.getMessage());
		}

		//スケジュールIDに紐付くスケジュール日程テーブルを検索
		List<ScheduleableDate> scheduleableDateList = new ArrayList<ScheduleableDate>();
		try {
			scheduleableDateList = scheduleableDateService.selectByScheduleId(scheduleId);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error" , e.getMessage());
		}

		//スケジュールIDに紐付くスケジュール日程テーブルを検索。スケジュール日程テーブルのIDをリスト化（idList）
		List<Integer> idList = new ArrayList<Integer>();
		for(ScheduleableDate scheduleableDate: scheduleableDateList) {
			idList.add(scheduleableDate.getScheduleableDateId());


			//日程テーブルに紐付く参加者が存在するか？
			List<UserParticipation> userList = new ArrayList<UserParticipation>();
			try {
				userList = participationService.selectByScheduleableDateId(scheduleableDate.getScheduleableDateId());
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("error" , e.getMessage());
			}

			List<UserParticipationId> userParticipationIdList = new ArrayList<UserParticipationId>();
			for(UserParticipation user : userList) {
				userParticipationIdList.add(user.getUserParticipationId());
			}

			if(userList != null) {
				//スケジュール日程テーブルのIDリスト（idList）から参加可否テーブルと日程テーブルを削除
				participationService.deleteparticipationAll(userParticipationIdList , schedule.getUser().getUserId());

			}

			scheduleableDateService.deleteScheduleDate(scheduleableDate.getScheduleableDateId(), schedule.getUser().getUserId());
		}



		//スケジュール取り消し
		scheduleService.deleteSchedule(schedule , schedule.getUser().getUserId());

		return"redirect:/schedule/menu4";



	}



}
