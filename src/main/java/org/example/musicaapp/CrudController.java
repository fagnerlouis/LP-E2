package org.example.musicaapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.musicaapp.dao.ArmaDAO;
import org.example.musicaapp.dao.CidadeDAO;
import org.example.musicaapp.dao.PersonagemDAO;

public class CrudController {

    // ARMA
    @FXML private TextField armaId, armaModelo, armaQtd, armaDono;
    @FXML private TableView<Arma> armaTable;
    @FXML private TableColumn<Arma, Long> armaColId;
    @FXML private TableColumn<Arma, String> armaColModelo, armaColDono;
    @FXML private TableColumn<Arma, Integer> armaColQtd;
    @FXML private TextArea armaMethodOut;

    // CIDADE
    @FXML private TextField cidadeId, cidadeNome, cidadeRegiao;
    @FXML private CheckBox cidadeViolenta;
    @FXML private TableView<Cidade> cidadeTable;
    @FXML private TableColumn<Cidade, Long> cidadeColId;
    @FXML private TableColumn<Cidade, String> cidadeColNome, cidadeColRegiao;
    @FXML private TableColumn<Cidade, Boolean> cidadeColViolenta;
    @FXML private TextArea cidadeMethodOut;

    // PERSONAGEM
    @FXML private TextField persId, persNome, persOrigem;
    @FXML private CheckBox persVivo;
    @FXML private TableView<Personagem> persTable;
    @FXML private TableColumn<Personagem, Long> persColId;
    @FXML private TableColumn<Personagem, String> persColNome, persColOrigem;
    @FXML private TableColumn<Personagem, Boolean> persColVivo;
    @FXML private TextArea persMethodOut;

    @FXML private Label status;

    private final ArmaDAO armaDAO = new ArmaDAO();
    private final CidadeDAO cidadeDAO = new CidadeDAO();
    private final PersonagemDAO personagemDAO = new PersonagemDAO();

