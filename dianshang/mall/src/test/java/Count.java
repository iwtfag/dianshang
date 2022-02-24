import dao.CarDao;
import dao.GoodsDao;
import dao.PaChongdao;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

public class Count {
    @Test
    public void test01() throws IOException {
        CarDao.DeleteGoods("100011207891","1");
    }

}
