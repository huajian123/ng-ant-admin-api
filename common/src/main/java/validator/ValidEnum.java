package validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 接口功能描述:　自定义枚举类型校验接口
 *
 * @author Eternal
 * @date 2018/11/8 14:14
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidEnum.List.class)
@Documented
@Constraint(validatedBy = ValidEnumValidator.class)
public @interface ValidEnum {

    /**
     * 自定义
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * 用来定义默认得消息模版, 当这个约束条件被验证失败的时候,通过此属性来输出错误信息
     */
    String message() default "";

    /**
     * 用于指定这个约束条件属于哪些校验组(必须)
     */
    Class<?>[] groups() default {};

    /**
     * 通过此属性来给约束条件指定严重级别. 这个属性并不被API自身所使用(必须)
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 定义List，为了让Bean的一个属性上可以添加多套规则
     */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ValidEnum[] value();
    }
}
