package org.choongang.myPage.controllers;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

@Data
public class RequestProfile {

    private String name;

    private String mobile;

    @Size(min=8)
    private String password;

    private String confirmPassword;

    private FileInfo profileImage;
}
