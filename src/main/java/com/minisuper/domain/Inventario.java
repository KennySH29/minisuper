package com.minisuper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "inventario")
@ToString(exclude = "producto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    @EqualsAndHashCode.Include
    private Integer idInventario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false, unique = true)
    private Producto producto;

    @NotNull(message = "La cantidad disponible no puede ser nula")
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible;

    @NotNull(message = "La cantidad mínima no puede ser nula")
    @Min(value = 0, message = "La cantidad mínima no puede ser negativa")
    @Column(name = "cantidad_minima", nullable = false)
    private Integer cantidadMinima;

    @Temporal(TemporalType.DATE)
    @Column(name = "ultima_actualizacion", nullable = false)
    private Date ultimaActualizacion;

    @PrePersist
    public void prePersist() {
        if (ultimaActualizacion == null) {
            ultimaActualizacion = new Date();
        }
    }

    @PreUpdate
    public void preUpdate() {
        ultimaActualizacion = new Date();
    }
}
