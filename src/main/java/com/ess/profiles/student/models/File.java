package com.ess.profiles.student.models;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Table(name = "files")
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;
    private String extension;

    @ManyToOne
    @JoinColumn(name="category_id",insertable = false,updatable = false)
    private Category category;
    private long category_id;

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    @JsonBackReference(value = "products-category")
    public Category getCategory() {
        return category;
    }

    public File() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
