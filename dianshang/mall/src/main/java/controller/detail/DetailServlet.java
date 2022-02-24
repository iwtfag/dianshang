package controller.detail;

import com.alibaba.fastjson.JSONObject;

import entity.Goods;
import manager.GoodsMgrDetail;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String json = "{}";
        String id = req.getParameter("id");
        if (id != null && id.trim().length() > 0) {

            GoodsMgrDetail detail = new GoodsMgrDetail();
            Goods goods = detail.DetailById(id);
            json = JSONObject.toJSONString(goods);
        }
        resp.setContentType("application/json;charset = utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();
    }


}
