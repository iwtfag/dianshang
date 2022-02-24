package dao;

import entity.Goods;
import entity.Order;
import entity.OrderDetail;
import entity.User;
import utils.JDBCUtils;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    /**
     * 存储order数据的方法
     *
     * @param uid gid Num
     * @return ret=1 success
     * ret！=1 fail
     */
    public static int saveOrder(String uid, String gid, String Num) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Order order = null;
        int ret = -1;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "insert into orderGoods(user_id,goods_id,goods_num,gmt_create)values(?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            order = new Order();
            stmt.setString(1, uid);
            stmt.setString(2, String.valueOf(gid));
            stmt.setString(3, Num);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ret = stmt.executeUpdate();
            if (ret == 1) {
                System.out.println("success insert data to order");
            } else {
                System.out.println("fail insert data to order");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }
        return ret;

    }


    /**
     * 通过queryId获取到id 用于下面方法查取此人的所有订单信息
     *
     * @param uid
     * @return
     */
    public static List<Order> queryOrderById(String uid) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Order order = null;
        List<Order> orderList = new ArrayList<>();
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "select * from orderGoods where user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                order = new Order();
                order.setId_order(rs.getString("id_order"));
                order.setUser_id(rs.getString("user_id"));
                order.setGoods_id(rs.getString("goods_id"));
                order.setGoods_num(rs.getString("goods_num"));
                order.setGmt_create(rs.getString("gmt_create"));
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return orderList;

    }

    /**
     * 删除一条订单数据
     *
     * @param id =>id_order
     * @return 返回一个确定值
     */
    public static int deleteById_order(String id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int i = -1;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "delete from orderGoods where id_order=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            i = stmt.executeUpdate();
            if (i == 1) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }
        return i;

    }


    public static Order queryNum(String uid, String gid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Order order = null;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "select * from ordergoods where user_id = ? and goods_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uid);
            stmt.setString(2, gid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                order = new Order();
                order.setId_order(rs.getString("id_order"));
                order.setUser_id(rs.getString("user_id"));
                order.setGoods_id(rs.getString("goods_id"));
                order.setGoods_num(rs.getString("goods_num"));
                order.setGmt_create(rs.getString("gmt_create"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return order;
    }

    public static List<OrderDetail> queryDetailByUid(String uid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String s = "";
        OrderDetail od = null;
        List<OrderDetail> detailList = new ArrayList<>();
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = " SELECT ordergoods.goods_id,ordergoods.gmt_create,ordergoods.id_order,ordergoods.goods_num,goods.goods_img,goods.goods_name,goods.goods_price,goods.description FROM ordergoods,goods where ordergoods.goods_id=goods.goods_id and ordergoods.user_id=? ORDER BY ordergoods.gmt_create desc";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                od = new OrderDetail();
                od.setGoods_id(rs.getString("goods_id"));
                od.setGmt_create(rs.getString("gmt_create"));
                od.setId_order(rs.getString("id_order"));
                od.setGoods_num(rs.getString("goods_num"));
                od.setGoods_img(rs.getString("goods_img"));
                od.setGoods_name(rs.getString("goods_name"));
                od.setGoods_price(rs.getString("goods_price"));
                od.setDescription(rs.getString("description"));
                detailList.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return detailList;

    }
}
