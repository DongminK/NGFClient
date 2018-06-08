package insoft.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

	public static String readCommand() {
		return readCommand("");
	}

	public static String readCommand(String title) {
		String command = "";

		InputStream is = System.in;

		try {

			if (title.compareTo("") != 0)
				System.out.print(title + " : ");

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			command = br.readLine().trim();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return command;
	}

	private static int nRequestId = 0;

	public static int getNextRequestId() {

		if (nRequestId > Integer.MAX_VALUE - 100)
			nRequestId = 0;

		return nRequestId++;
	}
}
