package org.example.entity;

@FunctionalInterface
public interface MyConsumer {
    void accept(String... strings);
}
