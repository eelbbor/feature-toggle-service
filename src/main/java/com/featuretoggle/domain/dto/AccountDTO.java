package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Account;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "")
public class AccountDTO extends IdentifiableDTO {
    private String name;

    //need the default constructor for response IO
    public AccountDTO() {
        this(null);
    }

    public AccountDTO(Account account) {
        super(account);
        if(account != null) {
            this.name = account.getName();
        }
    }

    @Override
    protected Class getTypeClass() {
        return Account.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
