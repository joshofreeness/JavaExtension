/*
 * Copyright (C) 2007 J�lio Vilmar Gesser.
 * 
 * This file is part of Java 1.5 parser and Abstract Syntax Tree.
 *
 * Java 1.5 parser and Abstract Syntax Tree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java 1.5 parser and Abstract Syntax Tree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java 1.5 parser and Abstract Syntax Tree.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 05/10/2006
 */
package japa.parser.ast;

import java.util.Iterator;

import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class ImportDeclaration extends Node {

    private final NameExpr name;

    private final boolean isStatic;

    private final boolean isAsterisk;

    public ImportDeclaration(int line, int column, NameExpr name, boolean isStatic, boolean isAsterisk) {
        super(line, column);
        this.name = name;
        this.isStatic = isStatic;
        this.isAsterisk = isAsterisk;
    }

    public NameExpr getName() {
        return name;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isAsterisk() {
        return isAsterisk;
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

//	public void visit(VariableDeclarationExpr n, Object arg) {
//	    printAnnotations(n.getAnnotations(), arg);
//	    printModifiers(n.getModifiers());
//	
//	    n.getType().accept(this, arg);
//	    printer.print(" ");
//	
//	    for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i.hasNext();) {
//	        VariableDeclarator v = i.next();
//	        v.accept(this, arg);
//	        if (i.hasNext()) {
//	            printer.print(", ");
//	        }
//	    }
//	}

}
