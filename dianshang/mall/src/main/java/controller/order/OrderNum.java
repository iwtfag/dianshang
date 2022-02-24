package controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import entity.Oid;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class OrderNum extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oidList = req.getParameter("oidList");

        System.out.println(oidList);

        ArrayList<Oid> oidList1 = JSON.parseObject(oidList, new TypeReference<ArrayList<Oid>>() {
        });
        for (int i = 0;i< oidList1.size();i++){
            Oid oid = oidList1.get(i);

        }


        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();

        pw.flush();
        pw.close();


    }
}
