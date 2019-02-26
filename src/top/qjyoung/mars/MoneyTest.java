package top.qjyoung.mars;

import java.math.BigDecimal;

/**
 * @author QiaoJianYong
 * @date 8:02 PM 2018/12/12
 * @email chinaqiaojianyong@gmail.com
 */
public class MoneyTest {
    public static void main(String[] args) {
        BigDecimal money = new BigDecimal(120.21);
        BigDecimal bigDecimal = money.multiply(new BigDecimal(120.21)).divide(new BigDecimal(120.21));
        System.out.println(bigDecimal);
    }
}
