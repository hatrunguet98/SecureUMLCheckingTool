package com.uet.toolCheckPolicyAbac.abac.model.JDT;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {

    private List<MethodDeclaration> methods;

    public MethodVisitor() {
        this.methods = new ArrayList<MethodDeclaration>();
    }

    /**
     * visit - this overrides the ASTVisitor's visit and allows this
     * class to visit MethodDeclaration nodes in the AST.
     */
    @Override
    public boolean visit(MethodDeclaration node) {
        this.methods.add(node);
        return super.visit(node);
    }

    /**
     * getMethods - this is an accessor methods to get the methods
     * visited by this class.
     * @return List<MethodDeclaration>
     */
    public List<MethodDeclaration> getMethods() {
        return this.methods;
    }
}
