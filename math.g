-> E;
E -> T E2 {} : {};
E2 -> '+' T E2 {} : {} | '' : {};
T -> F {} T2 {} : {};
T2 -> '*' F {} T2 {} : {} | "" : {};
F -> '(' E {} ')' : {} | Number {} : {};
Number -> Digit {} MaybeDigits {} : {};
Digit -> ['0' - '9'] : {};
MaybeDigits -> Digit {} MaybeDigits {} : {} | '' : {};