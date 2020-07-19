package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import com.google.sps.data.Cause;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/details-data")


public class DetailsDataServlet extends HttpServlet {
String postId;
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    response.setContentType("text/html;");
    PrintWriter out = response.getWriter();
     postId= request.getParameter("id");
 
    Cause selectedDetailsCause = null;

    Query query = new Query("CauseDon").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    
    for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        if((""+id).equals(postId)){
            System.out.println("post id =" + postId + " " + "id =" + "" +id);
            String title = (String) entity.getProperty("title");
            String description = (String) entity.getProperty("description");
            String imageUrl = (String) entity.getProperty("image");
            selectedDetailsCause = new Cause(id, title, description, imageUrl);
        }
    }
    out.println("<h2>" + selectedDetailsCause.getTitle() + "</h2>");
    out.println("<img id='imgDetails' src= " + selectedDetailsCause.getImageUrl() + ">");
    out.println("<p>" + selectedDetailsCause.getDescription() + "</p>");
    out.println("<br>");
    out.println("<h3> This Donation is going to " + selectedDetailsCause.getTitle() + ":</h3>");
  }
  
    // public String update(){
    //         return postId;
    //     }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String Usercomment = request.getParameter("message");
    String name = request.getParameter("name");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity messageEntity = new Entity(postId);
    messageEntity.setProperty("name", name);
    messageEntity.setProperty("message", Usercomment);
    datastore.put(messageEntity);

    response.sendRedirect("/details.html?id="+ postId);
  }

}