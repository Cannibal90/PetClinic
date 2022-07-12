package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO {
    private long totalElements;
    private int totalPages;
    private int number;
    private List<Object> items;
}
