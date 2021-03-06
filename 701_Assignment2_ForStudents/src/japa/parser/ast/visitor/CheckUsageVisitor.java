package japa.parser.ast.visitor;

import java.util.Iterator;
import java.util.List;

import se701.A2SemanticsException;
import symtab.BuiltInTypeSymbol;
import symtab.GlobalScope;
import symtab.Scope;
import symtab.Symbol;
import symtab.Type;
import symtab.VariableSymbol;
import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LiteralExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.SuperMemberAccessExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;

public class CheckUsageVisitor implements VoidVisitor<Object> {
	

	
    public void visit(Node n, Object arg) {
        throw new IllegalStateException(n.getClass().getName());
    }

    public void visit(CompilationUnit n, Object arg) {
        if (n.getPakage() != null) {
            n.getPakage().accept(this, arg);
        }
        if (n.getImports() != null) {
            for (ImportDeclaration i : n.getImports()) {
                i.accept(this, arg);
            }
        }
        if (n.getTypes() != null) {
        	List<TypeDeclaration> types =  n.getTypes();
        	for (int i=0; i<types.size(); i++){
        		types.get(i).accept(this, arg);
                
        	}
        	
        }
    }

    public void visit(PackageDeclaration n, Object arg) {
        n.getName().accept(this, arg);
    }

    public void visit(NameExpr n, Object arg) {
    }

    public void visit(QualifiedNameExpr n, Object arg) {
        n.getQualifier().accept(this, arg);
    }

    public void visit(ImportDeclaration n, Object arg) {
        n.getName().accept(this, arg);
    }

    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
    	
    	
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        

        if (n.getExtends() != null) {
            for (Iterator<ClassOrInterfaceType> i = n.getExtends().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }
        }

        if (n.getImplements() != null) {
            for (Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }
        }
        
