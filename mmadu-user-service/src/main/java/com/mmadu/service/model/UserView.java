package com.mmadu.service.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class UserView {
    private String id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("roles")
    private List<String> roles = new LinkedList<>();
    @JsonProperty("authorities")
    private List<String> authorities = new LinkedList<>();

    private Map<String, Object> properties = new LinkedHashMap<>();

    public UserView() {
    }

    public UserView(String username, String password, List<String> roles, List<String> authorities, Map<String, Object> properties) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
        this.properties = properties;
    }

    public UserView(String id, String username, String password, List<String> roles, List<String> authorities, Map<String, Object> properties) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
        this.properties = properties;
        this.id = id;
    }

    @JsonAnySetter
    public void setProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    public Optional<Object> getProperty(String value){
        return Optional.ofNullable(properties.get(value));
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        if(roles != null) {
            this.roles = roles;
        }
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        if(authorities != null) {
            this.authorities = authorities;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}