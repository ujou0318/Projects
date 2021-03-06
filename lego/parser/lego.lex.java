// CISC422, Winter 2004, Assignment 1, Juergen Dingel.
// Definition file of Lego for lexer generator.
// Generate parser with 
//	>> java JLex.Main lego.lex
package lego.parser;
import java.io.*;
import java_cup.runtime.Symbol;  // definition of scanner/parser interface
class LegoLex {
    // reads from System.in and produces token on System.out
    // invoke with "lego.parser.LegoLex < f"
    public static void main(String argv[]) throws java.io.IOException {
	try {
	    lexer theLexer = new lexer(System.in);
	    Symbol s = theLexer.next_token();
	    while (s.sym != sym.EOF) {
		System.out.println("Token: "+Utility.symToString(s.sym)+
				   "("+s.sym+") "+s.value);
		s = theLexer.next_token();
	    }
	    System.out.println("Token: "+Utility.symToString(s.sym)+
			       "("+s.sym+") "+s.value);
	}
	catch (IOException e) {
	    System.out.println("I/O error occurred while reading from stdin");
	    System.exit(1);
	} // end try/catch
    }
}
/* semantic value of token returned by scanner */
class TokenValue {          
    public String text;
    public int lineBegin;
    public int charBegin;
    public int charEnd;
    public String filename;   
    TokenValue() {
    }
    TokenValue(String text, int lineBegin, int charBegin, int charEnd) {
	this.text = text; 
	this.lineBegin = lineBegin; 
	this.charBegin = charBegin;
	this.charEnd = charEnd;
	this.filename = null;
    }
   // some functions to convert the value
   public String toString() { 
     return text;
   }
   public int toInt() {
       if (text.equals("MAX"))
	   return Integer.MAX_VALUE;
       return Integer.valueOf(text).intValue();
   }
} // TokenValue
/* some useful helper functions */
class Utility {
    public static int findKeyWord(String str) {
      if (str.equals("forall")) return sym.T_FORALL;
      else if (str.equals("exists")) return sym.T_EXISTS;
      else if (str.equals("Int")) return sym.T_INT;
      else if (str.equals("mod")) return sym.T_MOD;
      else if (str.equals("MAX")) return sym.T_INT;
      else return sym.T_IDENTIFER;
    }
 static public String symToString(int i) {
        switch (i) {
	case sym.T_IDENTIFER : return "IDENTIFER";
	case sym.T_PLUS : return "PLUS";
	case sym.T_MINUS : return "MINUS";
	case sym.T_DIVIDE : return "DIVIDE";
	case sym.T_TIMES : return "TIMES";
	case sym.T_MOD : return "MOD";
	case sym.T_GREATER : return "GREATER";
	case sym.T_GREATEREQ : return "GREATEREQ";
	case sym.T_EQ : return "EQ";
	case sym.T_NOT : return "NOT";
	case sym.T_AND : return "AND";
	case sym.T_OR : return "OR";
	case sym.T_IMPLIES : return "IMPLIES";
	case sym.T_EQUIV : return "EQUIV";
	case sym.T_FORALL : return "FORALL";
	case sym.T_EXISTS : return "EXISTS";
	case sym.T_INT : return "INT";
	case sym.T_LPAREN : return "LPAREN";
	case sym.T_RPAREN : return "RPAREN";
	case sym.T_LSQBRACE: return "LSQBRACE";
	case sym.T_RSQBRACE: return "RSQBRACE";
	case sym.T_DOT: return "DOT";
	case sym.T_COLON: return "COLON";
	case sym.T_ERROR: return "ERROR";
	case sym.EOF: return "EOF";
	case sym.T_EOF: return "EOF";
        default: return "unknown token";
        }
    } 
 // public static void assert( boolean expr ) { 
 // if (false == expr) {
 //	  throw (new Error("Error: Assertion failed."));
 //	}
 // }
  private static final String errorMsg[] = {
    "Error: Illegal character."
  };
  public static final int E_UNMATCHED = 0; 
  public static void error( int code ) {
      System.out.println(errorMsg[code]);
  }
} // Utility


class lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

    public String sourceFilename;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NOT_ACCEPT,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"21:8,17:3,21:2,0,21:18,17,13,21:4,14,21,2,3,9,7,21,8,6,10,18:10,1,21,16,11," +
