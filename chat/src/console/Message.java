package console;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String message;
    private String sender; // присутствует в сообщениях с сервера, или в первом сообщении от клиента
    private ArrayList<String> users; // присутствует в сообщениях с сервера об обновлении списка пользователей

    public Message(String message, String sender, ArrayList<String> users) {
        this.message = message;
        this.sender = sender;
        this.users = users;
    }

    public Message(String message, String sender) {
        this(message, sender, new ArrayList<String>());
    }

    public Message(String message) {
        this(message, "");
    }

    public Message() {
        this("");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public boolean hasMessage() {
        return !message.isEmpty();
    }

    public boolean hasUsers() {
        return !users.isEmpty();
    }

    public boolean hasSender() {
        return !sender.isEmpty();
    }

    public boolean isDisconnect() {
        return !hasMessage() && !hasUsers() && !hasSender();
    }

    @Override
    public String toString() {
        return "<" + sender + ":" + message + "//" + String.join(",", users) + ">";
    }
}