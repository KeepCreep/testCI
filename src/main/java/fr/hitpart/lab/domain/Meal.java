package fr.hitpart.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Meal.
 */
@Entity
@Table(name = "meal")
public class Meal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "kcal")
    private Float kcal;

    @Column(name = "picture")
    private String picture;

    @ManyToMany(mappedBy = "meals")
    @JsonIgnore
    private Set<Restaurant> restaurants = new HashSet<>();

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

    public Meal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Meal description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getKcal() {
        return kcal;
    }

    public Meal kcal(Float kcal) {
        this.kcal = kcal;
        return this;
    }

    public void setKcal(Float kcal) {
        this.kcal = kcal;
    }

    public String getPicture() {
        return picture;
    }

    public Meal picture(String picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Meal restaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
        return this;
    }

    public Meal addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        restaurant.getMeals().add(this);
        return this;
    }

    public Meal removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
        restaurant.getMeals().remove(this);
        return this;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
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
        Meal meal = (Meal) o;
        if (meal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Meal{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", kcal=" + getKcal() +
            ", picture='" + getPicture() + "'" +
            "}";
    }
}
