package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("file")
@RestController
public class ImportController {

    @Autowired
    private ImportService importService;

    @RequestMapping(value="", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void importCsv(@RequestBody MultipartFile file) throws IOException {
        importService.importCsv(file.getBytes());
    }

}
