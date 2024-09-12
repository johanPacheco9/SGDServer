/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanp.Domain.Models;

/**
 *
 * @author johan
 */
import java.io.Serializable;

public class File implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private long size;
    private String path;
    private String tag;
    private byte[] content; 
    private int author_id;

    // Constructor
    public File(String name, long size, String path, String tag, int author_id) {
        this.name = name;
        this.size = size;
        this.path = path;
        this.tag = tag;
        this.author_id = author_id;
    }
    public File(String name)
    {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getAuthorId() {
        return author_id;
    }

    public void setAuthorId(int author_id) {
        this.author_id = author_id;
    }


    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

