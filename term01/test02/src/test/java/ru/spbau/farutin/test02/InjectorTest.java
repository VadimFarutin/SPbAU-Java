package ru.spbau.farutin.test02;

import org.junit.Test;
import ru.spbau.farutin.test02.testClasses.*;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Tests for Injector.
 */
public class InjectorTest {
    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        Object object = Injector.initialize("ru.spbau.farutin.test02.testClasses.ClassWithoutDependencies", Collections.emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.farutin.test02.testClasses.ClassWithOneClassDependency",
                Collections.singletonList(ClassWithoutDependencies.class)
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.farutin.test02.testClasses.ClassWithOneInterfaceDependency",
                Collections.singletonList(InterfaceImpl.class)
        );
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }

    /**
     * Tests injection on classes with cycle dependencies.
     */
    @Test(expected = InjectionCycleException.class)
    public void injectorShouldFindCycle() throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.farutin.test02.testClasses.ClassWithCycleDependency",
                Collections.singletonList(ClassWithCycleDependency.class)
        );
    }

    /**
     * Tests injection passing incomplete dependencies list.
     */
    @Test(expected = ImplementationNotFoundException.class)
    public void injectorShouldNotFindImplementation() throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.farutin.test02.testClasses.ClassWithTwoClassesDependencies",
                Collections.singletonList(ClassWithCycleDependency.class)
        );
    }

    /**
     * Tests injection when several implementations for one dependency are found.
     */
    @Test(expected = AmbiguousImplementationException.class)
    public void injectorSeveralImplementationsFound() throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.farutin.test02.testClasses.ClassWithOneInterfaceDependency",
                Arrays.asList(InterfaceImpl.class, InterfaceImplSecond.class)
        );
    }

    /**
     * Tests injector calling it more than one time.
     */
    @Test
    public void injectorSeveralCalls() throws Exception {
        Object object = Injector.initialize("ru.spbau.farutin.test02.testClasses.ClassWithoutDependencies", Collections.emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);

        object = Injector.initialize(
                "ru.spbau.farutin.test02.testClasses.ClassWithOneClassDependency",
                Collections.singletonList(ClassWithoutDependencies.class)
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }
}
