$ E { int sum; }
$ E2 { int toRight, sum; }
$ T { int prod; }
$ T2 { int toRight, prod; }
$ F { int deg; }
$ F2 { int deg; }
$ D { int value; }
$ Number { int value; }
$ Digit { int value; }
$ MaybeDigits { int value, pow10; }


-> S;


S -> E : {
	System.out.println($1.sum);
};

E -> T E2{ $2.toRight = $1.prod; } : {
	$0.sum = $2.sum;
};

E2 -> '+' T E2{ $3.toRight = $0.toRight + $2.prod; } : {
	$0.sum = $3.sum;
} | '-' T E2{ $3.toRight = $0.toRight - $2.prod; } : {
	$0.sum = $3.sum;
} | Eps : {
	$0.sum = $0.toRight;
};

T -> F T2{ $2.toRight = $1.deg; } : {
	$0.prod = $2.prod;
};

T2 -> '*' F T2{ $3.toRight = $0.toRight * $2.deg; } : {
	$0.prod = $3.prod;
} | '/' F T2{ $3.toRight = $0.toRight / $2.deg; } : {
	$0.prod = $3.prod;
} | Eps : {
	$0.prod = $0.toRight;
};

F -> D F2{ $2.deg = $1.value; } : {
	$0.deg = $2.deg;
};

F2 -> '^' D F2{ $3.deg = $2.value; } : {
	$0.deg = (int) (Math.pow($0.deg, $3.deg) + 0.5);
} | Eps ;

D -> '(' E ')' : {
	$0.value = $2.sum;
} | Number : {
	$0.value = $1.value;
};

Number -> Digit MaybeDigits : {
	$0.value = $2.pow10 * $1.value + $2.value;
};

Digit -> ['0' - '9'] : {
	$0.value = $1.c - '0';
};

MaybeDigits -> Digit MaybeDigits : {
	$0.value = $2.pow10 * $1.value + $2.value;
	$0.pow10 = 10 * $2.pow10;
} | Eps : {
	$0.value = 0;
	$0.pow10 = 1;
};