package controller.car;

import dao.CarDao;
import entity.User;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CarInsertServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //id 是goods的id
        String gid = req.getParameter("gid");
        String num = req.getParameter("num");
        User user = (User) req.getSession().getAttribute("loginUser");
        String uid = user.getUserId();
        System.out.println(gid);
        String output = "";
        if (gid != "" && gid.trim().length() > 0 && uid != "" && uid.trim().length() > 0) {
            int i = CarDao.saveCar(gid,uid);
            if (i != 1) {
                output = "fail to add car";
            } else {
                output = "successfully!!!";
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(output);
        pw.flush();
        pw.close();

    }
}
