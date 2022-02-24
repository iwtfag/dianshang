
import cache.GoodsCache;
import com.alibaba.fastjson.JSONObject;
import dao.CarDao;
import dao.OrderDao;
import dao.PaChongdao;
import dao.UserDao;
import entity.Goods;
import entity.Order;
import entity.OrderDetail;
import entity.User;
import manager.OrderMgr;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import utils.JDBCUtils;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Test {

    @org.junit.Test
    public void test16() throws IOException {
        PaChongdao dao = new PaChongdao();
        dao.spiderGoodsPage("nike");
    }

    @org.junit.Test
    public void test17() throws IOException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Map<String, String> map = jedis.hgetAll("nike:66787767364");
        Set<String> keys = jedis.keys("nike*");
        System.out.println(jedis.hgetAll("nike:66787767364"));


    }

    @org.junit.Test
    public void test18() throws IOException {
        List<Goods> goods = GoodsCache.queryGoodsListByCache("nike");
        String s = JSONObject.toJSONString(goods);
        System.out.println(s);


    }

    @org.junit.Test
    public void test19() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        Set<String> nike = jedis.smembers("juice");

        List<String> list = new ArrayList<String>(nike);
        for (int i = 0; i < list.size(); i++) {

        }
    }

    @org.junit.Test
    public void test20() throws IOException {
        List<OrderDetail> list = OrderDao.queryDetailByUid("1");
        System.out.println(JSONObject.toJSONString(list));
    }

    @org.junit.Test
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + i; j < nums.length; j++) {
                target = nums[i] + nums[j];
                return new int[]{i, j};
            }
        }
        return new int[0];
    }
}
//https://cd.jd.com/description/channel?skuId=10036508162101&mainSkuId=10021480602380&charset=utf-8&cdn=2