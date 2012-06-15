package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class FileScanner {
	private BufferedReader br;
	private Set<Character> ignore;
	private int line, chr;
	private final int FIRST = 1;
	private StringBuilder sb;

	public FileScanner(String fileName, Set<Character> ignore)
			throws FileNotFoundException {
		br = new BufferedReader(new FileReader(fileName));
		this.ignore = ignore;
		this.ignore.remove((char) -1);
		line = FIRST;
		chr = FIRST;
		sb = new StringBuilder();
	}

	final void shift(int c) {
		if (c == '\n') {
			++line;
			chr = FIRST;
		} else if (c != -1) {
			++chr;
		}
	}

	final char read() {
		int c;
		try {
			c = br.read();
		} catch (IOException e) {
			e.printStackTrace();
			return (char) -1;
		}
		shift(c);
		return (char) c;
	}

	public char nextChar() {
		char c;
		do {
			c = read();
		} while (ignore.contains(c));
		return c;
	}

	public ParsedString nextToken() {
		sb.setLength(0);
		char c = read();
		while (ignore.contains(c)) {
			c = read();
		}
		String pos = getPosition();
		while (c != (char) -1 && !ignore.contains(c)) {
			sb.append(c);
			c = read();
		}
		return new ParsedString(sb.toString(), pos);
	}

	public static String quoted(String s) {
		return '\'' + s + '\'';
	}

	public void assertEquals(String s) throws ParseException {
		ParsedString next = nextToken();
		if (!s.equals(next.str)) {
			throw new ParseException(next.pos, quoted(s), quoted(next.str));
		}
	}

	public String getPosition() {
		return "line " + line + ", character " + chr;
	}
}
