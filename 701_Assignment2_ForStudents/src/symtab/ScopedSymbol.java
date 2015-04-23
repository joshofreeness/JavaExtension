package symtab;

import java.util.HashMap;
import java.util.Map;

public class ScopedSymbol extends Symbol implements Scope{

	private HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
	protected Scope enclosingScope = null;
	
	public ScopedSymbol(String name, Type type, Scope enclosingScope) {
		super(name, type);
		this.enclosingScope = enclosingScope;
	}
	
	public Map<String,Symbol> getSymbolTable(){
		return symbols;
	}
	
	@Override
	public String getScopeName() {
		return name;
	}
	
	@Override
	public Scope getEnclosingScope() {
		return enclosingScope;
	}
	
	@Override
	public void define(Symbol symbol) {
		String name = symbol.getName();
		if (symbols.get(name) != null) {
			throw new JavaxSemanticsException("\""+name+"\" is already defined in scope "+getScopeName());
		}
		symbols.put(name, symbol);
	}
	
	@Override
	public Symbol resolve(String name) {
		// if the symbol exists in the current scope, return it
		Symbol s = symbols.get(name);
		if (s != null)
			return s;
		
		// otherwise look in the enclosing scope, if there is one
		if (enclosingScope != null)
			return enclosingScope.resolve(name);
		
		// otherwise it doesn't exist
		return null;
	}

}
