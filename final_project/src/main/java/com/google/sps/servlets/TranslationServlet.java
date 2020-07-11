

package com.google.sps.servlets;



import com.google.sps.servlets.DataServlet;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.sps.servlets.Comment;

@WebServlet("/translate")
public class TranslationServlet extends HttpServlet {
  String lang;  
@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

     String languageCode = request.getParameter("languageCode");

    lang = languageCode;
    System.out.println(lang);
    
    response.sendRedirect("/index.html#donate");
  }
   @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("donateComments");
    PreparedQuery results = datastore.prepare(query);
    List < Comment > comments = new ArrayList < > ();
  
    for (Entity entity: results.asIterable()) {
      String name = (String) entity.getProperty("name");
      String Usercomment = (String) entity.getProperty("message");


      Translate translate = TranslateOptions.getDefaultInstance().getService();
      Translation translationUsercomment =
        translate.translate(Usercomment, Translate.TranslateOption.targetLanguage(lang));
      String translatedUsercomment = translationUsercomment.getTranslatedText();


      Comment comment = new Comment(name, translatedUsercomment);
      comments.add(comment);
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().println(gson.toJson(comments));
  }
  
}
