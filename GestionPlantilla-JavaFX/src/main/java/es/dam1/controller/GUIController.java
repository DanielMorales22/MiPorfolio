package es.dam1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import es.dam1.data.CuerpoTecnicoDAO;
import es.dam1.data.JugadorDAO;
import es.dam1.model.CuerpoTecnico;
import es.dam1.model.Jugador;

public class GUIController {

    // --- JUGADORES ---
    @FXML private TableView<Jugador> tablaJugadores;
    @FXML private TableColumn<Jugador, Integer> colDorsal;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, String> colPosicion;
    @FXML private TableColumn<Jugador, String> colClub;
    @FXML private TableColumn<Jugador, Integer> colEdad;

    @FXML private TextField tfBuscar;
    @FXML private TextField tfDorsal;
    @FXML private TextField tfNombre;
    @FXML private ComboBox<String> cbPosicion;
    @FXML private TextField tfClub;
    @FXML private TextField tfEdad;

    // --- CUERPO TECNICO ---
    @FXML private TableView<CuerpoTecnico> tablaCuerpo;
    @FXML private TableColumn<CuerpoTecnico, String> colCNombre;
    @FXML private TableColumn<CuerpoTecnico, Integer> colCEdad;
    @FXML private TableColumn<CuerpoTecnico, String> colCFuncion;

    @FXML private TextField tfCNombre;
    @FXML private TextField tfCEdad;
    @FXML private TextField tfCFuncion;

    private JugadorDAO jugadorDAO = new JugadorDAO();
    private CuerpoTecnicoDAO cuerpoDAO = new CuerpoTecnicoDAO();

