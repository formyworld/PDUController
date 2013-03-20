package oslab.iot.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

	private Properties prop = null;

	/**
	 * constructor using filename of properties as input argument
	 * @param filename
	 */
	public ReadProperties(String filename) {

		try {
			InputStream in = new FileInputStream(filename);

			prop = new Properties();

			prop.load(in);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * get the value of key from Properties 
	 * @param key
	 * @return
	 */
	public String getProp(String key) {
		return prop.getProperty(key);
	}

	public static void main(String args[]){
		ReadProperties p = new ReadProperties("pdu.properties");
		int port =Integer.parseInt( p.getProp("port"));
		System.out.println(port);
		
	}
}
