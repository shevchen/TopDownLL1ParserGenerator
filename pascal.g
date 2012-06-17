{
	import java.util.TreeSet;
	import java.util.Set;
}


$ S { Set<String> vars; }
$ E { Set<String> vars; }
$ F { Set<String> vars; String type; }
$ A { Set<String> vars; }
$ Word { String name; }
$ Alphanum { char c; }
$ Letter { char c; }
$ MaybeAlphanums { String s; }


-> S;


S ->{ $0.vars = new TreeSet<String>(); }
'v' 'a' 'r' F{ $4.vars = $0.vars; } ';' E{$6.vars = $0.vars; } : {
	for (String s : $4.vars)
		$0.vars.add("'" + s + "' of type '" + $4.type + "'");
	System.out.println("Variables defined:\n");
	for (String s : $0.vars)
		System.out.println(s);
};

E -> F ";" E{ $3.vars = $0.vars; } : {
	for (String s : $1.vars)
		$0.vars.add("'" + s + "' of type '" + $1.type + "'");
} | Eps ;


F ->{ $0.vars = new TreeSet<String>(); } Word A{ $2.vars = $0.vars; } ':' Word : {
	$0.vars.add($1.name);
	$0.type = $4.name;
};

A -> ',' Word A{ $3.vars = $0.vars; } : {
	$0.vars.add($2.name);
} | Eps ;

Word -> Letter MaybeAlphanums : {
	$0.name = $1.c + $2.s;
};

Letter -> ['a'-'z'] : {
	$0.c = $1.c;
} | ['A'-'Z'] : {
	$0.c = $1.c;
};

Alphanum -> Letter : {
	$0.c = $1.c;
} | ['0'-'9'] : {
	$0.c = $1.c;
} | '_' : {
	$0.c = $1.c;
};

MaybeAlphanums -> Alphanum MaybeAlphanums : {
	$0.s = $1.c + $2.s;
} | Eps : {
	$0.s = "";
};