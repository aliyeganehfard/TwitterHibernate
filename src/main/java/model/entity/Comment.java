package model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Tweet tweet;


}

