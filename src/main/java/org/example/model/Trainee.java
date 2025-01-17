package org.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Trainee implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date dateOfBirth;

    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "trainers_trainees",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private List<Trainer> trainerList = new ArrayList<>();

    @OneToMany(mappedBy = "trainee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Training> trainingList = new ArrayList<>();

    public String getUsername() {
        return this.getUser().getUsername();
    }

    public String getPassword() {
        return this.getUser().getPassword();
    }

    public void setUsername(String username) {
        this.getUser().setUsername(username);
    }

    public void setPassword(String password) {
        this.getUser().setPassword(password);
    }

    public void activateAccount() {
        this.getUser().setActive(true);
    }

    public void deactivateAccount() {
        this.getUser().setActive(false);
    }
}
