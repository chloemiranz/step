
package com.google.sps.comments;

public class Comment {
  private String email;
  private String comment;

  public Comment(String emailInput, String commentInput) {
    this.email = emailInput;
    this.comment = commentInput;
  }

  public String getEmail() {
    return email;
  }

  public String getComment() {
    return comment;
  }
}
