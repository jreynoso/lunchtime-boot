package com.dispassionproject.lunchtime.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.net.URL;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class LunchOption {

    private String id;
    private String name;
    private URL imageUrl;
    private String address;
    private Float rating;

}
