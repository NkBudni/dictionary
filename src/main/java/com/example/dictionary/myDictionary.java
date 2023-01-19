package com.example.dictionary;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class myDictionary extends Application {

    int yLine=10;
    TextField wordTextField;

    Button searchButton;

    Label meaningLable;

    ListView<String> suggestWordList;

    DictionaryUsingHashMap dictionaryUsingHashMap;

    Pane createContent(){
        Pane root= new Pane();
        root.setPrefSize(300,300);

        wordTextField=new TextField();
        wordTextField.setPromptText("Please enter a word");
        wordTextField.setTranslateY(yLine);
        wordTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
//                System.out.println("Event Raised");
//                meaningLable.setText(wordTextField.getText());
                String word= wordTextField.getText();
                if(word.isBlank()==false && word.length()>=3){
                   // fetch suggetions
                   String[] suggestions= dictionaryUsingHashMap.getSuggestions(word);

                   // bind suggestions to the list
                    suggestWordList.getItems().clear();
                    suggestWordList.getItems().addAll(suggestions);
                }
            }
        });

        meaningLable = new Label("I am the meaning");
        meaningLable.setTranslateY(yLine+30);

        dictionaryUsingHashMap= new DictionaryUsingHashMap();

        searchButton = new Button("Search");
        wordTextField.setTranslateY(yLine);
        searchButton.setTranslateX(200);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String word= wordTextField.getText();
                if(word.isBlank()){
                    meaningLable.setText("Please enter a word!");
                    meaningLable.setTextFill(Color.RED);
                }
                else{
                    meaningLable.setText(dictionaryUsingHashMap.findMeaning(word));
                    meaningLable.setTextFill(Color.BLACK);
                }
            }
        });

         suggestWordList= new ListView<>();
        suggestWordList.setTranslateY(yLine+70);
        // Build logic to get this list from dictionary based on matching words types in search box
        String[] suggestedList={ "Amit","Anish","Nitin","Rahul"};
        suggestWordList.getItems().addAll(suggestedList);
        suggestWordList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String selectWord= suggestWordList.getSelectionModel().getSelectedItem();
                meaningLable.setText(selectWord);
            }
        });



        root.getChildren().addAll(wordTextField,searchButton,meaningLable,suggestWordList);
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("myDictionary!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}