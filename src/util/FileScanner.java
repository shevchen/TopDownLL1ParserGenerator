package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileScanner {
	private BufferedReader br;
	private int line, chr;
	private final int FIRST = 1;
	private StringBuilder sb;

	public FileScanner(String fileName) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(fileName));
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
		} while (Character.isWhitespace(c));
		return c;
	}

	public ParsedString nextToken() {
		sb.setLength(0);
		char c = read();
		while (Character.isWhitespace(c)) {
			c = read();
		}
		String pos = getPosition();
		while (c != (char) -1 && !Character.isWhitespace(c)) {
			sb.append(c);
			c = read();
		}
		return new ParsedString(sb.toString(), pos);
	}

	public void assertEquals(String s) throws ParseException {
		ParsedString next = nextToken();
		if (!s.equals(next.str)) {
			throw new ParseException(next.pos, StringUtils.quoted(s),
					StringUtils.quoted(next.str));
		}
	}

	public String getPosition() {
		return "line " + line + ", character " + chr;
	}
}
