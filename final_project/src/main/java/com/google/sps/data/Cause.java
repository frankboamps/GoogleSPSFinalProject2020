package com.google.sps.data;

import java.util.List;

/** Class containing server statistics. */
public final class Cause {
  private final String title;
  private final String description;
  private final String imageUrl;
 
  public Cause(String title, String description, String imageUrl) {
      this.title = title;
      this.description = description;
      this.imageUrl = imageUrl;
  }

  public String getTitle() {
      return title;
  }

  public String getDescription() {
      return description;
  }
  
  public String getImageUrl() {
      return imageUrl;
  }

}