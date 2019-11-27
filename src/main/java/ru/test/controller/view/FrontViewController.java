package ru.test.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.test.controller.HomeDirRest;
import ru.test.logic.HomeDirService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FrontViewController {

    @Autowired
    private HomeDirService homeDirService;

    @GetMapping("/index")
    public String index(Model model) {
        System.out.println("Index method call...");
        String message = "Choose availible link:";
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/fileList")
    public String fileList(Model model) throws IOException {
        model.addAttribute("files", this.homeDirService.getHomeDirContent());
        model.addAttribute("path", "/files/get/");
        return "fileList";
    }

    @GetMapping("/fileUpload")
    public String fileUpload(Model model) {
        return "fileUpload";
    }

    @PostMapping("/resultUpload")
    public String resultUpload(@RequestParam("files") MultipartFile[] files) {
        this.homeDirService.storeMultiFiles(files);
        System.out.println("Result Upload Rest Call");

        return "/resultUpload";
    }

}

