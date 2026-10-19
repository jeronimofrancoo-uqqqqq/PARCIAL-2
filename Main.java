import java.util.ArrayList;
import javax.swing.*;


enum EspecialidadTecnico {
    COMPUTADORES,
    DISPOSITIVOS_MOVILES,
    SOPORTE_GENERAL
} enum EstadoLaboral {
    ACTIVO,
    INACTIVO
} enum EstadoOrden {
    ABIERTA,
    EN_PROCESO,
    FINALIZADA,
    ENTREGADA,
    CANCELADA
} enum EstadoIntervencion {
    PENDIENTE,
    EN_PROCESO,
    FINALIZADO
}

// ==========================================
// CLASES DE DOMINIO Y NEGOCIO
// ==========================================

class Cliente {
    private String documento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccion;
    private ArrayList<EquipoTecnologico> equipos;
    private ArrayList<OrdenServicio> ordenes;

    public Cliente(String documento, String nombreCompleto, String telefono, String correo, String direccion) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.equipos = new ArrayList<>();
        this.ordenes = new ArrayList<>();
    }

    public String getDocumento() { return documento; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
    public ArrayList<EquipoTecnologico> getEquipos() { return equipos; }
    public ArrayList<OrdenServicio> getOrdenes() { return ordenes; }

    public void agregarEquipo(EquipoTecnologico equipo) {
        if (!equipos.contains(equipo)) equipos.add(equipo);
    }

    public void agregarOrden(OrdenServicio orden) {
        if (!ordenes.contains(orden)) ordenes.add(orden);
    }

    @Override
    public String toString() {
        return "Cliente [Doc: " + documento + ", Nombre: " + nombreCompleto + ", Tel: " + telefono + "]";
    }
}

abstract class EquipoTecnologico {
    private String codigo;
    private String numeroSerie;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private String estadoFisico;

    public EquipoTecnologico(String codigo, String numeroSerie, String marca, String modelo, int anioFabricacion, String estadoFisico) {
        this.codigo = codigo;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.estadoFisico = estadoFisico;
    }

    public String getCodigo() { return codigo; }
    public String getNumeroSerie() { return numeroSerie; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnioFabricacion() { return anioFabricacion; }
    public String getEstadoFisico() { return estadoFisico; }

    public abstract String getCategoriaEquipo();

    @Override
    public String toString() {
        return "[" + getCategoriaEquipo() + "] Código: " + codigo + " - " + marca + " " + modelo;
    }
}

class ComputadorPortatil extends EquipoTecnologico {
    private double tamanoPantalla;
    private int memoriaRamGB;
    private int almacenamientoGB;
    private boolean bateriaExtraible;

    public ComputadorPortatil(String codigo, String serie, String marca, String modelo, int anio, String estado,
                              double pantalla, int ram, int alm, boolean bateria) {
        super(codigo, serie, marca, modelo, anio, estado);
        this.tamanoPantalla = pantalla;
        this.memoriaRamGB = ram;
        this.almacenamientoGB = alm;
        this.bateriaExtraible = bateria;
    }

    @Override
    public String getCategoriaEquipo() { return "PORTATIL"; }
}

class ComputadorEscritorio extends EquipoTecnologico {
    private String tipoProcesador;
    private int memoriaRamGB;
    private int almacenamientoGB;
    private boolean tarjetaGraficaDedicada;

    public ComputadorEscritorio(String codigo, String serie, String marca, String modelo, int anio, String estado,
                                String procesador, int ram, int alm, boolean gpu) {
        super(codigo, serie, marca, modelo, anio, estado);
        this.tipoProcesador = procesador;
        this.memoriaRamGB = ram;
        this.almacenamientoGB = alm;
        this.tarjetaGraficaDedicada = gpu;
    }

    @Override
    public String getCategoriaEquipo() { return "ESCRITORIO"; }
}

class TelefonoInteligente extends EquipoTecnologico {
    private String sistemaOperativo;
    private int almacenamientoGB;
    private int capacidadBateriaMah;
    private boolean compatible5G;

    public TelefonoInteligente(String codigo, String serie, String marca, String modelo, int anio, String estado,
                               String os, int alm, int mah, boolean g5) {
        super(codigo, serie, marca, modelo, anio, estado);
        this.sistemaOperativo = os;
        this.almacenamientoGB = alm;
        this.capacidadBateriaMah = mah;
        this.compatible5G = g5;
    }

    @Override
    public String getCategoriaEquipo() { return "TELEFONO"; }
}

class Tecnico {
    private String codigo;
    private String nombre;
    private String documento;
    private EspecialidadTecnico especialidad;
    private EstadoLaboral estadoLaboral;

