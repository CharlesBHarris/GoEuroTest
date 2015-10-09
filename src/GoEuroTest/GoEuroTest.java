/*
 * GoEuroTest
 * 
 * Date: October 9, 2015
 * 
 * Author: Charles B. Harris
 * 
 * This Java file implements a class to solve the GoEuro Java Developer Test
 * found here: https://github.com/goeuro/dev-test. The algorithm queries the GoEuro 
 * API at http://api.goeuro.com/api/v2/position/suggest/en/CITY_NAME, where CITY_NAME 
 * is an appropriate command line argument. The returned JSON object is then parsed 
 * and a CSV file is output containing the columns _id, name, type, latitude,
 * and longitude.
 */

package GoEuroTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class GoEuroTest {
    private static String readUrl(String urlString) throws IOException {
         BufferedReader reader = null;
         String output = "";
         try {
             URL url = new URL(urlString);
             reader = new BufferedReader(new InputStreamReader(url.openStream()));
             output = reader.readLine();
             return output;
         }catch (IOException e) {
             e.printStackTrace();
             return output;
         }finally {
             if (reader != null){
                 reader.close();
             }
         }
     }
     
     private static void parseJSON(String JSONtext) throws IOException {
         JSONArray jsonObject = new JSONArray(JSONtext);
         int n = jsonObject.length();
         if (n == 0){
             System.out.println("Empty JSON object returned. "
                                 + "Please check that the entered city is valid.");
             System.exit(0);
         }
         FileWriter writer = null;
         try {
             writer = new FileWriter("output.csv");
             writer.append("_id, name, type, latitude, longitude\n");
             for (int i = 0; i < n; i++){
                 JSONObject city = jsonObject.getJSONObject(i);
                 String id = Integer.toString(city.getInt("_id"));
                 String name = city.getString("name");
                 String type = city.getString("type");
                 JSONObject geo = city.getJSONObject("geo_position");
                 String latitude = Double.toString(geo.getDouble("latitude"));
                 String longitude = Double.toString(geo.getDouble("longitude"));
                 writer.append(id + ", " + name + ", " + type + ", " + 
                               latitude + ", " + longitude + "\n");
             }
             writer.flush();
             writer.close();
         }catch (IOException e) {
             e.printStackTrace();
         }finally {
             if (writer != null){
                 try{
                     writer.close();
                 }catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             
         }
         
     }
     
     public static void main(String[] args) throws Exception {
         String urlString = "http://api.goeuro.com/api/v2/position/suggest/en/" + args[0];
         String returnedJSON = readUrl(urlString);
         parseJSON(returnedJSON);
     }
}
