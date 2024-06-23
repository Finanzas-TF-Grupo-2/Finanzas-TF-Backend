package upc.edu.Finanzas_TF.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false, foreignKey = @ForeignKey(name = "FK_persona_id"))
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false, foreignKey = @ForeignKey(name = "FK_producto_id"))
    private Producto producto;

    @Column(name = "pago_en_cuotas")
    private boolean pagoEnCuotas;

    @Column(name = "numero_cuotas")
    private int numeroCuotas;

    @Column(name = "monto_cuota_final",  nullable = false)
    private double montoCuotaFinal;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "is_paid", nullable = false)
    private boolean paid = false;

    @Column(name = "monto_final", nullable = false)
    private double montoFinal;
}
