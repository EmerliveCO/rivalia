package com.rivalia.rivalia.shared.mapper;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class GlobalMapper {

    public <S, T> T map(S source, Class<T> targetClass) {
        if (source == null || targetClass == null ) return null;

        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for(Field targetField : targetFields) {
                    targetField.setAccessible(true);
                    if (targetField.getName().equals(sourceField.getName()) &&
                            targetField.getType().equals(sourceField.getType())) {
                        Object value = sourceField.get(source);
                        targetField.set(target, value);
                    }
                }
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of " + targetClass.getSimpleName(), e);
        }
    }
}
