package dao;

import com.alibaba.fastjson.JSONObject;
import entity.Goods;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库
 */

public class GoodsDao {
    /**
     * @param goods
     * @return ret 判断是否上传成功
     */
    public static int saveGoods(Goods goods) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int ret = -1;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "insert into goods(goods_name,goods_img,goods_price,description,goods_id,gmt_create,gmt_update,goods_detail) values(?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, goods.getGoods_name());
            stmt.setString(2, goods.getGoods_img());
            stmt.setString(3, goods.getGoods_price());
            stmt.setString(4, goods.getDescription());
            stmt.setString(5, goods.getGoods_id());
            stmt.setString(6, String.valueOf(new Timestamp(System.currentTimeMillis())));
            stmt.setString(7, String.valueOf(new Timestamp(System.currentTimeMillis())));
            stmt.setString(8, goods.getGoods_detail());
            ret = stmt.executeUpdate();
            if (ret != 1) {
                System.out.println("添加失败");
            } else {
                System.out.println("id:" + goods.getGoods_id() + "save in DB--------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);

        }
        return ret;

    }

    /**
     * 数据库查询所有的goods数据
     *
     * @param goods_name 商品名
     * @return goodList 返回一个商品列表，用来渲染index.html页面
     */
    public List<Goods> queryGoods(String goods_name) {
        Goods goods = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Goods> goodsList = new ArrayList<>();
        String goodsListJSON = "";
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "select * from goods where goods_name=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, goods_name);
            rs = stmt.executeQuery();
            while (rs.next()) {
                goods = new Goods();
                goods.setGoods_detail(rs.getString("goods_detail"));
                goods.setGoods_id(rs.getString("goods_id"));
                goods.setGoods_img(rs.getString("goods_img"));
                goods.setGmt_update(rs.getString("gmt_update"));
                goods.setGoods_price(rs.getString("goods_price"));
                goods.setDescription(rs.getString("description"));
                goods.setGmt_create(rs.getString("gmt_create"));
                goods.setGoods_name(rs.getString("goods_name"));
                goodsList.add(goods);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return goodsList;


    }

    public Goods getDetailGoods(String goods_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Goods goods = null;

        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "select * from goods where goods_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, goods_id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                goods = new Goods();
                goods.setGoods_detail(rs.getString("goods_detail"));
                goods.setGoods_id(rs.getString("goods_id"));
                goods.setGoods_img(rs.getString("goods_img"));
                goods.setGmt_update(rs.getString("gmt_update"));
                goods.setGoods_price(rs.getString("goods_price"));
                goods.setDescription(rs.getString("description"));
                goods.setGmt_create(rs.getString("gmt_create"));
                goods.setGoods_name(rs.getString("goods_name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return goods;
    }





}
