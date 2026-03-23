package com.minisuper.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(name = "codigo_barras", unique = true, length = 20)
    private String codigoBarras;

    @Column(length = 150)
    private String descripcion;

    @Column(precision=12, scale=2) 
    @NotNull(message="El precio no puede ser nulo") //Se importa de jakarta
    @DecimalMin(value="0.01", inclusive=true, message="El precio de compra debe ser mayor a cero")
    private BigDecimal precioCompra;

    @Column(precision=12, scale=2) 
    @NotNull(message="El precio no puede ser nulo") //Se importa de jakarta
    @DecimalMin(value="0.01", inclusive=true, message="El precio de venta debe ser mayor a cero")
    private BigDecimal precioVenta;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}