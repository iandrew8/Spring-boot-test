package com.springboot.systems.test.controllers.dtos;

import com.springboot.systems.test.models.enums.RecordStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseResponse {
    private String id;
    private Date dateCreated, dateChanged;
    private RecordStatus recordStatus;
}
