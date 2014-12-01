package com.wp.servlet;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

  private static final long serialVersionUID = -7091418255807535366L;


  public UploadServlet() {
    super();
  }

  public void destroy() {
    super.destroy();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      // Create a factory for disk-based file items
      DiskFileItemFactory factory = new DiskFileItemFactory();

      // Set factory constraints
      factory.setSizeThreshold(3000 * 1024 * 1024);
      File temp = new File("/home/lscm/tmp/uploadfile");
      factory.setRepository(temp);

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);

      // Add listener, implement the update method
      upload.setProgressListener(new UploadProgressListener(request));

      // Set overall request size constraint
      upload.setSizeMax(3000 * 1024 * 1024);
      // Parse the request

      List items = upload.parseRequest(request);

      // Process the uploaded items
      Iterator iter = items.iterator();
      while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        String fileName = item.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (item.isFormField()) {
          String fieldName = item.getFieldName();
          fileName = item.getName();
          String contentType = item.getContentType();
          boolean isInMemory = item.isInMemory();
          long sizeInBytes = item.getSize();
        } else {
          // Process a file upload
          SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
          String newFileName =
              df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

          File uploadedFile = new File(temp, newFileName);
          if(!uploadedFile.exists()){
            uploadedFile.createNewFile();
          }
          item.write(uploadedFile);
        }
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  class UploadProgressListener implements ProgressListener {
    private HttpServletRequest request;
    private long megaBytes = -1;
    private DecimalFormat df = new DecimalFormat("#00.0");

    UploadProgressListener(HttpServletRequest request) {
      this.request = request;
    }

    public void update(long bytesRead, long bytesTotal, int items) {
      // if no process, then return
      long mBytes = bytesRead / 1024;
      if (megaBytes == mBytes) {
        return;
      }
      megaBytes = mBytes;

      double percent = (double) bytesRead * 100 / (double) bytesTotal;
      // also can use log to show the progress.
      System.out.println(df.format(percent));
      request.getSession().setAttribute("UPLOAD_PERCENTAGE", df.format(percent));
    }
  };

  public void init() throws ServletException {
    // Put your code here
  }
}
