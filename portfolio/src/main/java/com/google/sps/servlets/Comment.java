package com.google.sps.comments;

public class Comment {
  private String email;
  private String comment;
  private String blobKey;
  private long timestamp;

  public Comment(String emailInput, String commentInput, String blobKey, long timestamp) {
    this.email = emailInput;
    this.comment = commentInput;
    this.blobKey = blobKey;
    this.timestamp = timestamp;
  }

  public String getEmail() {
    return email;
  }

  public String getComment() {
    return comment;
  }

  public String getBlobKey() {
    return blobKey;
  }

  public long getTimestamp() {
    return timestamp;
  }
}