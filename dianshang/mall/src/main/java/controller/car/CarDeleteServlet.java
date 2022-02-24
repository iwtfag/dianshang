package controller.car;

import dao.CarDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 这个方法第一种是按钮删除
 * 第二种是结算删除
 *
 */
public class CarDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String output = "";
        User user = (User)req.getSession().getAttribute("loginUser");
        String uid = user.getUserId();
        String gid = req.getParameter("gid");

        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        if (uid != null && uid.trim().length() > 0 && gid != null && gid.trim().length() > 0) {
            int i = CarDao.DeleteGoods(gid, uid);
            if (i == 1) {
                output = "successfully";
            }else {
                output = "fail";
            }
        }
        resp.setCharacterEncoding("utf-8");
        pw.println(output);
        pw.flush();
        pw.close();

    }
}
