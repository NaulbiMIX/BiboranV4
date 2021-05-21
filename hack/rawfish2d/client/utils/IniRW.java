package hack.rawfish2d.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

public class IniRW {
	
	public static String readIni(String file, String prop) {
		String value = null;
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(file));
			value = p.getProperty(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void writeIni(String filename, String key, String value) {
		try{
			Properties p = new Properties();
			p.load(new FileInputStream(filename));
			p.put(key, value);
			FileOutputStream out = new FileOutputStream(filename);
			p.save(out, null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
