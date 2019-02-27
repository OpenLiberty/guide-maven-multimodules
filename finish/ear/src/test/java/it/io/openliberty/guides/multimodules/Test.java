// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package it.io.openliberty.guides.multimodules;

import static org.junit.Assert.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
  String port = System.getProperty("liberty.test.port");
  String war = "converter";
  String urlBase = "http://localhost:" + port + "/" + war + "/";

  // tag::testIndexPage[]
  @org.junit.Test
  public void testIndexPage() throws Exception {
    String url = this.urlBase;
    HttpURLConnection con = testRequestHelper(url, "GET");
    assertEquals("Incorrect response code from " + url, 200, con.getResponseCode());
    assertTrue("Incorrect response from " + url,
        testBufferHelper(con).contains("Enter the height in centimeters"));
  }
  // end::testIndexPage[]

  // tag::testHeightsPage[]
  @org.junit.Test
  public void testHeightsPage() throws Exception {
    String url = this.urlBase + "heights.jsp?heightCm=10";
    HttpURLConnection con = testRequestHelper(url, "POST");
    assertTrue("Incorrect response from " + url,
        testBufferHelper(con).contains("3    inches"));
  }
  // end::testHeightsPage[]

  private HttpURLConnection testRequestHelper(String url, String method) throws Exception {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    // optional default is GET
    con.setRequestMethod(method);
    return con;
  }

  private String testBufferHelper(HttpURLConnection con) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    return response.toString();
  }

}
