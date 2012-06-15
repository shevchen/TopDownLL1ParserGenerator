package gen;

import java.io.FileNotFoundException;
import java.util.Set;

import util.FileScanner;
import util.Node;
import util.ParseException;
import util.StringUtils;

public class GrammarFileParser {
	private FileScanner fs;
	private char curChar;

	public GrammarFileParser(String fileName, Set<Character> ignore)
			throws FileNotFoundException {
		this.fs = new FileScanner(fileName, ignore);
		curChar = fs.read();
	}

	private Node f_Alpha(C_Alpha arg_0) throws ParseException {
		Node cur = new Node("Alpha");
		for (char c : new char[] { 'f', 'g', 'd', 'e', 'b', 'c', 'a', 'n', 'o',
				'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't', 's', 'r',
				'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				while (curChar < (char) 97 || curChar > (char) 122) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "a — z");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z' }) {
			if (c == curChar) {
				while (curChar < (char) 65 || curChar > (char) 90) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "A — Z");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Alpha(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Alphanum(C_Alphanum arg_0) throws ParseException {
		Node cur = new Node("Alphanum");
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_Alpha arg_1 = new C_Alpha();
				cur.addChild(f_Alpha(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Digit arg_1 = new C_Digit();
				cur.addChild(f_Digit(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '_' }) {
			if (c == curChar) {
				while (curChar < (char) 95 || curChar > (char) 95) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "_");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Alphanum(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_CharId(C_CharId arg_0) throws ParseException {
		Node cur = new Node("CharId");
		for (char c : new char[] { '"', '\'', '\\', 'f', 'b', 'n', 't', 'r' }) {
			if (c == curChar) {
				C_Escaped arg_1 = new C_Escaped();
				cur.addChild(f_Escaped(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { 'u' }) {
			if (c == curChar) {
				while (curChar < (char) 117 || curChar > (char) 117) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "u");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_Hex arg_2 = new C_Hex();
				cur.addChild(f_Hex(arg_2));
				C_Hex arg_3 = new C_Hex();
				cur.addChild(f_Hex(arg_3));
				C_Hex arg_4 = new C_Hex();
				cur.addChild(f_Hex(arg_4));
				C_Hex arg_5 = new C_Hex();
				cur.addChild(f_Hex(arg_5));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_CharId(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_CharRange(C_CharRange arg_0) throws ParseException {
		Node cur = new Node("CharRange");
		for (char c : new char[] { '[' }) {
			if (c == curChar) {
				while (curChar < (char) 91 || curChar > (char) 91) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "[");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_RangeBoundary arg_2 = new C_RangeBoundary();
				cur.addChild(f_RangeBoundary(arg_2));
				while (curChar < (char) 45 || curChar > (char) 45) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "-");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_RangeBoundary arg_4 = new C_RangeBoundary();
				cur.addChild(f_RangeBoundary(arg_4));
				while (curChar < (char) 93 || curChar > (char) 93) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "]");
					}
				}
				C__Terminal arg_5 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_CharRange(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Code(C_Code arg_0) throws ParseException {
		Node cur = new Node("Code");
		for (char c : new char[] { '{' }) {
			if (c == curChar) {
				while (curChar < (char) 123 || curChar > (char) 123) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "{");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_MaybeCodeChars arg_2 = new C_MaybeCodeChars();
				cur.addChild(f_MaybeCodeChars(arg_2));
				while (curChar < (char) 125 || curChar > (char) 125) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "}");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Code(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_CodeChar(C_CodeChar arg_0) throws ParseException {
		Node cur = new Node("CodeChar");
		for (char c : new char[] { '\t', '\n', ' ' }) {
			if (c == curChar) {
				C_WS arg_1 = new C_WS();
				cur.addChild(f_WS(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '"', '#', '!', '&', '\'', '$', '%', '*',
				'+', '(', ')', '.', '/', ',', '-', '3', '2', '1', '0', '7',
				'6', '5', '4', ';', ':', '9', '8', '?', '>', '=', '<', 'D',
				'E', 'F', 'G', '@', 'A', 'B', 'C', 'L', 'M', 'N', 'O', 'H',
				'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P', 'S', 'R', ']',
				'\\', '_', '^', 'Y', 'X', '[', 'Z', 'f', 'g', 'd', 'e', 'b',
				'c', '`', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w',
				'v', 'u', 't', 's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				while (curChar < (char) 33 || curChar > (char) 122) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "! — z");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '|' }) {
			if (c == curChar) {
				while (curChar < (char) 124 || curChar > (char) 124) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "|");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '~' }) {
			if (c == curChar) {
				while (curChar < (char) 126 || curChar > (char) 126) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "~");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_CodeChar(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_CodeWS(C_CodeWS arg_0) throws ParseException {
		Node cur = new Node("CodeWS");
		for (char c : new char[] { '{' }) {
			if (c == curChar) {
				C_Code arg_1 = new C_Code();
				cur.addChild(f_Code(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '\t', '\n', ' ' }) {
			if (c == curChar) {
				C_WS arg_1 = new C_WS();
				cur.addChild(f_WS(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_CodeWS(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Delims(C_Delims arg_0) throws ParseException {
		Node cur = new Node("Delims");
		for (char c : new char[] { '_' }) {
			if (c == curChar) {
				while (curChar < (char) 95 || curChar > (char) 95) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "_");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_QuotedChars arg_2 = new C_QuotedChars();
				cur.addChild(f_QuotedChars(arg_2));
				return cur;
			}
		}
		for (char c : new char[] { '\uffff' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Delims(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Digit(C_Digit arg_0) throws ParseException {
		Node cur = new Node("Digit");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				while (curChar < (char) 48 || curChar > (char) 57) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "0 — 9");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Digit(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Eps(C_Eps arg_0) throws ParseException {
		Node cur = new Node("Eps");
		for (char c : new char[] { '\t', '\n', '"', ' ', '\'', '$', '-', ';',
				':', ']', '_', '\uffff', '}', '|', '{' }) {
			if (c == curChar) {
				C__Terminal arg_1 = new C__Terminal("");
				cur.addChild(new Node(""));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Eps(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Escaped(C_Escaped arg_0) throws ParseException {
		Node cur = new Node("Escaped");
		for (char c : new char[] { 'b' }) {
			if (c == curChar) {
				while (curChar < (char) 98 || curChar > (char) 98) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "b");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { 't' }) {
			if (c == curChar) {
				while (curChar < (char) 116 || curChar > (char) 116) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "t");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { 'n' }) {
			if (c == curChar) {
				while (curChar < (char) 110 || curChar > (char) 110) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "n");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { 'f' }) {
			if (c == curChar) {
				while (curChar < (char) 102 || curChar > (char) 102) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "f");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { 'r' }) {
			if (c == curChar) {
				while (curChar < (char) 114 || curChar > (char) 114) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "r");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '"' }) {
			if (c == curChar) {
				while (curChar < (char) 34 || curChar > (char) 34) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\"");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\'' }) {
			if (c == curChar) {
				while (curChar < (char) 39 || curChar > (char) 39) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "'");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\\' }) {
			if (c == curChar) {
				while (curChar < (char) 92 || curChar > (char) 92) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\\");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Escaped(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_File(C_File arg_0) throws ParseException {
		Node cur = new Node("File");
		for (char c : new char[] { '$', '-', '{' }) {
			if (c == curChar) {
				C_Header arg_1 = new C_Header();
				cur.addChild(f_Header(arg_1));
				C_NonTermDef arg_2 = new C_NonTermDef();
				cur.addChild(f_NonTermDef(arg_2));
				C_Start arg_3 = new C_Start();
				cur.addChild(f_Start(arg_3));
				C_Rules arg_4 = new C_Rules();
				cur.addChild(f_Rules(arg_4));
				C_Delims arg_5 = new C_Delims();
				cur.addChild(f_Delims(arg_5));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_File(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Header(C_Header arg_0) throws ParseException {
		Node cur = new Node("Header");
		for (char c : new char[] { '{' }) {
			if (c == curChar) {
				C_Code arg_1 = new C_Code();
				cur.addChild(f_Code(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '$', '-' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Header(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Hex(C_Hex arg_0) throws ParseException {
		Node cur = new Node("Hex");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Digit arg_1 = new C_Digit();
				cur.addChild(f_Digit(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { 'f', 'd', 'e', 'b', 'c', 'a' }) {
			if (c == curChar) {
				while (curChar < (char) 97 || curChar > (char) 102) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "a — f");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { 'D', 'E', 'F', 'A', 'B', 'C' }) {
			if (c == curChar) {
				while (curChar < (char) 65 || curChar > (char) 70) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "A — F");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Hex(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Int(C_Int arg_0) throws ParseException {
		Node cur = new Node("Int");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Digit arg_1 = new C_Digit();
				cur.addChild(f_Digit(arg_1));
				C_MaybeDigits arg_2 = new C_MaybeDigits();
				cur.addChild(f_MaybeDigits(arg_2));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Int(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeAlphanums(C_MaybeAlphanums arg_0) throws ParseException {
		Node cur = new Node("MaybeAlphanums");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8', 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M', 'N', 'O',
				'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P', 'S', 'R',
				'_', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a', 'n',
				'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't', 's',
				'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_Alphanum arg_1 = new C_Alphanum();
				cur.addChild(f_Alphanum(arg_1));
				C_MaybeAlphanums arg_2 = new C_MaybeAlphanums();
				cur.addChild(f_MaybeAlphanums(arg_2));
				return cur;
			}
		}
		for (char c : new char[] { '\t', '\n', ' ', '-', ';', '{' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeAlphanums(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeChar(C_MaybeChar arg_0) throws ParseException {
		Node cur = new Node("MaybeChar");
		for (char c : new char[] { '#', '!', '&', '$', '%', '*', '+', '(', ')',
				'.', '/', ',', '-', '3', '2', '1', '0', '7', '6', '5', '4',
				';', ':', '9', '8', '?', '>', '=', '<', 'D', 'E', 'F', 'G',
				'@', 'A', 'B', 'C', 'L', 'M', 'N', 'O', 'H', 'I', 'J', 'K',
				'U', 'T', 'W', 'V', 'Q', 'P', 'S', 'R', ']', '\\', '_', '^',
				'Y', 'X', '[', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', '`', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', '~', '}', '|', '{', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_SingleChar arg_1 = new C_SingleChar();
				cur.addChild(f_SingleChar(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeChar(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeCodeChars(C_MaybeCodeChars arg_0) throws ParseException {
		Node cur = new Node("MaybeCodeChars");
		for (char c : new char[] { '\t', '\n', '"', '#', ' ', '!', '&', '\'',
				'$', '%', '*', '+', '(', ')', '.', '/', ',', '-', '3', '2',
				'1', '0', '7', '6', '5', '4', ';', ':', '9', '8', '?', '>',
				'=', '<', 'D', 'E', 'F', 'G', '@', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', ']', '\\', '_', '^', 'Y', 'X', '[', 'Z', 'f', 'g',
				'd', 'e', 'b', 'c', '`', 'a', 'n', 'o', 'l', 'm', 'j', 'k',
				'h', 'i', 'w', 'v', 'u', 't', 's', 'r', 'q', 'p', '~', '|',
				'z', 'y', 'x' }) {
			if (c == curChar) {
				C_CodeChar arg_1 = new C_CodeChar();
				cur.addChild(f_CodeChar(arg_1));
				C_MaybeCodeChars arg_2 = new C_MaybeCodeChars();
				cur.addChild(f_MaybeCodeChars(arg_2));
				return cur;
			}
		}
		for (char c : new char[] { '}' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeCodeChars(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeDigits(C_MaybeDigits arg_0) throws ParseException {
		Node cur = new Node("MaybeDigits");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Digit arg_1 = new C_Digit();
				cur.addChild(f_Digit(arg_1));
				C_MaybeDigits arg_2 = new C_MaybeDigits();
				cur.addChild(f_MaybeDigits(arg_2));
				return cur;
			}
		}
		for (char c : new char[] { '-', ']' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeDigits(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeRightSides(C_MaybeRightSides arg_0)
			throws ParseException {
		Node cur = new Node("MaybeRightSides");
		for (char c : new char[] { '|' }) {
			if (c == curChar) {
				while (curChar < (char) 124 || curChar > (char) 124) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "|");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_RightSide arg_2 = new C_RightSide();
				cur.addChild(f_RightSide(arg_2));
				C_MaybeRightSides arg_3 = new C_MaybeRightSides();
				cur.addChild(f_MaybeRightSides(arg_3));
				return cur;
			}
		}
		for (char c : new char[] { ';' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeRightSides(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeSynthCode(C_MaybeSynthCode arg_0) throws ParseException {
		Node cur = new Node("MaybeSynthCode");
		for (char c : new char[] { ':' }) {
			if (c == curChar) {
				while (curChar < (char) 58 || curChar > (char) 58) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, ":");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_Code arg_2 = new C_Code();
				cur.addChild(f_Code(arg_2));
				return cur;
			}
		}
		for (char c : new char[] { ';', '|' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeSynthCode(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeUnits(C_MaybeUnits arg_0) throws ParseException {
		Node cur = new Node("MaybeUnits");
		for (char c : new char[] { '"', '\'', 'D', 'E', 'F', 'G', 'A', 'B',
				'C', 'L', 'M', 'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W',
				'V', 'Q', 'P', 'S', 'R', 'Y', 'X', '[', 'Z', 'f', 'g', 'd',
				'e', 'b', 'c', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i',
				'w', 'v', 'u', 't', 's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_Unit arg_1 = new C_Unit();
				cur.addChild(f_Unit(arg_1));
				C_MaybeUnits arg_2 = new C_MaybeUnits();
				cur.addChild(f_MaybeUnits(arg_2));
				return cur;
			}
		}
		for (char c : new char[] { ';', ':', '|' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeUnits(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_NonTerm(C_NonTerm arg_0) throws ParseException {
		Node cur = new Node("NonTerm");
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_Alpha arg_1 = new C_Alpha();
				cur.addChild(f_Alpha(arg_1));
				C_MaybeAlphanums arg_2 = new C_MaybeAlphanums();
				cur.addChild(f_MaybeAlphanums(arg_2));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_NonTerm(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_NonTermDef(C_NonTermDef arg_0) throws ParseException {
		Node cur = new Node("NonTermDef");
		for (char c : new char[] { '$' }) {
			if (c == curChar) {
				while (curChar < (char) 36 || curChar > (char) 36) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "$");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_NonTerm arg_2 = new C_NonTerm();
				cur.addChild(f_NonTerm(arg_2));
				C_Code arg_3 = new C_Code();
				cur.addChild(f_Code(arg_3));
				C_NonTermDef arg_4 = new C_NonTermDef();
				cur.addChild(f_NonTermDef(arg_4));
				return cur;
			}
		}
		for (char c : new char[] { '-' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_NonTermDef(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_QuotedChar(C_QuotedChar arg_0) throws ParseException {
		Node cur = new Node("QuotedChar");
		for (char c : new char[] { '"' }) {
			if (c == curChar) {
				while (curChar < (char) 34 || curChar > (char) 34) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\"");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_SingleChar arg_2 = new C_SingleChar();
				cur.addChild(f_SingleChar(arg_2));
				while (curChar < (char) 34 || curChar > (char) 34) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\"");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\'' }) {
			if (c == curChar) {
				while (curChar < (char) 39 || curChar > (char) 39) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "'");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_SingleChar arg_2 = new C_SingleChar();
				cur.addChild(f_SingleChar(arg_2));
				while (curChar < (char) 39 || curChar > (char) 39) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "'");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_QuotedChar(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_QuotedChars(C_QuotedChars arg_0) throws ParseException {
		Node cur = new Node("QuotedChars");
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				C_QuotedChar arg_1 = new C_QuotedChar();
				cur.addChild(f_QuotedChar(arg_1));
				C_QuotedChars arg_2 = new C_QuotedChars();
				cur.addChild(f_QuotedChars(arg_2));
				return cur;
			}
		}
		for (char c : new char[] { '\uffff' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_QuotedChars(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_QuotedMaybeChar(C_QuotedMaybeChar arg_0)
			throws ParseException {
		Node cur = new Node("QuotedMaybeChar");
		for (char c : new char[] { '"' }) {
			if (c == curChar) {
				while (curChar < (char) 34 || curChar > (char) 34) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\"");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_MaybeChar arg_2 = new C_MaybeChar();
				cur.addChild(f_MaybeChar(arg_2));
				while (curChar < (char) 34 || curChar > (char) 34) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\"");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\'' }) {
			if (c == curChar) {
				while (curChar < (char) 39 || curChar > (char) 39) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "'");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_MaybeChar arg_2 = new C_MaybeChar();
				cur.addChild(f_MaybeChar(arg_2));
				while (curChar < (char) 39 || curChar > (char) 39) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "'");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_QuotedMaybeChar(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_RangeBoundary(C_RangeBoundary arg_0) throws ParseException {
		Node cur = new Node("RangeBoundary");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Int arg_1 = new C_Int();
				cur.addChild(f_Int(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				C_QuotedChar arg_1 = new C_QuotedChar();
				cur.addChild(f_QuotedChar(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_RangeBoundary(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_RightSide(C_RightSide arg_0) throws ParseException {
		Node cur = new Node("RightSide");
		for (char c : new char[] { '"', '\'', 'D', 'E', 'F', 'G', 'A', 'B',
				'C', 'L', 'M', 'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W',
				'V', 'Q', 'P', 'S', 'R', 'Y', 'X', '[', 'Z', 'f', 'g', 'd',
				'e', 'b', 'c', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i',
				'w', 'v', 'u', 't', 's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_Unit arg_1 = new C_Unit();
				cur.addChild(f_Unit(arg_1));
				C_MaybeUnits arg_2 = new C_MaybeUnits();
				cur.addChild(f_MaybeUnits(arg_2));
				C_MaybeSynthCode arg_3 = new C_MaybeSynthCode();
				cur.addChild(f_MaybeSynthCode(arg_3));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_RightSide(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Rules(C_Rules arg_0) throws ParseException {
		Node cur = new Node("Rules");
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_NonTerm arg_1 = new C_NonTerm();
				cur.addChild(f_NonTerm(arg_1));
				while (curChar < (char) 45 || curChar > (char) 45) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "-");
					}
				}
				C__Terminal arg_2 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				while (curChar < (char) 62 || curChar > (char) 62) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, ">");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_RightSide arg_4 = new C_RightSide();
				cur.addChild(f_RightSide(arg_4));
				C_MaybeRightSides arg_5 = new C_MaybeRightSides();
				cur.addChild(f_MaybeRightSides(arg_5));
				while (curChar < (char) 59 || curChar > (char) 59) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, ";");
					}
				}
				C__Terminal arg_6 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_Rules arg_7 = new C_Rules();
				cur.addChild(f_Rules(arg_7));
				return cur;
			}
		}
		for (char c : new char[] { '_', '\uffff' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Rules(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_SingleChar(C_SingleChar arg_0) throws ParseException {
		Node cur = new Node("SingleChar");
		for (char c : new char[] { '!' }) {
			if (c == curChar) {
				while (curChar < (char) 33 || curChar > (char) 33) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "!");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '#', '&', '$', '%' }) {
			if (c == curChar) {
				while (curChar < (char) 35 || curChar > (char) 38) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "# — &");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '*', '+', '(', ')', '.', '/', ',', '-', '3',
				'2', '1', '0', '7', '6', '5', '4', ';', ':', '9', '8', '?',
				'>', '=', '<', 'D', 'E', 'F', 'G', '@', 'A', 'B', 'C', 'L',
				'M', 'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q',
				'P', 'S', 'R', 'Y', 'X', '[', 'Z' }) {
			if (c == curChar) {
				while (curChar < (char) 40 || curChar > (char) 91) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "( — [");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { ']', '_', '^', 'f', 'g', 'd', 'e', 'b', 'c',
				'`', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v',
				'u', 't', 's', 'r', 'q', 'p', '~', '}', '|', '{', 'z', 'y', 'x' }) {
			if (c == curChar) {
				while (curChar < (char) 93 || curChar > (char) 126) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "] — ~");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\\' }) {
			if (c == curChar) {
				while (curChar < (char) 92 || curChar > (char) 92) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\\");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_CharId arg_2 = new C_CharId();
				cur.addChild(f_CharId(arg_2));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_SingleChar(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Start(C_Start arg_0) throws ParseException {
		Node cur = new Node("Start");
		for (char c : new char[] { '-' }) {
			if (c == curChar) {
				while (curChar < (char) 45 || curChar > (char) 45) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "-");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				while (curChar < (char) 62 || curChar > (char) 62) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, ">");
					}
				}
				C__Terminal arg_2 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				C_NonTerm arg_3 = new C_NonTerm();
				cur.addChild(f_NonTerm(arg_3));
				while (curChar < (char) 59 || curChar > (char) 59) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, ";");
					}
				}
				C__Terminal arg_4 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Start(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Term(C_Term arg_0) throws ParseException {
		Node cur = new Node("Term");
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				C_QuotedMaybeChar arg_1 = new C_QuotedMaybeChar();
				cur.addChild(f_QuotedMaybeChar(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { '[' }) {
			if (c == curChar) {
				C_CharRange arg_1 = new C_CharRange();
				cur.addChild(f_CharRange(arg_1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Term(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Unit(C_Unit arg_0) throws ParseException {
		Node cur = new Node("Unit");
		for (char c : new char[] { '"', '\'', '[' }) {
			if (c == curChar) {
				C_Term arg_1 = new C_Term();
				cur.addChild(f_Term(arg_1));
				return cur;
			}
		}
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				C_NonTerm arg_1 = new C_NonTerm();
				cur.addChild(f_NonTerm(arg_1));
				C_CodeWS arg_2 = new C_CodeWS();
				cur.addChild(f_CodeWS(arg_2));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Unit(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_WS(C_WS arg_0) throws ParseException {
		Node cur = new Node("WS");
		for (char c : new char[] { ' ' }) {
			if (c == curChar) {
				while (curChar < (char) 32 || curChar > (char) 32) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, " ");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\t' }) {
			if (c == curChar) {
				while (curChar < (char) 9 || curChar > (char) 9) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\\t");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\n' }) {
			if (c == curChar) {
				while (curChar < (char) 10 || curChar > (char) 10) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\\n");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur
						.addChild(new Node(""
								+ StringUtils.bestView(curChar, false)));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_WS(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	public Node getTree() throws ParseException {
		C_File stNonTerm = new C_File();
		Node ans = f_File(stNonTerm);
		if (curChar != (char) -1) {
			throw new ParseException(fs.getPosition(), "eof", "" + curChar);
		}
		return ans;
	}
}

class C__Terminal {
	public String text;

	public C__Terminal(String text) {
		this.text = text;
	}
}

class C_Alpha {
}

class C_Alphanum {
}

class C_CharId {
}

class C_CharRange {
}

class C_Code {
}

class C_CodeChar {
}

class C_CodeWS {
}

class C_Delims {
}

class C_Digit {
}

class C_Eps {
}

class C_Escaped {
}

class C_File {
}

class C_Header {
}

class C_Hex {
}

class C_Int {
}

class C_MaybeAlphanums {
}

class C_MaybeChar {
}

class C_MaybeCodeChars {
}

class C_MaybeDigits {
}

class C_MaybeRightSides {
}

class C_MaybeSynthCode {
}

class C_MaybeUnits {
}

class C_NonTerm {
}

class C_NonTermDef {
}

class C_QuotedChar {
}

class C_QuotedChars {
}

class C_QuotedMaybeChar {
}

class C_RangeBoundary {
}

class C_RightSide {
}

class C_Rules {
}

class C_SingleChar {
}

class C_Start {
}

class C_Term {
}

class C_Unit {
}

class C_WS {
}
