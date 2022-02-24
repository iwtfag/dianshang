import cache.GoodsCache;
import cache.SaveCaChe;
import com.alibaba.fastjson.JSONObject;
import dao.GoodsDao;
import dao.OrderDao;
import dao.PaChongdao;
import entity.Goods;
import manager.OrderMgr;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static javafx.beans.binding.Bindings.select;

public class Testp {

    @Test
    public void test01() throws IOException {
        String url = "https://search.jd.com/Search?keyword=polo";
        Document document = Jsoup.connect(url).get();
        //System.out.println(document);
        String s = "#J_goodsList > ul > li:nth-child(2) > div > div.p-img > a > img";
        Element select = document.getElementById("J_goodsList");
        //select1是ul的位置
        Elements select1 = select.select("[class=gl-warp clearfix][data-tpl=3]");
        Elements select3 = select1.select("[class=gl-item]");//li
        Elements select2 = select3.select("[class=gl-i-wrap]").select("[class=p-img]").select("a[href]");
        Elements select4 = select3.select("[class=gl-i-wrap]").select("[class=p-price]");
        Elements select6 = select3.select("[class=gl-i-wrap]").select("[class=p-name p-name-type-2]");


        for (int i = 0; i < select3.size(); i++) {
            //商品的唯一id sku=>goods_id
            String goods_id = select3.eq(i).attr("data-sku");


            //商品详情页的链接 detailHref
            String detailHref = select2.eq(i).attr("href");

            //商品的图片img
            String img = select2.select("img").eq(i).attr("data-lazy-img");

            Elements select5 = select4.select("[class=J_" + goods_id + "]");
            //商品的价格price
            String price = select5.select("i").html();

            //商品的描述description
            String description = select6.select("a").select("em").eq(i).text();


        }


    }

    @Test
    public void test02() throws IOException {

        String url = "https://cd.jd.com/description/channel?skuId=10036508162101&amp;mainSkuId=10021480602380";
        Document document = Jsoup.connect(url).ignoreContentType(true).header("cookie", "shshshfpa=bd0758de-7354-8b64-5ea1-64cae65164d7-1641904254; shshshfpb=z_L4_KNw4gOdCt9w3jvCsZw; user-key=b5083d7e-c272-4a9d-a75e-bd25f5fb6115; pinId=do1lGXkh-huWJ5YrXX6bfLV9-x-f3wj7; pin=jd_6f462e50e5bdd; unick=u_f6jfaqredig6; _tp=dLV1aEFVp1jsK8ZASc0Ur3pAWEc%2BqHTfoeyDK7C6LO0%3D; _pst=jd_6f462e50e5bdd; ipLocation=%u5c71%u897f; __jdv=76161171|direct|-|none|-|1643006605228; __jdu=16430066052271622263954; areaId=15; ipLoc-djd=15-1213-0-0; __jda=122270672.16430066052271622263954.1643006605.1643006605.1643006605.1; __jdc=122270672; shshshfp=0da226ac6913dac3332dc6cf8576737e; wlfstk_smdl=gl21ywp0gwm7tf8rtlie9adfbpp5zvpg; TrackID=19CnAfGQPn5Q3MA9_vWx_88vleIrTHf0kT5pIBYwBuNEmR7EgSd97cuWloyBF3djL3a_mQjIKA5ElY1bl-3j81s8T2Zlv-xmljOBjfSeixt4; thor=8A2F5F8999A799400D1B04935B0BF6207E99BCD39C6F5098685725D5DAAC230B0284A463637E62A7405D36E244F8D8B6F65147E1BC01647BFE233DCE62269EF3B578898CFC90B113A39736922711BC997F8A5E06E88C9DDE4F1085BC75DD66A13433EF957BD6BFE89F5CD3330B72AFC3802D8DAFD56CAD4A876326AFB80A7FDD54B1E0EEA45EF3505ACD3AEB2F4C8A9D6AB5E8802D0D1652141C60FD1C02D578; ceshi3.com=000; __jdb=122270672.5.16430066052271622263954|1.1643006605; shshshsID=b2da6f4bcd0746df574c1381511f2d01_3_1643006672310; cn=1; 3AB9D23F7A4B3C9B=KZ3U7LWZTPLZZ5BXGMWOVIUXRJZJWUSVX3LJFXA6C6JQMSLHHHYSXIPZZVL6XRC7K74ZEB2MN3UGEDC2RV3ZNHGYZ4").get();
        System.out.println(document);


    }

    @Test
    public void test2() throws IOException {
        PaChongdao dao = new PaChongdao();
        String s = dao.spiderGoodsPage("沙发");

    }

    @Test
    public void test3() throws IOException {
        Map<String, String> cookie = null;
        Connection.Response res = Jsoup.connect("https://item.jd.com/10026410786340.html").timeout(30000).execute();
        cookie = res.cookies();
        System.out.println(cookie);
    }

    @Test
    public void test04() throws IOException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Goods goods = new Goods();
        List<Goods> goodsList = new ArrayList<>();
        Map<String, String> map = jedis.hgetAll("nike:66787767364");
        //System.out.println(map);
        goods.setGoods_id(map.get("id"));
        goods.setGoods_price(map.get("good_price"));
        goods.setGoods_img(map.get("img"));
        goods.setGoods_detail(map.get("detail"));
        goods.setGoods_name(map.get("name"));
        goods.setDescription(map.get("description"));
        goodsList.add(goods);
        System.out.println(goods.getGoods_img());
    }

    @Test
    public void test05() throws IOException {
        List<Goods> goods = GoodsCache.queryGoodsListByCache("nike");
        String s = JSONObject.toJSONString(goods);
        System.out.println(s);

    }

    @Test
    public void test06() throws IOException {
        PaChongdao dao = new PaChongdao();
        String s = dao.spiderGoodsPage("颜料");
        GoodsDao dao1 = new GoodsDao();
        List<Goods> goods = dao1.queryGoods(s);

    }

    @Test
    public void test07() {

    }

    @Test
    public void test08() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        Map<String, String> map = new HashMap<>();
        String set = "";
        GoodsDao dao = new GoodsDao();
        Goods goods = null;
        List<Goods> goodsList = dao.queryGoods("nike");
        for (int i = 0; i < goodsList.size(); i++) {
            goods = goodsList.get(i);

            map.put("name", goods.getGoods_name());
            map.put("img", goods.getGoods_img());
            map.put("good_price", goods.getGoods_price());
            map.put("description", goods.getDescription());
            map.put("id", goods.getGoods_id());
            map.put("detail", goods.getGoods_detail());
            System.out.println(map);
            //set = jedis.hmset(goods.getGoods_name() + ":" + goods.getGoods_id(), map);
        }
    }

    @Test
    public void test09() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Map<String, String> map = jedis.hgetAll("nike:2888775361");
        String s = JSONObject.toJSONString(map);
        if (s.equals("{}")) {
            System.out.println(1);
        }
        //System.out.println(s);
    }

    @Test
    public void test10() {

        int a[] = {1, 2, 3, 4, 5, 9};
        int target = 13;
        int[] ints = Solution.twoSum(a, target);
        for (int b = 0; b < ints.length; b++) {
            System.out.println(ints[b]);
        }
    }
}
