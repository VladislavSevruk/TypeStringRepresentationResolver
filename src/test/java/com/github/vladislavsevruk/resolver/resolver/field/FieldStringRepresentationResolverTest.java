/*
 * MIT License
 *
 * Copyright (c) 2020 Uladzislau Seuruk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.vladislavsevruk.resolver.resolver.field;

import com.github.vladislavsevruk.resolver.test.context.ShortNameRepresentationContext;
import com.github.vladislavsevruk.resolver.test.data.TestModel;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.type.TypeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FieldStringRepresentationResolverTest {

    private FieldStringRepresentationResolver fieldTypeResolver = new FieldStringRepresentationResolver();
    private FieldStringRepresentationResolver shortNameFieldTypeResolver = new FieldStringRepresentationResolver(
            new ShortNameRepresentationContext());

    @Test
    void getDoubledArrayFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("doubledArrayField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        Assertions.assertEquals(Integer.class.getName() + "[][]", result);
    }

    @Test
    void getDoubledArrayFieldTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> expectedTypeMeta = new TypeMeta<>(Double.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Short.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ expectedTypeMeta, secondClassParameterTypeMeta });
        Field field = TestModel.class.getDeclaredField("doubledArrayField");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        Assertions.assertEquals(Integer.class.getName() + "[][]", result);
    }

    @Test
    void getDoubledArrayFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Double, Short>>() {};
        Field field = TestModel.class.getDeclaredField("doubledArrayField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        Assertions.assertEquals(Integer.class.getName() + "[][]", result);
    }

    @Test
    void getGenericFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("genericField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        Assertions.assertEquals(TestModel.class.getTypeParameters()[0].getName(), result);
    }

    @Test
    void getGenericFieldTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> expectedTypeMeta = new TypeMeta<>(Double.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Short.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ expectedTypeMeta, secondClassParameterTypeMeta });
        Field field = TestModel.class.getDeclaredField("genericField");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        Assertions.assertEquals(Double.class.getName(), result);
    }

    @Test
    void getGenericFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Double, Short>>() {};
        Field field = TestModel.class.getDeclaredField("genericField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        Assertions.assertEquals(Double.class.getName(), result);
    }

    @Test
    void getLowerWildcardFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("lowerWildcardField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String
                .format("%s<? super %s[]>", Set.class.getName(), TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getLowerWildcardFieldTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> firstClassParameterTypeMeta = new TypeMeta<>(Character.class);
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Double.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ firstClassParameterTypeMeta, innerTypeMeta });
        Field field = TestModel.class.getDeclaredField("lowerWildcardField");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String.format("%s<? super %s[]>", Set.class.getName(), Double.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getLowerWildcardFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Character, Double>>() {};
        Field field = TestModel.class.getDeclaredField("lowerWildcardField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String.format("%s<? super %s[]>", Set.class.getName(), Double.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedArrayFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedArrayField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = TestModel.class.getTypeParameters()[1].getName() + "[]";
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedArrayFieldTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> firstClassParameterTypeMeta = new TypeMeta<>(Float.class);
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Long.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ firstClassParameterTypeMeta, innerTypeMeta });
        Field field = TestModel.class.getDeclaredField("parameterizedArrayField");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = Long.class.getName() + "[]";
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedArrayFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Float, Long>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedArrayField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = Long.class.getName() + "[]";
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerArrayTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerArray");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String.format("%s<%s[]>", List.class.getName(), Long.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerArrayTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Character.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(String.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ innerTypeMeta, secondClassParameterTypeMeta });
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerArray");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String.format("%s<%s[]>", List.class.getName(), Long.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerArrayTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Character, String>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerArray");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String.format("%s<%s[]>", List.class.getName(), Long.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerParameterTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerParameter");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String.format("%s<%s<%s>>", Set.class.getName(), List.class.getName(),
                TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerParameterTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> firstClassParameterTypeMeta = new TypeMeta<>(Integer.class);
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Boolean.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ firstClassParameterTypeMeta, innerTypeMeta });
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerParameter");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String
                .format("%s<%s<%s>>", Set.class.getName(), List.class.getName(), Boolean.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerParameterTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Integer, Boolean>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerParameter");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String
                .format("%s<%s<%s>>", Set.class.getName(), List.class.getName(), Boolean.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerParameterizedArrayTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerParameterizedArray");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String
                .format("%s<%s[]>", Set.class.getName(), TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerParameterizedArrayTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Long.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Boolean.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ innerTypeMeta, secondClassParameterTypeMeta });
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerParameterizedArray");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String.format("%s<%s[]>", Set.class.getName(), Long.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeInnerParameterizedArrayTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Long, Boolean>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedFieldInnerParameterizedArray");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String.format("%s<%s[]>", Set.class.getName(), Long.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralInnerParameterTypesClassShortNameTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralInnerParameters");
        String result = shortNameFieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String
                .format("%s<%s<%s>, %s<%s>>", Map.class.getSimpleName(), Set.class.getSimpleName(),
                        TestModel.class.getTypeParameters()[1].getName(), List.class.getSimpleName(),
                        TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralInnerParameterTypesClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralInnerParameters");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String.format("%s<%s<%s>, %s<%s>>", Map.class.getName(), Set.class.getName(),
                TestModel.class.getTypeParameters()[1].getName(), List.class.getName(),
                TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralInnerParameterTypesTypeMetaShortNameTest() throws NoSuchFieldException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Short.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Byte.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralInnerParameters");
        String result = shortNameFieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String
                .format("%s<%s<%s>, %s<%s>>", Map.class.getSimpleName(), Set.class.getSimpleName(),
                        Byte.class.getSimpleName(), List.class.getSimpleName(), Short.class.getSimpleName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralInnerParameterTypesTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Short.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Byte.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralInnerParameters");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String
                .format("%s<%s<%s>, %s<%s>>", Map.class.getName(), Set.class.getName(), Byte.class.getName(),
                        List.class.getName(), Short.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralInnerParameterTypesTypeProviderShortNameTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Short, Byte>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralInnerParameters");
        String result = shortNameFieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String
                .format("%s<%s<%s>, %s<%s>>", Map.class.getSimpleName(), Set.class.getSimpleName(),
                        Byte.class.getSimpleName(), List.class.getSimpleName(), Short.class.getSimpleName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralInnerParameterTypesTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Short, Byte>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralInnerParameters");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String
                .format("%s<%s<%s>, %s<%s>>", Map.class.getName(), Set.class.getName(), Byte.class.getName(),
                        List.class.getName(), Short.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralParameterTypesClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralParameters");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), TestModel.class.getTypeParameters()[1].getName(),
                        TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralParameterTypesTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Byte.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(String.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralParameters");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), String.class.getName(), Byte.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSeveralParameterTypesTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Byte, String>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSeveralParameters");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), String.class.getName(), Byte.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSingleParameterTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSingleParameter");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String
                .format("%s<%s>", List.class.getName(), TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSingleParameterTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Integer.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Float.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSingleParameter");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String.format("%s<%s>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getParameterizedFieldTypeSingleParameterTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Integer, Float>>() {};
        Field field = TestModel.class.getDeclaredField("parameterizedFieldSingleParameter");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String.format("%s<%s>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getPrimitiveArrayFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("primitiveArrayField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        Assertions.assertEquals("int[]", result);
    }

    @Test
    void getPrimitiveArrayFieldTypeTypeMetaTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("primitiveArrayField");
        String result = fieldTypeResolver.resolveField(new TypeMeta<>(TestModel.class), field);
        Assertions.assertEquals("int[]", result);
    }

    @Test
    void getPrimitiveArrayFieldTypeTypeProviderTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("primitiveArrayField");
        String result = fieldTypeResolver.resolveField(new TypeProvider<TestModel>() {}, field);
        Assertions.assertEquals("int[]", result);
    }

    @Test
    void getPrimitiveFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("primitiveField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        Assertions.assertEquals("long", result);
    }

    @Test
    void getPrimitiveFieldTypeTypeMetaTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("primitiveField");
        String result = fieldTypeResolver.resolveField(new TypeMeta<>(TestModel.class), field);
        Assertions.assertEquals("long", result);
    }

    @Test
    void getPrimitiveFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        Field field = TestModel.class.getDeclaredField("primitiveField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        Assertions.assertEquals("long", result);
    }

    @Test
    void getSimpleFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("simpleField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        Assertions.assertEquals(String.class.getName(), result);
    }

    @Test
    void getSimpleFieldTypeTypeMetaTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("simpleField");
        String result = fieldTypeResolver.resolveField(new TypeMeta<>(TestModel.class), field);
        Assertions.assertEquals(String.class.getName(), result);
    }

    @Test
    void getSimpleFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        Field field = TestModel.class.getDeclaredField("simpleField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        Assertions.assertEquals(String.class.getName(), result);
    }

    @Test
    void getUpperWildcardFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("upperWildcardField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        String expectedRepresentation = String
                .format("%s<? extends %s[]>", Set.class.getName(), TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getUpperWildcardFieldTypeTypeMetaTest() throws NoSuchFieldException {
        TypeMeta<?> firstClassParameterTypeMeta = new TypeMeta<>(Character.class);
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Double.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ firstClassParameterTypeMeta, innerTypeMeta });
        Field field = TestModel.class.getDeclaredField("upperWildcardField");
        String result = fieldTypeResolver.resolveField(typeMeta, field);
        String expectedRepresentation = String
                .format("%s<? extends %s[]>", Set.class.getName(), Double.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getUpperWildcardFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Character, Double>>() {};
        Field field = TestModel.class.getDeclaredField("upperWildcardField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        String expectedRepresentation = String
                .format("%s<? extends %s[]>", Set.class.getName(), Double.class.getName());
        Assertions.assertEquals(expectedRepresentation, result);
    }

    @Test
    void getWrapperArrayFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("wrapperArrayField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        Assertions.assertEquals(Integer.class.getName() + "[]", result);
    }

    @Test
    void getWrapperArrayFieldTypeTypeMetaTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("wrapperArrayField");
        String result = fieldTypeResolver.resolveField(new TypeMeta<>(TestModel.class), field);
        Assertions.assertEquals(Integer.class.getName() + "[]", result);
    }

    @Test
    void getWrapperArrayFieldTypeTypeProviderTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("wrapperArrayField");
        String result = fieldTypeResolver.resolveField(new TypeProvider<TestModel>() {}, field);
        Assertions.assertEquals(Integer.class.getName() + "[]", result);
    }

    @Test
    void getWrapperFieldTypeClassTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("wrapperField");
        String result = fieldTypeResolver.resolveField(TestModel.class, field);
        Assertions.assertEquals(Short.class.getName(), result);
    }

    @Test
    void getWrapperFieldTypeTypeMetaTest() throws NoSuchFieldException {
        Field field = TestModel.class.getDeclaredField("wrapperField");
        String result = fieldTypeResolver.resolveField(new TypeMeta<>(TestModel.class), field);
        Assertions.assertEquals(Short.class.getName(), result);
    }

    @Test
    void getWrapperFieldTypeTypeProviderTest() throws NoSuchFieldException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        Field field = TestModel.class.getDeclaredField("wrapperField");
        String result = fieldTypeResolver.resolveField(typeProvider, field);
        Assertions.assertEquals(Short.class.getName(), result);
    }
}
