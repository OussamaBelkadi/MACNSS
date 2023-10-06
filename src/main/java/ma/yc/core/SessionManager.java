package ma.yc.core;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    Map<String, String> sessionObject = new HashMap<>();

    public void setSession(String key, String value){
        sessionObject.put(key,value);
    }

    public String getSession(String key){
        return sessionObject.get(key);
    }
}
