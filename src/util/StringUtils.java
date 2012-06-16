package util;

public class StringUtils {
	public static String quoted(String s) {
		return '\'' + s + '\'';
	}

	public static String bestView(char c, boolean needEscape) {
		switch (c) {
		case '\t':
			return "\\t";
		case '\n':
			return "\\n";
		case '\r':
			return "\\r";
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
