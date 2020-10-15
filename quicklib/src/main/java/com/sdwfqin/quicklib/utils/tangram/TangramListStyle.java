package com.sdwfqin.quicklib.utils.tangram;

import java.util.HashMap;

public class TangramListStyle {
    public static HashMap<String,Object> getStickystyle(){
        HashMap<String,Object> hashMap = new HashMap();
        hashMap.put("offset",2);
        hashMap.put("sticky","start");
        hashMap.put("enableScroll",true);
        hashMap.put("bgColor","#ff0000");
        return hashMap;
    }
}
