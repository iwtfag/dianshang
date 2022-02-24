package cache;

import dao.GoodsDao;
import entity.Goods;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveCaChe {
    /**
     * 背景：当数据在缓存中没有 ，只有数据库中存在
     * 该方法会把数据库中的数据存到缓存中
     *
     * @param goodsName
     * @return set 存 cache   key值是id
     * setName 存 cache key
     */
    public static String SaveCaCheByDB(String goodsName) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Map<String, String> map = new HashMap<>();

        String set = "";


        GoodsDao dao = new GoodsDao();
        Goods goods = null;
        List<Goods> goodsList = dao.queryGoods(goodsName);
        for (int i = 0; i < goodsList.size(); i++) {
            goods = goodsList.get(i);
            map.put("name", goods.getGoods_name());
            map.put("img", goods.getGoods_img());
            map.put("good_price", goods.getGoods_price());
            map.put("description", goods.getDescription());
            map.put("id", goods.getGoods_id());
            map.put("detail", goods.getGoods_detail());

            //存储 name----id 列表
            jedis.sadd(goodsName, goods.getGoods_id());
            //存储 id------属性列表
            set = jedis.hmset(goods.getGoods_id(), map);


        }


        return set;
    }
}
