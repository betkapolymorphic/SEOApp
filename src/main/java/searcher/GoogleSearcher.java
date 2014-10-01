package searcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Beta on 9/25/14.
 */
public class GoogleSearcher implements WebSearcher{
    public static final String  apiKey = "AIzaSyAPs79Ogu1fdsskgqSscX8VgnK2PlrHfCE";
    public static final String customSearchEngineKey = "008572143847545290175:i-1qjly7tuy";
    final static  String searchURL = "https://www.googleapis.com/customsearch/v1?";
    private static String makeSearchString(String qSearch,int start,int numOfResults) throws UnsupportedEncodingException {
        String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey+"&q=" +
                URLEncoder.encode(qSearch, "UTF-8")+"&count="+numOfResults;
        return toSearch;
    }
    private static String read(String pUrl)
    {
        //pUrl is the URL we created in previous step
        try
        {
            URL url=new URL(pUrl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer buffer=new StringBuffer();
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
            return buffer.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setRegion(String region) {

    }

    @Override
    public ArrayList<String> searh(String q, int num) {
        ArrayList<String> strings = new ArrayList<String>();
        /*Scanner sc = null;
        try {
            sc = new Scanner(new FileInputStream("1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = "";
        while (sc.hasNext()){
            s+=sc.nextLine();
        }*/

        for(int count=0;count<num;count++) {
            JSONObject object = null;
            try {
                object = new JSONObject(read(makeSearchString(q,count,10)));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JSONArray array = (JSONArray) object.get("items");
            //System.out.println(array);
            for(int i = 0;i<array.length();i++){
                count++;
                JSONObject object1 = (JSONObject) array.get(i);
                strings.add(object1.get("link").toString());
            }
        }
        return strings;
    }
}
