package labfx.cl.simplecalculatorjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HelloController {

    // --- MEMORIA INTERNA ---
    private double primerNumero = 0;
    private String operador = "";
    private boolean esNuevoNumero = true;

    // --- ELEMENTOS DE LA INTERFAZ ---
    @FXML
    private TextField txtDisplay;

    @FXML
    public void initialize() {
        txtDisplay.setText("0");
    }

    @FXML
    private void onIzquierdaClick() {
        txtDisplay.backward();
    }

    @FXML
    private void onDerechaClick() {
        txtDisplay.forward();
    }

    @FXML
    private void onNumeroClick(ActionEvent event) {
        Button botonPresionado = (Button) event.getSource();
        botonPresionado.setFocusTraversable(false); // Evita que el botón robe el foco

        if (esNuevoNumero) {
            txtDisplay.clear();
            esNuevoNumero = false;
        }

        txtDisplay.requestFocus(); // Mantiene el cursor activo en la pantalla
        txtDisplay.replaceSelection(botonPresionado.getText());
    }

    @FXML
    private void onLimpiarClick() {
        txtDisplay.setText("0");
        primerNumero = 0;
        operador = "";
        esNuevoNumero = true;
    }

    @FXML
    private void onOperacionClick(ActionEvent event) {
        if (!txtDisplay.getText().isEmpty()) {
            primerNumero = Double.parseDouble(txtDisplay.getText());
            Button botonPresionado = (Button) event.getSource();
            operador = botonPresionado.getText();
            esNuevoNumero = true;
        }
    }

    @FXML
    private void onIgualClick() {
        if (txtDisplay.getText().isEmpty() || operador.isEmpty()) {
            return;
        }

        double segundoNumero = Double.parseDouble(txtDisplay.getText());
        double resultado = 0;

        switch (operador) {
            case "+" -> resultado = primerNumero + segundoNumero;
            case "-" -> resultado = primerNumero - segundoNumero;
            case "*" -> resultado = primerNumero * segundoNumero;
            case "/" -> {
                if (segundoNumero != 0) {
                    resultado = primerNumero / segundoNumero;
                } else {
                    txtDisplay.setText("Error");
                    esNuevoNumero = true;
                    return;
                }
            }
        }

        // Si el resultado es exacto (ej. 5.0), lo muestra como 5
        if (resultado % 1 == 0) {
            txtDisplay.setText(String.valueOf((long) resultado));
        } else {
            txtDisplay.setText(String.valueOf(resultado));
        }

        esNuevoNumero = true;
        operador = "";
    }
}