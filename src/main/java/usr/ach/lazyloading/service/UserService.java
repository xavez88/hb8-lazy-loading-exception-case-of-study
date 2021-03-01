package usr.ach.lazyloading.service;

import usr.ach.lazyloading.model.User;

import java.util.List;

public interface UserService {

    User getById(final Long userId);

    List<User> getAll();

}
