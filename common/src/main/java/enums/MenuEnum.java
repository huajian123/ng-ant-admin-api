package enums;

/**
 * @program: fire
 * @description: 菜单类型
 * @author: fbl
 * @create: 2021-08-03 17:09
 **/
public enum MenuEnum {

    /**
     * 菜单
     */
    MENU("C"),
    /**
     * 按钮
     */
    BUTTON("F");

    private String value;

    MenuEnum(String value) {
        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
