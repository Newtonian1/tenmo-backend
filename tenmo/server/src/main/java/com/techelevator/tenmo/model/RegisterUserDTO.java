package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterUserDTO {

    @NotEmpty
    @Size(min=4, message = "Username must be at least 4 characters long")
    @Size(max=20, message = "Username cannot be more than 20 characters long")
    private String username;

    @NotEmpty
    @NotBlank(message="Password cannot be blank")
    @Size(min=4, message = "Password must be at least 4 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
