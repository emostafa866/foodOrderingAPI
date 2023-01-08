package com.fawry.foodorderingapi.model;

import com.fawry.foodorderingapi.entity.*;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Data

public class GroupDTO {
    private Long id;
    @Size(min = 2, max = 400, message = "The Title of Gruop is not valid")
    private String title;

    private String anyOneCanJoinWithoutRequest;

    private String groupIsFinished;

    @NotNull(message = "You Shoude Choose Restaurant")
    private Restaurant restaurant;

    private List<UsersDto> Users;

    private List<UsersDto> UsersRequestToJoin;

    private List<Order> order;

    public GroupDTO(Long id, String title, String anyOneCanJoinWithoutRequest, String groupIsFinished,
            Restaurant restaurant, List<MyUser> users, List<MyUser> usersRequestToJoin, List<Order> order) {
        this.id = id;
        this.title = title;
        this.anyOneCanJoinWithoutRequest = anyOneCanJoinWithoutRequest;
        this.groupIsFinished = groupIsFinished;
        this.restaurant = restaurant;
        Users = new ArrayList<>();
        for (MyUser user : users) {
            UsersDto usersDto = new UsersDto(user.getId(),user.getName(), user.getPhone(), user.getPassword(), user.getEmail());
            this.Users.add(usersDto);
        }
        this.UsersRequestToJoin = new ArrayList<>();
        for (MyUser user : usersRequestToJoin) {
            UsersDto usersDto = new UsersDto(user.getId(),user.getName(), user.getPhone(), user.getPassword(), user.getEmail());
            this.UsersRequestToJoin.add(usersDto);
        }
        this.order = order;
    }

}
