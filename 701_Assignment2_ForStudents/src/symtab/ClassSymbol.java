package symtab;


public class ClassSymbol extends ScopedSymbol implements Type {

	public ClassSymbol(String name, Type type, Scope enclosingScope) {
		super(name, type, enclosingScope);
	}

}

