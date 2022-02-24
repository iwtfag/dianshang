package manager;

import cache.GoodsCache;
import cache.SaveCaChe;
import com.alibaba.fastjson.JSONObject;
import dao.GoodsDao;
import dao.PaChongdao;
import entity.Goods;

import java.io.IOException;
import java.util.List;

public class GoodsMgr {

    /**
     * 获取所有的数据
     * 这个方法用来获取渲染index页面的goodsList数据
     * 获取数据分三步
     * 第一步，从cache获取
     * 第二步，获取不到，从dao的queryGoods方法获取(这个不用写管理)
     * 再获取不到，从spider获取(spider获取到存数据库，然后再走第二步)
     */

    public static List<Goods> getGoodsList(String goodsName) throws IOException {

        List<Goods> goodsList = null;
        GoodsDao dao = new GoodsDao();
        PaChongdao c_dao = new PaChongdao();


        // TODO: 从缓存里获取数据
        goodsList = GoodsCache.queryGoodsListByCache(goodsName);
        String s1 = JSONObject.toJSONString(goodsList);

        if (!s1.equals("[]")) {
            System.out.println("cache");
            return goodsList;
        }

        // TODO: 从DB里获取数据
        goodsList = dao.queryGoods(goodsName);
        String s3 = SaveCaChe.SaveCaCheByDB(goodsName);
        if (!s3.equals("")) {
            System.out.println("data save from DB to cache");
        }
        String s2 = JSONObject.toJSONString(goodsList);


        if (!s2.equals("[]")) {
            System.out.println("DB");
            return goodsList;
        }

        // TODO: 从爬虫获取数据
        String s = c_dao.spiderGoodsPage(goodsName);

        goodsList = dao.queryGoods(s);

        if (goodsList != null) {

            System.out.println("spider");
            return goodsList;
        }

        System.out.println("商品不存在~~~~");

        return null;

    }


}
