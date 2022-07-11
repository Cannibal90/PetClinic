package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private List<Object> items;
}
