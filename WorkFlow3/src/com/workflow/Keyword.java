package com.workflow;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Keyword {
	private static String getIni(String Entry) {

		String ret = "";

		try {
			// 生のバイトのストリーム
			FileInputStream fis = new FileInputStream("絶対パスを入力/git/workflow/WorkFlow3/Keyword.ini");
			// SHIFT_JIS として読み込む為の準備
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			// 行単位で読み込む為の準備
			BufferedReader br = new BufferedReader(isr);

			String line_buffer = "";
			// BufferedReader は、readLine が null を返すと読み込み終了
			String str = "";
			while (null != (line_buffer = br.readLine())) {
				String[] result = line_buffer.split("=");
				str = (String) result[0];
				str = str.trim();
				if (str.equalsIgnoreCase(Entry)) {
					ret = (String) result[1];
					ret = ret.trim();
				}
			}

			// 閉じる
			br.close();
			isr.close();
			fis.close();
		} catch (Exception e) {
			ret = e.getMessage();
		}

		return ret;

	}

	static String webSiteURL() {
		return getIni("webSiteURL");
	}

	static String botToken() {
		return getIni("botToken");
	}

	static String url() {
		return getIni("url");
	}

	static String user() {
		return getIni("user");
	}

	static String password() {
		return getIni("password");
	}

	static public String type(String type) {
		return getIni("type" + type);
	}
}
