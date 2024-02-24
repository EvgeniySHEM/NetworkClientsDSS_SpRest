package ru.sanctio.models.maper;

import java.util.List;

/**
 * Интерфейс для реализации маппинга данных
 * @param <E>
 * @param <D>
 */
public interface Mappable <E, D>{

    E toEntity(D d);

    D toDto(E e);

    List<D> toDtoList(List<E> e);
}
