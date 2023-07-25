package com.springboot.systems.system_wide.controllers.dtos;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
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
