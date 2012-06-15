package util;

public class StringUtils {
	public static String quoted(String s) {
		return '\'' + s + '\'';
	}

	public static String bestView(char c, boolean needEscape) {
		switch (c) {
		case 9:
			return "\\t";
		case 10:
			return "\\n";
		}
		if (c < 32 || c > 126) {
			return "\\u" + String.format("%04x", (int) c);
		}
		if (needEscape && (c == '\'' || c == '\\')) {
			return "\\" + c;
		}
		return "" + c;
	}

	public static String escape(String s) {
		return s.replaceAll("([\"\\\\])", "\\\\$1");
	}
}
