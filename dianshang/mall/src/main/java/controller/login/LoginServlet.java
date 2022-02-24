package controller.login;

import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        PrintWriter pw = resp.getWriter();
        if (name == null || "".equals(name) || password == null || "".equals(password)) {
            pw.println("用户名或密码为空");
        } else {
            UserDao dao = new UserDao();
            User user = dao.findUserByNamePwd(name, password);
            if (user == null) {
                pw.println("用户名或密码错误");
            } else {
                req.getSession().setAttribute("loginUser", user);
                User u = (User) req.getSession().getAttribute("loginUser");
                System.out.println("test====" + u);
                pw.println("登录成功,Here is " + u.getName());
            }
        }
        pw.flush();
        pw.close();
    }
}