    @FXML
    public void initialize() {
        // Bind colunas
        armaColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        armaColModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        armaColQtd.setCellValueFactory(new PropertyValueFactory<>("qtdTiros"));
        armaColDono.setCellValueFactory(new PropertyValueFactory<>("dono"));

        cidadeColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cidadeColNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cidadeColRegiao.setCellValueFactory(new PropertyValueFactory<>("regiao"));
        cidadeColViolenta.setCellValueFactory(new PropertyValueFactory<>("violenta"));

        persColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        persColNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        persColOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        persColVivo.setCellValueFactory(new PropertyValueFactory<>("vivo"));

        // Seleção de tabela preenche formulário
        armaTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                armaId.setText(Long.toString(n.getId()));
                armaModelo.setText(n.getModelo());
                armaQtd.setText(Integer.toString(n.getQtdTiros()));
                armaDono.setText(n.getDono());
            }
        });
        cidadeTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                cidadeId.setText(Long.toString(n.getId()));
                cidadeNome.setText(n.getNome());
                cidadeRegiao.setText(n.getRegiao());
                cidadeViolenta.setSelected(n.isViolenta());
            }
        });
        persTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                persId.setText(Long.toString(n.getId()));
                persNome.setText(n.getNome());
                persOrigem.setText(n.getOrigem());
                persVivo.setSelected(n.isVivo());
            }
        });

        onArmaRead(); onCidadeRead(); onPersRead();
    }

    /* ===== ARMA ===== */
    @FXML public void onArmaNew() {
        armaId.clear(); armaModelo.clear(); armaQtd.clear(); armaDono.clear(); armaMethodOut.clear();
        armaTable.getSelectionModel().clearSelection();
        status("Form Arma limpo.");
    }
    @FXML public void onArmaCreate() {
        try {
            int qtd = Integer.parseInt(armaQtd.getText().trim());
            long id = armaDAO.createReturningId(new Arma(armaModelo.getText().trim(), qtd, armaDono.getText().trim()));
            onArmaRead();
            selecionarLinhaPorId(armaTable, id);
            status("Arma criada (ID " + id + ").");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onArmaRead() {
        try { armaTable.setItems(FXCollections.observableArrayList(armaDAO.findAll())); status("Arma: lista atualizada."); }
        catch (Exception e) { error(e); }
    }
    @FXML public void onArmaUpdate() {
        try {
            long id = Long.parseLong(armaId.getText().trim());
            int qtd = Integer.parseInt(armaQtd.getText().trim());
            armaDAO.update(id, new Arma(armaModelo.getText().trim(), qtd, armaDono.getText().trim()));
            onArmaRead(); selecionarLinhaPorId(armaTable, id);
            status("Arma atualizada.");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onArmaDelete() {
        try {
            long id = Long.parseLong(armaId.getText().trim());
            armaDAO.delete(id);
            onArmaRead(); onArmaNew();
            status("Arma excluída.");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onArmaExecute() {
        try {
            Arma a = new Arma(armaModelo.getText().trim(),
                    Integer.parseInt(armaQtd.getText().trim()),
                    armaDono.getText().trim());
            String saida = executarMetodoArma(a);
            armaMethodOut.setText(saida);
        } catch (Exception e) { error(e); }
    }
    private String executarMetodoArma(Arma a) {
        return "Arma \"" + a.getModelo() + "\" de " + a.getDono() + " disparou " + a.getQtdTiros() + " tiros.";
    }

    /* ===== CIDADE ===== */
    @FXML public void onCidadeNew() {
        cidadeId.clear(); cidadeNome.clear(); cidadeRegiao.clear(); cidadeViolenta.setSelected(false); cidadeMethodOut.clear();
        cidadeTable.getSelectionModel().clearSelection();
        status("Form Cidade limpo.");
    }
    @FXML public void onCidadeCreate() {
        try {
            long id = cidadeDAO.createReturningId(new Cidade(cidadeNome.getText().trim(), cidadeRegiao.getText().trim(), cidadeViolenta.isSelected()));
            onCidadeRead(); selecionarLinhaPorId(cidadeTable, id);
            status("Cidade criada (ID " + id + ").");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onCidadeRead() {
        try { cidadeTable.setItems(FXCollections.observableArrayList(cidadeDAO.findAll())); status("Cidade: lista atualizada."); }
        catch (Exception e) { error(e); }
    }
    @FXML public void onCidadeUpdate() {
        try {
            long id = Long.parseLong(cidadeId.getText().trim());
            cidadeDAO.update(id, new Cidade(cidadeNome.getText().trim(), cidadeRegiao.getText().trim(), cidadeViolenta.isSelected()));
            onCidadeRead(); selecionarLinhaPorId(cidadeTable, id);
            status("Cidade atualizada.");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onCidadeDelete() {
        try {
            long id = Long.parseLong(cidadeId.getText().trim());
            cidadeDAO.delete(id);
            onCidadeRead(); onCidadeNew();
            status("Cidade excluída.");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onCidadeExecute() {
        try {
            Cidade c = new Cidade(cidadeNome.getText().trim(), cidadeRegiao.getText().trim(), cidadeViolenta.isSelected());
            String saida = executarMetodoCidade(c);
            cidadeMethodOut.setText(saida);
        } catch (Exception e) { error(e); }
    }
    private String executarMetodoCidade(Cidade c) {
        // EXEMPLO: se na E1 o método era "descricao()"
        // return c.descricao();
        return "Cidade " + c.getNome() + " (" + c.getRegiao() + ") - " + (c.isViolenta() ? "Violenta" : "Tranquila");
    }

    /* ===== PERSONAGEM ===== */
    @FXML public void onPersNew() {
        persId.clear(); persNome.clear(); persOrigem.clear(); persVivo.setSelected(false); persMethodOut.clear();
        persTable.getSelectionModel().clearSelection();
        status("Form Personagem limpo.");
    }
    @FXML public void onPersCreate() {
        try {
            long id = personagemDAO.createReturningId(new Personagem(persNome.getText().trim(), persOrigem.getText().trim(), persVivo.isSelected()));
            onPersRead(); selecionarLinhaPorId(persTable, id);
            status("Personagem criado (ID " + id + ").");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onPersRead() {
        try { persTable.setItems(FXCollections.observableArrayList(personagemDAO.findAll())); status("Personagem: lista atualizada."); }
        catch (Exception e) { error(e); }
    }
    @FXML public void onPersUpdate() {
        try {
            long id = Long.parseLong(persId.getText().trim());
            personagemDAO.update(id, new Personagem(persNome.getText().trim(), persOrigem.getText().trim(), persVivo.isSelected()));
            onPersRead(); selecionarLinhaPorId(persTable, id);
            status("Personagem atualizado.");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onPersDelete() {
        try {
            long id = Long.parseLong(persId.getText().trim());
            personagemDAO.delete(id);
            onPersRead(); onPersNew();
            status("Personagem excluído.");
        } catch (Exception e) { error(e); }
    }
    @FXML public void onPersExecute() {
        try {
            Personagem p = new Personagem(persNome.getText().trim(), persOrigem.getText().trim(), persVivo.isSelected());
            String saida = executarMetodoPersonagem(p);
            persMethodOut.setText(saida);
        } catch (Exception e) { error(e); }
    }
    private String executarMetodoPersonagem(Personagem p) {
        return (p.isVivo() ? "Vivo" : "Falecido") + " — " + p.getNome() + " de " + p.getOrigem();
    }

    /* helpers */
    private <T> void selecionarLinhaPorId(TableView<T> tv, long id) {
        if (tv == null || tv.getItems() == null) return;
        for (int i = 0; i < tv.getItems().size(); i++) {
            Object obj = tv.getItems().get(i);
            try {
                long value = (long) obj.getClass().getMethod("getId").invoke(obj);
                if (value == id) { tv.getSelectionModel().select(i); tv.scrollTo(i); break; }
            } catch (Exception ignored) {}
        }
    }
    private void status(String m){ status.setText(m); }
    private void error(Exception e){ e.printStackTrace(); new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait(); status("Erro: "+e.getMessage()); }
}
