package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.api.request.UserCreationRequest;
import com.example.demo.api.request.UserUpdateRequest;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(UserCreationRequest userCreationRequest){
        return userRepository.save(mapToUser(userCreationRequest));
    }

    private User mapToUser(UserCreationRequest createRequest){
        User user = new User();
        user.setNombre(createRequest.nombre());
        user.setContrasena(createRequest.contrasena());
        user.setEdad(createRequest.edad());
        user.setAdministrador(createRequest.administrador());
        return user;
    }

    public void removeUser(Long id){
        userRepository.deleteById(id);
    }

    public Optional<User> getUser(final Long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, UserUpdateRequest updateRequest) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setNombre(updateRequest.nombre());
            user.setContrasena(updateRequest.contrasena());
            user.setEdad(updateRequest.edad());
            user.setAdministrador(updateRequest.administrador());
            return userRepository.save(user);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + id);
    }
}
