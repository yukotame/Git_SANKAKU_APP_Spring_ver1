package sankaku.app.menu;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sankaku.domain.service.user.SankakuUserDetails;

@Controller
@RequestMapping("/login_menu")
public class InputLoginMenuController {

	@GetMapping
	public String login_menu(@AuthenticationPrincipal SankakuUserDetails userdetails , Model model) {

		model.addAttribute("userName", userdetails.getUsername());
		return "menu/login_menu_boot";


	}
}
