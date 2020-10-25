package com.uet.toolCheckPolicyAbac.abac.service;

import com.uet.toolCheckPolicyAbac.abac.model.PolicyRuler;
import com.uet.toolCheckPolicyAbac.abac.model.description.Condition;
import com.uet.toolCheckPolicyAbac.abac.model.description.Policy;
import com.uet.toolCheckPolicyAbac.abac.model.description.Rule;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ha Trung
 */
public class SourceCodeService {

    private static final Pattern getTargetElement = Pattern.compile("[^&]+");
    private static final Pattern getValueApi = Pattern.compile("[^/]+");
    private static final Pattern getValueRole = Pattern.compile("[^']+");
    private static final Pattern getValueCondition = Pattern.compile("[^\"]+");

    public Policy convertRuleAppToPolicy(List<String> policyFilePathList) {
        List<PolicyRuler> policyRulers = this.getPolicyRulers(policyFilePathList);
        List<Rule> ruleList = new ArrayList<>();
        for (PolicyRuler policyRuler : policyRulers) {
            Rule rule = new Rule();
            rule.setRole(policyRuler.getRole());
            rule.setAction(policyRuler.getAction());
            rule.setResource(policyRuler.getResource());
            this.setConditionRule(rule, policyRuler.getCondition());
            ruleList.add(rule);
        }
        return new Policy(ruleList);
    }

    private void setConditionRule(Rule rule, String conditionApp) {
        List<String> restriction = new ArrayList<>();
        Matcher conditionElement = getTargetElement.matcher(conditionApp);
        while (conditionElement.find()) {
            restriction.add(conditionElement.group());
        }
        rule.setCondition(new Condition(restriction));
    }

    public List<PolicyRuler> getPolicyRulers(List<String> policyFilePathList) {
        try {
            List<PolicyRuler> policyRulerList = new ArrayList<>();
            FileService fileService = new FileService();
            JDTParserService jDTParserService = new JDTParserService();
            String policyContents = fileService.getContentFiles(policyFilePathList);
            List<MethodDeclaration> methodDeclarationList = jDTParserService.getMethodDeclaration(policyContents);
            for (MethodDeclaration methodDeclaration : methodDeclarationList) {
                String api = jDTParserService.getAnnotationValue(methodDeclaration, "Mapping").replace("\")", "/\")");
                String per = jDTParserService.getAnnotationValue(methodDeclaration, "PreAuthorize");
                String condition = jDTParserService.getStatementValue(methodDeclaration, "checkPermission");
                String resource = this.getValueAnnotaition(api, getValueApi, 1);
                String conditionValue = this.getValueAnnotaition(condition, getValueCondition, 1);
                String action = this.getValueAnnotaition(api, getValueApi, 2);
                String role = "ROLE_" + this.getValueAnnotaition(per, getValueRole, 1);
                if (resource != null && action != null && role != null) {
                    PolicyRuler policyRuler = new PolicyRuler(role, action, resource, conditionValue.replaceAll("&&", "&"));
                    policyRulerList.add(policyRuler);
                    System.out.println(policyRuler.toString());
                }
            }
            return policyRulerList;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Can not set policy rule.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private String getValueAnnotaition(String content, Pattern regex, Integer index) {
        if (content != null && !content.isEmpty()) {
            List<String> annotationValue = new ArrayList<>();
            Matcher annotationValueElement = regex.matcher(content);
            while (annotationValueElement.find()) {
                annotationValue.add(annotationValueElement.group());
            }
            return annotationValue.get(index).replaceAll("\\s+", "");
        }
        return "";
    }
}
