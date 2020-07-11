// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import static java.util.Objects.isNull;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.comments.Comment;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("comment", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery commentResults = datastore.prepare(query);

    List<Comment> data = new ArrayList<>();

    /*for (Entity entity : commentResults.asList(FetchOptions.Builder.withDefaults())) {
        long id = entity.getKey().getId();
        String comment = (String) entity.getProperty("comment");
        String email = (String) entity.getProperty("email");

        Comment commentObj = new Comment(email, comment);
        data.add(commentObj);

    }*/

    Comment example = new Comment("hi", "hello");

    data.add(example);

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(data));

    /*String i = toJsonString(data);
    response.setContentType("application/json; charset=utf=8");
    response.getWriter().println(i);*/
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String email = (String) request.getParameter("email-input");
    String comment = (String) request.getParameter("text-input");

    Entity entity = new Entity("Comment");
    entity.setProperty("comment", comment);
    entity.setProperty("email", email);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(entity);

    response.sendRedirect("/index.html");
  }

  private static String toJsonString(List<Comment> data) {
    return new Gson().toJson(data);
  }
}
