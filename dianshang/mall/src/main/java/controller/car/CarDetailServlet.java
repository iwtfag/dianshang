package controller.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CarDao;
import entity.Car;
import entity.Goods;
import entity.User;
import manager.CarMgr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CarDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("loginUser");
        String uid = user.getUserId();
        String json = "{}";
        List<Goods> carList = CarMgr.getCarList(uid);
        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(carList);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();


    }
}
