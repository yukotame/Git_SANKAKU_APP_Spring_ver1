package sankaku.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sankaku.domain.model.Authority;
import sankaku.domain.model.User;
import sankaku.domain.service.user.UserRegisterService;


@Controller
@RequestMapping("userRegister")
public class UserRegisterController {

	@Autowired
	UserRegisterService userRegisterService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@ModelAttribute
	public UserForm setUserForm() {
		return new UserForm();
	}

	@GetMapping("form")
	String userInputGet() {
		//ユーザー登録入力画面の表示
		return "user/user_register_boot";
	}

	@PostMapping("form")
	String userInputPost() {
		//ユーザー登録入力画面の表示
		return "user/user_register_boot";
	}

	@PostMapping("register")
	String userRegister(@Validated  UserForm userForm , BindingResult result , Model model ) {


		//ユーザー登録の入力にエラーがあった場合、入力フォームに戻る。
		if(result.hasErrors()) {
			model.addAttribute("error", "ユーザー登録情報に誤りがあります。");
			return "user/user_register_boot";
		}


		//ユーザー登録確認画面へ
		return "user/user_confirm_boot";
	}

	@PostMapping("confirm")
	String userConfirm(@Validated  UserForm userForm , BindingResult result , Model model
			,RedirectAttributes redirectAttributes) {

		User user = new User();
		user.setUserId(userForm.getUserId());
		user.setUserName(userForm.getName());
		user.setAuthority(Authority.USER);
		user.setEmail(userForm.getEmail());
		user.setPassword(passwordEncoder.encode(userForm.getPassword()));

		//ユーザー登録処理へ
		try {
			userRegisterService.register(user);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}

		//入力画面へリダイレクト（二重クリック防止-PRG）
		redirectAttributes.addFlashAttribute("complete", "Completed!");
		return "redirect:/init/menu";

	}
}
