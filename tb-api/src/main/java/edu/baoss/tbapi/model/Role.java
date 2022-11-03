package edu.baoss.tbapi.model;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("ROLE_USER"),
    FITTER("ROLE_FITTER"),
    OFFERS_MANAGER("ROLE_OFFERS_MANAGER"),
    RESOURCE_MANAGER("ROLE_RESOURCE_MANAGER"),
    SALES_MANAGER("ROLE_SALES_MANAGER"),
    OPERATOR("ROLE_OPERATOR"),
    EMPLOYEE_MANAGER("ROLE_EMPLOYEE_MANAGER"),
    ADMIN("ROLE_ADMIN"),
    SUPER_ADMIN("ROLE_SUPER_ADMIN");

    String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
