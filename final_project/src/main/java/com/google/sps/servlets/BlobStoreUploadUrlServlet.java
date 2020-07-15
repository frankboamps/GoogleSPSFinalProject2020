package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * When the fetch() function requests the /blobstore-upload-url URL, the content of the response is
 * the URL that allows a user to upload a file to Blobstore. If this sounds confusing, try running a
 * dev server and navigating to /blobstore-upload-url to see the Blobstore URL.
 */
@WebServlet("/blobstore-upload-url")
public class BlobStoreUploadUrlServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    PrintWriter out = response.getWriter();

    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler");
    
      out.println("<h2>Add a new Donation</h2>");
      out.println("<h3>Fill this to create your own donation:</h3>");
      out.println("<form method=\"POST\" enctype=\"multipart/form-data\" action=\"" + uploadUrl + "\" id=\"usrform\">");
      out.println("Title of Cause: <input type=\"text\" name=\"title\" form=\"usrform\" required>");
      out.println("Description: <textarea rows=\"0\" cols=\"100\" name=\"description\" minlength=\"50\" maxlength=\"250\" form=\"usrform\" > </textarea>");
      out.println("<br/>");
      out.println("Upload an image for cause: <input type=\"file\" name=\"image\" form=\"usrform\" required>");
      out.println("<br/> <input type=\"submit\">");
      out.println("</form>");
    }
}