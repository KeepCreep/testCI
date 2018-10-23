package fr.hitpart.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "restaurant")
    private Set<Command> commands = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "restaurant_meal",
               joinColumns = @JoinColumn(name = "restaurants_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "meals_id", referencedColumnName = "id"))
    private Set<Meal> meals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Restaurant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Command> getCommands() {
        return commands;
    }

    public Restaurant commands(Set<Command> commands) {
        this.commands = commands;
        return this;
    }

    public Restaurant addCommands(Command command) {
        this.commands.add(command);
        command.setRestaurant(this);
        return this;
    }

    public Restaurant removeCommands(Command command) {
        this.commands.remove(command);
        command.setRestaurant(null);
        return this;
    }

    public void setCommands(Set<Command> commands) {
        this.commands = commands;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public Restaurant meals(Set<Meal> meals) {
        this.meals = meals;
        return this;
    }

    public Restaurant addMeal(Meal meal) {
        this.meals.add(meal);
        meal.getRestaurants().add(this);
        return this;
    }

    public Restaurant removeMeal(Meal meal) {
        this.meals.remove(meal);
        meal.getRestaurants().remove(this);
        return this;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
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
        Restaurant restaurant = (Restaurant) o;
        if (restaurant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
