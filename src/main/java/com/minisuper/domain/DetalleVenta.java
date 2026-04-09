package com.minisuper.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_detalle_venta")
public class DetalleVenta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_venta")
    private Integer idDetalleVenta;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio debe ser mayor o igual a cero")
    private BigDecimal precioUnitario;

    @Column(precision = 10, scale = 2)
    @NotNull(message = "El subtotal no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = true, message = "El subtotal debe ser mayor o igual a cero")
    private BigDecimal subtotal;
}