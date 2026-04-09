package com.minisuper.domain;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "bitacora")
public class Bitacora implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Integer idBitacora;

    @Column(name = "fecha_evento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;

    @Column(nullable = false, length = 100)
    private String accion;

    @Column(length = 300)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