"12,21:2,19:26,4,21,5,21,20,21,19:26,21,15,21:3,22:2")[0];

	private int yy_rmap[] = unpackFromString(1,30,
"0,1:7,2,1:3,3,1,4,5,6,7,1:2,8,1:3,9,1,10,11,12,1")[0];

	private int yy_nxt[][] = unpackFromString(13,23,
"-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,27,28,15,16,17,29:2,18,-1:35,19,-1:5,20" +
",-1:15,21,-1:25,22,-1:25,15,-1:23,16,-1:22,24,17,24,-1:20,20,-1:22,24:3,-1:" +
"14,25,-1:25,23,-1:15,26,-1:14");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

    return (new Symbol(sym.EOF, yychar, yychar, (new TokenValue(null,yyline,yychar,yychar))));
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						{ return (new Symbol(sym.T_COLON, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -2:
						break;
					case 2:
						{ return (new Symbol(sym.T_LPAREN, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -3:
						break;
					case 3:
						{ return (new Symbol(sym.T_RPAREN, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -4:
						break;
					case 4:
						{ return (new Symbol(sym.T_LSQBRACE, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -5:
						break;
					case 5:
						{ return (new Symbol(sym.T_RSQBRACE, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -6:
						break;
					case 6:
						{ return (new Symbol(sym.T_DOT, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -7:
						break;
					case 7:
						{ return (new Symbol(sym.T_PLUS, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -8:
						break;
					case 8:
						{ return (new Symbol(sym.T_MINUS, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -9:
						break;
					case 9:
						{ return (new Symbol(sym.T_TIMES, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -10:
						break;
					case 10:
						{ return (new Symbol(sym.T_DIVIDE, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -11:
						break;
					case 11:
						{ return (new Symbol(sym.T_EQ, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -12:
						break;
					case 12:
						{ return (new Symbol(sym.T_GREATER, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -13:
						break;
					case 13:
						{ return (new Symbol(sym.T_NOT, yychar, yychar+1, new TokenValue(yytext(), yyline, yychar, yychar+1))); }
					case -14:
						break;
					case 14:
						{
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED); }
					case -15:
						break;
					case 15:
						{}
					case -16:
						break;
					case 16:
						{ 
        String s = new String(yytext()); 
	return (new Symbol(sym.T_INT, yychar, yychar+s.length(), 
	          new TokenValue(s, yyline, yychar, yychar + s.length()))); }
					case -17:
						break;
					case 17:
						{
        String str = yytext();
        int sym = Utility.findKeyWord(str);
	return (new Symbol(sym, yychar, yychar+yytext().length(),
	       new TokenValue(yytext(), yyline, yychar, yychar + yytext().length()))); }
					case -18:
						break;
					case 18:
						
					case -19:
						break;
					case 19:
						{ return (new Symbol(sym.T_IMPLIES, yychar, yychar+2, new TokenValue(yytext(), yyline, yychar, yychar+2))); }
					case -20:
						break;
					case 20:
						{ 
        String s = new String(yytext()); 
	return (new Symbol(sym.T_INT, yychar, yychar+s.length(), 
	          new TokenValue(s, yyline, yychar, yychar + s.length()))); }
					case -21:
						break;
					case 21:
						{ return (new Symbol(sym.T_GREATEREQ, yychar, yychar+2, new TokenValue(yytext(), yyline, yychar, yychar+2))); }
					case -22:
						break;
					case 22:
						{ return (new Symbol(sym.T_AND, yychar, yychar+2, new TokenValue(yytext(), yyline, yychar, yychar+2))); }
					case -23:
						break;
					case 23:
						{ return (new Symbol(sym.T_OR, yychar, yychar+2, new TokenValue(yytext(), yyline, yychar, yychar+2))); }
					case -24:
						break;
					case 24:
						{
	return (new Symbol(sym.T_IDENTIFER, yychar, yychar+yytext().length(),
			   new TokenValue(yytext(), yyline, yychar, 
					yychar + yytext().length()))); }
					case -25:
						break;
					case 25:
						{ return (new Symbol(sym.T_EQUIV, yychar, yychar+3, new TokenValue(yytext(), yyline, yychar, yychar+3))); }
					case -26:
						break;
					case 27:
						{
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED); }
					case -27:
						break;
					case 28:
						{
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED); }
					case -28:
						break;
					case 29:
						{
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED); }
					case -29:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
