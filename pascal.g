{
	import java.util.TreeSet;
	import java.util.Set;
	
	import util.Pair;
}

$ S { Set<String> vars = new TreeSet<String>(); }
$ E { Set<String> vars; }
$ F { Set<String> vars; }
$ A { Set<String> vars; }
$ Word { String name; }
$ Alphanum { char c; }
$ Letter { char c; }
$ MaybeAlphanum { String s; }

-> S;

S -> 'v' 'a' 'r' F{ $4.vars = $0.vars; } ';' E {$6.vars = $0.vars; } : {
	System.out.println("Variables:\n" + $0.vars);
};
E -> F{ $1.vars = $0.vars; } ";" E{ $1.vars = $0.vars; } | Eps ;
F -> Word A{ $2.vars = $0.vars; } ':' Word ;
A -> ',' Word A{ $3.vars = $0.vars; } | Eps ;
Word -> Letter MaybeAlphanums ;
Letter -> ['a'-'z'] | ['A'-'Z'];
Alphanum -> Letter | ['0'-'9'] | '_';
MaybeAlphanums -> Alphanum MaybeAlphanums : {
	$0.s = $1.c + $2.s;
} | Eps : {
	$0.s = "";
};