package com.minisuper.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "compra")
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Integer idCompra;

    @Column(name = "fecha_compra", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(precision = 10, scale = 2)
    @NotNull(message = "El total no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = true, message = "El total debe ser mayor o igual a cero")
    private BigDecimal total;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalles;
}