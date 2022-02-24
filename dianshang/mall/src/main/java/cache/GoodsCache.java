package cache;

import com.alibaba.fastjson.JSONObject;
import entity.Goods;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GoodsCache {
    /**
     * 该方法是获取商品列表数据从缓存中
     *
     * @param goods_name
     * @return
     * @throws IOException
     */
    public static List<Goods> queryGoodsListByCache(String goods_name) throws IOException {

        System.out.println("===========================================");
        List<Goods> goodsList = new ArrayList<>();
        Goods goods = null;
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //得到该goods_name下所有商品的名称keys
        Set<String> members = jedis.smembers(goods_name);

        //Set<String> keys = jedis.keys(goods_name + ":*");
        List<String> list = new ArrayList<String>(members);
        for (int i = 0; i < list.size(); i++) {
            String gid = list.get(i);
            Map<String, String> map = jedis.hgetAll(gid);
            goods = new Goods();
            goods.setGoods_id(map.get("id"));

            goods.setGoods_price(map.get("good_price"));
            goods.setGoods_img(map.get("img"));
            goods.setGoods_detail(map.get("detail"));
            goods.setGoods_name(map.get("name"));
            goods.setDescription(map.get("description"));

            goodsList.add(goods);

        }


        return goodsList;
    }
}
