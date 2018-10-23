package fr.hitpart.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Shot.
 */
@Entity
@Table(name = "shot")
public class Shot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number_of_meal")
    private Integer numberOfMeal;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Eater eater;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Meal meal;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Command command;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfMeal() {
        return numberOfMeal;
    }

    public Shot numberOfMeal(Integer numberOfMeal) {
        this.numberOfMeal = numberOfMeal;
        return this;
    }

    public void setNumberOfMeal(Integer numberOfMeal) {
        this.numberOfMeal = numberOfMeal;
    }

    public Eater getEater() {
        return eater;
    }

    public Shot eater(Eater eater) {
        this.eater = eater;
        return this;
    }

    public void setEater(Eater eater) {
        this.eater = eater;
    }

    public Meal getMeal() {
        return meal;
    }

    public Shot meal(Meal meal) {
        this.meal = meal;
        return this;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Command getCommand() {
        return command;
    }

    public Shot command(Command command) {
        this.command = command;
        return this;
    }

    public void setCommand(Command command) {
        this.command = command;
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
        Shot shot = (Shot) o;
        if (shot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shot{" +
            "id=" + getId() +
            ", numberOfMeal=" + getNumberOfMeal() +
            "}";
    }
}
