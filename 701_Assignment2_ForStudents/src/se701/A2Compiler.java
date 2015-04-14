package se701;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.ExtendsVisitor;
import japa.parser.ast.visitor.SillyBreakVisitor;
import japa.parser.ast.visitor.DumpVisitor;

public class A2Compiler {
	
	/*
	 * This is the only method you should need to change inside this class. But do not modify the signature of the method! 
	 */
	public static void compile(File file) throws ParseException, FileNotFoundException {

		// parse the input, performs lexical and syntatic analysis
		JavaParser parser = new JavaParser(new FileReader(file));
		CompilationUnit ast = parser.CompilationUnit();
		
		// perform visit 1...
//		SillyBreakVisitor semanticsVisitor = new SillyBreakVisitor();
//		ast.accept(semanticsVisitor, null);
		
		// perform visit 2... etc etc 
		// ...
		
		//perform visit in order to know whether and where extendAll is being appropriately used.
		ExtendsVisitor extensionVisitor = new ExtendsVisitor();
		ast.accept(extensionVisitor, null);
		
		HashMap<String, ClassOrInterfaceDeclaration> classMap = extensionVisitor.getAllClasses();
		HashMap<String, ClassOrInterfaceType> typeMap = extensionVisitor.getAllTypes();
		
		if (extensionVisitor.someUsesExtendsAll){
			AnalyseInheritance(classMap);
			ChangeInheritanceToReal(classMap, typeMap);
		}
		
		
		
		// perform visit N 
		DumpVisitor printVisitor = new DumpVisitor();
		ast.accept(printVisitor, null);
		
		String result = printVisitor.getSource();
		
		// save the result into a *.java file, same level as the original file
		File javaFile = getAsJavaFile(file);
		writeToFile(javaFile, result);
	}
	
	private static void ChangeInheritanceToReal(HashMap<String, ClassOrInterfaceDeclaration> map, HashMap<String, ClassOrInterfaceType> typeMap){
		Collection<ClassOrInterfaceDeclaration> classes = map.values();
		
		ClassOrInterfaceDeclaration upmostClass = null;
	
		List<ClassOrInterfaceDeclaration> classesInHierachy = new ArrayList<ClassOrInterfaceDeclaration>(); //Classes that the extendsAll class extends from
		
		//If any of the classes extends another class make that the highest class in the structure
		for (ClassOrInterfaceDeclaration c : classes){
			if (c.isExtendsMultiple()){
				List<ClassOrInterfaceType> extendsList = c.getExtends();
				for (ClassOrInterfaceType COrI : extendsList){ //for every class that it extends
					ClassOrInterfaceDeclaration classDecleration = map.get(COrI.getName()); //Get the useful object
					classesInHierachy.add(classDecleration);
				}
			}
		}
		
		//we know only one class will implement something because of AnalyseInheritance method
		for (ClassOrInterfaceDeclaration c : classesInHierachy){
			if (c.getExtends() != null){
				if(c.getExtends().size()>0){
					upmostClass = c;
				}
			}
		}
		
		System.out.println(upmostClass.getName());
		
		classesInHierachy.remove(upmostClass); //remove so not duplicated in list
		
		for (ClassOrInterfaceDeclaration c : classesInHierachy){
			System.out.println(c.getName());
		}
		
		System.out.println(typeMap);
		
		for (int i = 0; i<classesInHierachy.size(); i++){
			if (i == 0){
				List<ClassOrInterfaceType> extendsList = classesInHierachy.get(i).getExtends();
				extendsList.add(typeMap.get(upmostClass.getName()));
				
			}
		}
		
		
		
		
		
		
		
		
	}
	
	/*
	 *Analysis if there are any errors in the use of multiple inheritance. Only to the extent of if it is possible to correctly make a class that
	 *extends what the developer wants it to extend 
	 */
	private static void AnalyseInheritance(HashMap<String, ClassOrInterfaceDeclaration> map){
		Collection<ClassOrInterfaceDeclaration> classes = map.values();
		
		//For all the classes in the file
		for (ClassOrInterfaceDeclaration c : classes){
			if (c.isExtendsMultiple()){ //If the class has an extends all
				int counter = 0;
				String justInCase = ""; //In case I need to throw a meaningful exception
				List<ClassOrInterfaceType> extendsList = c.getExtends();
				for (ClassOrInterfaceType COrI : extendsList){ //for every class that it extends
					ClassOrInterfaceDeclaration classDecleration = map.get(COrI.getName()); //Get the useful object
					if (classDecleration.getExtends() != null){
						counter += classDecleration.getExtends().size();
						justInCase += classDecleration.getBeginLine() + " ";
					}
				}
				if (counter > 1){
					String message = "Only one class can extend other classes when using extendAll, concider changing inheritance on lines: ";
					message += justInCase;

					throw new A2SemanticsException(message);
				}
				
			}
		}
		
	}
	
	/*
	 * Given a *.javax File, this method returns a *.java File at the same directory location  
	 */
	private static File getAsJavaFile(File javaxFile) {
		String javaxFileName = javaxFile.getName();
		File containingDirectory = javaxFile.getAbsoluteFile().getParentFile();
		String path = containingDirectory.getAbsolutePath()+System.getProperty("file.separator");
		String javaFilePath = path + javaxFileName.substring(0,javaxFileName.lastIndexOf("."))+".java";
		return new File(javaFilePath);
	}
	
	/*
	 * Given the specified file, writes the contents into it.
	 */
	private static void writeToFile(File file, String contents) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(file);
		writer.print(contents);
		writer.close();
	}
}
