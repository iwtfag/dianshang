
package manager;

import cache.QueryDetailCache;
import com.alibaba.fastjson.JSONObject;
import dao.GoodsDao;
import entity.Goods;

/**
 * 这是detail页面的管理层
 * 逻辑
 * 1.通过id从cache获取detail数据
 * 2.如果没有，同过id从db获取数据
 */


public class GoodsMgrDetail {

    public Goods DetailById(String id) {
        Goods goods = null;

        //Todo 从缓存获取数据
        goods = QueryDetailCache.CacheById(id);
        String s = JSONObject.toJSONString(goods);

        if (!s.equals("{}")) {
            System.out.println("cache get detail");
            return goods;
        }

        //todo 从db获取数据
        GoodsDao dao = new GoodsDao();
        goods = dao.getDetailGoods(id);
        if (goods != null) {
            System.out.println("db get detail");
            return goods;
        }


        return null;
    }


}

