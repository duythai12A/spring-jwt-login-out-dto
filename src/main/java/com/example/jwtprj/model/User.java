package com.example.jwtprj.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @NotBlank
        @Size(min = 3,max = 50)
        private String name;
        @NotBlank
        @Size(min = 3,max = 50)
        private String username;
        @NaturalId
        @NotBlank
        @Size(max = 50)
        @Email
        private String email;
        @JsonIgnore
        @NotBlank
        @Size(min = 6,max = 100)
        private String password;
        @Lob
        private String avatar;
        @ManyToMany(fetch = FetchType.EAGER)//lazy là không truyền ra ngoài
        @JoinTable(name = "user_role"/*join bảng trung gian user_role có 2 id của 2 bảng*/,
        joinColumns = @JoinColumn(name = "user_id")/*khóa ngoại chính*/,inverseJoinColumns = @JoinColumn(name = "role_id")/*khóa ngoại thứ 2*/)
        Set<Role> roles = new HashSet<>();

        public User( @NotBlank @Size(min = 3,max = 50)String name,
                     @NotBlank @Size(min = 3,max = 50)String username,
                     @NotBlank @Size(max = 50) @Email String email,
                     @NotBlank @Size(min = 6,max = 100) String encode){
                this.name=name;
                this.username=username;
                this.email=email;
                this.password=encode;
        }

}
