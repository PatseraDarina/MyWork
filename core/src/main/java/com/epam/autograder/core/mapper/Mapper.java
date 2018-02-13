package com.epam.autograder.core.mapper;

/**
 * Mapper interface
 *
 * @param <E> The type of object which need to be mapped.
 * @param <T> The type of object into which the object is mapped.
 */
public interface Mapper<E, T> {

    /**
     * @param e The type of object which need to be mapped.
     * @param t Instance of object
     * @return Mapped object
     */
    T map(E e, T t);
}
