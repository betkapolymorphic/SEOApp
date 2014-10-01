package searcher;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Beta on 9/25/14.
 */
public interface WebSearcher {
    void setRegion(String region);
    ArrayList<String> searh(String q,int num);
}
