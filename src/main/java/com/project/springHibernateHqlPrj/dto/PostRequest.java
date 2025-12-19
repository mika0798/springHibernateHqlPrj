package com.project.springHibernateHqlPrj.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String content;
    private int userId;
}
