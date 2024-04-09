package justins.runit.user;

public record User(
                    Integer id,
                    String name,
                    String Username,
                    String email,
                    Address address,
                    String phone,
                    String website,
                    Company company
        ) {
}
