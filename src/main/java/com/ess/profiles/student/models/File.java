package com.ess.profiles.student.models;


import javax.persistence.*;

@Table(name = "files")
@Entity
public class File {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;
    private String extension;

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