    public Tecnico(String codigo, String nombre, String documento, EspecialidadTecnico especialidad, EstadoLaboral estadoLaboral) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.documento = documento;
        this.especialidad = especialidad;
        this.estadoLaboral = estadoLaboral;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public EspecialidadTecnico getEspecialidad() { return especialidad; }
    public EstadoLaboral getEstadoLaboral() { return estadoLaboral; }
    
    public void setEstadoLaboral(EstadoLaboral estadoLaboral) { this.estadoLaboral = estadoLaboral; }

    @Override
    public String toString() {
        return "Técnico [" + codigo + "] " + nombre + " (" + especialidad + ") - " + estadoLaboral;
    }
}

abstract class Servicio {
    private String codigo;
    private String nombre;
    private String descripcion;
    protected double tarifaBase;

    public Servicio(String codigo, String nombre, String descripcion, double tarifaBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tarifaBase = tarifaBase;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    public abstract double calcularCosto(DetalleServicio detalle);
}

class Diagnostico extends Servicio {
    public Diagnostico(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, 35000.0);
    }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        return 35000.0;
    }
}

class MantenimientoPreventivo extends Servicio {
    public MantenimientoPreventivo(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, 0.0);
    }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        EquipoTecnologico tipoDeEquipo = detalle.getEquipo();
        if (tipoDeEquipo instanceof ComputadorPortatil) return 60000.0;
        if (tipoDeEquipo instanceof ComputadorEscritorio) return 70000.0;
        if (tipoDeEquipo instanceof TelefonoInteligente) return 40000.0;
        return 50000.0;
    }
}

class ReparacionCorrectiva extends Servicio {
    private static final double TARIFA_HORA = 45000.0;

    public ReparacionCorrectiva(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, 0.0);
    }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        double costoManoObra = detalle.getHorasTrabajo() * TARIFA_HORA;
        double costoRepuestos = 0.0;
        for (RepuestoUtilizado ru : detalle.getRepuestosUtilizados()) {
            costoRepuestos += ru.calcularSubtotal();
        }
        return costoManoObra + costoRepuestos;
    }
}

class Repuesto {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private int cantidadDisponible;
    private String categoriaCompatibilidad;

