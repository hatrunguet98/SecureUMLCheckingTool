/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.service;

import java.util.List;

import com.uet.toolCheckPolicyAbac.abac.model.JDT.MethodVisitor;
import org.eclipse.jdt.core.dom.*;

/**
 * @author Ha Trung
 */
public class JDTParserService {
    public List<MethodDeclaration> getMethodDeclaration(String str) {
        MethodVisitor methodVisitor = new MethodVisitor();
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(str.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        cu.accept(methodVisitor);
        return methodVisitor.getMethods();
    }

    public String getAnnotationValue(MethodDeclaration methodDeclaration, String typeName) {
        List modifiers = methodDeclaration.modifiers();
        for (Object mod : modifiers) {
            IExtendedModifier modifier = (IExtendedModifier) mod;
            if (modifier.isAnnotation()) {
                Annotation annotation = (Annotation) modifier;
                if (annotation.getTypeName().toString().contains(typeName)) {
                    return annotation.toString();
                }
            }
        }
        return "";
    }

    public String getStatementValue(MethodDeclaration methodDeclaration, String nameExpression) {
        Block block = methodDeclaration.getBody();
        List statements = block.statements();
        for (Object state : statements) {
            if (state instanceof ExpressionStatement) {
                ExpressionStatement statement = (ExpressionStatement) state;
                Expression expression = statement.getExpression();
                if (expression.toString().contains(nameExpression)) {
                    return expression.toString();
                }
            }
        }
        return "";
    }
}
