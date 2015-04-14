package com.oakslist.tools.load;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Siarhei_Varachai on 10/2/2014.
 */
public class LoadQueriesMap {

    private Map<String, String> queriesHashMap = new HashMap<String, String>();

    public LoadQueriesMap() {
        initQueriesHashMap();
    }

    private void initQueriesHashMap() {
        this.queriesHashMap.put("getip", "Hi! Send me your password, please...");
    }

    public Map<String, String> getHashMap() {
        return this.queriesHashMap;
    }
}
