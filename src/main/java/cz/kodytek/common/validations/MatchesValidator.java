package cz.kodytek.common.validations;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchesValidator implements ConstraintValidator<Matches, Object> {

    private String targetFieldName;
    private String fieldName;

    @Override
    public void initialize(Matches constraintAnnotation) {
        fieldName = constraintAnnotation.name();
        targetFieldName = constraintAnnotation.targetFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, targetFieldName);
            final Object secondObj = BeanUtils.getProperty(value, fieldName);

            System.out.println("P1: " + firstObj);
            System.out.println("P2: " + secondObj);

            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
