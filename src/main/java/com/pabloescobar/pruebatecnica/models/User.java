package com.pabloescobar.pruebatecnica.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Phone> phones;

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public List<Phone> getPhones() {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    // metodos de auditoria, created, modified
    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modified = new Date();
    }
}