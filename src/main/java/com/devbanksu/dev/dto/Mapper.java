package com.devbanksu.dev.dto;

public interface Mapper<T, DTO> {
    T mapearDTOAObjeto(DTO dto);
    DTO mapearObjetoADTO(T objeto);
}
