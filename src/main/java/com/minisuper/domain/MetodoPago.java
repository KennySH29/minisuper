package com.minisuper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "metodo_pago")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MetodoPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    @EqualsAndHashCode.Include
    private Integer idMetodoPago;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 30, message = "El nombre no puede superar 30 caracteres")
    @Column(nullable = false, unique = true, length = 30)
    private String nombre;

    @Size(max = 100, message = "La descripción no puede superar 100 caracteres")
    @Column(length = 100)
    private String descripcion;
}
