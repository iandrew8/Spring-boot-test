package com.springboot.systems.test.controllers.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    /**
     * @param data,       the data to be returned
     * @param message,    the message to be displayed
     * @param httpStatus, the http status code
     * @return ResponseEntity<Object>
     */
    public static ResponseEntity<Object> generateSuccessResponse(Object data, String message, HttpStatus httpStatus) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", httpStatus.value());
        responseMap.put("data", data);
        return new ResponseEntity<>(responseMap, httpStatus);
    }

    /**
     * @param dataKey,      the key to be used for the data
     * @param data,         the data to be returned
     * @param totalRecords, the total number of records
     * @param message,      the message to be displayed
     * @param httpStatus,   the http status code
     * @return ResponseEntity<Object>
     */
    public static ResponseEntity<Object> generateSuccessResponse(String dataKey, Object data, long totalRecords,
                                                                 String message, HttpStatus httpStatus) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("totalRecords", totalRecords);
        responseMap.put("message", message);
        responseMap.put("status", httpStatus.value());
        responseMap.put(dataKey, data);
        return new ResponseEntity<>(responseMap, httpStatus);
    }

    /**
     * @param dataKey,    the key to be used for the data
     * @param data,       the data to be returned
     * @param message,    the message to be displayed
     * @param httpStatus, the http status code
     * @return ResponseEntity<Object>
     */
    public static ResponseEntity<Object> generateSuccessResponse(String dataKey, Object data,
                                                                 String message, HttpStatus httpStatus) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", httpStatus.value());
        responseMap.put(dataKey, data);
        return new ResponseEntity<>(responseMap, httpStatus);
    }

    /**
     * @param data,       the data to be returned
     * @param httpStatus, the http status code
     * @return ResponseEntity<Object>
     */
    public static ResponseEntity<Object> generateErrorResponse(Object data, HttpStatus httpStatus) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", httpStatus.value());
        responseMap.put("error", data);
        return new ResponseEntity<>(responseMap, httpStatus);
    }

}
