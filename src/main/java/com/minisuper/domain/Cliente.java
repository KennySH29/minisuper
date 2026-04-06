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
@Table(name = "cliente")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    @EqualsAndHashCode.Include
    private Integer idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "La cédula es obligatoria")
    @Size(max = 20, message = "La cédula no puede superar 20 caracteres")
    @Column(nullable = false, unique = true, length = 20)
    private String cedula;

    @Size(max = 15, message = "El teléfono no puede superar 15 caracteres")
    @Column(length = 15)
    private String telefono;

    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 80, message = "El correo no puede superar 80 caracteres")
    @Column(unique = true, length = 80)
    private String correo;
}
