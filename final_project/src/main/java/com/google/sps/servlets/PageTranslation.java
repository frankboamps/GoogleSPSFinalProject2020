

package com.google.sps.servlets;



import com.google.sps.servlets.FormHandlerServlet;

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
import com.google.sps.data.Cause;
import java.io.PrintWriter;
import java.util.Map;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;




@WebServlet("/translatePage")
public class PageTranslation extends HttpServlet {
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
    response.setContentType("application/json");
    Query query = new Query("newCauseDonate").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    System.out.println(results);
    List<Cause> causes = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String title = (String) entity.getProperty("title");
      String description = (String) entity.getProperty("description");
      String imageUrl = (String) entity.getProperty("image");

       Translate translate = TranslateOptions.getDefaultInstance().getService();
      Translation translationTitle =
        translate.translate(title, Translate.TranslateOption.targetLanguage(lang));
      String translatedtitle = translationTitle.getTranslatedText();

      Translation translationDescription =
        translate.translate(description, Translate.TranslateOption.targetLanguage(lang));
      String translatedDescription = translationDescription.getTranslatedText();

      Cause tempCause = new Cause(translatedtitle, translatedDescription, imageUrl);
      causes.add(tempCause);
    }
    Gson gson = new Gson();
    String json = new Gson().toJson(causes);
    response.getWriter().println(json);
  }
  

  
}
