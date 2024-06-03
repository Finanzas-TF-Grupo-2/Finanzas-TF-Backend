package upc.edu.Finanzas_TF.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_tasa", nullable = false)
    private String tipoTasa;

    @Column(name = "frecuencia_pago  ", nullable = false)
    private String frecuenciaPago;

    @Column(name = "capitalizacion")
    private String capitalizacion; // Si aplica

    @Column(name = "porcentaje_tasa", nullable = false)
    private double porcentajeTasa;

    @Column(name = "dia_pago", nullable = false)
    private int diaPago;

    @Column(name = "saldo", nullable = false)
    private double saldo;

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Persona persona;
}
