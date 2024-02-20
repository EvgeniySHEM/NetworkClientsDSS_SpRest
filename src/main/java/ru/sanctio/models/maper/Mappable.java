package ru.sanctio.models.maper;

import java.util.List;

public interface Mappable <E, D>{

    E toEntity(D d);

    D toDto(E e);

    List<D> toDtoList(List<E> e);
}
