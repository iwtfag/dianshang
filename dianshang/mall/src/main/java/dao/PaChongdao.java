package dao;

import entity.Goods;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PaChongdao {


    /**
     * 爬取内容并存入数据库
     *
     * @param goods_name 要搜索的东西
     * @return
     * @throws IOException
     */
    public String spiderGoods(String goods_name, String url) throws IOException {
        System.out.println(url);
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Goods goods = null;
        //String cookie = "shshshfpa=bd0758de-7354-8b64-5ea1-64cae65164d7-1641904254; shshshfpb=z_L4_KNw4gOdCt9w3jvCsZw; user-key=b5083d7e-c272-4a9d-a75e-bd25f5fb6115; pinId=do1lGXkh-huWJ5YrXX6bfLV9-x-f3wj7; pin=jd_6f462e50e5bdd; unick=u_f6jfaqredig6; _tp=dLV1aEFVp1jsK8ZASc0Ur3pAWEc%2BqHTfoeyDK7C6LO0%3D; _pst=jd_6f462e50e5bdd; cd=1; ipLocation=%u5c71%u897f; areaId=15; __jdv=122270672|mall.sichuanqixin.com|t_1003336631_|tuiguang|f1cccb5c763c4bb59aef9232aca302b7|1643028554382; __jdu=164302855438076081036; ipLoc-djd=15-1213-3038-59931; __jda=122270672.164302855438076081036.1643028554.1643028554.1643028554.1; __jdc=122270672; wlfstk_smdl=mubuqqw6qjaz3b5btrw1kwum9yyye6g0; TrackID=1TQF1OI83VAHj5C48bABC1mzqYn4KlUdTSSkbJRIcp4W9IVNejI62RdKkEOic111pPJUHGqrefWXQTT8dVoTzIZJJ_DKRm4c0xdPlNhp2Ols; thor=8A2F5F8999A799400D1B04935B0BF6207E99BCD39C6F5098685725D5DAAC230B238DD5B7A4D9D63720452EB43B9443048BB196BFD896D928375F878C3B1F36EBF859FF3D7D95A247947EEB5D34869F3DDA9E1C2F8DE581CE0280871D9E78873F60630F861A714659F6C37AD45E5877C4BDF70A00B15F1D83155ED5F114189610883205581A2183D102211E5444B51ABBF864BDEB81E82A9C5A22193BE2E8BDBB; ceshi3.com=000; __jdb=122270672.6.164302855438076081036|1.1643028554; shshshfp=0da226ac6913dac3332dc6cf8576737e; shshshsID=c2662d46f770ec9bbcec300e63db350e_3_1643029466586; cn=1; 3AB9D23F7A4B3C9B=KZ3U7LWZTPLZZ5BXGMWOVIUXRJZJWUSVX3LJFXA6C6JQMSLHHHYSXIPZZVL6XRC7K74ZEB2MN3UGEDC2RV3ZNHGYZ4";
        String cookie2 = "__jda=122270672.16430312359001351467518.1643031236.1643031236.1643031236.1;";
        String cookie = "shshshfp=9eb9fe25c4af6d31a4c71952f83e7aed;shshshfpb=wiVWGAgq2HlaZDiOGVGlmuA";
        Map<String, String> map = new HashMap<>();



        Document document = Jsoup.connect(url).get();


        Element select = document.getElementById("J_goodsList");


        //select1是ul的位置
        Elements select1 = select.select("[class=gl-warp clearfix]");
        Elements select3 = select1.select("[class=gl-item]");//li
        Elements select2 = select3.select("[class=gl-i-wrap]").select("[class=p-img]").select("a[href]");
        Elements select4 = select3.select("[class=gl-i-wrap]").select("[class=p-price]");
        Elements select6 = select3.select("[class=gl-i-wrap]").select("[class=p-name p-name-type-2]");

        for (int i = 0; i < select3.size(); i++) {
            //商品的唯一id sku=>goods_id
            String goods_id = select3.eq(i).attr("data-sku");
            String goods_spu = select3.eq(i).attr("data-spu");
            //商品详情页的链接 detailHref
            String detailHref = select2.eq(i).attr("href");
            //商品的图片img
            String goods_img = select2.select("img").eq(i).attr("data-lazy-img");
            Elements select5 = select4.select("[class=J_" + goods_id + "]");
            //商品的价格price
            String price = select5.select("i").html();
            //商品的描述description
            String description = select6.select("a").select("em").eq(i).text();
            String newUrl = "https://cd.jd.com/description/channel?skuId=" + goods_id + "&mainSkuId=" + goods_spu;
            System.out.println(newUrl);
            if (!"".equals(goods_spu)) {
                goods = new Goods();
                goods.setGoods_name(goods_name);
                goods.setGoods_img(goods_img);
                goods.setGoods_id(goods_id);
                goods.setDescription(description);
                goods.setGoods_price(price);
                String detail = PaChongdao.getDetail(newUrl);

                goods.setGoods_detail(detail);


                // 判断detail是否为空 然后排除出去 剩余的数据存入数据库
                if (!"{}".equals(detail) && !"".equals(description)) {
                    GoodsDao.saveGoods(goods);
                    map.put("id", goods_id);
                    map.put("name", goods_name);
                    map.put("img", goods_img);
                    map.put("description", description);
                    map.put("good_price", price);
                    map.put("detail", detail);
                    jedis.sadd(goods_name, goods_id);
                    jedis.hmset(goods_id, map);

                    System.out.println("id:" + goods_id + "save in cache------");
                    System.out.println("name:" + goods_name + "save in redis---->name-id");
                }


            }
        }


        return goods_name;
    }

    /**
     * 得到详情页的内容
     *
     * @param url 在spiderGoods方法中拼接出来的newUrl，这个url是详情页的链接
     * @return 通过url返回一个详情页的body标签渲染内容
     * @throws IOException
     */
    public static String getDetail(String url) throws IOException {
//      String cookie = "shshshfpa=bd0758de-7354-8b64-5ea1-64cae65164d7-1641904254; shshshfpb=z_L4_KNw4gOdCt9w3jvCsZw; user-key=b5083d7e-c272-4a9d-a75e-bd25f5fb6115; pinId=do1lGXkh-huWJ5YrXX6bfLV9-x-f3wj7; pin=jd_6f462e50e5bdd; unick=u_f6jfaqredig6; _tp=dLV1aEFVp1jsK8ZASc0Ur3pAWEc%2BqHTfoeyDK7C6LO0%3D; _pst=jd_6f462e50e5bdd; cd=1; ipLocation=%u5c71%u897f; areaId=15; __jdv=122270672|mall.sichuanqixin.com|t_1003336631_|tuiguang|f1cccb5c763c4bb59aef9232aca302b7|1643028554382; __jdu=164302855438076081036; ipLoc-djd=15-1213-3038-59931; __jda=122270672.164302855438076081036.1643028554.1643028554.1643028554.1; __jdc=122270672; wlfstk_smdl=mubuqqw6qjaz3b5btrw1kwum9yyye6g0; TrackID=1TQF1OI83VAHj5C48bABC1mzqYn4KlUdTSSkbJRIcp4W9IVNejI62RdKkEOic111pPJUHGqrefWXQTT8dVoTzIZJJ_DKRm4c0xdPlNhp2Ols; thor=8A2F5F8999A799400D1B04935B0BF6207E99BCD39C6F5098685725D5DAAC230B238DD5B7A4D9D63720452EB43B9443048BB196BFD896D928375F878C3B1F36EBF859FF3D7D95A247947EEB5D34869F3DDA9E1C2F8DE581CE0280871D9E78873F60630F861A714659F6C37AD45E5877C4BDF70A00B15F1D83155ED5F114189610883205581A2183D102211E5444B51ABBF864BDEB81E82A9C5A22193BE2E8BDBB; ceshi3.com=000; __jdb=122270672.6.164302855438076081036|1.1643028554; shshshfp=0da226ac6913dac3332dc6cf8576737e; shshshsID=c2662d46f770ec9bbcec300e63db350e_3_1643029466586; cn=1; 3AB9D23F7A4B3C9B=KZ3U7LWZTPLZZ5BXGMWOVIUXRJZJWUSVX3LJFXA6C6JQMSLHHHYSXIPZZVL6XRC7K74ZEB2MN3UGEDC2RV3ZNHGYZ4";
        String cookie = "__jda=181809404.16430897790621155401474.1643089779.1643089779.1644503053.2;";
        String a = ".header(\"cookie\", cookie)";
        String body = Jsoup.connect(url).ignoreContentType(true).header("cookie", cookie).execute().body();
        //System.out.println(document);
//        String body = document.getElementsByTag("body").text();


        return body;

    }

    /**
     * 通过该方法获取京东前八页的内容
     * 该方法单纯拼接了前八页的url，然后通过spiderGoods方法爬取数据
     *
     * @param goods_name
     * @return
     * @throws IOException
     */
    public String spiderGoodsPage(String goods_name) throws IOException {
        PaChongdao dao = new PaChongdao();
        StringBuffer s = new StringBuffer();
        String s1 = s.append("https://search.jd.com/Search?keyword=").append(goods_name).append("&page=").toString();
        for (int i = 1; i < 2; i = i + 2) {
            String url = s1 + i;
            System.out.println(url);
            System.out.println("================");
            String s2 = dao.spiderGoods(goods_name, url);
            System.out.println(s2);

        }
        return goods_name;
    }


    public String spiderByName(String goodsName) {
        return goodsName;
    }


}
