package models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private String totalMoney;
    private LocalDateTime createAt;
    private LocalDateTime lastLogin;

    public User(int userId, String userName, String email, String password, String totalMoney) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.totalMoney = totalMoney;
    }

    public User(int userId, String userName, String email, String password, LocalDateTime createAt, LocalDateTime lastLogin, String totalMoney) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
        this.lastLogin = lastLogin;
        this.totalMoney = totalMoney;
    }

	public User(int id, String userName, String email, String password, LocalDateTime createAt,
			String totalMoney) {
        this.userId = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
        this.totalMoney = totalMoney;
	}


}
