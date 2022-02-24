package cache;

import entity.Goods;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class QueryDetailCache {
    public static Goods CacheById(String id){
        Goods goods = new Goods();
        Jedis jedis = new Jedis("127.0.0.1",6379);
        Map<String, String> map = jedis.hgetAll(id);
        goods.setGoods_id(map.get("id"));
        goods.setGoods_price(map.get("good_price"));
        goods.setGoods_img(map.get("img"));
        goods.setGoods_detail(map.get("detail"));
        goods.setGoods_name(map.get("name"));
        goods.setDescription(map.get("description"));
        return goods;
    }
}
