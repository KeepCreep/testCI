package fr.hitpart.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Eater.
 */
@Entity
@Table(name = "eater")
public class Eater implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    private Set<Command> commands = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Eater pseudo(String pseudo) {
        this.pseudo = pseudo;
        return this;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public Eater firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Eater lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Command> getCommands() {
        return commands;
    }

    public Eater commands(Set<Command> commands) {
        this.commands = commands;
        return this;
    }

    public Eater addCommands(Command command) {
        this.commands.add(command);
        command.setAuthor(this);
        return this;
    }

    public Eater removeCommands(Command command) {
        this.commands.remove(command);
        command.setAuthor(null);
        return this;
    }

    public void setCommands(Set<Command> commands) {
        this.commands = commands;
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
        Eater eater = (Eater) o;
        if (eater.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eater.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Eater{" +
            "id=" + getId() +
            ", pseudo='" + getPseudo() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
