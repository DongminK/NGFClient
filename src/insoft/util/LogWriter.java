package insoft.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogWriter {

	private static String filePath = "../logs/client_lite_";
	private static FileWriter fw = null;

	static {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String format = sdf.format(cal.getTime());

		filePath += format + ".log";

		try {
			fw = new FileWriter(filePath, true);
		} catch (Exception e) {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e1) {
				}
			}

			System.exit(1);
		}
	}

	public static void write(String log) {
		try {
			System.out.println(log);
			fw.append(log).append("\r\n");
			fw.flush();
		} catch (IOException e) {
		}
	}

	public static void close() {
		try {
			fw.close();
		} catch (IOException e) {
		}
	}

}