        if (n.getMembers() != null) {
            for( BodyDeclaration b: n.getMembers()){
         	   b.accept(this, null);
            }
         }
    }

    public void visit(EmptyTypeDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
    }

    public void visit(JavadocComment n, Object arg) {
    }

    public void visit(ClassOrInterfaceType n, Object arg) {
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
        }
    }

    public void visit(TypeParameter n, Object arg) {
        if (n.getTypeBound() != null) {
            for (Iterator<ClassOrInterfaceType> i = n.getTypeBound().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }
        }
    }

    public void visit(PrimitiveType n, Object arg) {
    }

    public void visit(ReferenceType n, Object arg) {
        n.getType().accept(this, arg);
    }

    public void visit(WildcardType n, Object arg) {
        if (n.getExtends() != null) {
            n.getExtends().accept(this, arg);
        }
        if (n.getSuper() != null) {
            n.getSuper().accept(this, arg);
        }
    }

    public void visit(FieldDeclaration n, Object arg) {
    	
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        n.getType().accept(this, arg);
       
        for (Iterator<VariableDeclarator> i = n.getVariables().iterator(); i.hasNext();) {
            VariableDeclarator var = i.next();
            var.accept(this, n.getType());
        }

    }

    public void visit(VariableDeclarator n, Object arg) {
    	
    	Scope scope = n.getScopeIn();

    	Symbol leftType = scope.resolve(((japa.parser.ast.type.Type)arg).toString());
    	String rightType= null;
    	if ( n.getInit() == null ){
    		
    	}else {
	    	Expression e = n.getInit();
	    	//e.setScope(scope);
	    	
	    	rightType= findRecusiveType(n.getInit());
	        //appropriateTypes(leftType, rightType, n);
	    	if (leftType.getType() == null){
	    		appropriateTypes(leftType.getName(), rightType, n.getInit());
	    	}else{
	    		appropriateTypes(leftType.getType().getName(), rightType, n.getInit());
	    	}
	    	
	        n.getId().accept(this, arg);
    	}
    	
    	
    	
        if (n.getInit() != null) {
            n.getInit().accept(this, arg);
        }
    }

    public void visit(VariableDeclaratorId n, Object arg) {
    }

    public void visit(ArrayInitializerExpr n, Object arg) {
        if (n.getValues() != null) {
            for (Iterator<Expression> i = n.getValues().iterator(); i.hasNext();) {
                Expression expr = i.next();
                expr.accept(this, arg);
            }
        }
    }

    public void visit(VoidType n, Object arg) {
    }

    public void visit(ArrayAccessExpr n, Object arg) {
        n.getName().accept(this, arg);
        n.getIndex().accept(this, arg);
    }

    public void visit(ArrayCreationExpr n, Object arg) {
        n.getType().accept(this, arg);

        if (n.getDimensions() != null) {
            for (Expression dim : n.getDimensions()) {
                dim.accept(this, arg);
            }
        } else {
            n.getInitializer().accept(this, arg);
        }
    }

    public void visit(AssignExpr n, Object arg) {
    	
    	Scope current = n.getScopeIn();
    	//current.resolve(n.getTarget().toString());
    	
    	String leftType = findRecusiveType(n.getTarget());
        String rightType = findRecusiveType(n.getValue());
        appropriateTypes(leftType, rightType, n);
        n.getTarget().accept(this, arg);
        n.getValue().accept(this, arg);
    }
    
    public void appropriateTypes(String left, String right, Expression n){
    	
    	if (left.equals("float")){
    		if (right.equals("double") || right.equals("long") || right.equals("int")){
    		  return;
    		} else {
    			throw new A2SemanticsException("Cannot assign type "+ right + " to type "+left +" on line "+ n.getBeginLine() );
    		}
    	}
    	
    	if (left.equals("double")){
    		if (right.equals("double") || right.equals("long") || right.equals("int")){
      		  return;
      		} else {
      			throw new A2SemanticsException("Cannot assign type "+ right + " to type "+left +" on line "+ n.getBeginLine() );
      		}
    	}
    	
    	
    	if (left.equals("long")){
    		if (right.equals("double") || right.equals("long") || right.equals("int")){
      		  return;
      		} else {
      			throw new A2SemanticsException("Cannot assign type "+ right + " to type "+left +" on line "+ n.getBeginLine() );
      		}
    	}
    	
    	if (left.equals("byte")){
    		if (right.equals("int")){
      		  return;
      		} else {
      			throw new A2SemanticsException("Cannot assign type "+ right + " to type "+left +" on line "+ n.getBeginLine() );
      		}
    	}
    	
    	if (left.equals("short")){
    		if (right.equals("int")){
      		  return;
      		} else {
      			throw new A2SemanticsException("Cannot assign type "+ right + " to type "+left +" on line "+ n.getBeginLine() );
      		}
    	}
    	
    	if (left.equals(right)){
    		return;
    	}
    	
    	throw new A2SemanticsException("Cannot assign type "+ right + " to type "+left +" on line "+ n.getBeginLine() );
    }
    
    
    public String findRecusiveType(Expression topExpression){
    	Scope aboveScope = topExpression.getScopeIn();
    	//Expression target = aboveExpression.getTarget();
    	//Symbol targetSymbol = aboveScope.resolve(target.toString());
    	//Type targetType = targetSymbol.getType();
    	
    	String resolvedType = "";
    	
    	
    	//Literal expressions
    	if (topExpression instanceof LiteralExpr){
    		resolvedType = checkLiteralExpressions(topExpression);
    	} else{
    		if (topExpression instanceof NameExpr ){
    			//handle nameexpr statement
    			NameExpr e = (NameExpr) topExpression;
    			String eName = e.getName();
    			Symbol symb = aboveScope.resolve(eName);
    			return symb.getType().getName();
    		} else if (topExpression instanceof FieldAccessExpr || topExpression instanceof MethodCallExpr) {
    			//Traverse to the bottom of the expression tree
    			Expression currentExpression = topExpression;
    			
    			while(!(currentExpression instanceof NameExpr) ){
    				Expression childExpression = getChildExpression(currentExpression);
    				childExpression.setParentExpression(currentExpression);
    				currentExpression = childExpression;
    			}
    			
    			Scope currentScope = topExpression.getScopeIn();
    			Type currentType = null;
    			//  traverse back up the tree
    			while (currentExpression != null){
    				//traverse back up the tree
    				
    				String name = getStringName(currentExpression);
    				
    				Symbol symb = currentScope.resolve(name);
    				currentType = symb.getType();
    				
    			
    				
    				if (currentType instanceof Scope){
    					Scope newScope = (Scope)currentType;
    					currentScope = newScope;
    					currentExpression = currentExpression.getParentExpression();
    				} else {
    					currentExpression = null;
    				}
    			}
    			
    			
    			resolvedType = currentType.getName();
    			
    		} else{
    			//if (topExpression)
    			if (topExpression instanceof ObjectCreationExpr){
    				ObjectCreationExpr temp = (ObjectCreationExpr) topExpression;
    			    //System.out.println();
    			    resolvedType = temp.getType().getName();
    			}
    		}
    	}
    	return resolvedType;
    }
    
    public String getStringName(Expression e){
    	String name;
    	if (e instanceof FieldAccessExpr){
    		name = ((FieldAccessExpr) e).getField();
    	}
    	else if (e instanceof MethodCallExpr){
    		name = ((MethodCallExpr) e).getName();
    	}
    	else{
    		name = ((NameExpr) e).getName();
    	}
    	return name;
    	
    }
    
    /*Store for later
     * try{
    				System.out.println(((FieldAccessExpr)currentExpression).getField());
    				} catch(Exception e){}
    				try{
    				System.out.println(((MethodCallExpr)currentExpression).getName());
    				} catch(Exception e){}
    				try{
    				System.out.println(((NameExpr)currentExpression).getName());
    				} catch(Exception e){}
     * */
    
    public Expression getChildExpression(Expression e){
		Expression childExpression;
		
		if (e instanceof FieldAccessExpr){
			FieldAccessExpr expr = (FieldAccessExpr) e;
			childExpression = expr.getScope();
		} else {
			MethodCallExpr expr = (MethodCallExpr) e;
			childExpression = expr.getScope();
		}
		
		
    	return childExpression;
    	
    }
    
    public String checkLiteralExpressions(Expression topExpression){
    	if (topExpression instanceof IntegerLiteralExpr || topExpression instanceof IntegerLiteralMinValueExpr){
				return "int";	
		}
		
		if (topExpression instanceof LongLiteralExpr ||  topExpression instanceof LongLiteralMinValueExpr){
			return "long";
    	}
		
		if (topExpression instanceof BooleanLiteralExpr){
				return "boolean";
		}
		
		if (topExpression instanceof DoubleLiteralExpr){
				return "double";	
		}
		
		if (topExpression instanceof CharLiteralExpr){
				return "char";
		}
		
		if (topExpression instanceof StringLiteralExpr){
				return "String";
		}
		return "";
    }

    public void visit(BinaryExpr n, Object arg) {
        n.getLeft().accept(this, arg);
        n.getRight().accept(this, arg);
    }

    public void visit(CastExpr n, Object arg) {
        n.getType().accept(this, arg);
        n.getExpr().accept(this, arg);
    }

    public void visit(ClassExpr n, Object arg) {
        n.getType().accept(this, arg);
    }

    public void visit(ConditionalExpr n, Object arg) {
        n.getCondition().accept(this, arg);
        n.getThenExpr().accept(this, arg);
        n.getElseExpr().accept(this, arg);
    }

    public void visit(EnclosedExpr n, Object arg) {
        n.getInner().accept(this, arg);
    }

    public void visit(FieldAccessExpr n, Object arg) {
        n.getScope().accept(this, arg);
    }

    public void visit(InstanceOfExpr n, Object arg) {
        n.getExpr().accept(this, arg);
        n.getType().accept(this, arg);
    }

    public void visit(CharLiteralExpr n, Object arg) {
    }

    public void visit(DoubleLiteralExpr n, Object arg) {
    }

    public void visit(IntegerLiteralExpr n, Object arg) {
    }

    public void visit(LongLiteralExpr n, Object arg) {
    }

    public void visit(IntegerLiteralMinValueExpr n, Object arg) {
    }

    public void visit(LongLiteralMinValueExpr n, Object arg) {
    }

    public void visit(StringLiteralExpr n, Object arg) {
    }

    public void visit(BooleanLiteralExpr n, Object arg) {
    }

    public void visit(NullLiteralExpr n, Object arg) {
    }

    public void visit(ThisExpr n, Object arg) {
        if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
        }
    }

    public void visit(SuperExpr n, Object arg) {
        if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
        }
    }

    public void visit(MethodCallExpr n, Object arg) {
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
        }
        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
    }

    public void visit(ObjectCreationExpr n, Object arg) {
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
        }


        n.getType().accept(this, arg);

        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }

    }

    public void visit(SuperMemberAccessExpr n, Object arg) {
    }

    public void visit(UnaryExpr n, Object arg) {

        n.getExpr().accept(this, arg);

    }

    public void visit(ConstructorDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        if (n.getParameters() != null) {
            for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
            }
        }

        if (n.getThrows() != null) {
            for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
            }
        }
        n.getBlock().accept(this, arg);
    }

    public void visit(MethodDeclaration n, Object arg) {
    	
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }

        n.getType().accept(this, arg);
        if (n.getParameters() != null) {
            for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
            }
        }


        if (n.getThrows() != null) {
            for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
            }
        }
        if (n.getBody() == null) {
        } else {
            n.getBody().accept(this, arg);
        }
    }

    public void visit(Parameter n, Object arg) {

        n.getType().accept(this, arg);
        n.getId().accept(this, arg);
    }

    public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
        if (n.isThis()) {
        } else {
            if (n.getExpr() != null) {
                n.getExpr().accept(this, arg);
            }
        }
        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
    }

    public void visit(TypeDeclarationStmt n, Object arg) {
        n.getTypeDeclaration().accept(this, arg);
    }

    public void visit(AssertStmt n, Object arg) {
        n.getCheck().accept(this, arg);
        if (n.getMessage() != null) {
            n.getMessage().accept(this, arg);
        }
    }

    public void visit(BlockStmt n, Object arg) {
    	
        if (n.getStmts() != null) {
            for (Statement s : n.getStmts()) {
                s.accept(this, arg);
            }
        }

    }

    public void visit(LabeledStmt n, Object arg) {
        n.getStmt().accept(this, arg);
    }

    public void visit(EmptyStmt n, Object arg) {
    }

    public void visit(ExpressionStmt n, Object arg) {
        n.getExpression().accept(this, arg);
    }

    public void visit(SwitchStmt n, Object arg) {
        n.getSelector().accept(this, arg);
        if (n.getEntries() != null) {
            for (SwitchEntryStmt e : n.getEntries()) {
                e.accept(this, arg);
            }
        }

    }

    public void visit(SwitchEntryStmt n, Object arg) {
        if (n.getLabel() != null) {
            n.getLabel().accept(this, arg);
        } else {
        }
        if (n.getStmts() != null) {
            for (Statement s : n.getStmts()) {
                s.accept(this, arg);
            }
        }
    }

    public void visit(BreakStmt n, Object arg) {
    }

    public void visit(ReturnStmt n, Object arg) {
        if (n.getExpr() != null) {
            n.getExpr().accept(this, arg);
        }
    }

    public void visit(EnumDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }

        if (n.getImplements() != null) {
            for (Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }
        }

        if (n.getEntries() != null) {
            for (Iterator<EnumConstantDeclaration> i = n.getEntries().iterator(); i.hasNext();) {
                EnumConstantDeclaration e = i.next();
                e.accept(this, arg);
            }
        }
    }

    public void visit(EnumConstantDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }

        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }

    }

    public void visit(EmptyMemberDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
    }

    public void visit(InitializerDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        n.getBlock().accept(this, arg);
    }

    public void visit(IfStmt n, Object arg) {
        n.getCondition().accept(this, arg);
        n.getThenStmt().accept(this, arg);
        if (n.getElseStmt() != null) {
            n.getElseStmt().accept(this, arg);
        }
    }

    public void visit(WhileStmt n, Object arg) {
        n.getCondition().accept(this, arg);
        n.getBody().accept(this, arg);
    }

    public void visit(ContinueStmt n, Object arg) {
    }

    public void visit(DoStmt n, Object arg) {
        n.getBody().accept(this, arg);
        n.getCondition().accept(this, arg);
    }

    public void visit(ForeachStmt n, Object arg) {
        n.getVariable().accept(this, arg);
        n.getIterable().accept(this, arg);
        n.getBody().accept(this, arg);
    }

    public void visit(ForStmt n, Object arg) {
        if (n.getInit() != null) {
            for (Iterator<Expression> i = n.getInit().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
        if (n.getCompare() != null) {
            n.getCompare().accept(this, arg);
        }
        if (n.getUpdate() != null) {
            for (Iterator<Expression> i = n.getUpdate().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
        n.getBody().accept(this, arg);
    }

    public void visit(ThrowStmt n, Object arg) {
        n.getExpr().accept(this, arg);
    }

    public void visit(SynchronizedStmt n, Object arg) {
        n.getExpr().accept(this, arg);
        n.getBlock().accept(this, arg);
    }

    public void visit(TryStmt n, Object arg) {
        n.getTryBlock().accept(this, arg);
        if (n.getCatchs() != null) {
            for (CatchClause c : n.getCatchs()) {
                c.accept(this, arg);
            }
        }
        if (n.getFinallyBlock() != null) {
            n.getFinallyBlock().accept(this, arg);
        }
    }

    public void visit(CatchClause n, Object arg) {
        n.getExcept().accept(this, arg);
        n.getCatchBlock().accept(this, arg);

    }

    public void visit(AnnotationDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
    }

    public void visit(AnnotationMemberDeclaration n, Object arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }

        n.getType().accept(this, arg);
        if (n.getDefaultValue() != null) {
            n.getDefaultValue().accept(this, arg);
        }
    }

    public void visit(MarkerAnnotationExpr n, Object arg) {
        n.getName().accept(this, arg);
    }

    public void visit(SingleMemberAnnotationExpr n, Object arg) {
        n.getName().accept(this, arg);
        n.getMemberValue().accept(this, arg);
    }

    public void visit(NormalAnnotationExpr n, Object arg) {
        n.getName().accept(this, arg);
        for (Iterator<MemberValuePair> i = n.getPairs().iterator(); i.hasNext();) {
            MemberValuePair m = i.next();
            m.accept(this, arg);
        }
    }

    public void visit(MemberValuePair n, Object arg) {
        n.getValue().accept(this, arg);
    }

    public void visit(LineComment n, Object arg) {
    }

    public void visit(BlockComment n, Object arg) {
    }

    public void visit(VariableDeclarationExpr n, Object arg) {

        n.getType().accept(this, arg);

        for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i.hasNext();) {
            VariableDeclarator v = i.next();
            //System.out.println(n.getScopeIn());
            v.accept(this, n.getType());
        }
    }
}
