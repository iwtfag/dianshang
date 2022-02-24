package manager;


import com.alibaba.fastjson.JSONObject;
import dao.GoodsDao;
import dao.OrderDao;
import entity.Goods;
import entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMgr {

    /**
     * 订单管理层通过提供的uid来查询个人订单
     * 1.通过uid得出goods的description和goods的img，price
     * 然后通过uid和gid得出num
     * 查询出来的结果要有goods全部内容 还有有order表的数量
     */

    public static List<Goods> queryNumById(String userId) {
        GoodsDao dao = new GoodsDao();
        List<Goods> goodsList = new ArrayList<>();
        Goods goods = null;
        List<Order> orderList = OrderDao.queryOrderById(userId);
        for (int i = 0; i < orderList.size(); i++) {
            String gid = orderList.get(i).getGoods_id();

            goods = dao.getDetailGoods(gid);
            goodsList.add(goods);


        }
        return goodsList;
    }

    public static String NumByGidUid(String uid, String gid) {
        Order order = OrderDao.queryNum(uid, gid);
        String num = order.getGoods_num();
        return num;
    }

    public static String dateByGidUid(String uid, String gid) {
        Order order = OrderDao.queryNum(uid, gid);
        String date = order.getGmt_create();
        return date;
    }

    public static Order QueryOrder(String uid){
        List<Order> orderList = OrderDao.queryOrderById(uid);
        Order order = null;
        for (int i =0;i< orderList.size();i++){
            order = orderList.get(i);
        }return order;
    }
}
