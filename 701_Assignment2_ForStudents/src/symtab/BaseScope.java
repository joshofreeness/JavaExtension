package symtab;

import java.util.HashMap;
import java.util.Map;

public class BaseScope implements Scope{

	private String scopeName;
	private Scope enclosingScope;
	
	private Map<String,Symbol> symbols = new HashMap<String,Symbol>();
	
	
	
	@Override
	public String getScopeName() {
		return scopeName;
	}

	@Override
	public Scope getEnclosingScope() {
		return enclosingScope;
	}

	@Override
	public void define(Symbol symbol) {
		if (symbols.containsKey(symbol.getName())){
			throw new JavaxSemanticsException("The Symbol " + symbol.getName() +" is already defined in the current scope" );
		}
		symbols.put(symbol.getName(), symbol);
		
	}

	@Override
	public Symbol resolve(String name) {
		if (!symbols.containsKey(name)){
			throw new JavaxSemanticsException("The Symbol " + name +" is not defined in the current scope" );
		}
		return symbols.get(name);
	}

}
