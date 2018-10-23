package fr.hitpart.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Command.
 */
@Entity
@Table(name = "command")
public class Command implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("commands")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("commands")
    private Eater author;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Command creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public Command name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Command restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Eater getAuthor() {
        return author;
    }

    public Command author(Eater eater) {
        this.author = eater;
        return this;
    }

    public void setAuthor(Eater eater) {
        this.author = eater;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Command command = (Command) o;
        if (command.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), command.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Command{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
