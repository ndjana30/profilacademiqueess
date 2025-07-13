package com.ess.profiles.student.rest;

import com.ess.profiles.student.models.Category;
import com.ess.profiles.student.models.File;
import com.ess.profiles.student.repositories.CategoryRepository;
import com.ess.profiles.student.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("create")
    public Object createCategory(@RequestParam("name") String name,
                                 @RequestParam("classe") String classe,
                                 @RequestParam("year") String year) {
        Category c = new Category();
        c.setName(name);
        c.setYear(year);
        c.setClasse(classe);
        categoryRepository.save(c);
        return new ResponseEntity<>("Category created Successfully", HttpStatus.OK);
    }

    @PostMapping("file/{categoryId}/add")
    public Object addFile(@RequestParam("file") MultipartFile file, @PathVariable("categoryId") Long categoryId) {
        File f = new File();
        System.out.println("Created empty file object");
        try {
            f.setData(file.getBytes());
            System.out.println("Data Set");
            System.out.println("Data size is: " + file.getBytes().length / 1000 + "KB");
            f.setName(file.getOriginalFilename());
            System.out.println("Name Set");
            f.setExtension(file.getContentType());
            f.setCategory_id(categoryId);
            System.out.println("Extension set");
            fileRepository.save(f);
            return new ResponseEntity<>("File set and saved, and attributed a category", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}/files/all")
    public Object getFilesFromCategory(@PathVariable("id") Long id) {
        List<Category> category = categoryRepository.findAll().stream().filter(c -> c.getId() == id).collect(Collectors.toList());
        Category c = category.get(0);
        return new ResponseEntity<>(c.getFiles(), HttpStatus.OK);


    }
}

