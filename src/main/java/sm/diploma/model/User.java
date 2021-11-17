package sm.diploma.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    private Long userId;
    private String nickName;
    private final LocalDate dateRegister;
    private final String userLogin;
    private LocalDate dateOfBirth;
    private String about;
    private List<User> followers;
    private List<User> following;

    public User(Long userId, String userLogin, String nickName, LocalDate dateOfBirth, LocalDate dateRegister, String about) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.nickName = nickName;
        this.dateRegister = dateRegister;
        this.dateOfBirth = dateOfBirth;
        this.about = about;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();

    }

    public User(String userLogin, String nickName, LocalDate dateOfBirth, String about) {
        this.nickName = nickName;
        this.userLogin = userLogin;
        this.dateRegister = LocalDate.now();
        this.dateOfBirth = dateOfBirth;
        this.about = about;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();

    }

    public User(String userLogin,String nickName) {
        this.userLogin = userLogin;
        this.nickName = nickName;
        this.dateRegister = LocalDate.now();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();

    }

    public User(User other) {
        this.userId = other.userId;
        this.userLogin = other.userLogin;
        this.nickName = other.nickName;
        this.dateRegister = other.dateRegister;
        this.dateOfBirth = other.dateOfBirth;
        this.about = other.about;
        this.followers = other.followers;
        this.following = other.following;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LocalDate getDateRegister() {
        return dateRegister;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (nickName != null ? !nickName.equals(user.nickName) : user.nickName != null) return false;
        if (!dateRegister.equals(user.dateRegister)) return false;
        return dateOfBirth != null ? dateOfBirth.equals(user.dateOfBirth) : user.dateOfBirth == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + dateRegister.hashCode();
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", dateRegister=" + dateRegister +
                ", dateOfBirth=" + dateOfBirth +
                ", about='" + about + '\'' +
                '}';
    }
}
