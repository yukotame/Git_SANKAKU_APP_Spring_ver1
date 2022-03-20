package sankaku.config;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class WebMvcControllerAdvice {

	/*
	 * This method changes empty character to null
	 */
	//共通例外を外だしできる。
	/*	@ExceptionHandler(NoSuchElementException.class)
		public String handleException(NoSuchElementException e , Model model) {
			model.addAttribute("message" , "スケジュールが検索できませんでした☆。");
			return "/error/CustomPage";

		}*/

}