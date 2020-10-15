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
package com.github.vladislavsevruk.resolver.type.mapper;

import com.github.vladislavsevruk.resolver.exception.TypeResolvingException;
import com.github.vladislavsevruk.resolver.type.MappedVariableHierarchy;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Currency;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class StringRepresentationVariableMapperTest {

    private StringRepresentationVariableMapper realMapper = new StringRepresentationVariableMapper();

    @Test
    void mapArraySeveralComponentTypesTypeMetaTest() {
        TypeMeta<?> arrayTypeMeta = new TypeMeta<>(int[].class,
                new TypeMeta[]{ new TypeMeta<>(int.class), new TypeMeta<>(int.class) });
        Assertions.assertThrows(TypeResolvingException.class, () -> realMapper.mapTypeVariables(arrayTypeMeta));
    }

    @Test
    void mapArrayTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(Double[].class);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(Double[].class));
    }

    @Test
    void mapArrayTypeMetaTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(double[].class);
        TypeMeta<?> arrayTypeMeta = new TypeMeta<>(double[].class, new TypeMeta[]{ new TypeMeta<>(double.class) });
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(arrayTypeMeta));
    }

    @Test
    void mapClassWithParameterizedSuperclassTest() {
        Class<?> clazz = new HashSet<List<Short>>() {}.getClass();
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(clazz);
        String expectedRepresentation = String.format("%s<%s>", List.class.getName(), Short.class.getName());
        expectedHierarchy.addTypeVariable(HashSet.class, HashSet.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractSet.class, AbstractSet.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(AbstractCollection.class, AbstractCollection.class.getTypeParameters()[0],
                expectedRepresentation);
        expectedHierarchy.addTypeVariable(Set.class, Set.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Collection.class, Collection.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Iterable.class, Iterable.class.getTypeParameters()[0], expectedRepresentation);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(clazz));
    }

    @Test
    void mapClassWithParameterizedSuperclassTypeMetaTest() {
        Class<?> clazz = new HashSet<List<Short>>() {}.getClass();
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(clazz);
        String expectedRepresentation = String.format("%s<%s>", List.class.getName(), Short.class.getName());
        expectedHierarchy.addTypeVariable(HashSet.class, HashSet.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractSet.class, AbstractSet.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(AbstractCollection.class, AbstractCollection.class.getTypeParameters()[0],
                expectedRepresentation);
        expectedHierarchy.addTypeVariable(Set.class, Set.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Collection.class, Collection.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Iterable.class, Iterable.class.getTypeParameters()[0], expectedRepresentation);
        TypeMeta<?> typeMeta = new TypeMeta<>(clazz);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(typeMeta));
    }

    @Test
    void mapObjectTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(Object.class);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(Object.class));
    }

    @Test
    void mapObjectTypeMetaTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(Object.class);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(TypeMeta.OBJECT_META));
    }

    @Test
    void mapParameterizedClassByParameterizedClassTypeMetaTest() {
        TypeMeta<?> shortMeta = new TypeMeta<>(Short.class);
        TypeMeta<?> setMeta = new TypeMeta<>(Set.class, new TypeMeta<?>[]{ shortMeta });
        TypeMeta<?> listMeta = new TypeMeta<>(LinkedList.class, new TypeMeta<?>[]{ setMeta });
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(LinkedList.class);
        String expectedRepresentation = String.format("%s<%s>", Set.class.getName(), Short.class.getName());
        expectedHierarchy
                .addTypeVariable(LinkedList.class, LinkedList.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractSequentialList.class, AbstractSequentialList.class.getTypeParameters()[0],
                        expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractList.class, AbstractList.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(AbstractCollection.class, AbstractCollection.class.getTypeParameters()[0],
                expectedRepresentation);
        expectedHierarchy.addTypeVariable(List.class, List.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(Deque.class, Deque.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(Queue.class, Queue.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Collection.class, Collection.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Iterable.class, Iterable.class.getTypeParameters()[0], expectedRepresentation);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(listMeta));
    }

    @Test
    void mapParameterizedClassTypeMetaExtraMetasTest() {
        TypeMeta<?> listMeta = new TypeMeta<>(LinkedList.class,
                new TypeMeta<?>[]{ new TypeMeta<>(String.class), new TypeMeta<>(Byte.class) });
        Assertions.assertThrows(TypeResolvingException.class, () -> realMapper.mapTypeVariables(listMeta));
    }

    @Test
    void mapParameterizedClassTypeMetaNumberOfActualTypesIsLessThanNumberOfVariablesTest() {
        TypeMeta<?> mapMeta = new TypeMeta<>(HashMap.class, new TypeMeta<?>[]{ new TypeMeta<>(String.class) });
        Assertions.assertThrows(TypeResolvingException.class, () -> realMapper.mapTypeVariables(mapMeta));
    }

    @Test
    void mapParameterizedClassTypeMetaTest() {
        TypeMeta<?> shortMeta = new TypeMeta<>(Short.class);
        TypeMeta<?> listMeta = new TypeMeta<>(LinkedList.class, new TypeMeta<?>[]{ shortMeta });
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(LinkedList.class);
        String expectedRepresentation = Short.class.getName();
        expectedHierarchy
                .addTypeVariable(LinkedList.class, LinkedList.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractSequentialList.class, AbstractSequentialList.class.getTypeParameters()[0],
                        expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractList.class, AbstractList.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(AbstractCollection.class, AbstractCollection.class.getTypeParameters()[0],
                expectedRepresentation);
        expectedHierarchy.addTypeVariable(List.class, List.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(Deque.class, Deque.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(Queue.class, Queue.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Collection.class, Collection.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Iterable.class, Iterable.class.getTypeParameters()[0], expectedRepresentation);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(listMeta));
    }

    @Test
    void mapParameterizedClassVariableReplacedByObjectMetaTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(LinkedList.class);
        String expectedRepresentation = LinkedList.class.getTypeParameters()[0].getName();
        expectedHierarchy
                .addTypeVariable(LinkedList.class, LinkedList.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractSequentialList.class, AbstractSequentialList.class.getTypeParameters()[0],
                        expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(AbstractList.class, AbstractList.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(AbstractCollection.class, AbstractCollection.class.getTypeParameters()[0],
                expectedRepresentation);
        expectedHierarchy.addTypeVariable(List.class, List.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(Deque.class, Deque.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy.addTypeVariable(Queue.class, Queue.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Collection.class, Collection.class.getTypeParameters()[0], expectedRepresentation);
        expectedHierarchy
                .addTypeVariable(Iterable.class, Iterable.class.getTypeParameters()[0], expectedRepresentation);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(LinkedList.class));
    }

    @Test
    void mapParameterizedClassWrongTypesNumberTypeMetaTest() {
        TypeMeta<?> arrayTypeMeta = new TypeMeta<>(List.class,
                new TypeMeta[]{ new TypeMeta<>(Float.class), new TypeMeta<>(Boolean.class) });
        Assertions.assertThrows(TypeResolvingException.class, () -> realMapper.mapTypeVariables(arrayTypeMeta));
    }

    @Test
    void mapSimpleClassTypeMetaExtraMetasTest() {
        TypeMeta<?> longTypeMeta = new TypeMeta<>(Long.class, new TypeMeta<?>[]{ new TypeMeta<>(Short.class) });
        Assertions.assertThrows(TypeResolvingException.class, () -> realMapper.mapTypeVariables(longTypeMeta));
    }

    @Test
    void mapSimpleClassWithSeveralSuperclassesTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(Long.class);
        expectedHierarchy
                .addTypeVariable(Comparable.class, Comparable.class.getTypeParameters()[0], Long.class.getName());
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(Long.class));
    }

    @Test
    void mapSimpleClassWithSeveralSuperclassesTypeMetaTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(Long.class);
        TypeMeta<?> longTypeMeta = new TypeMeta<>(Long.class);
        expectedHierarchy
                .addTypeVariable(Comparable.class, Comparable.class.getTypeParameters()[0], Long.class.getName());
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(longTypeMeta));
    }

    @Test
    void mapSimpleObjectHeirTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(Currency.class);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(Currency.class));
    }

    @Test
    void mapSimpleObjectHeirTypeMetaTest() {
        MappedVariableHierarchy<String> expectedHierarchy = new MappedVariableHierarchy<>(Currency.class);
        TypeMeta<?> currencyTypeMeta = new TypeMeta<>(Currency.class);
        Assertions.assertEquals(expectedHierarchy, realMapper.mapTypeVariables(currencyTypeMeta));
    }
}
