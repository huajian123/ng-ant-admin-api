import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-21 09:31
 **/
public class LocalDateTest {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate date = now.withDayOfYear(212);

        LocalDate date1 = now.withYear(2022);


        Period period = Period.of(2021, 2, 3);
        System.out.println("period------>" +period.toString());

        Instant instant = Instant.now();
        System.out.println("instant---->" + instant.toString());

    }
}
