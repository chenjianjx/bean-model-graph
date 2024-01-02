package org.beanmodelgraph.constructor.helper;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * thread-safe
 */
public class InheritanceService {

    public <T> Set<Class<? extends T>> getDirectSubTypes(Class<T> parentClass, List<String> basePackagesToScan) {
        return this.getSubTypes(parentClass, basePackagesToScan).stream()
                .filter(subType -> isDirectChildOf(subType, parentClass))
                .collect(Collectors.toSet());
    }

    public <T> Set<Class<? extends T>> getSubTypes(Class<T> parentClass, List<String> basePackagesToScan) {

        FilterBuilder filter = new FilterBuilder();
        basePackagesToScan.forEach(p -> filter.includePackage(p));


        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(basePackagesToScan.toArray(new String[basePackagesToScan.size()]))
                .filterInputsBy(filter)
        );


        Set<Class<? extends T>> subtypes = reflections.getSubTypesOf(parentClass);

        return subtypes;
    }

    public boolean isMethodInheritedFrom(Class<?> subClass, Method method, Set<Class<?>> candidateClasses) {
        Optional<Class<?>> target = candidateClasses.stream()
                .filter(pc -> (pc.isAssignableFrom(subClass) && !pc.equals(subClass))) //get parent classes
                .filter(pc -> hasMethodWithSameSignature(pc, method))
                .findFirst();

        return target.isPresent();
    }

    private boolean hasMethodWithSameSignature(Class<?> clazz, Method method) {
        try {
            clazz.getMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }


    private boolean isDirectChildOf(Class<?> subClass, Class<?> parentClass) {
        Optional<Class<?>> superClassOfSub = Optional.ofNullable(subClass.getSuperclass());
        Set<Class<?>> interfacesOfSub = Arrays.stream(Optional.ofNullable(subClass.getInterfaces())
                        .orElse(new Class<?>[0]))
                .collect(Collectors.toSet());

        return superClassOfSub.equals(Optional.of(parentClass)) || interfacesOfSub.contains(parentClass);
    }


}
