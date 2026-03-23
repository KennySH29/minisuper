package com.minisuper.domain;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(unique = true, length = 80)
    private String correo;

    @Column(length = 200)
    private String direccion;
}
