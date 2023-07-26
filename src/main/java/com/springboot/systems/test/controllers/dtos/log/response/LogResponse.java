package com.springboot.systems.test.controllers.dtos.log.response;

import com.springboot.systems.test.controllers.dtos.BaseResponse;
import com.springboot.systems.test.models.core.logs.Log;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogResponse extends BaseResponse {
    private String log;

    public LogResponse(Log log) {
        this.setId(log.getId());
        this.log = log.getLog();
        this.setDateCreated(log.getDateCreated());
        this.setDateChanged(log.getDateChanged());
        this.setRecordStatus(log.getRecordStatus());
    }
}
