package gen;

import java.io.FileNotFoundException;

import util.FileScanner;
import util.Node;
import util.ParseException;

public class Test {
	private FileScanner fs;
	private char curChar;

	public Test(String fileName) throws FileNotFoundException {
		this.fs = new FileScanner(fileName);
		curChar = fs.nextChar();
	}

	private Node f_NonTerm(C_NonTerm arg_0) throws ParseException {
		Node cur = new Node("NonTerm");
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Digit(C_Digit arg_0) throws ParseException {
		Node cur = new Node("Digit");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				cur.addChild(new Node("'0' — '9'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_CodeChar(C_CodeChar arg_0) throws ParseException {
		Node cur = new Node("CodeChar");
		for (char c : new char[] { '"', '#', '!', '&', '\'', '$', '%', '*',
				'+', '(', ')', '.', '/', ',', '-', '3', '2', '1', '0', '7',
				'6', '5', '4', ';', ':', '9', '8', '?', '>', '=', '<', 'D',
				'E', 'F', 'G', '@', 'A', 'B', 'C', 'L', 'M', 'N', 'O', 'H',
				'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P', 'S', 'R', ']',
				'\\', '_', '^', 'Y', 'X', '[', 'Z', 'f', 'g', 'd', 'e', 'b',
				'c', '`', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w',
				'v', 'u', 't', 's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				cur.addChild(new Node("'!' — 'z'"));
				return cur;
			}
		}
		for (char c : new char[] { '|' }) {
			if (c == curChar) {
				cur.addChild(new Node("'|'"));
				return cur;
			}
		}
		for (char c : new char[] { '~' }) {
			if (c == curChar) {
				cur.addChild(new Node("'~'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Escaped(C_Escaped arg_0) throws ParseException {
		Node cur = new Node("Escaped");
		for (char c : new char[] { 'b' }) {
			if (c == curChar) {
				cur.addChild(new Node("'b'"));
				return cur;
			}
		}
		for (char c : new char[] { 't' }) {
			if (c == curChar) {
				cur.addChild(new Node("'t'"));
				return cur;
			}
		}
		for (char c : new char[] { 'n' }) {
			if (c == curChar) {
				cur.addChild(new Node("'n'"));
				return cur;
			}
		}
		for (char c : new char[] { 'f' }) {
			if (c == curChar) {
				cur.addChild(new Node("'f'"));
				return cur;
			}
		}
		for (char c : new char[] { 'r' }) {
			if (c == curChar) {
				cur.addChild(new Node("'r'"));
				return cur;
			}
		}
		for (char c : new char[] { '"' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\"'"));
				return cur;
			}
		}
		for (char c : new char[] { '\'' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\''"));
				return cur;
			}
		}
		for (char c : new char[] { '\\' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\\'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_QuotedChar(C_QuotedChar arg_0) throws ParseException {
		Node cur = new Node("QuotedChar");
		for (char c : new char[] { '"' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\"'"));
				cur.addChild(new Node("'\"'"));
				return cur;
			}
		}
		for (char c : new char[] { '\'' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\''"));
				cur.addChild(new Node("'\''"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_QuotedChars(C_QuotedChars arg_0) throws ParseException {
		Node cur = new Node("QuotedChars");
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '\uffff' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Hex(C_Hex arg_0) throws ParseException {
		Node cur = new Node("Hex");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { 'f', 'd', 'e', 'b', 'c', 'a' }) {
			if (c == curChar) {
				cur.addChild(new Node("'a' — 'f'"));
				return cur;
			}
		}
		for (char c : new char[] { 'D', 'E', 'F', 'A', 'B', 'C' }) {
			if (c == curChar) {
				cur.addChild(new Node("'A' — 'F'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Start(C_Start arg_0) throws ParseException {
		Node cur = new Node("Start");
		for (char c : new char[] { '-' }) {
			if (c == curChar) {
				cur.addChild(new Node("'-'"));
				cur.addChild(new Node("'>'"));
				cur.addChild(new Node("';'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Alpha(C_Alpha arg_0) throws ParseException {
		Node cur = new Node("Alpha");
		for (char c : new char[] { 'f', 'g', 'd', 'e', 'b', 'c', 'a', 'n', 'o',
				'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't', 's', 'r',
				'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				cur.addChild(new Node("'a' — 'z'"));
				return cur;
			}
		}
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z' }) {
			if (c == curChar) {
				cur.addChild(new Node("'A' — 'Z'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_MaybeDigits(C_MaybeDigits arg_0) throws ParseException {
		Node cur = new Node("MaybeDigits");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '-', ']' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_CharId(C_CharId arg_0) throws ParseException {
		Node cur = new Node("CharId");
		for (char c : new char[] { '"', '\'', '\\', 'f', 'b', 'n', 't', 'r' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { 'u' }) {
			if (c == curChar) {
				cur.addChild(new Node("'u'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Int(C_Int arg_0) throws ParseException {
		Node cur = new Node("Int");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Alphanum(C_Alphanum arg_0) throws ParseException {
		Node cur = new Node("Alphanum");
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '_' }) {
			if (c == curChar) {
				cur.addChild(new Node("'_'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Header(C_Header arg_0) throws ParseException {
		Node cur = new Node("Header");
		for (char c : new char[] { '{' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '$', '-' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Code(C_Code arg_0) throws ParseException {
		Node cur = new Node("Code");
		for (char c : new char[] { '{' }) {
			if (c == curChar) {
				cur.addChild(new Node("'{'"));
				cur.addChild(new Node("'}'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_QuotedMaybeChar(C_QuotedMaybeChar arg_0)
			throws ParseException {
		Node cur = new Node("QuotedMaybeChar");
		for (char c : new char[] { '"' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\"'"));
				cur.addChild(new Node("'\"'"));
				return cur;
			}
		}
		for (char c : new char[] { '\'' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\''"));
				cur.addChild(new Node("'\''"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Rules(C_Rules arg_0) throws ParseException {
		Node cur = new Node("Rules");
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				cur.addChild(new Node("'-'"));
				cur.addChild(new Node("'>'"));
				cur.addChild(new Node("':'"));
				cur.addChild(new Node("';'"));
				return cur;
			}
		}
		for (char c : new char[] { '_', '\uffff' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_SingleChar(C_SingleChar arg_0) throws ParseException {
		Node cur = new Node("SingleChar");
		for (char c : new char[] { '!' }) {
			if (c == curChar) {
				cur.addChild(new Node("'!'"));
				return cur;
			}
		}
		for (char c : new char[] { '#', '&', '$', '%' }) {
			if (c == curChar) {
				cur.addChild(new Node("'#' — '&'"));
				return cur;
			}
		}
		for (char c : new char[] { '*', '+', '(', ')', '.', '/', ',', '-', '3',
				'2', '1', '0', '7', '6', '5', '4', ';', ':', '9', '8', '?',
				'>', '=', '<', 'D', 'E', 'F', 'G', '@', 'A', 'B', 'C', 'L',
				'M', 'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q',
				'P', 'S', 'R', 'Y', 'X', '[', 'Z' }) {
			if (c == curChar) {
				cur.addChild(new Node("'(' — '['"));
				return cur;
			}
		}
		for (char c : new char[] { ']', '_', '^', 'f', 'g', 'd', 'e', 'b', 'c',
				'`', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v',
				'u', 't', 's', 'r', 'q', 'p', '~', '}', '|', '{', 'z', 'y', 'x' }) {
			if (c == curChar) {
				cur.addChild(new Node("']' — '~'"));
				return cur;
			}
		}
		for (char c : new char[] { '\\' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\\'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_NonTermDef(C_NonTermDef arg_0) throws ParseException {
		Node cur = new Node("NonTermDef");
		for (char c : new char[] { '$' }) {
			if (c == curChar) {
				cur.addChild(new Node("'$'"));
				return cur;
			}
		}
		for (char c : new char[] { '-' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Unit(C_Unit arg_0) throws ParseException {
		Node cur = new Node("Unit");
		for (char c : new char[] { '"', '\'', '[' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { 'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M',
				'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P',
				'S', 'R', 'Y', 'X', 'Z', 'f', 'g', 'd', 'e', 'b', 'c', 'a',
				'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w', 'v', 'u', 't',
				's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Eps(C_Eps arg_0) throws ParseException {
		Node cur = new Node("Eps");
		for (char c : new char[] { '"', '\'', '$', '-', ';', ':', ']', '_',
				'\uffff', '}', '{' }) {
			if (c == curChar) {
				cur.addChild(new Node("'\ufffe'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Term(C_Term arg_0) throws ParseException {
		Node cur = new Node("Term");
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '[' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_CharRange(C_CharRange arg_0) throws ParseException {
		Node cur = new Node("CharRange");
		for (char c : new char[] { '[' }) {
			if (c == curChar) {
				cur.addChild(new Node("'['"));
				cur.addChild(new Node("'-'"));
				cur.addChild(new Node("']'"));
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_File(C_File arg_0) throws ParseException {
		Node cur = new Node("File");
		for (char c : new char[] { '$', '-', '{' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_MaybeUnits(C_MaybeUnits arg_0) throws ParseException {
		Node cur = new Node("MaybeUnits");
		for (char c : new char[] { '"', '\'', 'D', 'E', 'F', 'G', 'A', 'B',
				'C', 'L', 'M', 'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W',
				'V', 'Q', 'P', 'S', 'R', 'Y', 'X', '[', 'Z', 'f', 'g', 'd',
				'e', 'b', 'c', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i',
				'w', 'v', 'u', 't', 's', 'r', 'q', 'p', 'z', 'y', 'x' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '|' }) {
			if (c == curChar) {
				cur.addChild(new Node("'|'"));
				return cur;
			}
		}
		for (char c : new char[] { ':' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
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
				return cur;
			}
		}
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_Delims(C_Delims arg_0) throws ParseException {
		Node cur = new Node("Delims");
		for (char c : new char[] { '_' }) {
			if (c == curChar) {
				cur.addChild(new Node("'_'"));
				return cur;
			}
		}
		for (char c : new char[] { '\uffff' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_MaybeCodeChars(C_MaybeCodeChars arg_0) throws ParseException {
		Node cur = new Node("MaybeCodeChars");
		for (char c : new char[] { '"', '#', '!', '&', '\'', '$', '%', '*',
				'+', '(', ')', '.', '/', ',', '-', '3', '2', '1', '0', '7',
				'6', '5', '4', ';', ':', '9', '8', '?', '>', '=', '<', 'D',
				'E', 'F', 'G', '@', 'A', 'B', 'C', 'L', 'M', 'N', 'O', 'H',
				'I', 'J', 'K', 'U', 'T', 'W', 'V', 'Q', 'P', 'S', 'R', ']',
				'\\', '_', '^', 'Y', 'X', '[', 'Z', 'f', 'g', 'd', 'e', 'b',
				'c', '`', 'a', 'n', 'o', 'l', 'm', 'j', 'k', 'h', 'i', 'w',
				'v', 'u', 't', 's', 'r', 'q', 'p', '~', '|', 'z', 'y', 'x' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '}' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	private Node f_RangeBoundary(C_RangeBoundary arg_0) throws ParseException {
		Node cur = new Node("RangeBoundary");
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				return cur;
			}
		}
		for (char c : new char[] { '"', '\'' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
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
				return cur;
			}
		}
		for (char c : new char[] { '-', ';', '{' }) {
			if (c == curChar) {
				return cur;
			}
		}
		throw new ParseException(fs.getPosition(), curChar);
	}

	public Node getTree() throws ParseException {
		C_File stNonTerm = new C_File();
		Node ans = f_File(stNonTerm);
		if (curChar != (char) -1) {
			throw new ParseException(fs.getPosition(), "EOF", FileScanner
					.quoted("" + curChar));
		}
		return ans;
	}
}

class C_NonTerm {
}

class C_Digit {
}

class C_CodeChar {
}

class C_Escaped {
}

class C_QuotedChar {
}

class C_QuotedChars {
}

class C_Hex {
}

class C_Start {
}

class C_Alpha {
}

class C_MaybeDigits {
}

class C_CharId {
}

class C_Int {
}

class C_Alphanum {
}

class C_Header {
}

class C_Code {
}

class C_QuotedMaybeChar {
}

class C_Rules {
}

class C_SingleChar {
}

class C_NonTermDef {
}

class C_Unit {
}

class C_Eps {
}

class C_Term {
}

class C_CharRange {
}

class C_File {
}

class C_MaybeUnits {
}

class C_MaybeChar {
}

class C_Delims {
}

class C_MaybeCodeChars {
}

class C_RangeBoundary {
}

class C_MaybeAlphanums {
}
