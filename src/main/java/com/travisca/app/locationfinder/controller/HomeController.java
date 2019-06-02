package com.travisca.app.locationfinder.controller;

import com.travisca.app.locationfinder.exception.GenericError;
import com.travisca.app.locationfinder.exception.GenericException;
import com.travisca.app.locationfinder.service.IDataAggregatorService;
import com.travisca.app.locationfinder.service.ISearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class HomeController {
    //Gets the error catalogue which is loaded  on startup
    @Autowired
    private List<GenericError> errors;

    @Autowired
    private IDataAggregatorService dataAggregatorService;

    @GetMapping("/v1/location/search")
    public ResponseEntity<List<ISearchResponse>> requestHandler(@RequestParam("query") String city, @RequestParam("category") String category) {
        return ResponseEntity.ok(dataAggregatorService.aggregate(city, category));

    }

    @ExceptionHandler(GenericException.class)
    public void handleException(GenericException e, HttpServletResponse response) throws IOException {
        GenericError error = errors.parallelStream()
                .filter(err -> err.getErrorCode() == (e.getErrorCode()))
                .findFirst()
                .orElse(new GenericError(1000l, "Interval service issue."));
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getErrorCode() + ": " + error.getErrorMessage());
    }
}