    public Repuesto(String codigo, String nombre, String descripcion, double precioUnitario, int cantidadDisponible, String categoriaCompatibilidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.cantidadDisponible = cantidadDisponible;
        this.categoriaCompatibilidad = categoriaCompatibilidad;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getCantidadDisponible() { return cantidadDisponible; }

    public boolean descontarStock(int cantidad) {
        if (cantidad > 0 && this.cantidadDisponible >= cantidad) {
            this.cantidadDisponible -= cantidad;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Repuesto [" + codigo + "] " + nombre + " | Stock: " + cantidadDisponible + " | P/U: $" + precioUnitario;
    }
}

class RepuestoUtilizado {
    private Repuesto repuesto;
    private int cantidad;
    private double precioUnitarioAplicado;

    public RepuestoUtilizado(Repuesto repuesto, int cantidad, double precioUnitarioAplicado) {
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.precioUnitarioAplicado = precioUnitarioAplicado;
    }

    public Repuesto getRepuesto() { return repuesto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitarioAplicado() { return precioUnitarioAplicado; }

    public double calcularSubtotal() {
        return cantidad * precioUnitarioAplicado;
    }
}

class DetalleServicio {
    private String codigoDetalle;
    private EquipoTecnologico equipo;
    private Servicio servicio;
    private String descripcionFalla;
    private Tecnico tecnico;
    private double horasTrabajo;
    private String observacionesTecnicas;
    private EstadoIntervencion estado;
    private ArrayList<RepuestoUtilizado> repuestosUtilizados;

    public DetalleServicio(String codigoDetalle, EquipoTecnologico equipo, Servicio servicio, String descripcionFalla) {
        this.codigoDetalle = codigoDetalle;
        this.equipo = equipo;
        this.servicio = servicio;
        this.descripcionFalla = descripcionFalla;
        this.tecnico = null;
        this.horasTrabajo = 0.0;
        this.observacionesTecnicas = "Sin observaciones";
        this.estado = EstadoIntervencion.PENDIENTE;
        this.repuestosUtilizados = new ArrayList<>();
    }

    public String getCodigoDetalle() { return codigoDetalle; }
    public EquipoTecnologico getEquipo() { return equipo; }
    public Servicio getServicio() { return servicio; }
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
    public double getHorasTrabajo() { return horasTrabajo; }
    public EstadoIntervencion getEstado() { return estado; }
    public ArrayList<RepuestoUtilizado> getRepuestosUtilizados() { return repuestosUtilizados; }
    public void setHorasTrabajo(double horasTrabajo) { this.horasTrabajo = horasTrabajo; }
    public void setEstado(EstadoIntervencion estado) { this.estado = estado; }

    public void agregarRepuestoUtilizado(RepuestoUtilizado ru) {
        this.repuestosUtilizados.add(ru);
    }

    public double calcularCostoDetalle() {
        return servicio.calcularCosto(this);
    }

    @Override
    public String toString() {
        return "Detalle [" + codigoDetalle + "] Serv: " + servicio.getNombre() + " | Eq: " + equipo.getMarca() + " | Estado: " + estado + " | Costo: $" + calcularCostoDetalle();
    }
}

class OrdenServicio {
    private String codigo;
    private String fechaCreacion;
    private Cliente cliente;
    private String observaciones;
    private EstadoOrden estado;
    private ArrayList<DetalleServicio> detalles;

    public OrdenServicio(String codigo, String fechaCreacion, Cliente cliente, String observaciones) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.observaciones = observaciones;
        this.estado = EstadoOrden.ABIERTA;
        this.detalles = new ArrayList<>();
    }

    public String getCodigo() { return codigo; }
    public String getFechaCreacion() { return fechaCreacion; }
    public Cliente getCliente() { return cliente; }
    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }
    public ArrayList<DetalleServicio> getDetalles() { return detalles; }

    public void agregarDetalle(DetalleServicio detalle) {
        this.detalles.add(detalle);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (DetalleServicio d : detalles) {
            total += d.calcularCostoDetalle();
        }
        return total;
    }

    public boolean todosDetallesFinalizados() {
        if (detalles.isEmpty()) return false;
        for (DetalleServicio d : detalles) {
            if (d.getEstado() != EstadoIntervencion.FINALIZADO) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Orden [" + codigo + "] Cliente: " + cliente.getNombreCompleto() + " | Estado: " + estado + " | Total: $" + calcularTotal();
    }
}

//CLASE PRINCIPAL
class GestorTechRepair {
    private ArrayList<Cliente> clientes;
    private ArrayList<EquipoTecnologico> equipos;
    private ArrayList<Tecnico> tecnicos;
    private ArrayList<Repuesto> repuestos;
    private ArrayList<Servicio> servicios;
    private ArrayList<OrdenServicio> ordenes;

    public GestorTechRepair() {
        clientes = new ArrayList<>();
        equipos = new ArrayList<>();
        tecnicos = new ArrayList<>();
        repuestos = new ArrayList<>();
        servicios = new ArrayList<>();
        ordenes = new ArrayList<>();
        inicializarServicios();
    }

    private void inicializarServicios() {
        servicios.add(new Diagnostico("S01", "Diagnóstico General", "Evaluación inicial de fallas"));
        servicios.add(new MantenimientoPreventivo("S02", "Mantenimiento Preventivo", "Limpieza y optimización"));
        servicios.add(new ReparacionCorrectiva("S03", "Reparación Correctiva", "Sustitución de componentes y arreglo"));
    }

    public boolean registrarCliente(String documento, String nombre, String telefono, String correo, String direccion) {
        if (documento == null || documento.isEmpty() || buscarCliente(documento) != null) return false;
        clientes.add(new Cliente(documento, nombre, telefono, correo, direccion));
        return true;
    }

    public boolean registrarEquipo(String documentoCliente, EquipoTecnologico eq) {
        Cliente cli = buscarCliente(documentoCliente);
        if (cli == null || eq == null || buscarEquipo(eq.getCodigo()) != null) return false;
        equipos.add(eq);
        cli.agregarEquipo(eq);
        return true;
    }

    public boolean registrarTecnico(String cod, String nom, String doc, EspecialidadTecnico esp, EstadoLaboral est) {
        if (buscarTecnico(cod) != null) return false;
        tecnicos.add(new Tecnico(cod, nom, doc, esp, est));
        return true;
    }

    public boolean registrarRepuesto(String cod, String nom, String desc, double precio, int stock, String cat) {
        if (precio < 0 || stock < 0 || buscarRepuesto(cod) != null) return false;
        repuestos.add(new Repuesto(cod, nom, desc, precio, stock, cat));
        return true;
    }

    public OrdenServicio crearOrden(String codOrd, String fecha, String docCli, String obs) {
        Cliente cli = buscarCliente(docCli);
        if (cli == null || buscarOrden(codOrd) != null) return null;
        OrdenServicio ord = new OrdenServicio(codOrd, fecha, cli, obs);
        ordenes.add(ord);
        cli.agregarOrden(ord);
        return ord;
    }

    public boolean agregarDetalleOrden(String codOrd, String codDet, String codEq, String codServ, String falla) {
        OrdenServicio ord = buscarOrden(codOrd);
        EquipoTecnologico eq = buscarEquipo(codEq);
        Servicio serv = buscarServicio(codServ);

        if (ord == null || eq == null || serv == null) return false;
        if (ord.getEstado() == EstadoOrden.FINALIZADA || ord.getEstado() == EstadoOrden.ENTREGADA || ord.getEstado() == EstadoOrden.CANCELADA) {
            return false;
        }
        if (!ord.getCliente().getEquipos().contains(eq)) return false;

        DetalleServicio det = new DetalleServicio(codDet, eq, serv, falla);
        ord.agregarDetalle(det);
        return true;
    }

    public boolean asignarTecnico(String codOrd, String codDet, String codTec) {
        OrdenServicio ord = buscarOrden(codOrd);
        if (ord == null) return false;
        DetalleServicio det = null;
        for (DetalleServicio d : ord.getDetalles()) {
            if (d.getCodigoDetalle().equals(codDet)) { det = d; break; }
        }
        if (det == null) return false;
        Tecnico tec = buscarTecnico(codTec);
        if (tec == null || tec.getEstadoLaboral() == EstadoLaboral.INACTIVO) return false;

        det.setTecnico(tec);
        det.setEstado(EstadoIntervencion.EN_PROCESO);
        if (ord.getEstado() == EstadoOrden.ABIERTA) ord.setEstado(EstadoOrden.EN_PROCESO);
        return true;
    }

    public boolean registrarRepuestoEnDetalle(String codOrd, String codDet, String codRep, int cant) {
        if (cant <= 0) return false;
        OrdenServicio ord = buscarOrden(codOrd);
        if (ord == null) return false;
        DetalleServicio det = null;
        for (DetalleServicio d : ord.getDetalles()) {
            if (d.getCodigoDetalle().equals(codDet)) { det = d; break; }
        }
        if (det == null || !(det.getServicio() instanceof ReparacionCorrectiva)) return false;

        Repuesto rep = buscarRepuesto(codRep);
        if (rep == null || rep.getCantidadDisponible() < cant) return false;

        if (!rep.descontarStock(cant)) return false;
        det.agregarRepuestoUtilizado(new RepuestoUtilizado(rep, cant, rep.getPrecioUnitario()));
        return true;
    }

    public boolean actualizarEstadoDetalle(String codOrd, String codDet, EstadoIntervencion nuevoEst, double horas) {
        OrdenServicio ord = buscarOrden(codOrd);
        if (ord == null) return false;
        DetalleServicio det = null;
        for (DetalleServicio d : ord.getDetalles()) {
            if (d.getCodigoDetalle().equals(codDet)) { det = d; break; }
        }
        if (det == null) return false;

        if (nuevoEst == EstadoIntervencion.FINALIZADO) {
            if (det.getTecnico() == null) return false;
            if (det.getServicio() instanceof ReparacionCorrectiva && horas <= 0) return false;
            det.setHorasTrabajo(horas);
        }
        det.setEstado(nuevoEst);
        return true;
    }

    public boolean finalizarOrden(String codOrd) {
        OrdenServicio ord = buscarOrden(codOrd);
        if (ord == null || ord.getDetalles().isEmpty() || !ord.todosDetallesFinalizados()) return false;
        ord.setEstado(EstadoOrden.FINALIZADA);
        return true;
    }

    public Cliente buscarCliente(String doc) {
        for (Cliente c : clientes) if (c.getDocumento().equals(doc)) return c;
        return null;
    }

    public EquipoTecnologico buscarEquipo(String cod) {
        for (EquipoTecnologico e : equipos) if (e.getCodigo().equals(cod)) return e;
        return null;
    }

    public Tecnico buscarTecnico(String cod) {
        for (Tecnico t : tecnicos) if (t.getCodigo().equals(cod)) return t;
        return null;
    }

    public Repuesto buscarRepuesto(String cod) {
        for (Repuesto r : repuestos) if (r.getCodigo().equals(cod)) return r;
        return null;
    }

    public Servicio buscarServicio(String cod) {
        for (Servicio s : servicios) if (s.getCodigo().equals(cod)) return s;
        return null;
    }

    public OrdenServicio buscarOrden(String cod) {
        for (OrdenServicio o : ordenes) if (o.getCodigo().equals(cod)) return o;
        return null;
    }

    public ArrayList<Cliente> getClientes() { return clientes; }
    public ArrayList<Tecnico> getTecnicos() { return tecnicos; }
    public ArrayList<Repuesto> getRepuestos() { return repuestos; }
    public ArrayList<OrdenServicio> getOrdenes() { return ordenes; }
    public ArrayList<EquipoTecnologico> getEquipos() { return equipos; }
}

// ==========================================
// CLASE PRINCIPAL CON INTERFAZ JOPTIONPANE
// ==========================================
public class Main {
    public static void main(String[] args) {
        GestorTechRepair gestor = new GestorTechRepair();
        cargarDatosDemo(gestor);

        int opcion = 0;
        do {
            String menu = "=== SISTEMA DE GESTIÓN TECHREPAIR ==="
                    + "\n 1. Registrar cliente"
                    + "\n 2. Registrar equipo tecnológico"
                    + "\n 3. Registrar técnico"
                    + "\n 4. Registrar repuesto en inventario"
                    + "\n 5. Crear orden de servicio"
                    + "\n 6. Agregar detalle a orden de servicio"
                    + "\n 7. Asignar técnico a detalle"
                    + "\n 8. Registrar repuestos utilizados"
                    + "\n 9. Consultar costo de orden o detalle"
                    + "\n10. Actualizar estado de un detalle"
                    + "\n11. Finalizar orden de servicio"
                    + "\n12. Consultar órdenes de un cliente"
                    + "\n13. Consultar trabajos de un técnico"
                    + "\n14. Identificar equipos pendientes"
                    + "\n15. Generar reporte de servicios y facturación"
                    + "\n16. Generar reporte de técnicos"
                    + "\n17. Consultar inventario crítico (< 5)"
                    + "\n18. Salir"
                    + "\n\nIngrese el número de la opción deseada:";

            String input = JOptionPane.showInputDialog(null, menu, "TechRepair - Menú Principal", JOptionPane.QUESTION_MESSAGE);
            if (input == null) break;

            try {
                opcion = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            switch (opcion) {
                case 1: {
                    String doc = JOptionPane.showInputDialog("Documento de identidad:");
                    String nom = JOptionPane.showInputDialog("Nombre completo:");
                    String tel = JOptionPane.showInputDialog("Teléfono:");
                    String mail = JOptionPane.showInputDialog("Correo electrónico:");
                    String dir = JOptionPane.showInputDialog("Dirección de residencia:");
                    if (gestor.registrarCliente(doc, nom, tel, mail, dir)) {
                        JOptionPane.showMessageDialog(null, "¡Cliente registrado con éxito!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Documento duplicado o datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 2: {
                    String docC = JOptionPane.showInputDialog("Documento del cliente propietario:");
                    String tipoStr = JOptionPane.showInputDialog("Tipo de equipo:\n1. Computador Portátil\n2. Computador de Escritorio\n3. Teléfono Inteligente");
                    if (tipoStr == null) break;
                    int tipo = Integer.parseInt(tipoStr.trim());

                    String cod = JOptionPane.showInputDialog("Código único de equipo:");
                    String serie = JOptionPane.showInputDialog("Número de serie:");
                    String marca = JOptionPane.showInputDialog("Marca:");
                    String modelo = JOptionPane.showInputDialog("Modelo:");
                    int anio = Integer.parseInt(JOptionPane.showInputDialog("Año de fabricación:"));
                    String estFis = JOptionPane.showInputDialog("Estado físico inicial:");

                    EquipoTecnologico eq = null;
                    if (tipo == 1) {
                        double pant = Double.parseDouble(JOptionPane.showInputDialog("Tamaño pantalla (pulgadas):"));
                        int ram = Integer.parseInt(JOptionPane.showInputDialog("Memoria RAM (GB):"));
                        int alm = Integer.parseInt(JOptionPane.showInputDialog("Almacenamiento (GB):"));
                        boolean bat = Boolean.parseBoolean(JOptionPane.showInputDialog("¿Batería extraíble? (true/false):"));
                        eq = new ComputadorPortatil(cod, serie, marca, modelo, anio, estFis, pant, ram, alm, bat);
                    } else if (tipo == 2) {
                        String proc = JOptionPane.showInputDialog("Tipo de procesador:");
                        int ram = Integer.parseInt(JOptionPane.showInputDialog("Memoria RAM (GB):"));
                        int alm = Integer.parseInt(JOptionPane.showInputDialog("Almacenamiento (GB):"));
                        boolean gpu = Boolean.parseBoolean(JOptionPane.showInputDialog("¿Tarjeta gráfica dedicada? (true/false):"));
                        eq = new ComputadorEscritorio(cod, serie, marca, modelo, anio, estFis, proc, ram, alm, gpu);
                    } else if (tipo == 3) {
                        String os = JOptionPane.showInputDialog("Sistema Operativo:");
                        int alm = Integer.parseInt(JOptionPane.showInputDialog("Almacenamiento (GB):"));
                        int mah = Integer.parseInt(JOptionPane.showInputDialog("Capacidad batería (mAh):"));
                        boolean g5 = Boolean.parseBoolean(JOptionPane.showInputDialog("¿Compatible 5G? (true/false):"));
                        eq = new TelefonoInteligente(cod, serie, marca, modelo, anio, estFis, os, alm, mah, g5);
                    }

                    if (gestor.registrarEquipo(docC, eq)) {
                        JOptionPane.showMessageDialog(null, "¡Equipo registrado y asociado con éxito!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Cliente no existe o código de equipo duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 3: {
                    String cod = JOptionPane.showInputDialog("Código de técnico:");
                    String nom = JOptionPane.showInputDialog("Nombre completo:");
                    String doc = JOptionPane.showInputDialog("Documento de identidad:");
                    String espStr = JOptionPane.showInputDialog("Especialidad:\n1. COMPUTADORES\n2. DISPOSITIVOS_MOVILES\n3. SOPORTE_GENERAL");
                    int espOpt = Integer.parseInt(espStr.trim());
                    EspecialidadTecnico esp = EspecialidadTecnico.SOPORTE_GENERAL;
                    if (espOpt == 1) esp = EspecialidadTecnico.COMPUTADORES;
                    else if (espOpt == 2) esp = EspecialidadTecnico.DISPOSITIVOS_MOVILES;

                    if (gestor.registrarTecnico(cod, nom, doc, esp, EstadoLaboral.ACTIVO)) {
                        JOptionPane.showMessageDialog(null, "¡Técnico registrado con éxito!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Código o documento duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 4: {
                    String cod = JOptionPane.showInputDialog("Código de repuesto:");
                    String nom = JOptionPane.showInputDialog("Nombre del repuesto:");
                    String desc = JOptionPane.showInputDialog("Descripción:");
                    double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio unitario:"));
                    int stock = Integer.parseInt(JOptionPane.showInputDialog("Existencias iniciales (stock):"));
                    String cat = JOptionPane.showInputDialog("Categoría de compatibilidad:");

                    if (gestor.registrarRepuesto(cod, nom, desc, precio, stock, cat)) {
                        JOptionPane.showMessageDialog(null, "¡Repuesto registrado en inventario!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Valores negativos o código duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 5: {
                    String cod = JOptionPane.showInputDialog("Código único de orden:");
                    String fecha = JOptionPane.showInputDialog("Fecha de creación (AAAA-MM-DD):");
                    String docC = JOptionPane.showInputDialog("Documento del cliente:");
                    String obs = JOptionPane.showInputDialog("Observaciones generales:");

                    OrdenServicio ord = gestor.crearOrden(cod, fecha, docC, obs);
                    if (ord != null) {
                        JOptionPane.showMessageDialog(null, "¡Orden creada exitosamente (Estado: ABIERTA)!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Cliente no existe o código de orden duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 6: {
                    String codOrd = JOptionPane.showInputDialog("Código de orden existente:");
                    String codDet = JOptionPane.showInputDialog("Código único del nuevo detalle:");
                    String codEq = JOptionPane.showInputDialog("Código del equipo del cliente:");
                    String codServ = JOptionPane.showInputDialog("Código de servicio (S01: Diagnóstico, S02: Preventivo, S03: Correctivo):");
                    String falla = JOptionPane.showInputDialog("Descripción de la falla o necesidad:");

                    if (gestor.agregarDetalleOrden(codOrd, codDet, codEq, codServ, falla)) {
                        JOptionPane.showMessageDialog(null, "¡Detalle agregado a la orden con éxito!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Orden cerrada, equipo no pertenece al cliente o datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 7: {
                    String codOrd = JOptionPane.showInputDialog("Código de orden:");
                    String codDet = JOptionPane.showInputDialog("Código de detalle:");
                    String codTec = JOptionPane.showInputDialog("Código de técnico responsable:");

                    if (gestor.asignarTecnico(codOrd, codDet, codTec)) {
                        JOptionPane.showMessageDialog(null, "¡Técnico asignado correctamente al detalle!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Técnico inactivo, no existe o datos incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 8: {
                    String codOrd = JOptionPane.showInputDialog("Código de orden:");
                    String codDet = JOptionPane.showInputDialog("Código de detalle:");
                    String codRep = JOptionPane.showInputDialog("Código de repuesto:");
                    int cant = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a utilizar:"));

                    if (gestor.registrarRepuestoEnDetalle(codOrd, codDet, codRep, cant)) {
                        JOptionPane.showMessageDialog(null, "¡Repuesto incorporado y descontado del inventario!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Stock insuficiente, servicio no es correctivo o datos incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 9: {
                    String codOrd = JOptionPane.showInputDialog("Ingrese código de orden a consultar:");
                    OrdenServicio ord = gestor.buscarOrden(codOrd);
                    if (ord != null) {
                        StringBuilder sb = new StringBuilder("=== ORDEN DE SERVICIO: " + ord.getCodigo() + " ===\n");
                        sb.append("Cliente: ").append(ord.getCliente().getNombreCompleto()).append("\n");
                        sb.append("Estado Orden: ").append(ord.getEstado()).append("\n\nDetalles:\n");
                        for (DetalleServicio d : ord.getDetalles()) {
                            sb.append(" - ").append(d.getCodigoDetalle()).append(" | ").append(d.getServicio().getNombre())
                              .append(" | Costo: $").append(d.calcularCostoDetalle()).append("\n");
                        }
                        sb.append("\nVALOR TOTAL DE LA ORDEN: $").append(ord.calcularTotal());
                        mostrarTextoEnVentana(sb.toString(), "Consulta de Orden y Costos");
                    } else {
                        JOptionPane.showMessageDialog(null, "Orden no encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                }
                case 10: {
                    String codOrd = JOptionPane.showInputDialog("Código de orden:");
                    String codDet = JOptionPane.showInputDialog("Código de detalle:");
                    String estStr = JOptionPane.showInputDialog("Nuevo Estado:\n1. PENDIENTE\n2. EN_PROCESO\n3. FINALIZADO");
                    int estOpt = Integer.parseInt(estStr.trim());
                    EstadoIntervencion nuevoEst = EstadoIntervencion.PENDIENTE;
                    if (estOpt == 2) nuevoEst = EstadoIntervencion.EN_PROCESO;
                    else if (estOpt == 3) nuevoEst = EstadoIntervencion.FINALIZADO;

                    double horas = 0.0;
                    if (nuevoEst == EstadoIntervencion.FINALIZADO) {
                        horas = Double.parseDouble(JOptionPane.showInputDialog("Horas de trabajo invertidas:"));
                    }

                    if (gestor.actualizarEstadoDetalle(codOrd, codDet, nuevoEst, horas)) {
                        JOptionPane.showMessageDialog(null, "¡Estado de intervención actualizado!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Faltan requisitos (ej. técnico asignado u horas requeridas).", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 11: {
                    String codOrd = JOptionPane.showInputDialog("Código de orden a finalizar:");
                    if (gestor.finalizarOrden(codOrd)) {
                        JOptionPane.showMessageDialog(null, "¡Orden finalizada exitosamente!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: La orden tiene detalles pendientes o vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 12: {
                    String docCli = JOptionPane.showInputDialog("Documento del cliente:");
                    Cliente cli = gestor.buscarCliente(docCli);
                    if (cli != null) {
                        StringBuilder sb = new StringBuilder("=== ÓRDENES DE: " + cli.getNombreCompleto() + " ===\n\n");
                        for (OrdenServicio o : cli.getOrdenes()) {
                            sb.append("Orden: ").append(o.getCodigo()).append(" | Estado: ").append(o.getEstado())
                              .append(" | Total: $").append(o.calcularTotal()).append("\n");
                            for (DetalleServicio d : o.getDetalles()) {
                                sb.append("   -> Detalle: ").append(d.getCodigoDetalle()).append(" | ").append(d.getServicio().getNombre())
                                  .append(" | Estado: ").append(d.getEstado()).append(" | Costo: $").append(d.calcularCostoDetalle()).append("\n");
                            }
                        }
                        mostrarTextoEnVentana(sb.toString(), "Órdenes del Cliente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                }
                case 13: {
                    String codTec = JOptionPane.showInputDialog("Código del técnico:");
                    Tecnico t = gestor.buscarTecnico(codTec);
                    if (t != null) {
                        StringBuilder sb = new StringBuilder("=== TRABAJOS DEL TÉCNICO: " + t.getNombre() + " ===\n\n");
                        for (OrdenServicio o : gestor.getOrdenes()) {
                            for (DetalleServicio d : o.getDetalles()) {
                                if (d.getTecnico() != null && d.getTecnico().getCodigo().equals(codTec)) {
                                    sb.append("Orden: ").append(o.getCodigo()).append(" | Detalle: ").append(d.getCodigoDetalle())
                                      .append(" | Estado: ").append(d.getEstado()).append(" | Costo: $").append(d.calcularCostoDetalle()).append("\n");
                                }
                            }
                        }
                        mostrarTextoEnVentana(sb.toString(), "Trabajos por Técnico");
                    } else {
                        JOptionPane.showMessageDialog(null, "Técnico no encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                }
                case 14: {
                    StringBuilder sb = new StringBuilder("=== EQUIPOS CON INTERVENCIONES PENDIENTES ===\n\n");
                    for (EquipoTecnologico eq : gestor.getEquipos()) {
                        boolean pendiente = false;
                        for (OrdenServicio o : gestor.getOrdenes()) {
                            for (DetalleServicio d : o.getDetalles()) {
                                if (d.getEquipo().equals(eq) && d.getEstado() != EstadoIntervencion.FINALIZADO) {
                                    pendiente = true;
                                    break;
                                }
                            }
                            if (pendiente) break;
                        }
                        if (pendiente) {
                            sb.append(" - ").append(eq.toString()).append("\n");
                        }
                    }
                    mostrarTextoEnVentana(sb.toString(), "Equipos Pendientes");
                    break;
                }
                case 15: {
                    int cDiag = 0, cMant = 0, cRep = 0;
                    double fPort = 0, fEsc = 0, fTel = 0;

                    for (OrdenServicio o : gestor.getOrdenes()) {
                        if (o.getEstado() == EstadoOrden.FINALIZADA || o.getEstado() == EstadoOrden.ENTREGADA) {
                            for (DetalleServicio d : o.getDetalles()) {
                                if (d.getEstado() == EstadoIntervencion.FINALIZADO) {
                                    Servicio s = d.getServicio();
                                    double costo = d.calcularCostoDetalle();
                                    String cat = d.getEquipo().getCategoriaEquipo();

                                    if (s instanceof Diagnostico) cDiag++;
                                    else if (s instanceof MantenimientoPreventivo) cMant++;
                                    else if (s instanceof ReparacionCorrectiva) cRep++;

                                    if (cat.equals("PORTATIL")) fPort += costo;
                                    else if (cat.equals("ESCRITORIO")) fEsc += costo;
                                    else if (cat.equals("TELEFONO")) fTel += costo;
                                }
                            }
                        }
                    }

                    String reporte = "=== REPORTE OPERATIVO Y DE FACTURACIÓN ===\n\n"
                            + "Cantidad Diagnósticos finalizados: " + cDiag + "\n"
                            + "Cantidad Mantenimientos preventivos finalizados: " + cMant + "\n"
                            + "Cantidad Reparaciones correctivas finalizadas: " + cRep + "\n\n"
                            + "Total Facturado Portátiles: $" + fPort + "\n"
                            + "Total Facturado Computadores de Escritorio: $" + fEsc + "\n"
                            + "Total Facturado Teléfonos Inteligentes: $" + fTel;
                    mostrarTextoEnVentana(reporte, "Reporte de Servicios");
                    break;
                }
                case 16: {
                    StringBuilder sb = new StringBuilder("=== REPORTE DE RENDIMIENTO DE TÉCNICOS ===\n\n");
                    for (Tecnico t : gestor.getTecnicos()) {
                        int finalizados = 0;
                        double valorAtendido = 0.0;
                        for (OrdenServicio o : gestor.getOrdenes()) {
                            for (DetalleServicio d : o.getDetalles()) {
                                if (d.getTecnico() != null && d.getTecnico().getCodigo().equals(t.getCodigo())) {
                                    if (d.getEstado() == EstadoIntervencion.FINALIZADO) {
                                        finalizados++;
                                        valorAtendido += d.calcularCostoDetalle();
                                    }
                                }
                            }
                        }
                        sb.append("Técnico: ").append(t.getNombre()).append("\n")
                          .append(" - Intervenciones Finalizadas: ").append(finalizados).append("\n")
                          .append(" - Valor Total Atendido: $").append(valorAtendido).append("\n\n");
                    }
                    mostrarTextoEnVentana(sb.toString(), "Reporte de Técnicos");
                    break;
                }
                case 17: {
                    StringBuilder sb = new StringBuilder("=== INVENTARIO CRÍTICO (Stock < 5) ===\n\n");
                    boolean critico = false;
                    for (Repuesto r : gestor.getRepuestos()) {
                        if (r.getCantidadDisponible() < 5) {
                            sb.append("Código: ").append(r.getCodigo()).append(" | Nombre: ").append(r.getNombre())
                              .append(" | Stock: ").append(r.getCantidadDisponible()).append(" | P/U: $").append(r.getPrecioUnitario()).append("\n");
                            critico = true;
                        }
                    }
                    if (!critico) sb.append("No hay repuestos en nivel crítico.");
                    mostrarTextoEnVentana(sb.toString(), "Inventario Crítico");
                    break;
                }
                case 18:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema TechRepair. ¡Hasta luego!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcion != 18);
    }

    private static void mostrarTextoEnVentana(String texto, String titulo) {
        JTextArea textArea = new JTextArea(texto, 15, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void cargarDatosDemo(GestorTechRepair g) {
        g.registrarCliente("1094", "Carlos Pérez", "3112223344", "carlos@mail.com", "Calle 15 Armenia");
        g.registrarEquipo("1094", new ComputadorPortatil("EQ01", "SN100", "Lenovo", "ThinkPad", 2022, "Bueno", 15.6, 16, 512, true));
        g.registrarTecnico("T01", "Andrés Gómez", "12345", EspecialidadTecnico.COMPUTADORES, EstadoLaboral.ACTIVO);
        g.registrarRepuesto("R01", "Teclado Repuesto", "Teclado Lenovo", 45000.0, 4, "PORTATIL"); // Crítico (4 < 5)
        g.crearOrden("ORD01", "2026-10-19", "1094", "Revisión general");
        g.agregarDetalleOrden("ORD01", "DET01", "EQ01", "S01", "Falla de encendido");
    }
}