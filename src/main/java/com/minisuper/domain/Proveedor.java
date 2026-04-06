package com.minisuper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "proveedor")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    @EqualsAndHashCode.Include
    private Integer idProveedor;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede superar 15 caracteres")
    @Column(nullable = false, length = 15)
    private String telefono;

    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 80, message = "El correo no puede superar 80 caracteres")
    @Column(unique = true, length = 80)
    private String correo;

    @Size(max = 200, message = "La dirección no puede superar 200 caracteres")
    @Column(length = 200)
    private String direccion;
}
