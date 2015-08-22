package com.ansteel.core.tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ansteel.core.utils.JsonUtils;

public class Resource {
	public static final String CONTEXT_ATTR_NAME = "com.baidu.fis.resource";
    public static final String STYLE_PLACEHOLDER = "<!--FIS_STYLE_PLACEHOLDER-->";
    public static final String SCRIPT_PLACEHOLDER = "<!--FIS_SCRIPT_PLACEHOLDER-->";
    
    public static enum ScriptPoolRenderOrder {
        important,
        normal, 
        optional;
    }
    
    private String mapDir;
    @SuppressWarnings("rawtypes")
    private Map<String, Map> map;
    private Map<String, String> loaded;
    private Map<String, ArrayList<String>> collection;
    private ScriptPoolRenderOrder[] scriptPoolRenderOrder = {ScriptPoolRenderOrder.important,
            ScriptPoolRenderOrder.normal, ScriptPoolRenderOrder.optional};
    
    private Map<String, StringBuilder> scriptPool;
    
    public Boolean debug = false;

    public void setMapDir(String dir) {
        this.mapDir = dir;
    }
    
    @SuppressWarnings("rawtypes")
    public Resource(){
        this.map = new HashMap<String, Map>();
        this.loaded = new HashMap<String, String>();
        this.collection = new HashMap<String, ArrayList<String>>();
        this.scriptPool = new HashMap<String, StringBuilder>();
    }
    
    public Resource(String dir){
        this();
        this.setMapDir(dir);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map<String, Map> getMap(String id) throws FileNotFoundException{
        String namespace = "__global__";
        int pos = id.indexOf(':');
        if (pos != -1) {
            namespace = id.substring(0, pos);
        }
        if(!map.containsKey(namespace)){
            String filename = namespace.equals("__global__") ? "map.json" : namespace + "-map.json";
            File file = new File(mapDir + "/" + filename);
            map.put(namespace, (Map)JsonUtils.objectFromJson(file, Map.class));
        }
        return map.get(namespace);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes"})
    public String require(String id) throws FileNotFoundException{
        String uri = loaded.get(id);
        if(uri != null){
            return uri;
        } else {
            Map<String, Map> map = this.getMap(id);
            Map<String, Map> res = map.get("res");
            Map<String, Object> info = res.get(id);
            if(info == null){
                throw new IllegalArgumentException("missing resource [" + id + "]");
            } else {
                String pkg = (String) info.get("pkg");
                if(!debug && pkg != null){
                    res = map.get("pkg");
                    info = res.get(pkg);
                    uri = (String) info.get("uri");
                    if(info.containsKey("has")){
                       // Object[] has = ((JSONArray)info.get("has")).toArray();
                    	List has = (List)info.get("has");
                        for(Object obj:has){
                            loaded.put((String) obj, uri);
                        }
                    }
                } else {
                    uri = (String) info.get("uri");
                    loaded.put(id, uri);
                }
                if(info.containsKey("deps")){
                	List deps = (List) info.get("deps");
                    for(Object dep:deps){
                        this.require((String) dep);
                    }
                }
                String type = (String) info.get("type");
                ArrayList<String> list = collection.get(type);
                if(list == null){
                    list = new ArrayList<String>();
                    collection.put(type, list);
                }
                list.add(uri);
            }
        }
        return uri;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes"})
    public Map<String, Object> getInfo(String id) throws FileNotFoundException{
    	Map<String, Map> map = this.getMap(id);
        Map<String, Map> res = map.get("res");
        Map<String, Object> info = res.get(id);
        if(info == null){
            throw new IllegalArgumentException("missing resource [" + id + "]");
        } else {
        	return info;
        }
    }

    public String getMapDir() {
        return mapDir;
    }

    public void reset() {
        map.clear();
        loaded.clear();
        collection.clear();
        scriptPool.clear();
    }

    public void addScriptPool(String code, String type) {
        StringBuilder pool = scriptPool.get(type);
        if (pool == null) {
            pool = new StringBuilder();
            scriptPool.put(type, pool);
        }
        pool.append("<script type=\"text/javascript\">!function(){");
        pool.append(code);
        pool.append("}()</script>\n");
    }

    public void addScriptPool(String code) {
        addScriptPool(code, "normal");
    }

    public void setScriptPoolRenderOrder(ScriptPoolRenderOrder[] order) {
        scriptPoolRenderOrder = order;
    }

    public String renderScriptPool() {
        StringBuilder code = new StringBuilder();
        for (int i = 0, len = scriptPoolRenderOrder.length; i < len; i++) {
            String type = scriptPoolRenderOrder[i].name();
            StringBuilder pool = scriptPool.get(type);
            if (pool != null) {
                code.append(pool);
            }
        }
        scriptPool = new HashMap<String, StringBuilder>();
        return code.toString();
    }
    
    public String render(String type){
        return render(type, true);
    }
    
    public String replace(String html){
        html = html.replace(Resource.STYLE_PLACEHOLDER, this.render("css"));
        html = html.replace(Resource.SCRIPT_PLACEHOLDER, this.render("js") + this.renderScriptPool());
        return html;
    }
    
    public String render(String type, Boolean reset){
        ArrayList<String> list = this.getCollection(type);
        StringBuffer buffer = new StringBuffer();
        if(list != null){
            if(type.equals("js")){
                for(String uri:list){
                    buffer.append("<script type=\"text/javascript\" src=\"").append(uri).append("\"></script>\n");
                }
            } else if(type.equals("css")){
                for(String uri:list){
                    buffer.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(uri).append("\"/>\n");
                }
            }
            if(reset){
                list.clear();
            }
        }
        return buffer.toString();
    }
    
    public ArrayList<String> getCollection(String type){
        return collection.get(type);
    }
}