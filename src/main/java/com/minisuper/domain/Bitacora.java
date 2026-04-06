package com.minisuper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "bitacora")
@ToString(exclude = "usuario")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    @EqualsAndHashCode.Include
    private Integer idBitacora;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String accion;

    @NotBlank
    @Size(max = 30)
    @Column(name = "tabla_afectada", nullable = false, length = 30)
    private String tablaAfectada;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @Size(max = 200)
    @Column(length = 200)
    private String detalle;

    @PrePersist
    public void prePersist() {
        if (fecha == null) {
            fecha = new Date();
        }
    }
}
