package proyecto.ludwingixcayau.modulo2;

/**
 * Created by ludwing ixcayau on 11/04/2015.
 */
public class Producto{
    private String nombre;
    private String tipo;
    private int precio;
    private int cantidad;

    public Producto(String nombre, String tipo, int precio, int cantidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
