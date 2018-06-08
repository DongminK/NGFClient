package insoft;

import insoft.client.XPathXMLParser;
import insoft.openmanager.message.Message;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class XMLTest {

	public static void main(String[] args) {
		
		BufferedReader br =  null;
		String file = "C:\\IN-SOFT\\OpenManager3.0\\config\\WATCH\\OS.xml";
		
		StringBuffer sb = new StringBuffer();
		try {
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			String temp = "";
			
			while((temp = br.readLine()) != null) {
				
				if (temp.startsWith("<?xml "))
					continue;
				sb.append(temp);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Message msg = XPathXMLParser.parse("TEST", sb.toString(), "/collect/properties/*");
		
		System.out.println(msg);
	}
}
