package validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类功能描述:　校验枚举实现
 *
 * @author Eternal
 * @date 2018/11/8 14:37
 */

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Object key, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = isInEnum(enumClass, key);
        if(!result){
            //禁用默认提示信息
            constraintValidatorContext.disableDefaultConstraintViolation();
            //更新提示信息
            constraintValidatorContext.buildConstraintViolationWithTemplate(enumDetails(enumClass)).addConstraintViolation();
        }
        return result;
    }

    private static boolean isInEnum(Class<? extends Enum<?>> enumClass, Object key) {

        if (null == key || null == enumClass) {
            return false;
        }

        Object[] objects = enumClass.getEnumConstants();
        try {
            Method code = enumClass.getMethod("getCode");
            for (Object obj : objects) {
                if (code.invoke(obj) == key) {
                    return true;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String enumDetails(Class<? extends Enum<?>> enumClass){
        StringBuilder message = new StringBuilder("枚举类型范围[ ");
        Object[] objects = enumClass.getEnumConstants();
        try {
            Method code = enumClass.getMethod("getCode");
            Method msg = enumClass.getMethod("getMsg");
            for (Object obj : objects) {
                message.append(code.invoke(obj).toString()).append(":").append(msg.invoke(obj)).append(" ");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return message + "]";
    }
}
