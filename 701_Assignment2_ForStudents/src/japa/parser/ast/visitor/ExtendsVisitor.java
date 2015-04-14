package japa.parser.ast.visitor;

import java.util.HashMap;
import java.util.List;

import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
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
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
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

public class ExtendsVisitor implements VoidVisitor<Object>{

	private HashMap<String, ClassOrInterfaceDeclaration> classHashMap = new HashMap<String, ClassOrInterfaceDeclaration>();
	private HashMap<String, ClassOrInterfaceType> classTypeHashMap = new HashMap<String, ClassOrInterfaceType>();
	public boolean someUsesExtendsAll = false;
	
	@Override
	public void visit(Node n, Object arg) {
		
	}
	
	public HashMap<String, ClassOrInterfaceDeclaration> getAllClasses()
	{
		return classHashMap;
	}
	
	public HashMap<String, ClassOrInterfaceType> getAllTypes()
	{
		return classTypeHashMap;
	}

	@Override
	public void visit(CompilationUnit n, Object arg) {
		 if (n.getTypes() != null) {
	        	List<TypeDeclaration> types =  n.getTypes();
	        	for (int i=0; i<types.size(); i++){
	        		types.get(i).accept(this, arg);

	        	}
		 }
	}

	@Override
	public void visit(PackageDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(ImportDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(TypeParameter n, Object arg) {
		System.out.println("hi");
		
	}

	@Override
	public void visit(LineComment n, Object arg) {
		
	}

	@Override
	public void visit(BlockComment n, Object arg) {
		
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		classHashMap.put(n.getName(), n);
		//System.out.println(n.getName());
		if (n.isExtendsMultiple()){
			someUsesExtendsAll = true;
//			List<ClassOrInterfaceType> extendsClasses =  n.getExtends();
//			int count = 0;
//			for (int i = 0; i<extendsClasses.size(); i++ ){
//				System.out.println(extendsClasses.get(i).toString());
//				//extendsClasses.get(i).accept(this, null);
//			}
		}
		
	}

	@Override
	public void visit(EnumDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(EmptyTypeDeclaration n, Object arg) {
	}

	@Override
	public void visit(EnumConstantDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(AnnotationDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(AnnotationMemberDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(FieldDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(VariableDeclarator n, Object arg) {
		
	}

	@Override
	public void visit(VariableDeclaratorId n, Object arg) {
		
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(Parameter n, Object arg) {
		
	}

	@Override
	public void visit(EmptyMemberDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(InitializerDeclaration n, Object arg) {
		
	}

	@Override
	public void visit(JavadocComment n, Object arg) {
		
	}

	@Override
	public void visit(ClassOrInterfaceType n, Object arg) {
		classTypeHashMap.put(n.getName(), n);
		
	}

	@Override
	public void visit(PrimitiveType n, Object arg) {
		
	}

	@Override
	public void visit(ReferenceType n, Object arg) {
		
	}

	@Override
	public void visit(VoidType n, Object arg) {
		
	}

	@Override
	public void visit(WildcardType n, Object arg) {
		
	}

	@Override
	public void visit(ArrayAccessExpr n, Object arg) {
		
	}

	@Override
	public void visit(ArrayCreationExpr n, Object arg) {
		
	}

	@Override
	public void visit(ArrayInitializerExpr n, Object arg) {
		
	}

	@Override
	public void visit(AssignExpr n, Object arg) {
		
	}

	@Override
	public void visit(BinaryExpr n, Object arg) {
		
	}

	@Override
	public void visit(CastExpr n, Object arg) {
		
	}

	@Override
	public void visit(ClassExpr n, Object arg) {
		
	}

	@Override
	public void visit(ConditionalExpr n, Object arg) {
		
	}

	@Override
	public void visit(EnclosedExpr n, Object arg) {
		
	}

	@Override
	public void visit(FieldAccessExpr n, Object arg) {
		
	}

	@Override
	public void visit(InstanceOfExpr n, Object arg) {
		
	}

	@Override
	public void visit(StringLiteralExpr n, Object arg) {
		
	}

	@Override
	public void visit(IntegerLiteralExpr n, Object arg) {
		
	}

	@Override
	public void visit(LongLiteralExpr n, Object arg) {
		
	}

	@Override
	public void visit(IntegerLiteralMinValueExpr n, Object arg) {
		
	}

	@Override
	public void visit(LongLiteralMinValueExpr n, Object arg) {
		
	}

	@Override
	public void visit(CharLiteralExpr n, Object arg) {
		
	}

	@Override
	public void visit(DoubleLiteralExpr n, Object arg) {
		
	}

	@Override
	public void visit(BooleanLiteralExpr n, Object arg) {
		
	}

	@Override
	public void visit(NullLiteralExpr n, Object arg) {
		
	}

	@Override
	public void visit(MethodCallExpr n, Object arg) {
		
	}

	@Override
	public void visit(NameExpr n, Object arg) {
		
	}

	@Override
	public void visit(ObjectCreationExpr n, Object arg) {
		
	}

	@Override
	public void visit(QualifiedNameExpr n, Object arg) {
		
	}

	@Override
	public void visit(SuperMemberAccessExpr n, Object arg) {
		
	}

	@Override
	public void visit(ThisExpr n, Object arg) {
		
	}

	@Override
	public void visit(SuperExpr n, Object arg) {
		
	}

	@Override
	public void visit(UnaryExpr n, Object arg) {
		
	}

	@Override
	public void visit(VariableDeclarationExpr n, Object arg) {
		
	}

	@Override
	public void visit(MarkerAnnotationExpr n, Object arg) {
		
	}

	@Override
	public void visit(SingleMemberAnnotationExpr n, Object arg) {
		
	}

	@Override
	public void visit(NormalAnnotationExpr n, Object arg) {
		
	}

	@Override
	public void visit(MemberValuePair n, Object arg) {
		
	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
		
	}

	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
	}

	@Override
	public void visit(AssertStmt n, Object arg) {
		
	}

	@Override
	public void visit(BlockStmt n, Object arg) {
		
	}

	@Override
	public void visit(LabeledStmt n, Object arg) {
		
	}

	@Override
	public void visit(EmptyStmt n, Object arg) {
		
	}

	@Override
	public void visit(ExpressionStmt n, Object arg) {
		
	}

	@Override
	public void visit(SwitchStmt n, Object arg) {
		
	}

	@Override
	public void visit(SwitchEntryStmt n, Object arg) {
		
	}

	@Override
	public void visit(BreakStmt n, Object arg) {
		
	}

	@Override
	public void visit(ReturnStmt n, Object arg) {
		
	}

	@Override
	public void visit(IfStmt n, Object arg) {
		
	}

	@Override
	public void visit(WhileStmt n, Object arg) {
		
	}

	@Override
	public void visit(ContinueStmt n, Object arg) {
		
	}

	@Override
	public void visit(DoStmt n, Object arg) {
		
	}

	@Override
	public void visit(ForeachStmt n, Object arg) {
		
	}

	@Override
	public void visit(ForStmt n, Object arg) {
		
	}

	@Override
	public void visit(ThrowStmt n, Object arg) {
		
	}

	@Override
	public void visit(SynchronizedStmt n, Object arg) {
		
	}

	@Override
	public void visit(TryStmt n, Object arg) {
		
	}

	@Override
	public void visit(CatchClause n, Object arg) {
		
	}

}
