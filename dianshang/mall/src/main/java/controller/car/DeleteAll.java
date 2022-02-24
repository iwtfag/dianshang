package controller.car;

import dao.CarDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("loginUser");
        PrintWriter pw = resp.getWriter();
        resp.setContentType("html/text;charset=utf-8");
        String output = "";
        String userId = user.getUserId();
        int i = CarDao.DeleteAll(userId);
        if (i!=1){
            output = "fail deleteAll";
        }else {
            output = "success";
        }
        pw.println(output);
        pw.flush();
        pw.close();
    }
}
