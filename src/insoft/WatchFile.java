package insoft;

import java.io.BufferedReader;
import java.io.File;

public class WatchFile {

	public static void main(String[] args) {
		
		
		String fileName = "C:\\IN-SOFT\\OpenManager3.0\\config\\WATCH";
		
		
		
		StringBuffer sb = new StringBuffer("<watches>\n");
		try {
			
			File f = new File(fileName);
			
			for (File file : f.listFiles()) {
				
				
				if (file.isFile()) {
					String watchType = file.getName().replace(".xml", "");
					sb.append("\t<watch name=\"" + watchType + "\" caption=\"" + watchType + "\" icon=\"\">\n");
				}
				
			}
			
			
			
			
		} catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		sb.append("</watches>");
		
		System.out.println(sb.toString());
	}
}
