package com.wp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUploadPercent extends HttpServlet {

  private static final long serialVersionUID = -6783491256532300522L;

  public GetUploadPercent() {
    super();
  }


  public void destroy() {
    super.destroy();
    // Just puts "destroy" string in log
    // Put your code here
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html");
    HttpSession session = request.getSession();
    Object is_begin = session.getAttribute("UPLOAD_PERCENTAGE");
    if (is_begin == null)
      return;
    if ("0".equals(is_begin.toString()))
      return;

    PrintWriter out = response.getWriter();
    Object upload_percentage = session.getAttribute("UPLOAD_PERCENTAGE");
    out.write("{percentage:'" + upload_percentage.toString() + "'}");
    out.flush();
  }

  public void init() throws ServletException {
    // Put your code here
  }
}
