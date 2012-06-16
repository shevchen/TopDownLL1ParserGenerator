-> File ;

File -> Header NonTermDef Start Rules Delims ;

Header -> Code | Eps ;
Code -> "{" MaybeCodeChars "}" ;
MaybeCodeChars -> CodeChar MaybeCodeChars | Eps ;
CodeChar -> WS | [ 33 - 122 ] | '|' | '~' ;

NonTermDef -> "$" NonTerm Code NonTermDef | Eps ;
NonTerm -> Alpha MaybeAlphanums ;
Alpha -> [ "a" - "z" ] | [ "A" - "Z" ] ;
MaybeAlphanums -> Alphanum MaybeAlphanums | Eps ;
Alphanum -> Alpha | Digit | "_" ;
Digit -> [ "0" - "9" ] ;

Start -> "-" ">" NonTerm ";" ;

Rules -> NonTerm "-" ">" RightSide MaybeRightSides ";" Rules | Eps ;
RightSide -> Unit MaybeUnits MaybeSynthCode ;
MaybeSynthCode -> ":" Code | Eps ;
MaybeRightSides -> "|" RightSide MaybeRightSides | Eps ;
MaybeUnits -> Unit MaybeUnits | Eps ;
Unit -> Term | NonTerm CodeWS ;
CodeWS -> Code | WS ;
Term -> QuotedChar | CharRange ;
QuotedChar -> "\"" SingleChar "\"" | "\'" SingleChar "\'" ;
SingleChar -> [ 32 - 33 ] | [ 35 - 38 ] | [ 40 - 91 ] | [ 93 - 126 ] | "\\" CharId ;
CharId -> Escaped | "u" Hex Hex Hex Hex ;
Escaped -> "b" | "t" | "n" | "f" | "r" | "\"" | "\'" | "\\" ;
Hex -> Digit | [ "a" - "f" ] | [ "A" - "F" ] ;
CharRange -> "[" RangeBoundary "-" RangeBoundary "]" ;
RangeBoundary -> Int | QuotedChar ;
Int -> Digit MaybeDigits ;
MaybeDigits -> Digit MaybeDigits | Eps ;

Delims -> "_" QuotedChars | Eps ;
QuotedChars -> QuotedChar QuotedChars | Eps ;