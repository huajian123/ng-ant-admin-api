/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-21 13:43
 **/
public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

        if (stringThreadLocal.get() == null) {
            String s = stringThreadLocal.get();

            System.out.println("null");
        }else{
            System.out.println("!=null");
        }

    }
}
