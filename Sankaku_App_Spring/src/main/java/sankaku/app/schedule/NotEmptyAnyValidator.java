package sankaku.app.schedule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class NotEmptyAnyValidator implements ConstraintValidator<NotEmptyAny, ScheduleForm>{

	private String message;

	@Override
	public void initialize(NotEmptyAny constraintAnnotation) {
		message = constraintAnnotation.message();

	}

	@Override
    public boolean isValid(ScheduleForm value, ConstraintValidatorContext context) {

    	if(value.getScheduleDate1() == null &&
			value.getScheduleDate2() == null &&
			value.getScheduleDate3() == null ){

			context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("scheduleDate1").addConstraintViolation();
    			return false;

    	}else {

    	return true;

    	}



    }

}
