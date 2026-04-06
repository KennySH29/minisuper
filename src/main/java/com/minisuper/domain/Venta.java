package com.minisuper.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "venta")
@ToString(exclude = {"usuario", "cliente", "metodoPago", "detalles"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    @EqualsAndHashCode.Include
    private Integer idVenta;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @NotNull(message = "El total no puede ser nulo")
    @DecimalMin(value = "0.01", inclusive = true, message = "El total debe ser mayor a cero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (fecha == null) {
            fecha = new Date();
        }
    }
}
