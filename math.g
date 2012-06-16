$ E { int sum; }
$ E2 { int sum; }
$ T { int prod; }
$ T2 { int prod; }
$ F { int value; }
$ Number { int value; }
$ Digit { int value; }
$ MaybeDigits { boolean exist; int value; }


-> S;


S -> E : {
	System.out.println($1.sum);
};

E -> T E2 : {
	$0.sum = $1.prod + $2.sum;	
};

E2 -> '+' T E2 : {
	$0.sum = $2.prod + $3.sum;
} | Eps : {
	$0.sum = 0;
};

T -> F T2 : {
	$0.prod = $1.value * $2.prod;
};

T2 -> '*' F T2 : {
	$0.prod = $2.value * $3.prod;
} | Eps : {
	$0.prod = 1;
};

F -> '(' E ')' : {
	$0.value = $2.sum;
} | Number : {
	$0.value = $1.value;
};

Number -> Digit MaybeDigits : {
	$0.value = $2.exist ? (10 * $1.value + $2.value) : $1.value;
};

Digit -> ['0' - '9'] : {
	$0.value = $1.text.charAt(0) - '0';
};

MaybeDigits -> Digit MaybeDigits : {
	$0.exist = true;
	$0.value = $2.exist ? (10 * $1.value + $2.value) : $1.value;
} | Eps : {
	$0.exist = false;
};