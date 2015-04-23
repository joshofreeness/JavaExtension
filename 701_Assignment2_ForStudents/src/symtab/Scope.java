package symtab;

import java.util.Map;

public interface Scope {
	
	public String getScopeName();
	public Scope getEnclosingScope();
	public void define(Symbol symbol);
	public Symbol resolve(String name);
	public Map<String, Symbol> getSymbolTable();

}
