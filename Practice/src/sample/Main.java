package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import json.simple.parser;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();


    @Override
    public void start(final Stage primaryStage) throws Exception{
        Label label1 = new Label("Выберите файл JSON");
        final Button button1 = new Button("Обзор..");
        final Button button2 = new Button("Заполнить Excel");
        final TextArea textArea = new TextArea();
        textArea.setMinHeight(30);
        textArea.setMaxHeight(30);
        textArea.setMaxWidth(200);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            List<File> files;
            @Override
            public void handle(ActionEvent event) {
                textArea.clear();
                File file = fileChooser.showOpenDialog(primaryStage);
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Files", "*.*"),
                        new FileChooser.ExtensionFilter("JSON", "*.json"));
                if (file != null) {
                    openFile(file);
                    files = Arrays.asList(file);
                    printLog(textArea, files);
                }
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                File f1 = new File(files.getPath());
                f1 = MyExcel.CreateExcel();
                Object obj = new JSONParser().parse(jsonString);
                JSONObject jo = (JSONObject) obj;
                int i = 0;int j = 0;
                while(!jo.isEmpty()) {
                    String blockArr = (JSONArray) jo.get("Блок");
                    Iterator blockArrItr = blockArr.iterator();
                    while (blockArrItr.hasNext()){
                        f1 = MyExcel.writeIntoExcel(f1, i, j, blockArr);
                        jo.blockArrItr
                        i++;
                    }
                    String moduleArr = (JSONArray) jo.get("Модуль");
                    Iterator moduleArrItr = moduleArr.iterator();
                    while (moduleArrItr.hasNext()){
                        f1 = MyExcel.writeIntoExcel(f1, i, j, moduleArr);
                        j++;
                    }
                    JSONArray complectArr = (JSONArray) jo.get("Комплектование");
                    Iterator complectArrItr = complectArr.iterator();
                    while (complectArrItr.hasNext()){
                        f1 = MyExcel.writeIntoExcel(f1, i, j, complectArr);
                        j++;
                    }
                    JSONArray montazhArr = (JSONArray) jo.get("Монтаж");
                    Iterator montazhArrItr = montazhArr.iterator();
                    while (montazhArrItr.hasNext()){
                        f1 = MyExcel.writeIntoExcel(f1, i, j, montazhArr);
                        j++;
                    }
                    JSONArray settingsArr = (JSONArray) jo.get("Настройка");
                    Iterator settingsArrItr = settingsArr.iterator();
                    while (settingsArrItr.hasNext()){
                        f1 = MyExcel.writeIntoExcel(f1, i, j, settingsArr);
                        j++;
                    }
                    JSONArray gotovostArr = (JSONArray) jo.get("Готовность");
                    Iterator gotovostArrItr = gotovostArr.iterator();
                    while (gotovostArrItr.hasNext()){
                        f1 = MyExcel.writeIntoExcel(f1, i, j, gotovostArr);
                        j++;
                    }
                }
            }
        });
        Group group = new Group(button1);
        Group group2 = new Group(button2);
        FlowPane root = new FlowPane(label1, textArea, group, group2);
        Scene scene = new Scene(root, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("1C to Excel");
        primaryStage.show();

    }

    private void printLog(TextArea textArea, List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        for (File file : files) {
            textArea.appendText(file.getAbsolutePath() + "\n");
        }
    }

    private void openFile(File file) {
        try {
            this.desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
