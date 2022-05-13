import annotation.Excel;
import lombok.Data;
import util.ServletUtils;
import util.excel.ExcelUtil;

import java.util.ArrayList;

/**
 * @program: springBoot-demo
 * @description:  这里直接运行没有response，需要客户端浏览器操作
 * @author: fbl
 * @create: 2021-09-13 08:17
 **/
public class ExcelUtilTest {
    public static void main(String[] args) {
        ArrayList<MyMessage> myMessages = new ArrayList<>();
        MyMessage myMessage = new MyMessage();
        myMessage.setName("fbl");
        myMessage.setAge(25);
        myMessages.add(myMessage);

        // 测试导出工具类
        ExcelUtil<MyMessage> selectUserVoExcelUtil = new ExcelUtil<>(MyMessage.class);
        selectUserVoExcelUtil.exportExcel(myMessages,"测试", ServletUtils.getResponse());
    }
}

@Data
class MyMessage {
    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Integer id;
    @Excel(name = "用户名")
    private String name;
    @Excel(name = "年龄")
    private Integer age;
}
