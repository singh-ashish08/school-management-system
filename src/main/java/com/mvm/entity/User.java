package com.mvm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="user_security")
public class User {
private int id;
@Id
@Column(name = "name")
private String userName;
private String password;
}
