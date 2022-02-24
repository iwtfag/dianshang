package controller.car;

import dao.CarDao;
import entity.Car;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class updateNumServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gid = req.getParameter("gid");
        User user = (User) req.getSession().getAttribute("loginUser");
        String uid = user.getUserId();
        String num = req.getParameter("num");
        PrintWriter pw = resp.getWriter();
        String output = "";
        Car car = null;
        if (!gid.equals("") && gid.trim().length() > 0 && !num.equals("") && num.trim().length() > 0) {
            car = new Car();
            car.setNum(num);
            car.setGoods_id(gid);
            car.setUserid(uid);
            int i = CarDao.updateNum(car);
            if (i != 1) {
                output = "fail update";
            } else {
                output = "success!!!";
            }


        }
        pw.println(output);
        pw.flush();
        pw.close();
    }
}