    @FXML
    public void initialize() {
        colDorsal.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        colCNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCFuncion.setCellValueFactory(new PropertyValueFactory<>("funcion"));

        cbPosicion.setItems(FXCollections.observableArrayList(
            "Portero", "Defensa", "Centrocampista", "Delantero"
        ));

        cargarJugadores();
        cargarCuerpo();

        tablaJugadores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tfDorsal.setText(String.valueOf(newVal.getDorsal()));
                tfNombre.setText(newVal.getNombre());
                cbPosicion.setValue(newVal.getPosicion());
                tfClub.setText(newVal.getClub());
                tfEdad.setText(String.valueOf(newVal.getEdad()));
            }
        });

        tablaCuerpo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tfCNombre.setText(newVal.getNombre());
                tfCEdad.setText(String.valueOf(newVal.getEdad()));
                tfCFuncion.setText(newVal.getFuncion());
            }
        });
    }

    @FXML
    public void cargarJugadores() {
        ObservableList<Jugador> lista = FXCollections.observableArrayList(jugadorDAO.listarJugadores());
        tablaJugadores.setItems(lista);
        limpiarCamposJugador();
    }

    @FXML
    public void buscarJugador() {
        String nombre = tfBuscar.getText().trim();
        if (nombre.isEmpty()) {
            cargarJugadores();
            return;
        }
        ObservableList<Jugador> lista = FXCollections.observableArrayList(jugadorDAO.buscarPorNombre(nombre));
        tablaJugadores.setItems(lista);
    }

    @FXML
    public void aniadirJugador() {
        if (!validarCamposJugador()) return;
        Jugador j = new Jugador(
            0,
            Integer.parseInt(tfDorsal.getText().trim()),
            tfNombre.getText().trim(),
            cbPosicion.getValue(),
            tfClub.getText().trim(),
            Integer.parseInt(tfEdad.getText().trim())
        );
        if (jugadorDAO.aniadirJugador(j)) {
            cargarJugadores();
        } else {
            mostrarError("Error al añadir el jugador.");
        }
    }

    @FXML
    public void editarJugador() {
        Jugador seleccionado = tablaJugadores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Selecciona un jugador de la tabla.");
            return;
        }
        if (!validarCamposJugador()) return;
        Jugador j = new Jugador(
            seleccionado.getId(),
            Integer.parseInt(tfDorsal.getText().trim()),
            tfNombre.getText().trim(),
            cbPosicion.getValue(),
            tfClub.getText().trim(),
            Integer.parseInt(tfEdad.getText().trim())
        );
        if (jugadorDAO.editarJugador(j)) {
            cargarJugadores();
        } else {
            mostrarError("Error al editar el jugador.");
        }
    }

    @FXML
    public void eliminarJugador() {
        Jugador seleccionado = tablaJugadores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Selecciona un jugador de la tabla.");
            return;
        }
        if (jugadorDAO.eliminarJugador(seleccionado.getId())) {
            cargarJugadores();
        } else {
            mostrarError("Error al eliminar el jugador.");
        }
    }

    @FXML
    public void cargarCuerpo() {
        ObservableList<CuerpoTecnico> lista = FXCollections.observableArrayList(cuerpoDAO.listarCuerpo());
        tablaCuerpo.setItems(lista);
        limpiarCamposCuerpo();
    }

    @FXML
    public void aniadirCuerpo() {
        if (!validarCamposCuerpo()) return;
        CuerpoTecnico c = new CuerpoTecnico(
            0,
            tfCNombre.getText().trim(),
            Integer.parseInt(tfCEdad.getText().trim()),
            tfCFuncion.getText().trim()
        );
        if (cuerpoDAO.aniadirCuerpo(c)) {
            cargarCuerpo();
        } else {
            mostrarError("Error al añadir el miembro.");
        }
    }

    @FXML
    public void editarCuerpo() {
        CuerpoTecnico seleccionado = tablaCuerpo.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Selecciona un miembro de la tabla.");
            return;
        }
        if (!validarCamposCuerpo()) return;
        CuerpoTecnico c = new CuerpoTecnico(
            seleccionado.getId(),
            tfCNombre.getText().trim(),
            Integer.parseInt(tfCEdad.getText().trim()),
            tfCFuncion.getText().trim()
        );
        if (cuerpoDAO.editarCuerpo(c)) {
            cargarCuerpo();
        } else {
            mostrarError("Error al editar el miembro.");
        }
    }

    @FXML
    public void eliminarCuerpo() {
        CuerpoTecnico seleccionado = tablaCuerpo.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Selecciona un miembro de la tabla.");
            return;
        }
        if (cuerpoDAO.eliminarCuerpo(seleccionado.getId())) {
            cargarCuerpo();
        } else {
            mostrarError("Error al eliminar el miembro.");
        }
    }

    private boolean validarCamposJugador() {
        if (tfDorsal.getText().trim().isEmpty() || tfNombre.getText().trim().isEmpty()
                || cbPosicion.getValue() == null || tfClub.getText().trim().isEmpty()
                || tfEdad.getText().trim().isEmpty()) {
            mostrarError("Rellena todos los campos.");
            return false;
        }
        try {
            Integer.parseInt(tfDorsal.getText().trim());
            Integer.parseInt(tfEdad.getText().trim());
        } catch (NumberFormatException e) {
            mostrarError("Dorsal y edad deben ser números.");
            return false;
        }
        return true;
    }

    private boolean validarCamposCuerpo() {
        if (tfCNombre.getText().trim().isEmpty() || tfCEdad.getText().trim().isEmpty()
                || tfCFuncion.getText().trim().isEmpty()) {
            mostrarError("Rellena todos los campos.");
            return false;
        }
        try {
            Integer.parseInt(tfCEdad.getText().trim());
        } catch (NumberFormatException e) {
            mostrarError("La edad debe ser un número.");
            return false;
        }
        return true;
    }

    private void limpiarCamposJugador() {
        tfDorsal.clear();
        tfNombre.clear();
        cbPosicion.setValue(null);
        tfClub.clear();
        tfEdad.clear();
    }

    private void limpiarCamposCuerpo() {
        tfCNombre.clear();
        tfCEdad.clear();
        tfCFuncion.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
