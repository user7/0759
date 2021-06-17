package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.function.Consumer;
import console.Connection;

public class Controller {
    private String name = null;
    private Connection connection = new Connection();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextField textField;

    public void initialize() {
        connect(Client.address);
    }

    public void connect(String addr) {
        connection.close();
        Consumer<String> callback = msg -> Platform.runLater(() -> {
            if (msg == null) {
                messageGui(MessageType.SYSTEM, "Сервер отключился");
                name = null;
            } else
                messageGui(msg.startsWith("[") ? MessageType.INCOMING : MessageType.SYSTEM, msg);
        });
        try {
            connection = new Connection(addr, callback);
        } catch (IOException e) {
            Client.logError("Ошибка при подключении: ", e);
            messageGui(MessageType.SYSTEM, "Ошибка при подключении: " + e);
        }
    }

    public void shutdown() {
        connection.close();
    }

    enum MessageType {
        SYSTEM,
        INCOMING,
        OUTGOING,
    }

    public void messageGui(MessageType type, String msg) {
        Text t = new Text();
        switch (type) {
            case SYSTEM:
                t.setStyle("-fx-fill: #FF0000");
                break;
            case INCOMING:
                t.setStyle("-fx-fill: #FFFF00");
                break;
            case OUTGOING:
                t.setStyle("-fx-fill: #00FF00");
                msg = "[" + name + "] " + msg;
                break;
            default:
                System.out.println("Неизвестный тип сообщения " + type);
        }
        t.setText(msg + "\n");
        textFlow.getChildren().add(t);
        scrollPane.setVvalue(1D);
    }

    public void onTextFieldEnter(KeyEvent keyEvent) {
        if (!keyEvent.getCode().equals(KeyCode.ENTER))
            return;
        if (textField.getText().equals(""))
            return;
        String line = textField.getText();
        textField.clear();
        String[] msgParts = line.split(" ");

        // обработка команды отключения /d
        if (msgParts[0].equals("/d")) {
            connection.close();
            return;
        }

        // обработка команды подключения /c [addr]
        if (msgParts[0].equals("/c")) {
            connect(msgParts.length > 1 ? msgParts[1] : "");
            return;
        }

        // попытка послать сообщение, когда нет подключения
        if (connection.isClosed()) {
            messageGui(MessageType.SYSTEM, "Нет подключения к серверу, используйте /c addr");
            return;
        }

        // Первый ответ серверу это наше имя
        if (name == null)
            name = line;
        else
            messageGui(MessageType.OUTGOING, line);
        connection.send(line);
    }

    // TODO destructor
}