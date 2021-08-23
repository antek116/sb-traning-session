package com.sbbetting.training.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"email", "name", "lastName", "city"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("city")
    private final String city;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("lastName")
    private final String lastName;

    public UserDTO(String name,
                   String city,
                   String email,
                   String lastName) {
        this.name = name;
        this.city = city;
        this.email = email;
        this.lastName = lastName;
    }

    public static class Builder {
        private String name;
        private String city;
        private String email;
        private String lastName;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(name, city, email, lastName);
        }

    }
}
