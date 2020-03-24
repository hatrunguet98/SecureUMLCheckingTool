/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.model;

/**
 * @author Ha Trung
 */
public class PolicyRuler {

    private String name;
    private String description;
    private String target;
    private String condition;
    private String resource;
    private String role;
    private String action;

    public PolicyRuler() {
    }

    public PolicyRuler(String role, String action, String resource, String condition) {
        if (condition != null && !condition.isEmpty()) {
            this.condition = condition;
        } else {
            this.condition = "True";
        }
        this.resource = resource;
        this.role = role;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        if (condition != null && !condition.isEmpty()) {
            this.condition = condition;
        } else {
            this.condition = "True";
        }
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
