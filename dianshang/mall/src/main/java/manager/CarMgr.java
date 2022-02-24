package manager;

import dao.CarDao;
import dao.GoodsDao;
import entity.Car;
import entity.Goods;

import java.util.ArrayList;
import java.util.List;

public class CarMgr {
    /**
     * 通过一个user_id获取car列表的goods_id
     * 再通过goods_id查询到goods列表
     */
    public static List<Goods> getCarList(String uid) {
        CarDao dao = new CarDao();
        GoodsDao gDao = null;
        Goods goods = null;
        List<Goods> goodsList = new ArrayList<>();
        List<Car> carList = dao.queryCarList(uid);
        for (int i = 0; i < carList.size(); i++) {
            gDao = new GoodsDao();
            Car car = carList.get(i);
            String goods_id = car.getGoods_id();
            goods = gDao.getDetailGoods(goods_id);
            goodsList.add(goods);
        }
        return goodsList;
    }
}
