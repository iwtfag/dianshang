package dao;

import entity.Car;
import entity.Goods;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao {
    public static int saveCar(String goods_id, String user_id) {

        Connection conn = null;
        PreparedStatement stmt = null;
        Car car = null;
        int ret = -1;
        try {
            //Todo
            car = new Car();
            conn = JDBCUtils.getJdbcConnection();
            String sql = "insert into car(goods_id,gmt_create,gmt_update,userid,num)values(?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, goods_id);
            stmt.setString(2, String.valueOf(new Timestamp(System.currentTimeMillis())));
            stmt.setString(3, String.valueOf(new Timestamp(System.currentTimeMillis())));
            stmt.setString(4, user_id);
            stmt.setString(5, "1");
            ret = stmt.executeUpdate();
            if (ret != 1) {
                System.out.println("添加失败");
            } else {
                System.out.println("添加成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }
        return ret;
    }

    public List<Car> queryCarList(String id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Car car = null;
        List<Car> carList = new ArrayList<>();
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "select * from car where userid = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                car = new Car();
                car.setGoods_id(rs.getString("goods_id"));
                car.setUserid(rs.getString("userid"));
                car.setGmt_update(rs.getString("gmt_update"));
                car.setGoods_id(rs.getString("goods_id"));
                car.setGmt_create(rs.getString("gmt_create"));
                carList.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return carList;

    }

    /**
     * 通过uid删除车中全部商品
     * @param uid
     * @return
     */
    public static int DeleteAll(String uid){
        Connection conn = null;
        PreparedStatement stmt = null;
        int ret = -1;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "delete from car where userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uid);

            ret = stmt.executeUpdate();
            if (ret != 1) {
                System.out.println("商品删除失败");
            } else {
                System.out.println("商品删除成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }

        return ret;
    }

    public static int DeleteGoods(String gid,String uid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int ret = -1;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "delete from car where goods_id = ? and userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, gid);
            stmt.setString(2,uid);
            ret = stmt.executeUpdate();
            if (ret != 1) {
                System.out.println("商品删除失败");
            } else {
                System.out.println("商品删除成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }

        return ret;
    }


    public static int updateNum(Car car) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int ret = -1;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "UPDATE car set num = ? where goods_id = ? and userid = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, car.getNum());
            stmt.setString(2, car.getGoods_id());
            stmt.setString(3, car.getUserid());
            ret = stmt.executeUpdate();
            if (ret==1){
                System.out.println("successfully");
            }else {
                System.out.println("fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }
        return ret;
    }


}
