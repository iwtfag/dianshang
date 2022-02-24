package controller.index;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.GoodsDao;
import entity.Goods;
import manager.GoodsMgr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class QueryServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String goods_name = req.getParameter("name");
        List<Goods> goodsList = new ArrayList<>();
        String json = "{}";
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (!goods_name.equals("") && goods_name.trim().length() > 0) {
            goodsList = GoodsMgr.getGoodsList(goods_name);
            json = JSONObject.toJSONString(goodsList);

        }
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }
}

