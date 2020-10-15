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
import com.github.vladislavsevruk.resolver.resolver.storage.TypeResolverStorage;
import com.github.vladislavsevruk.resolver.type.mapper.TypeVariableMapper;
import com.github.vladislavsevruk.resolver.type.storage.MappedVariableHierarchyStorage;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Provides replaceable modules schemas required for string representation of generic types resolving mechanism.
 */
@Log4j2
public final class StringRepresentationResolvingModuleFactory {

    private static final ReadWriteLock MAPPED_VARIABLE_HIERARCHY_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock TYPE_RESOLVER_PICKER_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock TYPE_RESOLVER_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock TYPE_VARIABLE_MAPPER_LOCK = new ReentrantReadWriteLock();
    private static ResolvingModuleFactoryMethod<String, MappedVariableHierarchyStorage<String>>
            mappedVariableHierarchyStorage;
    private static ResolvingModuleFactoryMethod<String, TypeResolverPicker<String>> typeResolverPicker;
    private static ResolvingModuleFactoryMethod<String, TypeResolverStorage<String>> typeResolverStorage;
    private static ResolvingModuleFactoryMethod<String, TypeVariableMapper<String>> typeVariableMapper;

    private StringRepresentationResolvingModuleFactory() {
    }

    /**
     * Returns current instance of <code>ResolvingModuleFactoryMethod</code> for <code>MappedVariableHierarchyStorage</code>.
     */
    public static ResolvingModuleFactoryMethod<String, MappedVariableHierarchyStorage<String>> mappedVariableHierarchyStorage() {
        MAPPED_VARIABLE_HIERARCHY_STORAGE_LOCK.readLock().lock();
        ResolvingModuleFactoryMethod<String, MappedVariableHierarchyStorage<String>> storageToReturn
                = StringRepresentationResolvingModuleFactory.mappedVariableHierarchyStorage;
        MAPPED_VARIABLE_HIERARCHY_STORAGE_LOCK.readLock().unlock();
        return storageToReturn;
    }

    /**
     * Replaces instance of <code>ResolvingModuleFactoryMethod</code> for <code>MappedVariableHierarchyStorage</code>.
     * All further resolves will use new instance.
     *
     * @param storage new instance of <code>ResolvingModuleFactoryMethod</code> for <code>MappedVariableHierarchyStorage</code>.
     */
    public static void replaceMappedVariableHierarchyStorage(
            ResolvingModuleFactoryMethod<String, MappedVariableHierarchyStorage<String>> storage) {
        MAPPED_VARIABLE_HIERARCHY_STORAGE_LOCK.writeLock().lock();
        log.info(() -> String.format("Replacing MappedVariableHierarchyStorage by '%s'.",
                storage == null ? null : storage.getClass().getName()));
        StringRepresentationResolvingModuleFactory.mappedVariableHierarchyStorage = storage;
        MAPPED_VARIABLE_HIERARCHY_STORAGE_LOCK.writeLock().unlock();
        if (StringRepresentationResolvingContextManager.isAutoRefreshContext()) {
            StringRepresentationResolvingContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeResolverPicker</code>. All further
     * resolves will use new instance.
     *
     * @param picker new instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeResolverPicker</code>.
     */
    public static void replaceTypeResolverPicker(
            ResolvingModuleFactoryMethod<String, TypeResolverPicker<String>> picker) {
        TYPE_RESOLVER_PICKER_LOCK.writeLock().lock();
        log.info(() -> String
                .format("Replacing TypeResolverPicker by '%s'.", picker == null ? null : picker.getClass().getName()));
        StringRepresentationResolvingModuleFactory.typeResolverPicker = picker;
        TYPE_RESOLVER_PICKER_LOCK.writeLock().unlock();
        if (StringRepresentationResolvingContextManager.isAutoRefreshContext()) {
            StringRepresentationResolvingContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeResolverStorage</code>. All further
     * resolves will use new instance.
     *
     * @param storage new instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeResolverStorage</code>.
     */
    public static void replaceTypeResolverStorage(
            ResolvingModuleFactoryMethod<String, TypeResolverStorage<String>> storage) {
        TYPE_RESOLVER_STORAGE_LOCK.writeLock().lock();
        log.info(() -> String.format("Replacing TypeResolverStorage by '%s'.",
                storage == null ? null : storage.getClass().getName()));
        StringRepresentationResolvingModuleFactory.typeResolverStorage = storage;
        TYPE_RESOLVER_STORAGE_LOCK.writeLock().unlock();
        if (StringRepresentationResolvingContextManager.isAutoRefreshContext()) {
            StringRepresentationResolvingContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeVariableMapper</code>. All further
     * resolves will use new instance.
     *
     * @param mapper new instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeVariableMapper</code>.
     */
    public static void replaceTypeVariableMapper(
            ResolvingModuleFactoryMethod<String, TypeVariableMapper<String>> mapper) {
        TYPE_VARIABLE_MAPPER_LOCK.writeLock().lock();
        log.info(() -> String
                .format("Replacing TypeVariableMapper by '%s'.", mapper == null ? null : mapper.getClass().getName()));
        StringRepresentationResolvingModuleFactory.typeVariableMapper = mapper;
        TYPE_VARIABLE_MAPPER_LOCK.writeLock().unlock();
        if (StringRepresentationResolvingContextManager.isAutoRefreshContext()) {
            StringRepresentationResolvingContextManager.refreshContext();
        }
    }

    /**
     * Returns current instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeResolverPicker</code>.
     */
    public static ResolvingModuleFactoryMethod<String, TypeResolverPicker<String>> typeResolverPicker() {
        TYPE_RESOLVER_PICKER_LOCK.readLock().lock();
        ResolvingModuleFactoryMethod<String, TypeResolverPicker<String>> pickerToReturn
                = StringRepresentationResolvingModuleFactory.typeResolverPicker;
        TYPE_RESOLVER_PICKER_LOCK.readLock().unlock();
        return pickerToReturn;
    }

    /**
     * Returns current instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeResolverStorage</code>.
     */
    public static ResolvingModuleFactoryMethod<String, TypeResolverStorage<String>> typeResolverStorage() {
        TYPE_RESOLVER_STORAGE_LOCK.readLock().lock();
        ResolvingModuleFactoryMethod<String, TypeResolverStorage<String>> storageToReturn
                = StringRepresentationResolvingModuleFactory.typeResolverStorage;
        TYPE_RESOLVER_STORAGE_LOCK.readLock().unlock();
        return storageToReturn;
    }

    /**
     * Returns current instance of <code>ResolvingModuleFactoryMethod</code> for <code>TypeVariableMapper</code>.
     */
    public static ResolvingModuleFactoryMethod<String, TypeVariableMapper<String>> typeVariableMapper() {
        TYPE_VARIABLE_MAPPER_LOCK.readLock().lock();
        ResolvingModuleFactoryMethod<String, TypeVariableMapper<String>> mapperToReturn
                = StringRepresentationResolvingModuleFactory.typeVariableMapper;
        TYPE_VARIABLE_MAPPER_LOCK.readLock().unlock();
        return mapperToReturn;
    }
}
