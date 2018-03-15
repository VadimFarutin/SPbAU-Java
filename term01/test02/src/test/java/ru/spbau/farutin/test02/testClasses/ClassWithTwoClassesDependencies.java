package ru.spbau.farutin.test02.testClasses;

public class ClassWithTwoClassesDependencies {

    public final ClassWithoutDependencies dependency1;
    public final ClassWithCycleDependency dependency2;

    public ClassWithTwoClassesDependencies(ClassWithoutDependencies dependency1,
                                           ClassWithCycleDependency dependency2) {
        this.dependency1 = dependency1;
        this.dependency2 = dependency2;
    }
}
