package model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String name;


    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL , mappedBy = "following")
    private Set<Account> followers;

    @JoinTable(name = "followers" ,
            joinColumns = { @JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Account> following;



    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", followers=" + followers.size() +
                ", following=" + following.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
