package com.ssafy.myfarm.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    private String saveFolder;
    private String originalFile;
    private String saveFile;
}
