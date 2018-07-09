package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private List<User> repository = new ArrayList<>();
    private User user = new User(1, "Al", "al@mail.ru", "1234", Role.ROLE_USER);

    public InMemoryUserRepositoryImpl() {
        repository.add(user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if (repository.stream().anyMatch(x -> x.getId().equals(id))) {
            User user = repository.stream().filter(x -> x.getId().equals(id)).findFirst().get();
            repository.remove(user);
            return true;
        }
        throw new NotFoundException(String.format("User with id = %s does not exist", id));
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        repository.add(user);
        return repository.stream().filter(x -> x.getId().equals(user.getId())).findFirst().get();
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        if (repository.stream().anyMatch(x -> x.getId().equals(id))) {
            return repository.stream().filter(x -> x.getId().equals(id)).findFirst().get();
        }
        throw new NotFoundException(String.format("User with id = %s does not exist", id));
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.stream().sorted(Comparator.comparing(AbstractNamedEntity::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        if (repository.stream().anyMatch(x -> x.getEmail().equals(email))) {
            return repository.stream().filter(x -> x.getEmail().equals(email)).findFirst().get();
        }
        throw new NotFoundException(String.format("User with Email = %s does not exist", email));
    }
}
