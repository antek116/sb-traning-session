package com.sbbetting.training.service;

import com.sbbetting.training.database.model.User;
import com.sbbetting.training.database.model.UserDetails;
import com.sbbetting.training.model.dto.response.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDTO getUserData(User user) {
        final UserDetails userDetails = user.getUserDetails();
        return new UserDTO.Builder()
                .withCity(userDetails.getCity())
                .withEmail(user.getEmail())
                .withName(userDetails.getName())
                .withLastName(userDetails.getLastName())
                .build();
    }
}
