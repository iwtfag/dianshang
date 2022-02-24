import com.alibaba.fastjson.JSONObject;
import dao.CarDao;
import entity.Car;
import entity.Goods;
import manager.CarMgr;
import manager.GoodsMgrDetail;
import org.junit.Test;

import java.util.List;

public class Test01 {

    @Test
    public void test01(){
        GoodsMgrDetail mgr = new GoodsMgrDetail();
        Goods goods = mgr.DetailById("41438284654");
        System.out.println(goods.getGoods_name());
    }
    @Test
    public void test02(){
        Car car = new Car();
        car.setNum("20");
        car.setUserid("1");
        car.setGoods_id("209951");
        CarDao.updateNum(car);
    }
    @Test
    public void test03(){
        CarDao.saveCar("1002992","2");
    }
    @Test
    public void test04(){
        CarDao dao = new CarDao();
        List<Car> carList = dao.queryCarList("1");
        System.out.println(carList);
        for (int i = 0;i< carList.size();i++){
            Car car = carList.get(i);
            String s = JSONObject.toJSONString(car);
            System.out.println(s);
        }
    }
    @Test
    public void test05(){
        List<Goods> carList = CarMgr.getCarList("1");
        for (int i = 0;i<carList.size();i++){
            Goods goods = carList.get(i);
            String s = JSONObject.toJSONString(goods);
            System.out.println(s);
        }
    }
    @Test
    public void test06(){
        Car car = new Car();
        car.setUserid("1");
        car.setGoods_id("41488171278");
        car.setNum("10");
        CarDao.updateNum(car);
    }
}
