-> File ;

File -> Header NonTermDef Start Rules Delims ;

Header -> Code ";" | Eps ;
Code -> "#" MaybeCodeChars "#" | Eps ;
MaybeCodeChars -> CodeChar MaybeCodeChars | Eps ;
CodeChar -> [ 0 - 34 ] | [ 36 - 127 ] ;
Eps -> "" ;

NonTermDef -> "$" NonTerm Code ";" NonTermDef | Eps ;
NonTerm -> Alpha MaybeAlphanums ;
Alpha -> [ "a" - "z" ] | [ "A" - "Z" ] ;
MaybeAlphanums -> Alphanum MaybeAlphanums | Eps ;
Alphanum -> Alpha | Digit | "_" ;
Digit -> [ "0" - "9" ] ;

Start -> "-" ">" NonTerm ";" ;

Rules -> NonTerm "-" ">" Unit MaybeUnits MaybeSynthCode ";" Rules | Eps ;
MaybeUnits -> Unit MaybeUnits | Eps ;
Unit -> Term | NonTerm MaybeInheritCode ;
MaybeInheritCode -> Code | Eps ;
MaybeSynthCode -> "|" Code | Eps ;
Term -> QuotedMaybeChar | CharRange ;
QuotedMaybeChar -> """ MaybeChar """ | "'" MaybeChar "'" ;
MaybeChar -> SingleChar | Eps ;
SingleChar -> [ 0 - 91 ] | [ 93 - 127 ] | "\" CharId ;
CharId -> Escaped | "u" Hex Hex Hex Hex ;
Escaped -> "b" | "t" | "n" | "f" | "r" | """ | "'" | "\" ;
Hex -> Digit | [ "a" - "f" ] | [ "A" - "F" ] ;
CharRange -> "[" RangeBoundary "-" RangeBoundary "]" ;
RangeBoundary -> Int | QuotedChar ;
Int -> Digit MaybeDigits ;
MaybeDigits -> Digit MaybeDigits | Eps ;
QuotedChar -> """ SingleChar """ | "'" SingleChar "'" ;

Delims -> "_" QuotedChars | Eps ;
QuotedChars -> QuotedChar QuotedChars | Eps ;