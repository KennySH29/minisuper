package com.minisuper.service;

import com.minisuper.domain.Cliente;
import com.minisuper.domain.DetalleVenta;
import com.minisuper.domain.MetodoPago;
import com.minisuper.domain.Producto;
import com.minisuper.domain.Usuario;
import com.minisuper.domain.Venta;
import com.minisuper.repository.ClienteRepository;
import com.minisuper.repository.DetalleVentaRepository;
import com.minisuper.repository.MetodoPagoRepository;
import com.minisuper.repository.ProductoRepository;
import com.minisuper.repository.UsuarioRepository;
import com.minisuper.repository.VentaRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final UsuarioRepository usuarioRepository;

    public VentaService(VentaRepository ventaRepository,
            DetalleVentaRepository detalleVentaRepository,
            ClienteRepository clienteRepository,
            ProductoRepository productoRepository,
            MetodoPagoRepository metodoPagoRepository,
            UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Venta> getVentas() {
        return ventaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MetodoPago> getMetodosPago() {
        return metodoPagoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public void registrarVenta(Integer idCliente,
            Integer idUsuario,
            Integer idMetodoPago,
            Integer idProducto,
            Integer cantidad) {

        if (idUsuario == null || idMetodoPago == null || idProducto == null || cantidad == null) {
            throw new IllegalArgumentException("Todos los campos obligatorios deben completarse.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Optional<MetodoPago> metodoOpt = metodoPagoRepository.findById(idMetodoPago);
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("El usuario no existe.");
        }

        if (metodoOpt.isEmpty()) {
            throw new IllegalArgumentException("El método de pago no existe.");
        }

        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        Cliente cliente = null;
        if (idCliente != null) {
            cliente = clienteRepository.findById(idCliente).orElse(null);
        }

        Producto producto = productoOpt.get();

        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

        Venta venta = new Venta();
        venta.setFechaVenta(new Date());
        venta.setCliente(cliente);
        venta.setUsuario(usuarioOpt.get());
        venta.setMetodoPago(metodoOpt.get());
        venta.setTotal(subtotal);

        Venta ventaGuardada = ventaRepository.save(venta);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(ventaGuardada);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(subtotal);

        detalleVentaRepository.save(detalle);
    }
}
