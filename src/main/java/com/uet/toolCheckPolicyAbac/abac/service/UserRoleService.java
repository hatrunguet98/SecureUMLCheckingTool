package com.uet.toolCheckPolicyAbac.abac.service;

import com.uet.toolCheckPolicyAbac.abac.model.ResultCheckPolicy;
import com.uet.toolCheckPolicyAbac.abac.model.RolePermisson;
import com.uet.toolCheckPolicyAbac.abac.model.description.Rule;

import java.util.ArrayList;
import java.util.List;

public class UserRoleService {

    public ResultCheckPolicy checkRolePermission(ArrayList<RolePermisson> rolePermissionsDb, ArrayList<RolePermisson> rolePermissionsCsv ){
        this.compareTowRolePermission(rolePermissionsDb, rolePermissionsCsv);
        String errorMessage = "Application system ensures availability .";
        Boolean isSuBoolean = true;
        if (rolePermissionsCsv.size() > 0) {
            errorMessage = "User Role Assignment Violation:\nLack:\n ";
            for (RolePermisson rolePermisson : rolePermissionsCsv) {
                errorMessage = this.setError(rolePermisson, errorMessage);
                isSuBoolean = false;
            }
        }
        if (rolePermissionsDb.size() > 0) {
            if(isSuBoolean){
                errorMessage="";
            } else {
                errorMessage = errorMessage + "\n ";
            }
            errorMessage = errorMessage + "User Role Assignment Violation:\nRedundancy:\n ";
            for (RolePermisson rolePermisson : rolePermissionsDb) {
                errorMessage = this.setError(rolePermisson, errorMessage);
                isSuBoolean = false;
            }
        }
        return new ResultCheckPolicy(isSuBoolean, errorMessage);
    }

    private void compareTowRolePermission(ArrayList<RolePermisson> rolePermissionsDb, ArrayList<RolePermisson> rolePermissionsCsv ) {
        List<RolePermisson> rolePermissionsSame = new ArrayList<>();
        for(RolePermisson rPDB:rolePermissionsDb ){
            for(RolePermisson rPCSV:rolePermissionsCsv ){
                if (rPDB.getUser().equalsIgnoreCase(rPCSV.getUser())
                        && rPDB.getRole().equalsIgnoreCase(rPCSV.getRole())) {
                    rolePermissionsSame.add(rPDB);
                    rolePermissionsSame.add(rPCSV);
                }
            }
        }
        rolePermissionsDb.removeAll(rolePermissionsSame);
        rolePermissionsCsv.removeAll(rolePermissionsSame);
    }

    private String setError(RolePermisson rolePermisson, String errorMessage) {
        errorMessage = errorMessage.concat(" User: " + rolePermisson.getUser() + ".\n");
        errorMessage = errorMessage.concat(" Role: " + rolePermisson.getRole() + ".\n");
        errorMessage = errorMessage.concat("    ==============    \n");
        return errorMessage;
    }
}
