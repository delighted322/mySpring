package com.example.mySpring.model;

public class User {
    private String name;
    private int age;

    public String sayHi(String yourName) {
        return "Hi " + yourName + ", my name is " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
