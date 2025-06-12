// package com.vysh.projectmanager.model;

// import jakarta.persistence.*;
// import lombok.*;

// import java.util.List;

// @Entity
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String username;

//     @Column(unique = true)
//     private String email;

//     private String password;

//     private String role; // "ADMIN" or "USER"

//     @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL)
//     private List<Task> tasks;
// }
package com.vysh.projectmanager.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    // Either ROLE_USER or ROLE_ADMIN
    private String role;

    private boolean enabled = true;
   

}
