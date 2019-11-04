package ru.test.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ru.test.controller.HomeDirRest;
import ru.test.logic.HomeDirService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.List;


@RestController
@RequestMapping(value = "/files")
public class HomeDirRestImpl implements HomeDirRest {

    @Autowired
    public HomeDirService homeDirService;

    @Override
    @RequestMapping(value = "/directory", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<String> getHomeDirContent() {
        return this.homeDirService.getHomeDirContent();
    }

    //headers = {"content-type=text/plain"}
    @Override
    @RequestMapping(value = "/get",consumes = {MediaType.TEXT_HTML_VALUE}, produces = {MediaType.TEXT_HTML_VALUE}, headers = "content-type=text/plain")
    public @ResponseBody List<String> getFileContent(@RequestParam("fileName") String fileName) {

        return this.homeDirService.getFileContent(fileName);
    }

    @Override
    @RequestMapping(value="/get/{filename:.+}", produces = "text/plain")
    public Resource getFileByName(@PathVariable String filename, HttpServletResponse responce) throws FileNotFoundException {
        File file= this.homeDirService.getFile(filename);
       // responce.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        responce.setHeader("Content-Disposition", "inline; filename=" + file.getName());
        responce.setHeader("Content-Length", String.valueOf(file.length()));
        //responce.setCharacterEncoding("UTF-8");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return resource/*new FileSystemResource(file)*/;
    }
}
