package controller.order;

import com.alibaba.fastjson.JSONObject;
import dao.OrderDao;
import entity.Goods;
import entity.Order;
import entity.OrderDetail;
import entity.User;
import manager.OrderMgr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class QueryOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("loginUser");
        String userId = user.getUserId();
        resp.setContentType("application/json;charset=utf-8");
        String json = "{}";
        List<OrderDetail> orderList = null;
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json;charset=utf-8");
        if (!userId.equals("") && userId.trim().length() > 0) {
            orderList = OrderDao.queryDetailByUid(userId);
            json = JSONObject.toJSONString(orderList);
        }pw.println(json);
        pw.flush();
        pw.close();


    }
}
