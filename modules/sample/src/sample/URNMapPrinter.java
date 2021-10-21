package sample;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.json.JSONObject;

import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;
import rescuecore2.messages.control.ControlMessageComponentFactory;
import rescuecore2.messages.control.ControlMessageFactory;
import rescuecore2.misc.CommandLineOptions;
import rescuecore2.registry.Factory;
import rescuecore2.standard.entities.StandardEntityFactory;
import rescuecore2.standard.entities.StandardPropertyFactory;
import rescuecore2.standard.messages.StandardMessageComponentFactory;
import rescuecore2.standard.messages.StandardMessageFactory;

public class URNMapPrinter {

	public static void main(String[] args) throws IOException, ConfigException {
		System.out.println("URNMapPrinter --python_out=file --js_out=file --json_out=file");
		Config config=new Config();
		CommandLineOptions.processArgs(args,config);
		
		JSONObject json = toJSON();
		write2file(config.getValue("json_out", "rcrs_urn.json"), json.toString());
		write2file(config.getValue("python_out", "rcrs_urn.py"), toPython(json));
		write2file(config.getValue("js_out", "rcrs_urn.js"), toJS(json));
	}

	private static void write2file(String path,String content) throws FileNotFoundException {
		try (PrintStream out = new PrintStream(new FileOutputStream(path))) {
		    out.print(content);
		    out.close();
		}
	}
	
	public static String toPython(JSONObject json) {
		String out="#AutoGenerated By rcrs-server\n#Please do not modify it\n\n";
		out+="from enum import IntEnum\n";
		String map="{int(u):u for u in ";
		for (String key : json.keySet()) {
			out+="\n\nclass "+key+"(IntEnum):\n";
			JSONObject sub = ((JSONObject)json.get(key));
			for (String prettyName : sub.keySet()) {
				Integer urn=(int) sub.get(prettyName);
				out+="\t"+prettyName+" = 0x"+Integer.toHexString(urn)+"\n";
			}
			map+= "list("+key+") +";
		}
		map=map.substring(0,map.length()-1)+"}";
		out+="\n\nMAP="+map;
		return out;
	}
	public static String toJS(JSONObject json) {
		String out="//AutoGenerated By rcrs-server\n//Please do not modify it\n\n";
		
		String map="Object.assign({},";
		
		for (String key : json.keySet()) {
			out+="\n\nconst "+key+"={\n";
			JSONObject sub = ((JSONObject)json.get(key));
			for (String prettyName : sub.keySet()) {
				int urn=(int) sub.get(prettyName);
				out+="\t"+prettyName+":0x"+Integer.toHexString(urn)+",\n";
			}
			out+="}";
			map+="...Object.keys("+key+").map(p=>({["+key+"[p]]:p})),";
		}
		map=map.substring(0,map.length()-1)+")";
		out+="\n\nMAP="+map;
		return out;
	}
	public static JSONObject toJSON() {
		JSONObject json=new JSONObject();
		json.put("ControlMSG", buildJson(ControlMessageFactory.INSTANCE));
		json.put("ComponentControlMSG", buildJson(ControlMessageComponentFactory.INSTANCE));
		json.put("Command", buildJson(StandardMessageFactory.INSTANCE));
		json.put("ComponentCommand", buildJson(StandardMessageComponentFactory.INSTANCE));
		json.put("Entity", buildJson(StandardEntityFactory.INSTANCE));
		json.put("Property", buildJson(StandardPropertyFactory.INSTANCE));
		return json;
	}
	private static JSONObject buildJson(Factory fac) {
		JSONObject obj=new JSONObject();
		for (Integer urn : fac.getKnownURNs()) {
			String prettyName = fac.getPrettyName(urn);
			obj.put(prettyName,urn);
		}
		return obj;
	}
}