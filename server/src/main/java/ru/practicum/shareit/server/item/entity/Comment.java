package ru.practicum.shareit.server.item.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс модель, описывающий структуру сущности <b>Comment</b>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment", schema = "public")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Item item;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "created")
    private LocalDateTime created;
}
