package symtab;

public class ClassSymbol extends ScopedSymbol implements Type{

	public ClassSymbol(String name, Scope enclosingScope) {
		super(name, enclosingScope);
	}

}
