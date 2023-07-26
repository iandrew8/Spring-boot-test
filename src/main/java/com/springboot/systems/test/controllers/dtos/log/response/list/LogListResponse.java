package com.springboot.systems.test.controllers.dtos.log.response.list;

import com.springboot.systems.test.controllers.dtos.log.response.LogResponse;
import com.springboot.systems.test.models.core.logs.Log;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LogListResponse {
    private List<LogResponse> records = new ArrayList<>();

    public LogListResponse(List<Log> logs) {
        logs.forEach(log ->
                this.records.add(new LogResponse(log)));
    }
}
