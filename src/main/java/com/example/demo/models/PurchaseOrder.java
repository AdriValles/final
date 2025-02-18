package com.example.demo.models;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Pedido")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "numeroPedido")
    private Long numeroPedido;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "total")
    private double total;

    @Column(name = "estado")
    private String estado;

    @Column(name = "usuario")
    private String usuario;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PurchaseOrder other = (PurchaseOrder) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public PurchaseOrder(Long id, Long numeroPedido, String descripcion, double total, String estado, String usuario) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.descripcion = descripcion;
        this.total = total;
        this.estado = estado;
        this.usuario = usuario;
    }

    public PurchaseOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    

}
