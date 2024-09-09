package myapp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import myapp.model.Role;
import myapp.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<User> showUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
    @Transactional(readOnly = true)
    public User show(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable( (User) entityManager.find(User.class, username));
    }

    @Transactional
    public void save(User user) {
        Role defaultRole = new Role("ROLE_USER");
        defaultRole.setUser(user);
        user.setRoles(new ArrayList<>(Collections.singletonList(defaultRole)));
        entityManager.persist(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        User userToBeUpdated = entityManager.find(User.class, id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setAge(updatedUser.getAge());
        entityManager.merge(userToBeUpdated);
    }

    @Transactional
    public void delete(int id) {
        User userToBeDeleted = entityManager.find(User.class, id);

        if (userToBeDeleted != null) {
            entityManager.remove(userToBeDeleted);
        }
    }
}
