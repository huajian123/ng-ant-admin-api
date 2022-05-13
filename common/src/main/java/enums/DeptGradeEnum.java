package enums;

/**
 * @program: fire
 * @description: 部门级别
 * @author: fbl
 * @create: 2021-08-03 17:09
 **/
public enum DeptGradeEnum {

    FIRST(1),
    SECOND(2),
    THIRD(3);

    private Integer value;

    DeptGradeEnum(Integer value) {
        this.value = value;

    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
