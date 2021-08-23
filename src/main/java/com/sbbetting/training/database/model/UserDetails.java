package com.sbbetting.training.database.model;

public class UserDetails {

    private final String name;
    private final String lastName;
    private final String city;

    private UserDetails(String name,
                        String lastName,
                        String city) {
        this.name = name;
        this.lastName = lastName;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public static class Builder {
        private String name;
        private String lastName;
        private String city;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(name, lastName, city);
        }
    }
}
