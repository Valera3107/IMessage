package com.ua.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @NotBlank(message = "Please fill the text!")
  @Length(max = 255, message = "Text' too long (more then 255 chars)")
  private String text;

  @Column
  @NotBlank(message = "Please fill the tag!")
  @Length(max = 255, message = "Tag too long (more then 255 chars)")
  private String tag;

  @Lob
  private String file;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  public Message() {
  }

  public Message(String text, String tag, User user) {
    this.text = text;
    this.tag = tag;
    this.user = user;
  }

  public String getPeopleName(){
    return user != null ? user.getName() : "<none>";
  }

  public Long getUserId() {return user.getId(); }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Message)) return false;
    Message message1 = (Message) o;
    return getId() == message1.getId() &&
      Objects.equals(getText(), message1.getText()) &&
      Objects.equals(getTag(), message1.getTag());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(), getText(), getTag());
  }

}
