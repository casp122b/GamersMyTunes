/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Music;
import DAL.SongOpener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * FXML Controller class
 *
 * @author Casper & Jens
 */
public class SongUIController implements Initializable {

    private Music music;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtFile;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnBrowse;
    @FXML
    private Button btnSave;
    
    @FXML
    private ComboBox<String> cmbCategory;
    ObservableList<String> list = FXCollections.observableArrayList("Pop","Rock","Dance","OST","Classic","Techno","Metal","Hip-hop");
    @FXML
    private AnchorPane root;
    @FXML
    private Text lblTitle;
    @FXML
    private Text lblArtist;
    @FXML
    private Text lblCategory;
    @FXML
    private Text lblTime;
    @FXML
    private Text lblFile;
    @FXML
    private Button btnAdd;
    private PlayerUIController main;
    private SongOpener so;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCategory.setItems(list);
    }
    
    @FXML
    public void btnBrowseActionPerformed(ActionEvent event) {
    
    FileChooser fileChooser = new FileChooser();
        Window win = root.getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);
        txtFile.setText(file.getPath());

        prepopulateFields(file);
        
    }

    @FXML
    public void btnCancelActionPerformed(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void btnSaveActionPerformed(ActionEvent event)
    {
    // Save to Music<>
        String title = txtTitle.getText().trim();
        String artist = txtArtist.getText().trim();
        String category = cmbCategory.getValue();
        String time = txtTime.getText().trim();
        String file = txtFile.getText().trim();
        if (!file.equals("") || file.endsWith(".mp3") || file.endsWith(".wav"))
        {
            if (!title.equals(""))
            {
                music = new Music(title, artist, category, time, file);
                main.addToSongs(music);
                
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }
            else
            {
                new Alert(Alert.AlertType.ERROR, "You must input a title for the song!").showAndWait();
            }
        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "You must load a valid music file!").showAndWait();
        }
    }
    
    @FXML
    public void btnAddCategoryActionPerformed(ActionEvent event)                                               
    { 
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add a new category");
        dialog.setHeaderText("Add category");
        dialog.setContentText("Enter new category:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            cmbCategory.getItems().add(result.get());
        }
    }    

    void setMainWindow(PlayerUIController aThis) {
        main = aThis;
    }
    
    private void prepopulateFields(File file)
    {

        try
        {
            so = new SongOpener(file.getPath());
            txtTitle.setText(so.getTitle());
            txtArtist.setText(so.getArtist());
            //txtCategory.setText(so.getCategory());
            txtTime.setText(so.getTime());
        }
        catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e)
        {
            Logger.getLogger(SongUIController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
