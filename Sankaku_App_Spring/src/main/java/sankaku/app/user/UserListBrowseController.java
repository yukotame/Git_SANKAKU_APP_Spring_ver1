package sankaku.app.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sankaku.domain.model.User;
import sankaku.domain.service.user.UserBrowseService;


@Controller
@RequestMapping("userBrowse")
public class UserListBrowseController {

	@Autowired
	UserBrowseService userBrowseService;

	@GetMapping("menu5")
	String userBrowse( Model model ) {
		List<User> userList = new ArrayList<User>();
		userList = userBrowseService.userFindAll();

		model.addAttribute("userList" , userList);

		//ユーザー登録確認画面へ
		return "user/user_browse_boot";
	}


}
