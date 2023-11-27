package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitItem implements Serializable {

    private String message;

    private Author author;
}
