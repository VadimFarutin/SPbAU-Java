package ru.spbau.farutin.test02;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Injector - implements Dependency Injection.
 */
public class Injector {
    private static List<Class<?>> availableImpl = null;
    private static Set<Class<?>> classesToBuild = new HashSet<>();
    private static Map<Class<?>, Object> builtClasses = new HashMap<>();

    /**
     * Builds instance of given class.
     * @param rootClassName class to build
     * @param availableClasses classes available to depend on
     * @return built instance
     * @throws ImplementationNotFoundException if there was a dependency
     * which has not got available implementation
     * @throws InjectionCycleException if dependencies form cycle
     * @throws AmbiguousImplementationException if there are more than one
     * available implementations for some dependency
     * @throws IllegalAccessException if one of used constructors is enforcing
     * Java language access control and the underlying constructor is inaccessible
     * @throws InstantiationException if for one of constructors the class that
     * declares the underlying constructor represents an abstract class
     * @throws InvocationTargetException if on of used constructors throws an exception
     */
    public static @NotNull Object initialize(@NotNull String rootClassName,
                                             @NotNull List<Class<?>> availableClasses)
            throws ImplementationNotFoundException,
                   InjectionCycleException,
                   AmbiguousImplementationException,
                   IllegalAccessException,
                   InstantiationException,
                   InvocationTargetException {
        availableImpl = availableClasses;
        classesToBuild.clear();
        builtClasses.clear();
        Class<?> rootClass = null;

        try {
            rootClass = Class.forName(rootClassName);
        } catch (ClassNotFoundException e) {
            throw new ImplementationNotFoundException();
        }

        return buildClass(rootClass);
    }

    private static @NotNull Object buildClass(@NotNull Class<?> rootClass)
            throws IllegalAccessException,
                   InvocationTargetException,
                   InstantiationException,
                   InjectionCycleException,
                   AmbiguousImplementationException,
                   ImplementationNotFoundException {
        if (classesToBuild.contains(rootClass)) {
            throw new InjectionCycleException();
        }

        if (builtClasses.containsKey(rootClass)) {
            return builtClasses.get(rootClass);
        }

        classesToBuild.add(rootClass);

        Constructor<?> constructor = rootClass.getConstructors()[0];
        Class<?>[] parameters = constructor.getParameterTypes();
        Object[] parametersImpl = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> dependence = null;

            for (Class<?> impl : availableImpl) {
                if (parameters[i].isAssignableFrom(impl)) {
                    if (dependence == null) {
                        dependence = impl;
                    } else {
                        throw new AmbiguousImplementationException();
                    }
                }
            }

            if (dependence == null) {
                throw new ImplementationNotFoundException();
            }

            parametersImpl[i] = buildClass(dependence);
        }

        Object rootObject = constructor.newInstance(parametersImpl);
        builtClasses.put(rootClass, rootObject);
        classesToBuild.remove(rootClass);

        return rootObject;
    }
}
