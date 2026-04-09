package com.minisuper.domain;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "metodo_pago")
public class MetodoPago implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo")
    private Integer idMetodo;

    @Column(nullable = false, unique = true, length = 50)
    private String descripcion;
}
