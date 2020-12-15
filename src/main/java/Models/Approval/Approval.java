package Models.Approval;

import Models.User.UserAccount;

public class Approval {
    final Integer ID;
    final UserAccount account;
    boolean actioned;

    public Approval(Integer ID, UserAccount account, boolean actioned) {
        this.ID = ID;
        this.account = account;
        this.actioned = actioned;
    }

    public Integer getID() {
        return ID;
    }

    public UserAccount getAccount() {
        return account;
    }

    public boolean isActioned() {
        return actioned;
    }

    public void setActioned(boolean actioned) {
        this.actioned = actioned;
    }
}
