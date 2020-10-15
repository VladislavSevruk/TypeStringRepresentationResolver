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
package com.github.vladislavsevruk.resolver.resolver.executable;

import com.github.vladislavsevruk.resolver.exception.TypeResolvingException;
import com.github.vladislavsevruk.resolver.test.data.TestModel;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.type.TypeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ExecutableStringRepresentationResolverTest {

    private ExecutableStringRepresentationResolver executableTypeResolver
            = new ExecutableStringRepresentationResolver();

    @Test
    void getExceptionTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver
                .getExceptionTypes(TestModel.class, TestModel.class.getMethod("getExceptionType"));
        List<String> expectedStringRepresentation = Collections.singletonList(ParseException.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getExceptionTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> firstClassParameterTypeMeta = new TypeMeta<>(Float.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Long.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ firstClassParameterTypeMeta, secondClassParameterTypeMeta });
        List<String> result = executableTypeResolver
                .getExceptionTypes(typeMeta, TestModel.class.getMethod("getExceptionType"));
        List<String> expectedStringRepresentation = Collections.singletonList(ParseException.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getExceptionTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Float, Long>>() {};
        List<String> result = executableTypeResolver
                .getExceptionTypes(typeProvider, TestModel.class.getMethod("getExceptionType"));
        List<String> expectedStringRepresentation = Collections.singletonList(ParseException.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getGenericReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getGenericReturnType"));
        Assertions.assertEquals(TestModel.class.getTypeParameters()[0].getName(), result);
    }

    @Test
    void getGenericReturnTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> expectedTypeMeta = new TypeMeta<>(Float.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Long.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ expectedTypeMeta, secondClassParameterTypeMeta });
        String result = executableTypeResolver
                .getReturnType(typeMeta, TestModel.class.getMethod("getGenericReturnType"));
        Assertions.assertEquals(Float.class.getName(), result);
    }

    @Test
    void getGenericReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Float, Long>>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getGenericReturnType"));
        Assertions.assertEquals(Float.class.getName(), result);
    }

    @Test
    void getMethodGenericReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getMethodGenericReturnType"));
        Assertions
                .assertEquals(TestModel.class.getMethod("getMethodGenericReturnType").getTypeParameters()[0].getName(),
                        result);
    }

    @Test
    void getMethodGenericReturnTypeOverridesClassTypeVariableClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(TestModel.class,
                TestModel.class.getMethod("getMethodGenericReturnTypeOverridesClassTypeVariable"));
        Assertions.assertEquals(
                TestModel.class.getMethod("getMethodGenericReturnTypeOverridesClassTypeVariable").getTypeParameters()[0]
                        .getName(), result);
    }

    @Test
    void getMethodGenericReturnTypeOverridesClassTypeVariableTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> expectedTypeMeta = new TypeMeta<>(Float.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Long.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ expectedTypeMeta, secondClassParameterTypeMeta });
        String result = executableTypeResolver.getReturnType(typeMeta,
                TestModel.class.getMethod("getMethodGenericReturnTypeOverridesClassTypeVariable"));
        Assertions.assertEquals(
                TestModel.class.getMethod("getMethodGenericReturnTypeOverridesClassTypeVariable").getTypeParameters()[0]
                        .getName(), result);
    }

    @Test
    void getMethodGenericReturnTypeOverridesClassTypeVariableTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Float, Long>>() {};
        String result = executableTypeResolver.getReturnType(typeProvider,
                TestModel.class.getMethod("getMethodGenericReturnTypeOverridesClassTypeVariable"));
        Assertions.assertEquals(
                TestModel.class.getMethod("getMethodGenericReturnTypeOverridesClassTypeVariable").getTypeParameters()[0]
                        .getName(), result);
    }

    @Test
    void getMethodGenericReturnTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> expectedTypeMeta = new TypeMeta<>(Float.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Long.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ expectedTypeMeta, secondClassParameterTypeMeta });
        String result = executableTypeResolver
                .getReturnType(typeMeta, TestModel.class.getMethod("getMethodGenericReturnType"));
        Assertions
                .assertEquals(TestModel.class.getMethod("getMethodGenericReturnType").getTypeParameters()[0].getName(),
                        result);
    }

    @Test
    void getMethodGenericReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Float, Long>>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getMethodGenericReturnType"));
        Assertions
                .assertEquals(TestModel.class.getMethod("getMethodGenericReturnType").getTypeParameters()[0].getName(),
                        result);
    }

    @Test
    void getNoParameterTypeClassTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver
                .getParameterTypes(TestModel.class, TestModel.class.getMethod("getNoParameterType"));
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getNoParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver
                .getParameterTypes(new TypeMeta<>(TestModel.class), TestModel.class.getMethod("getNoParameterType"));
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getNoParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<?> result = executableTypeResolver
                .getParameterTypes(typeProvider, TestModel.class.getMethod("getNoParameterType"));
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getParameterTypeNotOverriddenObjectMethodThrowsExceptionClassTest() throws NoSuchMethodException {
        Method method = TestModel.class.getMethod("wait");
        Assertions.assertThrows(TypeResolvingException.class,
                () -> executableTypeResolver.getParameterTypes(TestModel.class, method));
    }

    @Test
    void getParameterTypeNotOverriddenObjectMethodThrowsExceptionTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class);
        Method method = TestModel.class.getMethod("wait");
        Assertions.assertThrows(TypeResolvingException.class,
                () -> executableTypeResolver.getParameterTypes(typeMeta, method));
    }

    @Test
    void getParameterTypeNotOverriddenObjectMethodThrowsExceptionTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        Method method = TestModel.class.getMethod("wait");
        Assertions.assertThrows(TypeResolvingException.class,
                () -> executableTypeResolver.getParameterTypes(typeProvider, method));
    }

    @Test
    void getParameterizedArrayReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getParameterizedArrayReturnType"));
        Assertions.assertEquals(TestModel.class.getTypeParameters()[0].getName() + "[]", result);
    }

    @Test
    void getParameterizedArrayReturnTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Character.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Integer.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ innerTypeMeta, secondClassParameterTypeMeta });
        String result = executableTypeResolver
                .getReturnType(typeMeta, TestModel.class.getMethod("getParameterizedArrayReturnType"));
        Assertions.assertEquals(Character.class.getName() + "[]", result);
    }

    @Test
    void getParameterizedArrayReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Character, Integer>>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getParameterizedArrayReturnType"));
        Assertions.assertEquals(Character.class.getName() + "[]", result);
    }

    @Test
    void getParameterizedReturnTypeInnerArrayTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getParameterizedReturnTypeInnerArrayType"));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerArrayTypeTypeMetaTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getParameterizedReturnTypeInnerArrayType"));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerArrayTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getParameterizedReturnTypeInnerArrayType"));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerParameterTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(TestModel.class,
                TestModel.class.getMethod("getParameterizedReturnTypeInnerParameterType"));
        String expectedStringRepresentation = String.format("%s<%s<%s>>", List.class.getName(), Set.class.getName(),
                TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerParameterTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Short.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Long.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ innerTypeMeta, secondClassParameterTypeMeta });
        String result = executableTypeResolver
                .getReturnType(typeMeta, TestModel.class.getMethod("getParameterizedReturnTypeInnerParameterType"));
        String expectedStringRepresentation = String
                .format("%s<%s<%s>>", List.class.getName(), Set.class.getName(), Short.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Short, Long>>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getParameterizedReturnTypeInnerParameterType"));
        String expectedStringRepresentation = String
                .format("%s<%s<%s>>", List.class.getName(), Set.class.getName(), Short.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerParameterizedArrayTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(TestModel.class,
                TestModel.class.getMethod("getParameterizedReturnTypeInnerParameterizedArrayType"));
        String expectedStringRepresentation = String
                .format("%s<%s[]>", Set.class.getName(), TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerParameterizedArrayTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Short.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(String.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ innerTypeMeta, secondClassParameterTypeMeta });
        String result = executableTypeResolver.getReturnType(typeMeta,
                TestModel.class.getMethod("getParameterizedReturnTypeInnerParameterizedArrayType"));
        String expectedStringRepresentation = String.format("%s<%s[]>", Set.class.getName(), Short.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeInnerParameterizedArrayTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Short, String>>() {};
        String result = executableTypeResolver.getReturnType(typeProvider,
                TestModel.class.getMethod("getParameterizedReturnTypeInnerParameterizedArrayType"));
        String expectedStringRepresentation = String.format("%s<%s[]>", Set.class.getName(), Short.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSeveralInnerParameterTypesClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(TestModel.class,
                TestModel.class.getMethod("getParameterizedReturnTypeSeveralInnerParameterTypes"));
        String expectedStringRepresentation = String.format("%s<%s<%s, %s>>", Set.class.getName(), Map.class.getName(),
                TestModel.class.getTypeParameters()[0].getName(), TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSeveralInnerParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Short.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Byte.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        String result = executableTypeResolver.getReturnType(typeMeta,
                TestModel.class.getMethod("getParameterizedReturnTypeSeveralInnerParameterTypes"));
        String expectedStringRepresentation = String
                .format("%s<%s<%s, %s>>", Set.class.getName(), Map.class.getName(), Short.class.getName(),
                        Byte.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSeveralInnerParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Short, Byte>>() {};
        String result = executableTypeResolver.getReturnType(typeProvider,
                TestModel.class.getMethod("getParameterizedReturnTypeSeveralInnerParameterTypes"));
        String expectedStringRepresentation = String
                .format("%s<%s<%s, %s>>", Set.class.getName(), Map.class.getName(), Short.class.getName(),
                        Byte.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSeveralParameterTypesClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(TestModel.class,
                TestModel.class.getMethod("getParameterizedReturnTypeSeveralParameterTypes"));
        String expectedStringRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), TestModel.class.getTypeParameters()[0].getName(),
                        TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSeveralParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(String.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Boolean.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        String result = executableTypeResolver
                .getReturnType(typeMeta, TestModel.class.getMethod("getParameterizedReturnTypeSeveralParameterTypes"));
        String expectedStringRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), String.class.getName(), Boolean.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSeveralParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<String, Boolean>>() {};
        String result = executableTypeResolver.getReturnType(typeProvider,
                TestModel.class.getMethod("getParameterizedReturnTypeSeveralParameterTypes"));
        String expectedStringRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), String.class.getName(), Boolean.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSingleParameterTypesClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(TestModel.class,
                TestModel.class.getMethod("getParameterizedReturnTypeSingleParameterTypes"));
        String expectedStringRepresentation = String
                .format("%s<%s>", List.class.getName(), TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSingleParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Character.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Byte.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ innerTypeMeta, secondClassParameterTypeMeta });
        String result = executableTypeResolver
                .getReturnType(typeMeta, TestModel.class.getMethod("getParameterizedReturnTypeSingleParameterTypes"));
        String expectedStringRepresentation = String.format("%s<%s>", List.class.getName(), Character.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getParameterizedReturnTypeSingleParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Character, Byte>>() {};
        String result = executableTypeResolver.getReturnType(typeProvider,
                TestModel.class.getMethod("getParameterizedReturnTypeSingleParameterTypes"));
        String expectedStringRepresentation = String.format("%s<%s>", List.class.getName(), Character.class.getName());
        Assertions.assertEquals(expectedStringRepresentation, result);
    }

    @Test
    void getPrimitiveArrayParameterTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getPrimitiveArrayParameterType", short[].class));
        Assertions.assertEquals(Collections.singletonList("short[]"), result);
    }

    @Test
    void getPrimitiveArrayParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getPrimitiveArrayParameterType", short[].class));
        Assertions.assertEquals(Collections.singletonList("short[]"), result);
    }

    @Test
    void getPrimitiveArrayParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getPrimitiveArrayParameterType", short[].class));
        Assertions.assertEquals(Collections.singletonList("short[]"), result);
    }

    @Test
    void getPrimitiveArrayReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getPrimitiveArrayReturnType"));
        Assertions.assertEquals("int[]", result);
    }

    @Test
    void getPrimitiveArrayReturnTypeTypeMetaTest() throws NoSuchMethodException {
        String result = executableTypeResolver.getReturnType(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getPrimitiveArrayReturnType"));
        Assertions.assertEquals("int[]", result);
    }

    @Test
    void getPrimitiveArrayReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getPrimitiveArrayReturnType"));
        Assertions.assertEquals("int[]", result);
    }

    @Test
    void getPrimitiveReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getPrimitiveReturnType"));
        Assertions.assertEquals("int", result);
    }

    @Test
    void getPrimitiveReturnTypeTypeMetaTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(new TypeMeta<>(TestModel.class), TestModel.class.getMethod("getPrimitiveReturnType"));
        Assertions.assertEquals("int", result);
    }

    @Test
    void getPrimitiveReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getPrimitiveReturnType"));
        Assertions.assertEquals("int", result);
    }

    @Test
    void getReturnTypeNotOverriddenObjectMethodThrowsExceptionClassTest() throws NoSuchMethodException {
        Method method = TestModel.class.getMethod("wait");
        Assertions.assertThrows(TypeResolvingException.class,
                () -> executableTypeResolver.getReturnType(TestModel.class, method));
    }

    @Test
    void getReturnTypeNotOverriddenObjectMethodThrowsExceptionTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class);
        Method method = TestModel.class.getMethod("wait");
        Assertions.assertThrows(TypeResolvingException.class,
                () -> executableTypeResolver.getReturnType(typeMeta, method));
    }

    @Test
    void getReturnTypeNotOverriddenObjectMethodThrowsExceptionTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        Method method = TestModel.class.getMethod("wait");
        Assertions.assertThrows(TypeResolvingException.class,
                () -> executableTypeResolver.getReturnType(typeProvider, method));
    }

    @Test
    void getSeveralGenericParameterTypesClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSeveralGenericParameterTypes", Object.class, Object.class));
        Assertions.assertEquals(Arrays.asList(TestModel.class.getTypeParameters()[0].getName(),
                TestModel.class.getTypeParameters()[1].getName()), result);
    }

    @Test
    void getSeveralGenericParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Long.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Integer.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta,
                TestModel.class.getMethod("getSeveralGenericParameterTypes", Object.class, Object.class));
        Assertions.assertEquals(Arrays.asList(Long.class.getName(), Integer.class.getName()), result);
    }

    @Test
    void getSeveralGenericParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Long, Integer>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSeveralGenericParameterTypes", Object.class, Object.class));
        Assertions.assertEquals(Arrays.asList(Long.class.getName(), Integer.class.getName()), result);
    }

    @Test
    void getSeveralParameterizedParameterTypeSeveralInnerParameterTypesClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class, TestModel.class
                .getMethod("getSeveralParameterizedParameterTypeSeveralInnerParameterTypes", List.class, Set.class));
        String expectedStringRepresentation1 = String
                .format("%s<%s<%s, %s>>", List.class.getName(), Map.class.getName(),
                        TestModel.class.getTypeParameters()[0].getName(),
                        TestModel.class.getTypeParameters()[1].getName());
        String expectedStringRepresentation2 = String
                .format("%s<%s<%s, %s>>", Set.class.getName(), Map.Entry.class.getName(),
                        TestModel.class.getTypeParameters()[0].getName(),
                        TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(Arrays.asList(expectedStringRepresentation1, expectedStringRepresentation2), result);
    }

    @Test
    void getSeveralParameterizedParameterTypeSeveralInnerParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Long.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Integer.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta, TestModel.class
                .getMethod("getSeveralParameterizedParameterTypeSeveralInnerParameterTypes", List.class, Set.class));
        String expectedStringRepresentation1 = String
                .format("%s<%s<%s, %s>>", List.class.getName(), Map.class.getName(), Long.class.getName(),
                        Integer.class.getName());
        String expectedStringRepresentation2 = String
                .format("%s<%s<%s, %s>>", Set.class.getName(), Map.Entry.class.getName(), Long.class.getName(),
                        Integer.class.getName());
        Assertions.assertEquals(Arrays.asList(expectedStringRepresentation1, expectedStringRepresentation2), result);
    }

    @Test
    void getSeveralParameterizedParameterTypeSeveralInnerParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Long, Integer>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider, TestModel.class
                .getMethod("getSeveralParameterizedParameterTypeSeveralInnerParameterTypes", List.class, Set.class));
        String expectedStringRepresentation1 = String
                .format("%s<%s<%s, %s>>", List.class.getName(), Map.class.getName(), Long.class.getName(),
                        Integer.class.getName());
        String expectedStringRepresentation2 = String
                .format("%s<%s<%s, %s>>", Set.class.getName(), Map.Entry.class.getName(), Long.class.getName(),
                        Integer.class.getName());
        Assertions.assertEquals(Arrays.asList(expectedStringRepresentation1, expectedStringRepresentation2), result);
    }

    @Test
    void getSeveralParameterizedParameterTypesClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSeveralParameterizedParameterTypes", List.class, Set.class));
        String expectedStringRepresentation1 = String
                .format("%s<%s>", List.class.getName(), TestModel.class.getTypeParameters()[0].getName());
        String expectedStringRepresentation2 = String
                .format("%s<%s>", Set.class.getName(), TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(Arrays.asList(expectedStringRepresentation1, expectedStringRepresentation2), result);
    }

    @Test
    void getSeveralParameterizedParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Byte.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Float.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta,
                TestModel.class.getMethod("getSeveralParameterizedParameterTypes", List.class, Set.class));
        String expectedStringRepresentation1 = String.format("%s<%s>", List.class.getName(), Byte.class.getName());
        String expectedStringRepresentation2 = String.format("%s<%s>", Set.class.getName(), Float.class.getName());
        Assertions.assertEquals(Arrays.asList(expectedStringRepresentation1, expectedStringRepresentation2), result);
    }

    @Test
    void getSeveralParameterizedParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Byte, Float>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSeveralParameterizedParameterTypes", List.class, Set.class));
        String expectedStringRepresentation1 = String.format("%s<%s>", List.class.getName(), Byte.class.getName());
        String expectedStringRepresentation2 = String.format("%s<%s>", Set.class.getName(), Float.class.getName());
        Assertions.assertEquals(Arrays.asList(expectedStringRepresentation1, expectedStringRepresentation2), result);
    }

    @Test
    void getSeveralSimpleParameterTypesClassTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSeveralSimpleParameterTypes", Character.class, int.class));
        Assertions.assertEquals(Arrays.asList(Character.class.getName(), "int"), result);
    }

    @Test
    void getSeveralSimpleParameterTypesTypeMetaTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getSeveralSimpleParameterTypes", Character.class, int.class));
        Assertions.assertEquals(Arrays.asList(Character.class.getName(), "int"), result);
    }

    @Test
    void getSeveralSimpleParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<?> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSeveralSimpleParameterTypes", Character.class, int.class));
        Assertions.assertEquals(Arrays.asList(Character.class.getName(), "int"), result);
    }

    @Test
    void getSimpleReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getSimpleReturnType"));
        Assertions.assertEquals(String.class.getName(), result);
    }

    @Test
    void getSimpleReturnTypeTypeMetaTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(new TypeMeta<>(TestModel.class), TestModel.class.getMethod("getSimpleReturnType"));
        Assertions.assertEquals(String.class.getName(), result);
    }

    @Test
    void getSimpleReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getSimpleReturnType"));
        Assertions.assertEquals(String.class.getName(), result);
    }

    @Test
    void getSingleGenericParameterTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleGenericParameterType", Object.class));
        Assertions.assertEquals(Collections.singletonList(TestModel.class.getTypeParameters()[0].getName()), result);
    }

    @Test
    void getSingleGenericParameterTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> expectedTypeMeta = new TypeMeta<>(Character.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(String.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ expectedTypeMeta, secondClassParameterTypeMeta });
        List<String> result = executableTypeResolver
                .getParameterTypes(typeMeta, TestModel.class.getMethod("getSingleGenericParameterType", Object.class));
        Assertions.assertEquals(Collections.singletonList(Character.class.getName()), result);
    }

    @Test
    void getSingleGenericParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Character, String>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleGenericParameterType", Object.class));
        Assertions.assertEquals(Collections.singletonList(Character.class.getName()), result);
    }

    @Test
    void getSingleLowerWildcardArrayParameterTypeClassTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleLowerWildcardArrayParameterType", List[].class));
        String expectedStringRepresentation = String
                .format("%s<? super %s>[]", List.class.getName(), Number.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleLowerWildcardArrayParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getSingleLowerWildcardArrayParameterType", List[].class));
        String expectedStringRepresentation = String
                .format("%s<? super %s>[]", List.class.getName(), Number.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleLowerWildcardArrayParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<?> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleLowerWildcardArrayParameterType", List[].class));
        String expectedStringRepresentation = String
                .format("%s<? super %s>[]", List.class.getName(), Number.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedArrayParameterTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleParameterizedArrayParameterType", Object[].class));
        String expectedStringRepresentation = TestModel.class.getTypeParameters()[1].getName() + "[]";
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedArrayParameterTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> firstClassParameterTypeMeta = new TypeMeta<>(Boolean.class);
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(String.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ firstClassParameterTypeMeta, innerTypeMeta });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta,
                TestModel.class.getMethod("getSingleParameterizedArrayParameterType", Object[].class));
        String expectedStringRepresentation = String.class.getName() + "[]";
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedArrayParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Boolean, String>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleParameterizedArrayParameterType", Object[].class));
        String expectedStringRepresentation = String.class.getName() + "[]";
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerArrayTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeInnerArrayType", List.class));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerArrayTypeTypeMetaTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getSingleParameterizedParameterTypeInnerArrayType", List.class));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerArrayTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeInnerArrayType", List.class));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Integer.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerParameterTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeInnerParameterType", Set.class));
        String expectedStringRepresentation = String.format("%s<%s<%s>>", Set.class.getName(), List.class.getName(),
                TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerParameterTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Float.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Character.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeInnerParameterType", Set.class));
        String expectedStringRepresentation = String
                .format("%s<%s<%s>>", Set.class.getName(), List.class.getName(), Float.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Float, Character>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeInnerParameterType", Set.class));
        String expectedStringRepresentation = String
                .format("%s<%s<%s>>", Set.class.getName(), List.class.getName(), Float.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerParameterizedArrayTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class, TestModel.class
                .getMethod("getSingleParameterizedParameterTypeInnerParameterizedArrayType", List.class));
        String expectedStringRepresentation = String
                .format("%s<%s[]>", List.class.getName(), TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerParameterizedArrayTypeTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Double.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Boolean.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta, TestModel.class
                .getMethod("getSingleParameterizedParameterTypeInnerParameterizedArrayType", List.class));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Double.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeInnerParameterizedArrayTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Double, Boolean>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider, TestModel.class
                .getMethod("getSingleParameterizedParameterTypeInnerParameterizedArrayType", List.class));
        String expectedStringRepresentation = String.format("%s<%s[]>", List.class.getName(), Double.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSeveralInnerParameterTypesClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSeveralInnerParameterTypes", List.class));
        String expectedStringRepresentation = String.format("%s<%s<%s, %s>>", List.class.getName(), Map.class.getName(),
                TestModel.class.getTypeParameters()[0].getName(), TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSeveralInnerParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(Short.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Long.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSeveralInnerParameterTypes", List.class));
        String expectedStringRepresentation = String
                .format("%s<%s<%s, %s>>", List.class.getName(), Map.class.getName(), Short.class.getName(),
                        Long.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSeveralInnerParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Short, Long>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSeveralInnerParameterTypes", List.class));
        String expectedStringRepresentation = String
                .format("%s<%s<%s, %s>>", List.class.getName(), Map.class.getName(), Short.class.getName(),
                        Long.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSeveralParameterTypesClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSeveralParameterTypes", Map.class));
        String expectedStringRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), TestModel.class.getTypeParameters()[0].getName(),
                        TestModel.class.getTypeParameters()[1].getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSeveralParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(String.class);
        TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(Boolean.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class, new TypeMeta<?>[]{ innerTypeMeta1, innerTypeMeta2 });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSeveralParameterTypes", Map.class));
        String expectedStringRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), String.class.getName(), Boolean.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSeveralParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<String, Boolean>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSeveralParameterTypes", Map.class));
        String expectedStringRepresentation = String
                .format("%s<%s, %s>", Map.class.getName(), String.class.getName(), Boolean.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSingleParameterTypesClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSingleParameterTypes", Collection.class));
        String expectedStringRepresentation = String
                .format("%s<%s>", Collection.class.getName(), TestModel.class.getTypeParameters()[0].getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSingleParameterTypesTypeMetaTest() throws NoSuchMethodException {
        TypeMeta<?> innerTypeMeta = new TypeMeta<>(Byte.class);
        TypeMeta<?> secondClassParameterTypeMeta = new TypeMeta<>(Boolean.class);
        TypeMeta<?> typeMeta = new TypeMeta<>(TestModel.class,
                new TypeMeta<?>[]{ innerTypeMeta, secondClassParameterTypeMeta });
        List<String> result = executableTypeResolver.getParameterTypes(typeMeta,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSingleParameterTypes", Collection.class));
        String expectedStringRepresentation = String.format("%s<%s>", Collection.class.getName(), Byte.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleParameterizedParameterTypeSingleParameterTypesTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel<Byte, Boolean>>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleParameterizedParameterTypeSingleParameterTypes", Collection.class));
        String expectedStringRepresentation = String.format("%s<%s>", Collection.class.getName(), Byte.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSinglePrimitiveParameterTypeClassTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSinglePrimitiveParameterType", int.class));
        Assertions.assertEquals(Collections.singletonList("int"), result);
    }

    @Test
    void getSinglePrimitiveParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getSinglePrimitiveParameterType", int.class));
        Assertions.assertEquals(Collections.singletonList("int"), result);
    }

    @Test
    void getSinglePrimitiveParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<?> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSinglePrimitiveParameterType", int.class));
        Assertions.assertEquals(Collections.singletonList("int"), result);
    }

    @Test
    void getSingleSimpleParameterTypeClassTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleSimpleParameterType", Object.class));
        Assertions.assertEquals(Collections.singletonList(Object.class.getName()), result);
    }

    @Test
    void getSingleSimpleParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getSingleSimpleParameterType", Object.class));
        Assertions.assertEquals(Collections.singletonList(Object.class.getName()), result);
    }

    @Test
    void getSingleSimpleParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<?> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleSimpleParameterType", Object.class));
        Assertions.assertEquals(Collections.singletonList(Object.class.getName()), result);
    }

    @Test
    void getSingleUpperWildcardArrayParameterTypeClassTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleUpperWildcardArrayParameterType", List[].class));
        String expectedStringRepresentation = String
                .format("%s<? extends %s>[]", List.class.getName(), Number.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleUpperWildcardArrayParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getSingleUpperWildcardArrayParameterType", List[].class));
        String expectedStringRepresentation = String
                .format("%s<? extends %s>[]", List.class.getName(), Number.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleUpperWildcardArrayParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<?> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleUpperWildcardArrayParameterType", List[].class));
        String expectedStringRepresentation = String
                .format("%s<? extends %s>[]", List.class.getName(), Number.class.getName());
        Assertions.assertEquals(Collections.singletonList(expectedStringRepresentation), result);
    }

    @Test
    void getSingleWrapperParameterTypeClassTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getSingleWrapperParameterType", Long.class));
        Assertions.assertEquals(Collections.singletonList(Long.class.getName()), result);
    }

    @Test
    void getSingleWrapperParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<?> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getSingleWrapperParameterType", Long.class));
        Assertions.assertEquals(Collections.singletonList(Long.class.getName()), result);
    }

    @Test
    void getSingleWrapperParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<?> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getSingleWrapperParameterType", Long.class));
        Assertions.assertEquals(Collections.singletonList(Long.class.getName()), result);
    }

    @Test
    void getVoidReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getVoidReturnType"));
        Assertions.assertEquals("void", result);
    }

    @Test
    void getVoidReturnTypeTypeMetaTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(new TypeMeta<>(TestModel.class), TestModel.class.getMethod("getVoidReturnType"));
        Assertions.assertEquals("void", result);
    }

    @Test
    void getVoidReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getVoidReturnType"));
        Assertions.assertEquals("void", result);
    }

    @Test
    void getWrapperArrayParameterTypeClassTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(TestModel.class,
                TestModel.class.getMethod("getWrapperArrayParameterType", Short[].class));
        Assertions.assertEquals(Collections.singletonList(Short.class.getName() + "[]"), result);
    }

    @Test
    void getWrapperArrayParameterTypeTypeMetaTest() throws NoSuchMethodException {
        List<String> result = executableTypeResolver.getParameterTypes(new TypeMeta<>(TestModel.class),
                TestModel.class.getMethod("getWrapperArrayParameterType", Short[].class));
        Assertions.assertEquals(Collections.singletonList(Short.class.getName() + "[]"), result);
    }

    @Test
    void getWrapperArrayParameterTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        List<String> result = executableTypeResolver.getParameterTypes(typeProvider,
                TestModel.class.getMethod("getWrapperArrayParameterType", Short[].class));
        Assertions.assertEquals(Collections.singletonList(Short.class.getName() + "[]"), result);
    }

    @Test
    void getWrapperArrayReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getWrapperArrayReturnType"));
        Assertions.assertEquals(Integer.class.getName() + "[]", result);
    }

    @Test
    void getWrapperArrayReturnTypeTypeMetaTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(new TypeMeta<>(TestModel.class), TestModel.class.getMethod("getWrapperArrayReturnType"));
        Assertions.assertEquals(Integer.class.getName() + "[]", result);
    }

    @Test
    void getWrapperArrayReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getWrapperArrayReturnType"));
        Assertions.assertEquals(Integer.class.getName() + "[]", result);
    }

    @Test
    void getWrapperReturnTypeClassTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(TestModel.class, TestModel.class.getMethod("getWrapperReturnType"));
        Assertions.assertEquals(Double.class.getName(), result);
    }

    @Test
    void getWrapperReturnTypeTypeMetaTest() throws NoSuchMethodException {
        String result = executableTypeResolver
                .getReturnType(new TypeMeta<>(TestModel.class), TestModel.class.getMethod("getWrapperReturnType"));
        Assertions.assertEquals(Double.class.getName(), result);
    }

    @Test
    void getWrapperReturnTypeTypeProviderTest() throws NoSuchMethodException {
        TypeProvider<?> typeProvider = new TypeProvider<TestModel>() {};
        String result = executableTypeResolver
                .getReturnType(typeProvider, TestModel.class.getMethod("getWrapperReturnType"));
        Assertions.assertEquals(Double.class.getName(), result);
    }
}
