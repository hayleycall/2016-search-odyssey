package com.searchodyssey.app;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Iterator;

import com.searchodyssey.app.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.RequestDispatcher;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;

import java.net.URLEncoder;

import redis.clients.jedis.Jedis;
 
@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
 
    protected void doPost(HttpServletRequest request,
			  HttpServletResponse response) throws ServletException, IOException{

        String search = request.getParameter("search");
	//response.getWriter().println(search);
     	// make a JedisIndex
       	Jedis jedis = JedisMaker.make();
	JedisIndex index = new JedisIndex(jedis); 
		
        // search for the first term
	//String term1 = "java";
	// System.out.println("Query: " + term1);
	// WikiSearch search1 = search(term1, index);
	WikiSearch search1 = WikiSearch.search(search, index);
	
	// search for the second term
	/*String term2 = "programming";
	System.out.println("Query: " + term2);
      	WikiSearch search2 = search(term2, index);
	search2.print();
		
	// compute the intersection of the searches
	System.out.println("Query: " + term1 + " AND " + term2);
	WikiSearch intersection = search1.and(search2);
	intersection.print(); */   
	List<Entry<String, Integer>> entries = search1.sort();
	/*ArrayList<String> snippets=new ArrayList<String>();
	
	//get first sentence of each page
	for (Entry<String, Integer> entry: entries) {
	    String url=entry.getKey();
	    //String firstSentence=getFirstSentence(url, response);
	    //snippets.add(firstSentence);
	     ArrayList<String> keys=getFirstSentence(url, response);
	     /* for(String s : keys)
		response.getWriter().println(s);
		}*/

	String json = listmap_to_json_string(entries);
	//response.getWriter().println(json);
	request.setAttribute("json",json);
	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/SearchResults");
	  requestDispatcher.forward(request, response);
         
    }

    /*  public ArrayList<String> getFirstSentence(String sURL,  HttpServletResponse response){
	 // Connect to the URL using java's native library
	try{
	    String encodedString = URLEncoder.encode("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exsentences=1&explaintext=&titles="+sURL,"UTF-8");
	    URL url = new URL(encodedString);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();

	// Convert to a JSON object to print data
	    //JsonParser jp = new JsonParser(); //from gson
	    //JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    String result = getStringFromInputStream((InputStream) request.getContent());
	     response.getWriter().println(result);
          JSONObject rootObj =new JSONObject(result);
           // JsonObject rootObj = root.getAsJsonObject(); //May be an array, may be an object.
	    JSONObject jsonChildObject = (JSONObject)rootObj.get("query");
	     Iterator iterator  = jsonChildObject.keys();
            String key = null;
	    ArrayList<String> keys=new ArrayList<String>();
	    while(iterator.hasNext()){
                key = (String)iterator.next();
		keys.add(key);
	        response.getWriter().println(key);
                //response.getWriter().println(((JSONObject)jsonChildObject.get(key)).get("extract"));
            }
	    //String firstSentence= rootobj.get("query.pages").getAsString();
	    String firstSentence=null;
	    // return firstSentence;
	    return keys;
	}
	catch(java.net.MalformedURLException e){}
	catch(java.io.IOException e){}
	return null;
	}*/
    
    public String listmap_to_json_string(List<Entry<String, Integer>> list)
    {       
	JSONArray json_arr=new JSONArray();
	int i=1;
	if(list==null || list.size()==0){
	    JSONObject json_obj=new JSONObject();
	    try {
		    json_obj.put("count", "");
		    json_obj.put("text","No results! :(");
		} catch (JSONException e) {
		    e.printStackTrace();
		}
	     json_arr.put(json_obj);
	    return json_arr.toString();
	}
	for (Entry<String, Integer> entry : list) {
	    JSONObject json_obj=new JSONObject();
		String key = entry.getKey();
	        Integer value = entry.getValue();
		try {
		    json_obj.put("count",value);
		    json_obj.put("text",key);
		} catch (JSONException e) {
		    e.printStackTrace();
		}                           
	    json_arr.put(json_obj);
	    if(++i>10)
		break;
	}
	return json_arr.toString();
    }


	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
 
}
