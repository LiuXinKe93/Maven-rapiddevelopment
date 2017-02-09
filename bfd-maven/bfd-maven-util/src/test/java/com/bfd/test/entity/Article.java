package com.bfd.test.entity;

public class Article {
	
	private String content;  
    private String title;  
    private String url;  
  
    public Article() {  
    }  
  
    public Article(String content, String title, String url) {  
        super();  
        this.content = content;  
        this.title = title;  
        this.url = url;  
    }  
  
    public String getContent() {  
        return content;  
    }  
  
    public void setContent(String content) {  
        this.content = content;  
    }  
  
    public String getTitle() {  
        return title;  
    }  
  
    public void setTitle(String title) {  
        this.title = title;  
    }  
  
    public String getUrl() {  
        return url;  
    }  
  
    public void setUrl(String url) {  
        this.url = url;  
    }  
}
