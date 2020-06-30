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

import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*; 

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private final ArrayList<String> listData = new ArrayList<String>();
    private final ArrayList<String> comments = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
   
    listData.add("Hello!"); 
    listData.add("How are you?");
    listData.add("Great weather today!");

    //converting data to JSON
    String json = convertToJson(listData);
    
    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);

  }

  private String convertToJson(ArrayList<String> data) {
    String json = "";
    json += data.get(0);
    json += ", ";
    json += data.get(1);
    json += ", ";
    json += data.get(2);
    json += "";
    return json;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "text-input", "");
    comments.add(text); 

    Entity taskEntity = new Entity("Task");
    taskEntity.setProperty("comment", text);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);

    // Break the text into individual words.
    //String[] words = text.split("\\s*,\\s*");

    // Respond with the result.
    response.setContentType("text/html;");
    //response.getWriter().println(Arrays.toString(words));
    response.getWriter().println(text);

    //response.sendRedirect("/index.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

}
