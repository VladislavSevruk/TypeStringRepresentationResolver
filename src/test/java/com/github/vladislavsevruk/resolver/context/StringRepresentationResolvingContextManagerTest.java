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
package com.github.vladislavsevruk.resolver.context;

import com.github.vladislavsevruk.resolver.resolver.picker.TypeResolverPicker;
import com.github.vladislavsevruk.resolver.resolver.storage.FullNameResolverStorage;
import com.github.vladislavsevruk.resolver.resolver.storage.TypeResolverStorage;
import com.github.vladislavsevruk.resolver.type.mapper.StringRepresentationVariableMapper;
import com.github.vladislavsevruk.resolver.type.mapper.TypeVariableMapper;
import com.github.vladislavsevruk.resolver.type.storage.MappedVariableHierarchyStorage;
import com.github.vladislavsevruk.resolver.type.storage.StringRepresentationMappedVariableHierarchyStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringRepresentationResolvingContextManagerTest {

    @Mock
    private MappedVariableHierarchyStorage<String> mappedVariableHierarchyStorage;
    @Mock
    private TypeResolverPicker<String> typeResolverPicker;
    @Mock
    private TypeResolverStorage<String> typeResolverStorage;
    @Mock
    private TypeVariableMapper<String> typeVariableMapper;

    @AfterAll
    static void setInitialAutoContextRefresh() {
        resetModulesAndContext();
    }

    @Test
    void autoRefreshContextAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.enableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory
                .replaceMappedVariableHierarchyStorage(context -> mappedVariableHierarchyStorage);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(context -> typeResolverPicker);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(context -> typeResolverStorage);
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(context -> typeVariableMapper);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(mappedVariableHierarchyStorage, resolvingContext2.getMappedVariableHierarchyStorage());
        Assertions.assertEquals(typeResolverPicker, resolvingContext2.getTypeResolverPicker());
        Assertions.assertEquals(typeResolverStorage, resolvingContext2.getTypeResolverStorage());
        Assertions.assertEquals(typeVariableMapper, resolvingContext2.getTypeVariableMapper());
    }

    @Test
    void autoRefreshContextAfterMappedVariableHierarchyStorageUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.enableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory
                .replaceMappedVariableHierarchyStorage(context -> mappedVariableHierarchyStorage);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(mappedVariableHierarchyStorage, resolvingContext2.getMappedVariableHierarchyStorage());
        Assertions.assertNotSame(resolvingContext1.getTypeResolverPicker(), resolvingContext2.getTypeResolverPicker());
        Assertions
                .assertNotSame(resolvingContext1.getTypeResolverStorage(), resolvingContext2.getTypeResolverStorage());
        Assertions.assertNotSame(resolvingContext1.getTypeVariableMapper(), resolvingContext2.getTypeVariableMapper());
    }

    @Test
    void autoRefreshContextAfterTypeResolverPickerUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.enableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(context -> typeResolverPicker);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(typeResolverPicker, resolvingContext2.getTypeResolverPicker());
        Assertions.assertNotSame(resolvingContext1.getTypeVariableMapper(), resolvingContext2.getTypeVariableMapper());
        Assertions
                .assertNotSame(resolvingContext1.getTypeResolverStorage(), resolvingContext2.getTypeResolverStorage());
        Assertions.assertNotSame(resolvingContext1.getMappedVariableHierarchyStorage(),
                resolvingContext2.getMappedVariableHierarchyStorage());
    }

    @Test
    void autoRefreshContextAfterTypeResolverStorageUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.enableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(context -> typeResolverStorage);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(typeResolverStorage, resolvingContext2.getTypeResolverStorage());
        Assertions.assertNotSame(resolvingContext1.getTypeResolverPicker(), resolvingContext2.getTypeResolverPicker());
        Assertions.assertNotSame(resolvingContext1.getTypeVariableMapper(), resolvingContext2.getTypeVariableMapper());
        Assertions.assertNotSame(resolvingContext1.getMappedVariableHierarchyStorage(),
                resolvingContext2.getMappedVariableHierarchyStorage());
    }

    @Test
    void autoRefreshContextAfterTypeVariableMapperUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.enableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(context -> typeVariableMapper);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertNotSame(resolvingContext1.getMappedVariableHierarchyStorage(),
                resolvingContext2.getMappedVariableHierarchyStorage());
        Assertions.assertNotSame(resolvingContext1.getTypeResolverPicker(), resolvingContext2.getTypeResolverPicker());
        Assertions
                .assertNotSame(resolvingContext1.getTypeResolverStorage(), resolvingContext2.getTypeResolverStorage());
        Assertions.assertEquals(typeVariableMapper, resolvingContext2.getTypeVariableMapper());
    }

    @Test
    void equalContextAfterRefreshWithoutUpdatesTest() {
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingContextManager.refreshContext();
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void newContextAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory
                .replaceMappedVariableHierarchyStorage(context -> mappedVariableHierarchyStorage);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(context -> typeResolverPicker);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(context -> typeResolverStorage);
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(context -> typeVariableMapper);
        StringRepresentationResolvingContextManager.refreshContext();
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(mappedVariableHierarchyStorage, resolvingContext2.getMappedVariableHierarchyStorage());
        Assertions.assertEquals(typeResolverPicker, resolvingContext2.getTypeResolverPicker());
        Assertions.assertEquals(typeResolverStorage, resolvingContext2.getTypeResolverStorage());
        Assertions.assertEquals(typeVariableMapper, resolvingContext2.getTypeVariableMapper());
    }

    @Test
    void newContextAfterRefreshAfterMappedVariableHierarchyStorageUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory
                .replaceMappedVariableHierarchyStorage(context -> mappedVariableHierarchyStorage);
        StringRepresentationResolvingContextManager.refreshContext();
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(mappedVariableHierarchyStorage, resolvingContext2.getMappedVariableHierarchyStorage());
        Assertions.assertNotSame(resolvingContext1.getTypeResolverPicker(), resolvingContext2.getTypeResolverPicker());
        Assertions
                .assertNotSame(resolvingContext1.getTypeResolverStorage(), resolvingContext2.getTypeResolverStorage());
        Assertions.assertNotSame(resolvingContext1.getTypeVariableMapper(), resolvingContext2.getTypeVariableMapper());
    }

    @Test
    void newContextAfterRefreshAfterTypeResolverPickerUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(context -> typeResolverPicker);
        StringRepresentationResolvingContextManager.refreshContext();
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(typeResolverPicker, resolvingContext2.getTypeResolverPicker());
        Assertions.assertNotSame(resolvingContext1.getTypeVariableMapper(), resolvingContext2.getTypeVariableMapper());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext2.getTypeVariableMapper().getClass());
        Assertions
                .assertNotSame(resolvingContext1.getTypeResolverStorage(), resolvingContext2.getTypeResolverStorage());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext2.getTypeResolverStorage().getClass());
        Assertions.assertNotSame(resolvingContext1.getMappedVariableHierarchyStorage(),
                resolvingContext2.getMappedVariableHierarchyStorage());
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext2.getMappedVariableHierarchyStorage().getClass());
    }

    @Test
    void newContextAfterRefreshAfterTypeResolverStorageUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(context -> typeResolverStorage);
        StringRepresentationResolvingContextManager.refreshContext();
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(typeResolverStorage, resolvingContext2.getTypeResolverStorage());
        Assertions.assertNotSame(resolvingContext1.getTypeResolverPicker(), resolvingContext2.getTypeResolverPicker());
        Assertions.assertNotSame(resolvingContext1.getTypeVariableMapper(), resolvingContext2.getTypeVariableMapper());
        Assertions.assertNotSame(resolvingContext1.getMappedVariableHierarchyStorage(),
                resolvingContext2.getMappedVariableHierarchyStorage());
    }

    @Test
    void newContextAfterRefreshAfterTypeVariableMapperUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(context -> typeVariableMapper);
        StringRepresentationResolvingContextManager.refreshContext();
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertNotSame(resolvingContext1.getTypeResolverPicker(), resolvingContext2.getTypeResolverPicker());
        Assertions
                .assertNotSame(resolvingContext1.getTypeResolverStorage(), resolvingContext2.getTypeResolverStorage());
        Assertions.assertEquals(typeVariableMapper, resolvingContext2.getTypeVariableMapper());
        Assertions.assertNotSame(resolvingContext1.getMappedVariableHierarchyStorage(),
                resolvingContext2.getMappedVariableHierarchyStorage());
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext2.getMappedVariableHierarchyStorage().getClass());
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterMappedVariableHierarchyStorageUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory
                .replaceMappedVariableHierarchyStorage(context -> mappedVariableHierarchyStorage);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory
                .replaceMappedVariableHierarchyStorage(context -> mappedVariableHierarchyStorage);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(context -> typeResolverPicker);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(context -> typeResolverStorage);
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(context -> typeVariableMapper);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterTypeResolverPickerUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(context -> typeResolverPicker);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterTypeResolverStorageUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(context -> typeResolverStorage);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterTypeVariableMapperUpdatesTest() {
        resetModulesAndContext();
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(context -> typeVariableMapper);
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedTest() {
        ResolvingContext<String> resolvingContext1 = StringRepresentationResolvingContextManager.getContext();
        ResolvingContext<String> resolvingContext2 = StringRepresentationResolvingContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    private static void resetModulesAndContext() {
        StringRepresentationResolvingContextManager.disableContextAutoRefresh();
        StringRepresentationResolvingModuleFactory.replaceMappedVariableHierarchyStorage(null);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(null);
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(null);
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(null);
        StringRepresentationResolvingContextManager.refreshContext();
    }
}
