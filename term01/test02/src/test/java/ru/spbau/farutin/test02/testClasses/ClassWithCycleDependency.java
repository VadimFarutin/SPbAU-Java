package ru.spbau.farutin.test02.testClasses;

public class ClassWithCycleDependency {

    public final ClassWithCycleDependency dependency;

    public ClassWithCycleDependency(ClassWithCycleDependency dependency) {
        this.dependency = dependency;
    }
}
