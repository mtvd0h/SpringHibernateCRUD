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


    @Transactional
    public void save(User user) {
        Role defaultRole = new Role("ROLE_USER");
        defaultRole.setUser(user);
        user.setRoles(new ArrayList<>(Collections.singletonList(defaultRole)));
        entityManager.persist(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username){
        User user = (User) entityManager.createQuery("from User where username = :username").setParameter("username", username).getSingleResult();
        return Optional.ofNullable(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        User userToBeUpdated = entityManager.find(User.class, id);
        userToBeUpdated.setUsername(updatedUser.getUsername());
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
