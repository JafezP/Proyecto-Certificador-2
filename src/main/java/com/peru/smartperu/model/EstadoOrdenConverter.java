package com.peru.smartperu.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoOrdenConverter implements AttributeConverter<OrdenReparacion.EstadoOrden, String> {
    @Override
    public String convertToDatabaseColumn(OrdenReparacion.EstadoOrden attribute) {
        return attribute != null ? attribute.getDisplayValue() : null;
    }

    @Override
    public OrdenReparacion.EstadoOrden convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (OrdenReparacion.EstadoOrden estado : OrdenReparacion.EstadoOrden.values()) {
            if (estado.getDisplayValue().equals(dbData)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("EstadoOrden desconocido: " + dbData);
    }
}

