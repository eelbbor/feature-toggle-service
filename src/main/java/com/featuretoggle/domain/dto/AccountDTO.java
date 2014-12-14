package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Account;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = AccountDTO.TYPE)
public class AccountDTO extends IdentifiableDTO {
    public static final String TYPE = "account";
    private String name;

    //need the default constructor for response IO
    public AccountDTO() {
        this(null);
    }

    public AccountDTO(Account account) {
        super(account);
        if (account != null) {
            this.name = account.getName();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
