package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload implements Serializable {

    private List<CommitItem> commits;
}
