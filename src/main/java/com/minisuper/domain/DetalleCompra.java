package com.minisuper.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_compra")
public class DetalleCompra implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_compra")
    private Integer idDetalleCompra;

    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "costo_unitario", precision = 10, scale = 2)
    @NotNull(message = "El costo no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = true, message = "El costo debe ser mayor o igual a cero")
    private BigDecimal costoUnitario;

    @Column(precision = 10, scale = 2)
    @NotNull(message = "El subtotal no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = true, message = "El subtotal debe ser mayor o igual a cero")
    private BigDecimal subtotal;
}