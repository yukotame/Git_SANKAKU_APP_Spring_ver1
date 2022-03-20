package sankaku.app.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/init")
public class InputInitialMenuController {


	@GetMapping("/menu")
	public String init(@ModelAttribute("complete")String complete) {

		return "menu/initial_menu_boot";
	}




}
