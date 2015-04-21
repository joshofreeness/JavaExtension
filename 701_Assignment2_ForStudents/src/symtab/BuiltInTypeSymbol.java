package symtab;

public class BuiltInTypeSymbol extends Symbol implements Type {

	public BuiltInTypeSymbol(String name, Type type) {
		super(name, type);
	}

}
