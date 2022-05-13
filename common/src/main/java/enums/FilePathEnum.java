package enums;

/**
 * @program: springBoot-demo
 * @description:
 * @author: fbl
 * @create: 2021-10-26 15:56
 **/
public enum FilePathEnum {

    PATH("C:/var/guoheng/picture/");


    private String value;

    FilePathEnum(String value) {
        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
