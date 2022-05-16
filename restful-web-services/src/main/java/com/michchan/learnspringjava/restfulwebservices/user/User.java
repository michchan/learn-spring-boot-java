package com.michchan.learnspringjava.restfulwebservices.user;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Schema(description = "All details about the user")
public class User {

    private Integer id;

    @Size(min=2, max=100, message = "User's name must be characters with length between 2 to 100")
    @Schema(description = "Name should have at least 2 characters")
    private String name;

    @Past
    @Schema(description = "Birthdate should be in the past")
    private Date birthdate;

    public User(Integer id, String name, Date birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

}
