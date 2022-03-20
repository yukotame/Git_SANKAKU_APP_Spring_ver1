package sankaku.app.schedule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class NotSameDateValidator implements ConstraintValidator<NotSameDate, ScheduleForm>{

	private String message;

	@Override
	public void initialize(NotSameDate constraintAnnotation) {
		message = constraintAnnotation.message();

	}

	@Override
    public boolean isValid(ScheduleForm value, ConstraintValidatorContext context) {


		//nullでないかつ同じ日を持っている場合エラー
		if(value.getScheduleDate1() != null && value.getScheduleDate2() != null &&
		   value.getScheduleDate1().equals(value.getScheduleDate2())) {
			context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("scheduleDate2").addConstraintViolation();
			return false;
		}

		//nullでないかつ同じ日を持っている場合エラー
		if(value.getScheduleDate2() != null && value.getScheduleDate3() != null &&
				value.getScheduleDate2().equals(value.getScheduleDate3())) {
			context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("scheduleDate1").addConstraintViolation();
			return false;
		}

		//nullでないかつ同じ日を持っている場合エラー
		if(value.getScheduleDate3() != null && value.getScheduleDate1() != null &&
				value.getScheduleDate3().equals(value.getScheduleDate1())) {
			context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("scheduleDate1").addConstraintViolation();
			return false;
		}

    	return true;

	}

}
