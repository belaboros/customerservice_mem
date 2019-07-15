package com.bb.cs.domain;

import org.springframework.util.ObjectUtils;

public class Customer implements Comparable<Customer> {

    private Integer id;
    private String title;
    private String name;

    /**
     * Default constructor is needed to construct Customer from JSON
     */
    public Customer() {
    }

    public Customer(String title, String name) {
        this(-1, title, name);
    }

    public Customer(int id, String title, String name) {
        this.id = id;
        this.title = title;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Customer other) {
        return Integer.compare(id, other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}