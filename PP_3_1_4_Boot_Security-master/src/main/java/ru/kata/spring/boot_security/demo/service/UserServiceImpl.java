package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDaoImpl roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDaoImpl roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        user.setRoles(getRealRoles(user.getRoles()));
        user.setUserPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        return true;
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    private List<Role> getRealRoles(List<Role> roleList) { //Возвращает список ролей с ID из БД
        List<Role> tempList = new ArrayList<>();
        if (roleList == null) {
            tempList.add(roleDao.getRoleByName("USER"));
            return tempList;
        } else {
            for (Role role : roleList) {
                if (role.getName().equals("ADMIN")) {
                    tempList.add(roleDao.getRoleByName("ADMIN"));
                }
                if (role.getName().equals("USER")) {
                    tempList.add(roleDao.getRoleByName("USER"));
                }
            }
        }
        return tempList;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        user.setRoles(getRealRoles(user.getRoles()));
        userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByPrincipalName(String principalName) {
        for (User user : userDao.getAllUsers()) {
            if (user.getUserName().equals(principalName)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.getUserDelailsFromUser();
    }
}
