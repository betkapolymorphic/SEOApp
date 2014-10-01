package searcher;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Beta on 9/26/14.
 */
public class YandexSearcher implements WebSearcher {

    String region = "213";
    @Override
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public ArrayList<String> searh(String q, int num) {
        String u = "http://xmlsearch.yandex.ru/xmlsearch?lr=" +region+
                "&groupby=attr%3Dd.mode%3Ddeep.groups-on-page%3D10.docs-in-group%3D1"+
                "&user=TDsobaka&key=03.20185043:16e1a3b9c94a640db1a99e0315ff3268" +
                "&l10n=ru&sortby=rlv&filter=strict&query=";
        ArrayList<String> strings = new ArrayList<String>();
        try {
            URLConnection connection = new URL(u + URLEncoder.encode(q, "UTF-8")).openConnection();
            connection.setDoInput(true);
            connection.connect();

            Scanner sc = new Scanner(connection.getInputStream());
            String str = "";
            while (sc.hasNext()){
                str+=sc.nextLine();
            }
            JSONObject xmlJSONObj = XML.toJSONObject(str);

            JSONArray array = (JSONArray)
                    ((JSONObject)((JSONObject)
                            ((JSONObject)
                                    ((JSONObject) xmlJSONObj.get("yandexsearch"))
                                            .get("response"))
                                    .get("results")).get("grouping")).get("group");

            for(int i=0;i<array.length();i++) {
                Object o = ((JSONObject) ((JSONObject) array.get(i))
                        .get("doc")).get("url");
                //System.out.println(o);
                strings.add(o.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> searh = new YandexSearcher().searh("дубликаты номеров москва", 1);
        for(String s : searh){
            System.out.println(s);
        }
    }
}
