package com.minisuper.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_venta")
    private Integer idDetalleVenta;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(precision=12, scale=2) 
    @NotNull(message="El precio no puede ser nulo") //Se importa de jakarta
    @DecimalMin(value="0.01", inclusive=true, message="El precio debe ser mayor o igual a cero")
    private BigDecimal precioUnitario;
    
    @Column(precision=12, scale=2) 
    @NotNull(message="El precio no puede ser nulo") //Se importa de jakarta
    @DecimalMin(value="0.01", inclusive=true, message="El precio debe ser mayor o igual a cero")
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;
}