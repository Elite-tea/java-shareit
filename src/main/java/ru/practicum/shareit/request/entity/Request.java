package ru.practicum.shareit.request.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.shareit.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request", schema = "public")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private User requestor;

    @Column(name = "created")
    private LocalDateTime created;

}
