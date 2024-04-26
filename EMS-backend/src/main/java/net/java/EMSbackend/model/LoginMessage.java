package net.java.EMSbackend.model;

public class LoginMessage {

    boolean status;
    String message;
    String email;
    String role;

    public LoginMessage() {
    }

    public LoginMessage(boolean status, String message, String email, String role) {

        this.status = status;
        this.message = message;
        this.email = email;
        this.role = role;

    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
