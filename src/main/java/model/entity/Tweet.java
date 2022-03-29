package model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private String title;
    @Column(length = 280)
    private String description;
    @Column
    private Long dl;

    @Column
    private Long l;

    @ManyToOne
    private Account account;

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", like=" + dl +
                ", dislike=" + l +
                ", account=" + account +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tweet tweet = (Tweet) o;
        return id != null && Objects.equals(id, tweet.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
