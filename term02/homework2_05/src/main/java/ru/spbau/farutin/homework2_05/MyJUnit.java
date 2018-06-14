package ru.spbau.farutin.homework2_05;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework2_05.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * MyJUnit - console application to run tests in given class.
 */
public class MyJUnit {
    private Method beforeClass = null;
    private Method afterClass = null;

    private List<Method> before = new ArrayList<>();
    private List<Method> after = new ArrayList<>();
    private List<Method> tests = new ArrayList<>();

    private Object classToTest = null;

    public static void main(String[] args) {
        System.out.println();

        if (args.length != 1) {
            System.err.println("Usage: gradlew run -Parg1=<class_name>");
            return;
        }

        String name = args[0];

        try {
            Class classToTest = Class.forName(name);
            MyJUnit myJUnit = new MyJUnit(classToTest);
            myJUnit.test();
        } catch (ClassNotFoundException e) {
            System.err.printf("Failed to load class %s\n", name);
        } catch (IllegalTestSuiteConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.printf("Failed to invoke method: %s\n", e.getMessage());
        } catch (InstantiationException e) {
            System.err.printf("Failed to instantiate class %s\n", name);
        }
    }

    /**
     * Loads methods annotated for testing from <code>classToTest</code>
     * and verifies that these annotations are correct.
     * @param classToTest class with tests in it
     * @throws IllegalTestSuiteConfigurationException if there are more than one
     * <code>@BeforeClass</code> or <code>@AfterClass</code> annotated methods
     * or there are at least one method with several annotations
     * @throws IllegalAccessException if failed to access <code>classToTest</code>
     * @throws InstantiationException if failed to instantiate <code>classToTest</code>
     */
    public MyJUnit(@NotNull Class classToTest) throws
            IllegalTestSuiteConfigurationException,
            IllegalAccessException,
            InstantiationException {
        this.classToTest = classToTest.newInstance();

        for (Method method : classToTest.getDeclaredMethods()) {
            int annotationCnt = 0;

            if (method.getAnnotation(BeforeClass.class) != null) {
                if (beforeClass != null) {
                    throw new IllegalTestSuiteConfigurationException(
                            "More than one @BeforeClass annotation");
                }

                beforeClass = method;
                annotationCnt++;
            }

            if (method.getAnnotation(AfterClass.class) != null) {
                if (afterClass != null) {
                    throw new IllegalTestSuiteConfigurationException(
                            "More than one @AfterClass annotation");
                }

                afterClass = method;
                annotationCnt++;
            }

            if (method.getAnnotation(Before.class) != null) {
                before.add(method);
                annotationCnt++;
            }

            if (method.getAnnotation(After.class) != null) {
                after.add(method);
                annotationCnt++;
            }

            if (method.getAnnotation(Test.class) != null) {
                tests.add(method);
                annotationCnt++;
            }

            if (annotationCnt > 1) {
                throw new IllegalTestSuiteConfigurationException(
                        "Multiple annotations");
            }
        }
    }

    /**
     * Runs all stored tests.
     * @throws InvocationTargetException if failed to invoke one of the methods
     * @throws IllegalAccessException if failed to invoke one of the methods
     */
    public void test() throws InvocationTargetException, IllegalAccessException {
        if (beforeClass != null) {
            beforeClass.invoke(classToTest);
        }

        for (Method testMethod : tests) {
            String name = testMethod.getDeclaringClass().getName()
                          + "."
                          + testMethod.getName();
            Test annotation = testMethod.getAnnotation(Test.class);
            String ignore = annotation.ignore();
            Class expected = annotation.expected();

            if (!ignore.equals(Test.RUN)) {
                System.out.printf("%s ignored: %s\n", name, ignore);
            } else {
                for (Method method : before) {
                    method.invoke(classToTest);
                }

                long start = System.currentTimeMillis();
                boolean failed = false;
                Throwable catched = null;

                try {
                    testMethod.invoke(classToTest);
                } catch (InvocationTargetException e) {
                    catched = e.getTargetException();
                }

                if (catched != null && (expected.equals(Test.NO_THROW.class)
                                        || !catched.getClass().equals(expected))) {
                    System.out.printf("%s failed: %s\n", name, catched.getMessage());
                    failed = true;
                }

                if (catched == null && !expected.equals(Test.NO_THROW.class)) {
                    System.out.printf("%s failed: expected %s\n", name, expected.getName());
                    failed = true;
                }

                if (!failed) {
                    long end = System.currentTimeMillis();
                    System.out.printf("%s finished in %d ms\n", name, end - start);
                }

                for (Method method : after) {
                    method.invoke(classToTest);
                }
            }
        }

        if (afterClass != null) {
            afterClass.invoke(classToTest);
        }
    }
}
