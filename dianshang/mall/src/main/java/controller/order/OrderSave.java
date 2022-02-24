package controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import dao.OrderDao;
import entity.User;
import entity.gidNum;
import manager.OrderMgr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class OrderSave extends HttpServlet {


    /**
     * 上传订单数据的servlet
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html,charset=utf-8");
        String output = "";
        User user = (User) req.getSession().getAttribute("loginUser");
        String userId = user.getUserId();
        String num = "";
        String gid = "";
        int a = 0;

        String carGoods = req.getParameter("list");
        System.out.println(carGoods);
        ArrayList<gidNum> gims = JSON.parseObject(carGoods, new TypeReference<ArrayList<gidNum>>() {
        });
        for (int i = 0; i < gims.size(); i++) {
            num = gims.get(i).getNum();
            gid = gims.get(i).getGid();
            if (num.equals("")) {
                num = "1";
                a = OrderDao.saveOrder(userId, gid, num);
                if (a == 1) {
                    output = "success to saveOrder";
                } else {
                    output = "fail to success";
                }
            } else {
                a = OrderDao.saveOrder(userId, gid, num);
                if (a == 1) {
                    output = "success to saveOrder";
                } else {
                    output = "fail to success";
                }
            }


        }

        pw.println(output);
        pw.flush();
        pw.close();


    }

}
