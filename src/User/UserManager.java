package User;


import fileManager.FileManager;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String,User> users = new HashMap<>();
    private final FileManager fileManager;

    public UserManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.users = fileManager.loadUserList(); // 초기 로딩
    }

    private boolean isDuplicate(String id) { return users.containsKey(id); }

    public boolean loginUser(String id, String pw) {
        return users.get(id) != null && users.get(id).matchPw(pw);
    }

    public boolean registerUser(String id, String pw) {
        if (users.containsKey(id)) return false;
        users.put(id, new User(id, pw));
        return fileManager.saveUserList(users);
    }
}
