package com.oficina.dto;

import java.util.List;

public class PageResponseDTO<T> {

    private List<T> content;
    private int totalPages;

    public PageResponseDTO(List<T> content, int totalPages) {
        this.content = content;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }
}