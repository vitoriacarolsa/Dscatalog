package com.devsuperior.dsCatagog.dto;

import com.devsuperior.dsCatagog.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO{
    private String password;

   public UserInsertDTO(){
        super();
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
